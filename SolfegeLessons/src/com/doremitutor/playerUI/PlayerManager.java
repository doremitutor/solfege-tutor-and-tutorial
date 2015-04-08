package com.doremitutor.playerUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.doremitutor.midi.MidiProxy;
import com.doremitutor.scoreDisplay.Figure;
import com.doremitutor.scoreDisplay.Score;
import com.doremitutor.scoreDisplay.Figure.Note;
import com.doremitutor.support.MidiEngine;

public class PlayerManager implements ActionListener, ItemListener, ChangeListener{
	static final String loop="loop";
	static final String sing="sing";
	static boolean repeat=false;	
	static boolean playPrecount=true;
	public static boolean enlightBars=true;
	
	public static PlayerManager THE=null;
	static{
		THE=new PlayerManager();
	}
	private PlayerManager(){
		assert THE==null:"Singleton case";
	}
	
	public void itemStateChanged(ItemEvent e){
		if(e.getSource()instanceof JCheckBox){
			JCheckBox item=(JCheckBox)e.getSource();
			if(item.getName().equals(TransportPane.precount)){
				playPrecount=item.isSelected();
			}
			if(item.getName().equals(TransportPane.noteNames)){
				for(Figure figure:Score.readFigures()){
					if(figure instanceof Note){
						((Note)figure).showName(item.isSelected());
					}
				}
			}
			if(item.getName().equals(TransportPane.barLight)){
				enlightBars=item.isSelected();
			}
		}
	}
	public void stateChanged(ChangeEvent e){
		if(e.getSource()instanceof JSlider){
			JSlider slider=(JSlider)e.getSource();
			if(!slider.getValueIsAdjusting()){
				final String name=slider.getName();
				final int value=slider.getValue();
				if(name.equals(TransportPane.tempo)){
					int tempo=slider.getValue();
					MidiProxy.THE.setTempo(tempo);
					TempoLabel.setTempoText(tempo);
					return;
				}
				if(name.equals(TransportPane.volume)){
					
					MidiEngine.metroChannel.controlChange(7, value);
					MidiEngine.singChannel.controlChange(7, value);
					return;
				}
				if(name.equals(PlayerManager.sing)){
					return;
				}
			}
		}
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource()instanceof JRadioButton){
			int program=instrumentMap.get(e.getActionCommand());
			if(MidiEngine.singChannel.getProgram()!=program){
				MidiEngine.singChannel.programChange(0, program);
			}
			return;
		}
		String cmd=e.getActionCommand();
		if(cmd.equals(TransportPane.startStop)){
			if(((JToggleButton)e.getSource()).isSelected()){
				MidiProxy.THE.play(playPrecount);
			}else{
				MidiProxy.THE.pause();
			}
			return;
		}
		if(cmd.equals(TransportPane.rewind)){
			MidiProxy.THE.rw();
			return;
		}
		if(cmd.equals(TransportPane.tuneUp)){
			MidiProxy.THE.tuneUp();
			return;
		}
	}
	private Map<String, Integer> instrumentMap=new HashMap<String, Integer>(){
		{
			put(TransportPane.flute, 73);
			put(TransportPane.clarinet, 71);
			put(TransportPane.violin, 40);
			put(TransportPane.organ, 19);
		}
	};
}