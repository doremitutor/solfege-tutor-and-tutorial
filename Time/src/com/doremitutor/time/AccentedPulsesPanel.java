package com.doremitutor.time;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.doremitutor.support.MidiEngine;
import com.doremitutor.support.Texts.Lang;

public class AccentedPulsesPanel extends JPanel implements ActionListener, MouseListener, ItemListener, Runnable {
	static Lang LANG;
	private JLabel sliderLabel;
	private JLabel radButtLabel;
	private JLabel checkBoxLabel;
	private JSlider slider;
	private JButton button;
	private JCheckBox noAccentCheckBox;
	private JCheckBox agogicCheckBox;
	private JCheckBox dynamicCheckBox;
	private JCheckBox tonicCheckBox;
	private JRadioButton radButt2;
	private JRadioButton radButt3;
	private JRadioButton radButt4;
	private JRadioButton radButt5;
	private JRadioButton radButt6;
	private JRadioButton radButt7;
	private ButtonGroup buttongroup;
	private Dimension radButtDim;
	private Timer timer;
	private Thread clickerThread;
	private ClickerDriver clickerDriver;
	private boolean clickDispatched;
	private boolean stressed;
	private boolean agogic;
	private boolean dynamic;
	private boolean tonic;
	private int beatCount;
	private int maxCount = 1;
	private int length = 150;
	private int velocity = 64;
	private int noteNumber = 59;
	
	static private AccentedPulsesPanel instance=null;
	static AccentedPulsesPanel getInstance(boolean isJFrame){
		assert instance==null:"Singleton case";
		return instance=new AccentedPulsesPanel(isJFrame);
	}
	private AccentedPulsesPanel(boolean isJFrame){
				
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));	
		if(!MidiEngine.reserveSynth()||MidiEngine.metroChannel==null){
			add(Box.createHorizontalGlue());
			add(MidiEngine.getNoAudioSign());
			add(Box.createHorizontalGlue());
			return;
		}		
		clickDispatched = false;
		sliderLabel = new JLabel("Pulsos por minuto");
		sliderLabel.setAlignmentX(0.5f);
		radButtLabel = new JLabel("Pulsos por grupo");
		radButtLabel.setAlignmentX(0.5f);
		checkBoxLabel = new JLabel("Tipo de acento");
		checkBoxLabel.setAlignmentX(0.5f);
		slider = new JSlider(SwingConstants.HORIZONTAL, 60, 180, 120);
		slider.setFocusable(false);
		slider.setAlignmentX(0.5f);
		slider.setOpaque(false);
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
		button.setAlignmentX(0.5f);
		button.setToolTipText("Mantenga oprimido para escuchar los pulsos");
		button.addMouseListener(this);
		agogic = false;
		dynamic = false;
		tonic = false;
		noAccentCheckBox = new JCheckBox("Sin acento");
		noAccentCheckBox.setOpaque(false);
		noAccentCheckBox.setFocusable(false);
		noAccentCheckBox.setEnabled(false);
		noAccentCheckBox.setSelected(true);
		noAccentCheckBox.addItemListener(this);
		noAccentCheckBox.setToolTipText("Eliminar los acentos seleccionados");
		agogicCheckBox = new JCheckBox("Agógico");
		agogicCheckBox.setOpaque(false);
		agogicCheckBox.setFocusable(false);
		agogicCheckBox.addItemListener(this);
		agogicCheckBox.setToolTipText("Acentuar con duración");
		dynamicCheckBox = new JCheckBox("Dinámico");
		dynamicCheckBox.setOpaque(false);
		dynamicCheckBox.setFocusable(false);
		dynamicCheckBox.addItemListener(this);
		dynamicCheckBox.setToolTipText("Acentuar con volumen");
		tonicCheckBox = new JCheckBox("Tónico");
		tonicCheckBox.setOpaque(false);
		tonicCheckBox.setFocusable(false);
		tonicCheckBox.addItemListener(this);
		tonicCheckBox.setToolTipText("Acentuar con tono o altura");
		radButtDim = new Dimension(32, 18);
		radButt2 = new JRadioButton("2", true);
		radButt2.setOpaque(false);
		radButt2.setFocusable(false);
		radButt2.setMinimumSize(radButtDim);
		radButt2.setPreferredSize(radButtDim);
		radButt2.addItemListener(this);
		radButt3 = new JRadioButton("3");
		radButt3.setOpaque(false);
		radButt3.setFocusable(false);
		radButt3.setMinimumSize(radButtDim);
		radButt3.setPreferredSize(radButtDim);
		radButt3.addItemListener(this);
		radButt4 = new JRadioButton("4");
		radButt4.setOpaque(false);
		radButt4.setFocusable(false);
		radButt4.setMinimumSize(radButtDim);
		radButt4.setPreferredSize(radButtDim);
		radButt4.addItemListener(this);
		radButt5 = new JRadioButton("5");
		radButt5.setOpaque(false);
		radButt5.setFocusable(false);
		radButt5.setMinimumSize(radButtDim);
		radButt5.setPreferredSize(radButtDim);
		radButt5.addItemListener(this);
		radButt6 = new JRadioButton("6");
		radButt6.setOpaque(false);
		radButt6.setFocusable(false);
		radButt6.setMinimumSize(radButtDim);
		radButt6.setPreferredSize(radButtDim);
		radButt6.addItemListener(this);
		radButt7 = new JRadioButton("7");
		radButt7.setOpaque(false);
		radButt7.setFocusable(false);
		radButt7.setMinimumSize(radButtDim);
		radButt7.setPreferredSize(radButtDim);
		radButt7.addItemListener(this);
		disableRadButtons();
		buttongroup = new ButtonGroup();
		buttongroup.add(radButt2);
		buttongroup.add(radButt3);
		buttongroup.add(radButt4);
		buttongroup.add(radButt5);
		buttongroup.add(radButt6);
		buttongroup.add(radButt7);
		timer = new Timer(setPeriod(), this);
		timer.setInitialDelay(0);
		createClicker();
		Box sliderBox = Box.createVerticalBox();
		sliderBox.setFocusable(false);
		sliderBox.add(sliderLabel);
		sliderBox.add(Box.createVerticalGlue());
		sliderBox.add(slider);
		add(sliderBox);
		Box checkBoxBox = Box.createHorizontalBox();
		checkBoxBox.setFocusable(false);
		checkBoxBox.add(noAccentCheckBox);
		checkBoxBox.add(Box.createHorizontalGlue());
		checkBoxBox.add(agogicCheckBox);
		checkBoxBox.add(Box.createHorizontalGlue());
		checkBoxBox.add(dynamicCheckBox);
		checkBoxBox.add(Box.createHorizontalGlue());
		checkBoxBox.add(tonicCheckBox);
		sliderBox.add(Box.createVerticalGlue());
		sliderBox.add(checkBoxLabel);
		sliderBox.add(checkBoxBox);
		add(Box.createHorizontalGlue());
		Box rigthBox = Box.createVerticalBox();
		rigthBox.setAlignmentX(0.5f);
		JPanel radioButtonPanel = new JPanel();// new GridLayout(3,2)
		radioButtonPanel.setOpaque(false);
		radioButtonPanel.setToolTipText("Indique cada cuantos pulsos acentuar");
		Dimension radPanelDim = new Dimension(100, 90);
		radioButtonPanel.setMaximumSize(radPanelDim);
		radioButtonPanel.setPreferredSize(radPanelDim);
		radioButtonPanel.add(radButt2);
		radioButtonPanel.add(radButt3);
		radioButtonPanel.add(radButt4);
		radioButtonPanel.add(radButt5);
		radioButtonPanel.add(radButt6);
		radioButtonPanel.add(radButt7);
		rigthBox.add(radButtLabel);
		rigthBox.add(radioButtonPanel);
		rigthBox.add(Box.createVerticalGlue());
		rigthBox.add(button);
		add(rigthBox);
		if(isJFrame){
			//do something
		}
	}

	private int setPeriod() {
		return 60000 / slider.getValue();
	}

	private void count() {
		if (stressed) {
			unstress();
		}
		beatCount++;
		if (beatCount > maxCount) {
			beatCount = 0;
			stress();
		}
	}

	private void stress() {
		if (agogic) {
			length = 350;
		}
		if (dynamic) {
			velocity = 100;
		}
		if (tonic) {
			noteNumber = 60;
		}
		stressed = true;
	}

	private void unstress() {
		if (agogic) {
			length = 50;
		}
		if (dynamic) {
			velocity = 64;
		}
		if (tonic) {
			noteNumber = 59;
		}
	}

	private void enableRadButtons() {
		radButt2.setEnabled(true);
		radButt3.setEnabled(true);
		radButt4.setEnabled(true);
		radButt5.setEnabled(true);
		radButt6.setEnabled(true);
		radButt7.setEnabled(true);
	}

	private void disableRadButtons() {
		radButt2.setEnabled(false);
		radButt3.setEnabled(false);
		radButt4.setEnabled(false);
		radButt5.setEnabled(false);
		radButt6.setEnabled(false);
		radButt7.setEnabled(false);
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
		stress();
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

	public void itemStateChanged(ItemEvent ie) {
		if (ie.getItem() instanceof JCheckBox) {
			if ((JCheckBox) ie.getItem() == noAccentCheckBox) {
				if (ie.getStateChange() == ItemEvent.SELECTED) {
					agogicCheckBox.setSelected(false);
					dynamicCheckBox.setSelected(false);
					tonicCheckBox.setSelected(false);
					noAccentCheckBox.setEnabled(false);
					disableRadButtons();
				} else {
					enableRadButtons();
				}
			}
			if ((JCheckBox) ie.getItem() == agogicCheckBox) {
				if (ie.getStateChange() == ItemEvent.SELECTED) {
					agogic = true;
					noAccentCheckBox.setEnabled(true);
					noAccentCheckBox.setSelected(false);
				} else {
					agogic = false;
					length = 50;
					if (!dynamicCheckBox.isSelected() && !tonicCheckBox.isSelected()) {
						noAccentCheckBox.setSelected(true);
						noAccentCheckBox.setEnabled(true);
					}
				}
			}
			if ((JCheckBox) ie.getItem() == dynamicCheckBox) {
				if (ie.getStateChange() == ItemEvent.SELECTED) {
					dynamic = true;
					noAccentCheckBox.setEnabled(true);
					noAccentCheckBox.setSelected(false);
				} else {
					dynamic = false;
					velocity = 64;
					if (!agogicCheckBox.isSelected() && !tonicCheckBox.isSelected()) {
						noAccentCheckBox.setSelected(true);
						noAccentCheckBox.setEnabled(true);
					}
				}
			}
			if ((JCheckBox) ie.getItem() == tonicCheckBox) {
				if (ie.getStateChange() == ItemEvent.SELECTED) {
					tonic = true;
					noAccentCheckBox.setEnabled(true);
					noAccentCheckBox.setSelected(false);
				} else {
					tonic = false;
					noteNumber = 59;
					if (!agogicCheckBox.isSelected() && !dynamicCheckBox.isSelected()) {
						noAccentCheckBox.setSelected(true);
						noAccentCheckBox.setEnabled(true);
					}
				}
			}
		}
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
			MidiEngine.singChannel.noteOn(noteNumber, velocity);
			try {
				Thread.sleep(length);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			MidiEngine.singChannel.noteOff(noteNumber);
			count();
		}

		synchronized void click() {
			clickDispatched = true;
			notifyAll();
		}
	}
}
