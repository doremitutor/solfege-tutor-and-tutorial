package com.doremitutor.support;

import java.awt.Color;
import java.awt.Font;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MidiEngine{
	private MidiEngine(){assert false:"Monostate case";}
	
	public static int[] midiNote={ 36, 38, 40, 41, 43, 45, 47,		//C2-B2 0-6
									48, 50, 52, 53, 55, 57, 59,		//C3-B3 7-13
									60, 62, 64, 65, 67, 69, 71, 	//C4-B4 14-20
									72, 74, 76, 77, 79, 81, 83, 	//C5-B5 21-27
									84, 86, 88, 89, 91, 93, 95,		//C6-B6 28-34
									96};
	public static Synthesizer synth=null;
	public static Sequencer seq=null;
	public static MidiChannel singChannel=null;
	public static MidiChannel metroChannel=null;
	private static boolean synthTaken=false;
	private static boolean seqTaken=false;
	
	public static boolean reserveSynth(){
		if(synthTaken){
			return false;
		}
		String synthName="Gervill";
		for(MidiDevice.Info info:MidiSystem.getMidiDeviceInfo()){
			if(info.toString().equals(synthName)){
				MidiDevice device=null;
				try {
					device=MidiSystem.getMidiDevice(info);
					if(device instanceof Synthesizer){
						synth=(Synthesizer)device;
						synth.open();
						MidiChannel[] channels=synth.getChannels();
						singChannel=channels[0];
						MidiEngine.singChannel.controlChange(10, 64);
						metroChannel=channels[9];
						metroChannel.controlChange(10, 14);
					}
				} catch (MidiUnavailableException e) {
					return false;
				}
				synthTaken=true;
				break;
			}
		}
		return synth!=null;
	}
	public static boolean reserveSeq(){
		if(synth==null||!synth.isOpen()||seqTaken){
			return false;
		}
		String seqName="Real Time Sequencer";
		for(MidiDevice.Info info:MidiSystem.getMidiDeviceInfo()){
			if(info.toString().equals(seqName)){
				try {
					MidiDevice device=MidiSystem.getMidiDevice(info);
					if(device instanceof Sequencer){
						seq=(Sequencer)device;
						seq.open();
						seq.getTransmitter().setReceiver(synth.getReceiver());
					}
				} catch (MidiUnavailableException e) {
					return false;
				}
				seqTaken=true;
				break;
			}			
		}
		return seq!=null;
	}
	public static void releaseResources(){		
		if(seq!=null&&seq.isOpen()){
			if(seq.isRunning()){
				seq.stop();
			}			
			seq.close();
		}
		if(synth!=null&&synth.isOpen()){
			MidiEngine.synth.close();
		}
	}
	public static JPanel getNoAudioSign(){// should it be able to accept a dimension parameter??
		return new JPanel(){
			{
				BoxLayout layout=new BoxLayout(this, BoxLayout.PAGE_AXIS);
				setLayout(layout);
				setFocusable(false);
				setOpaque(false);
				JLabel label=new JLabel("No se encontraron recursos de audio");
				label.setOpaque(true);
				label.setForeground(Color.RED);
				label.setBackground(Color.CYAN);
				label.setAlignmentX(0.5f);
				label.setFont(new Font("Serif", Font.BOLD, 30));
				add(Box.createVerticalGlue());
				add(label);
				add(Box.createVerticalGlue());
			}
		};
	}
}