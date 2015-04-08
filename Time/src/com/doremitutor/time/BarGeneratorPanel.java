package com.doremitutor.time;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.doremitutor.support.MidiEngine;
import com.doremitutor.support.Texts;
import com.doremitutor.support.Texts.Lang;
import com.doremitutor.support.Texts.Text;

public class BarGeneratorPanel extends JPanel implements ActionListener, MouseListener, ItemListener, Runnable{
	static Lang LANG;
	private JLabel sliderLabel;
	private JLabel radButtLabel;
	private JSlider slider;
	private JButton button;
	private JRadioButton radButt2;
	private JRadioButton radButt3;
	private JRadioButton radButt4;
	private ButtonGroup radButtongroup;
	private Dimension radButtDim;
	private Timer timer;
	private Thread clickerThread;
	private ClickerDriver clickerDriver;
	private boolean clickDispatched;
	private int beatCount;
	private int maxCount = 1;
	private int length = 150;
	private int velocity = 127;
	private int noteNumber = 76;	
	static private BarGeneratorPanel instance=null;
	
	static BarGeneratorPanel getInstance(boolean isJFrame){
		assert instance==null:"Singleton case";
		return instance=new BarGeneratorPanel(isJFrame);
	}
	private BarGeneratorPanel(boolean isJFrame){		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));	
		if(!MidiEngine.reserveSynth()||MidiEngine.metroChannel==null){
			add(Box.createHorizontalGlue());
			add(MidiEngine.getNoAudioSign());
			add(Box.createHorizontalGlue());
			return;
		}
		clickDispatched = false;
		sliderLabel = new JLabel(Texts.getText(Text.PULSES_PER_MINUTE, LANG));
		sliderLabel.setAlignmentX(0.5f);
		radButtLabel = new JLabel(Texts.getText(Text.PULSES_PER_BAR, LANG));
		radButtLabel.setAlignmentX(0.5f);
		slider = new JSlider(SwingConstants.HORIZONTAL, 60, 180, 120);
		slider.setFocusable(false);
		slider.setAlignmentX(0.5f);
		slider.setOpaque(false);
		slider.setMajorTickSpacing(20);
		slider.setMinorTickSpacing(10);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setSnapToTicks(true);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent ce) {
				timer.setDelay(setPeriod());
			}
		});
		button = new JButton(Texts.getText(Text.PULSES, LANG));
		button.setFocusable(false);
		button.setAlignmentX(0.5f);
		button.setToolTipText(Texts.getText(Text.KEEP_THE_BUTTON_DOWN_TO_LISTEN, LANG));
		button.addMouseListener(this);
		radButtDim = new Dimension(32, 18);
		radButt2 = new JRadioButton("2", true);
		radButt3 = new JRadioButton("3");
		radButt4 = new JRadioButton("4");
		radButtongroup = new ButtonGroup();
		radButtongroup.add(radButt2);
		radButtongroup.add(radButt3);
		radButtongroup.add(radButt4);
		setUpRadButtons(true);
		Box sliderBox = Box.createVerticalBox();
		sliderBox.setFocusable(false);
		sliderBox.add(sliderLabel);
		sliderBox.add(Box.createVerticalGlue());
		sliderBox.add(slider);
		add(Box.createHorizontalGlue());
		add(sliderBox);
		Box checkBoxBox = Box.createHorizontalBox();
		checkBoxBox.setFocusable(false);
		sliderBox.add(Box.createVerticalGlue());
		sliderBox.add(new JLabel(Texts.getText(Text.KEEP_THE_BUTTON_DOWN_TO_LISTEN, LANG)){
			{
				setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
				setAlignmentX(0.5f);
			}
		});
		sliderBox.add(checkBoxBox);
		add(Box.createHorizontalGlue());
		Box rigthBox = Box.createVerticalBox();
		rigthBox.setAlignmentX(0.5f);
		Box radioButtonBox = Box.createVerticalBox();
		Dimension radPanelDim = new Dimension(50, 90);
		radioButtonBox.setMaximumSize(radPanelDim);
		radioButtonBox.setPreferredSize(radPanelDim);
		radioButtonBox.add(radButt2);
		radioButtonBox.add(radButt3);
		radioButtonBox.add(radButt4);
		rigthBox.add(radButtLabel);
		rigthBox.add(radioButtonBox);
		rigthBox.add(Box.createVerticalGlue());
		rigthBox.add(button);
		add(rigthBox);
		add(Box.createHorizontalGlue());   
		timer = new Timer(setPeriod(), this);
		timer.setInitialDelay(0);
		createClicker();
		if(isJFrame){
			//do something
		}
	}
	private void setUpRadButtons(boolean on) {
		Enumeration<AbstractButton> buttons=radButtongroup.getElements();
		while(buttons.hasMoreElements()){
			AbstractButton button=buttons.nextElement();
			button.setEnabled(on);
			button.setOpaque(false);
			button.setFocusable(false);
			button.setMinimumSize(radButtDim);
			button.setPreferredSize(radButtDim);
			button.addItemListener(this);
		}
	}
	private int setPeriod() {
		return 60000 / slider.getValue();
	}
	private void count() {
		beatCount++;
		if (beatCount > maxCount) {
			beatCount = 0;
			stress(true);
		}else{
			stress(false);
		}		
	}
	private void stress(boolean stress){
		if(stress){
			noteNumber=76;
		}else if(noteNumber!=77){
			noteNumber=77;
		}
	}
	private void createClicker() {
		clickerDriver = new ClickerDriver();
		clickerThread = new Thread(this);
		clickerThread.setDaemon(true);
		clickerThread.start();
	}
	public void actionPerformed(ActionEvent ae) {
		clickerDriver.click();
	}
	public void mousePressed(MouseEvent me) {
		beatCount = 0;
		stress(true);
		timer.start();
	}
	public void mouseReleased(MouseEvent me) {
		timer.stop();
	}
	public void mouseExited(MouseEvent me) {
		timer.stop();
	}
	public void mouseEntered(MouseEvent me) {}
	public void mouseClicked(MouseEvent me) {}
	public void itemStateChanged(ItemEvent ie) {			
		if (ie.getItem() instanceof JRadioButton && ie.getStateChange() == ItemEvent.SELECTED) {
			JRadioButton radButton = (JRadioButton) (ie.getSource());
			maxCount = Integer.valueOf(radButton.getText()) - 1;
		}
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
			MidiEngine.metroChannel.noteOn(noteNumber, velocity);
			try {
				Thread.sleep(length);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			MidiEngine.metroChannel.noteOff(noteNumber);
			count();
		}
		synchronized void click() {
			clickDispatched = true;
			notifyAll();
		}
	}
}
