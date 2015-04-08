package com.doremitutor.time;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.doremitutor.support.MidiEngine;
import com.doremitutor.support.Texts;
import com.doremitutor.support.Texts.Lang;
import com.doremitutor.support.Texts.Text;

class MixedPulsesPanel extends JPanel implements ActionListener, MouseListener, Runnable {
	static Lang LANG;
	private Color color;
	private JLabel label;
	private JSlider slider;
	private JButton button;
	private Timer timer;
	private Thread clickerThread;
	private ClickerDriver clickerDriver;
	private boolean clickDispatched;
	private int noteNumber;
	static private MixedPulsesPanel instance=null;
	
	static MixedPulsesPanel getInstance(boolean isJFrame){
		assert instance==null:"Singleton case";
		return instance=new MixedPulsesPanel(isJFrame);
	}
	private MixedPulsesPanel(boolean isJFrame){			
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));	
		if(!MidiEngine.reserveSynth()||MidiEngine.metroChannel==null){
			add(Box.createVerticalGlue());
			add(MidiEngine.getNoAudioSign());
			add(Box.createVerticalGlue());
			return;
		}		
		clickDispatched = false;
		color = new Color(0xff, 0xcc, 0x99);
		label = new JLabel("Pulsos por minuto");
		label.setAlignmentX(0.5f);
		slider = new JSlider(SwingConstants.HORIZONTAL, 60, 180, 120);
		slider.setFocusable(false);
		slider.setAlignmentX(0.5f);
		slider.setBackground(color);
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(10);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		slider.setToolTipText("Seleccione rapidez");
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ce) {
				timer.setDelay(setPeriod());
			}
		});
		button = new JButton("Pulsos");
		button.setFocusable(false);
		button.setToolTipText("Mantenga oprimido para escuchar los pulsos");
		button.addMouseListener(this);
		button.setAlignmentX(0.5f);
		timer = new Timer(setPeriod(), this);
		timer.setInitialDelay(0);
		createClicker();
		Box sliderBox = Box.createVerticalBox();
		sliderBox.setFocusable(false);
		sliderBox.add(label);
		sliderBox.add(Box.createVerticalGlue());
		sliderBox.add(slider);
		sliderBox.add(Box.createVerticalGlue());
		sliderBox.add(new JLabel(Texts.getText(Text.KEEP_THE_BUTTON_DOWN_TO_LISTEN, LANG)){
			{
				setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
				setAlignmentX(0.5f);
			}
		});
		sliderBox.add(Box.createVerticalGlue());
		add(sliderBox);
		add(button);
		add(Box.createVerticalGlue());
		if(isJFrame){
			//do something
		}
		

		
		
	}
	private int setPeriod() {
		return 60000 / slider.getValue();
	}
	private void pickNote(){
		noteNumber=(int)(Math.random()*(88-27))+27;
	}
	public void actionPerformed(ActionEvent ae) {
		clickerDriver.click();
	}
	public void mousePressed(MouseEvent me) {
		timer.start();
	}
	public void mouseReleased(MouseEvent me) {
		timer.stop();
	}
	public void mouseEntered(MouseEvent me) {
	}
	public void mouseExited(MouseEvent me) {
		timer.stop();
	}
	public void mouseClicked(MouseEvent me) {
	}
	private void createClicker() {
		clickerDriver = new ClickerDriver();
		clickerThread = new Thread(this);
		clickerThread.setDaemon(true);
		clickerThread.start();
	}
	public void run() {
		while (true) {
			clickerDriver.armClick();
		}
	}
	class ClickerDriver {
		synchronized void armClick() {
			while (!clickDispatched) {
				try {
					wait();
				} catch (InterruptedException e) {
					return;
				}
			}
			clickDispatched = false;
			pickNote();
			MidiEngine.metroChannel.noteOn(noteNumber, 127);
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			MidiEngine.metroChannel.noteOn(noteNumber, 0);
		}
		synchronized void click() {
			clickDispatched = true;
			notifyAll();
		}
	}
}