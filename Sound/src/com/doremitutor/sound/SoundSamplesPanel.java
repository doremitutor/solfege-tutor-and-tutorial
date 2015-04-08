package com.doremitutor.sound;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.doremitutor.support.Texts;
import com.doremitutor.support.Texts.Lang;
import com.doremitutor.support.Texts.Text;

public class SoundSamplesPanel extends JPanel{
	static Lang LANG;
	private URL file100=null;
	private URL file200=null;
	private URL file400=null;
	private URL file800=null;
	private Clip clip100=null;
	private Clip clip200=null;
	private Clip clip400=null;
	private Clip clip800=null;
	private AudioFormat af100=null;
	private AudioFormat af200=null;
	private AudioFormat af400=null;
	private AudioFormat af800=null;
	private AudioInputStream ais100=null;
	private AudioInputStream ais200=null;
	private AudioInputStream ais400=null;
	private AudioInputStream ais800=null;
	private JButton button100;
	private JButton button200;
	private JButton button400;
	private JButton button800;
	static private SoundSamplesPanel instance=null;
	
	static SoundSamplesPanel getInstance(){
		assert instance==null:"Singleton case";
		return instance=new SoundSamplesPanel();
	}
	
	private SoundSamplesPanel(){
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		button100=new JButton("100 Hz");
		button200=new JButton("200 Hz");
		button400=new JButton("400 Hz");
		button800=new JButton("800 Hz");
		button100.setFocusable(false);
		button200.setFocusable(false);
		button400.setFocusable(false);
		button800.setFocusable(false);
		Box upBox=Box.createHorizontalBox();
		upBox.add(Box.createHorizontalGlue());
		upBox.add(button100);
		upBox.add(Box.createHorizontalGlue());
		upBox.add(button200);
		upBox.add(Box.createHorizontalGlue());
		upBox.add(button400);
		upBox.add(Box.createHorizontalGlue());
		upBox.add(button800);
		upBox.add(Box.createHorizontalGlue());
		upBox.setAlignmentX(0.5f);
		add(Box.createVerticalGlue());
		add(upBox);
		add(Box.createVerticalGlue());
		add(new JLabel(Texts.getText(Text.KEEP_ONE_BUTTON_DOWN_TO_LISTEN, LANG)){
			{
				setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
				setAlignmentX(0.5f);
			}
		});
		add(Box.createVerticalGlue());
		try {
			setUpButtons();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	private void setUpButtons() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		file100=getClass().getResource("clips/file100.wav");
		ais100=AudioSystem.getAudioInputStream(file100);
		af100=AudioSystem.getAudioFileFormat(file100).getFormat();
		DataLine.Info info100=new DataLine.Info(Clip.class, af100);
		clip100=(Clip)AudioSystem.getLine(info100);
		clip100.open(ais100);
		button100.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me){
				clip100.setFramePosition(0);
				clip100.loop(Clip.LOOP_CONTINUOUSLY);
			}
			public void mouseReleased(MouseEvent me){
				clip100.stop();
			}
			public void mouseExited(MouseEvent me){
				clip100.stop();
			}
		});
		button100.setEnabled(true);		
		file200=getClass().getResource("clips/file200.wav");
		ais200=AudioSystem.getAudioInputStream(file200);
		af200=AudioSystem.getAudioFileFormat(file200).getFormat();
		DataLine.Info info200=new DataLine.Info(Clip.class, af200);
		clip200=(Clip)AudioSystem.getLine(info200);
		clip200.open(ais200);
		button200.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me){
				clip200.setFramePosition(0);
				clip200.loop(Clip.LOOP_CONTINUOUSLY);
			}
			public void mouseReleased(MouseEvent me){
				clip200.stop();
			}
			public void mouseExited(MouseEvent me){
				clip200.stop();
			}
		});
		button200.setEnabled(true);		
		file400=getClass().getResource("clips/file400.wav");
		ais400=AudioSystem.getAudioInputStream(file400);
		af400=AudioSystem.getAudioFileFormat(file400).getFormat();
		DataLine.Info info400=new DataLine.Info(Clip.class, af400);
		clip400=(Clip)AudioSystem.getLine(info400);
		clip400.open(ais400);
		button400.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me){
				clip400.setFramePosition(0);
				clip400.loop(Clip.LOOP_CONTINUOUSLY);
			}
			public void mouseReleased(MouseEvent me){
				clip400.stop();
			}
			public void mouseExited(MouseEvent me){
				clip400.stop();
			}
		});
		button400.setEnabled(true);		
		file800=getClass().getResource("clips/file800.wav");
		ais800=AudioSystem.getAudioInputStream(file800);
		af800=AudioSystem.getAudioFileFormat(file800).getFormat();
		DataLine.Info info800=new DataLine.Info(Clip.class, af800);
		clip800=(Clip)AudioSystem.getLine(info800);
		clip800.open(ais800);
		button800.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent me){
				clip800.setFramePosition(0);
				clip800.loop(Clip.LOOP_CONTINUOUSLY);
			}
			public void mouseReleased(MouseEvent me){
				clip800.stop();
			}
			public void mouseExited(MouseEvent me){
				clip800.stop();
			}
		});
		button800.setEnabled(true);		
	}
}