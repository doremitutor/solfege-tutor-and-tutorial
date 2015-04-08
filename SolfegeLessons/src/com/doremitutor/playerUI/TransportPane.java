package com.doremitutor.playerUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.doremitutor.data.LessonData;
import com.doremitutor.midi.MidiProxy;
import com.doremitutor.midi.MidiProxy.Status;
import com.doremitutor.scoreDisplay.FrameWorkUtilities;
import com.doremitutor.support.Texts;
import com.doremitutor.support.Texts.Lang;
import com.doremitutor.support.Texts.Text;

public class TransportPane{
	public static final int MAX_VOL=127;
	public static final int DEFAULT_VOL=127;
	public static final int MIN_VOL=64;
	private static Lang LANG=LessonData.getLang();

	public static final String noteNames="noteNames";
	public static final String barLight="barLight";
	public static final String precount="precount";
	public static final String tuneUp="tuneUp";
	public static final String startStop="startStop";
	public static final String rewind="rewind";
	public static final String tempoText="tempo";
	public static final String volume="metro";
	public static final String flute=Texts.getText(Text.FLUTE, LANG);
	public static final String clarinet=Texts.getText(Text.CLARINET, LANG);
	public static final String violin=Texts.getText(Text.VIOLIN, LANG);
	public static final String organ=Texts.getText(Text.ORGAN, LANG);
	public static final int tempo=LessonData.getTempo();
	
	private TransportPane (){assert false:"Monostate case";}		
	//instrumentBox
	private static final ButtonGroup instrumentGroup=new ButtonGroup();
	private static final JRadioButton fluteRadioButton = new JRadioButton(TransportPane.flute);
	private static final JRadioButton clarinetRadioButton = new JRadioButton(TransportPane.clarinet);
	private static final JRadioButton violinRadioButton = new JRadioButton(TransportPane.violin);
	private static final JRadioButton organRadioButton = new JRadioButton(TransportPane.organ);
	private static final Box instrumentBox=Box.createVerticalBox();
	private static final void setUpInstrButton(JRadioButton button, Dimension dim){
		FrameWorkUtilities.makeTransparentAndUnfocusable(button);
		button.setAlignmentX(0.0f);
		button.setPreferredSize(dim);
		button.setActionCommand(button.getText());		
		button.addActionListener(PlayerManager.THE);
		instrumentGroup.add(button);
		instrumentBox.add(button);
	}
	static{
		final JComponent[] comp={fluteRadioButton, clarinetRadioButton, violinRadioButton, organRadioButton};
		final int minimumWidth=getMinimumJComponentPreferredWidth(comp);
		final Dimension dim=new Dimension(minimumWidth, 15);
		setUpInstrButton(fluteRadioButton, dim);
		instrumentBox.add(Box.createVerticalGlue());
		setUpInstrButton(clarinetRadioButton, dim);
		instrumentBox.add(Box.createVerticalGlue());
		setUpInstrButton(violinRadioButton, dim);
		instrumentBox.add(Box.createVerticalGlue());
		setUpInstrButton(organRadioButton, dim);		
		fluteRadioButton.setSelected(true);
	}	
	//optionBox
	private static final String preCountCheckBoxText=Texts.getText(Text.INITIAL_BAR, LANG);
	private static final String noteNameCheckBoxText=Texts.getText(Text.SHOW_NOTE_NAMES, LANG);
	private static final String enlightBarCheckBoxText=Texts.getText(Text.ENHANCE_PLAYING_BAR, LANG);
	private static final JCheckBox preCountCheckBox=new JCheckBox(preCountCheckBoxText);
	private static final JCheckBox enlightBarCheckBox=new JCheckBox(enlightBarCheckBoxText);
	private static final JCheckBox noteNameCheckBox=new JCheckBox(noteNameCheckBoxText);
	private static final Box optionBox=Box.createVerticalBox();
	private static final void setUpOptionBox(JCheckBox checkBox, String name, int height){
		FrameWorkUtilities.makeTransparentAndUnfocusable(checkBox);
		checkBox.setPreferredSize(new Dimension(checkBox.getPreferredSize().width, height));
		checkBox.setMaximumSize(new Dimension(checkBox.getMaximumSize().width, height));
		checkBox.setSelected(true);
		checkBox.addItemListener(PlayerManager.THE);
		checkBox.setName(name);
		optionBox.add(checkBox);
	}
	static{
		final int height=20;
		setUpOptionBox(preCountCheckBox, TransportPane.precount, height);
		optionBox.add(Box.createVerticalGlue());
		setUpOptionBox(enlightBarCheckBox, TransportPane.barLight, height);
		optionBox.add(Box.createVerticalGlue());
		setUpOptionBox(noteNameCheckBox, TransportPane.noteNames, height);		
	}
	//helpLabelBox
	private static final JLabel startStopLabel=new JLabel(Texts.getText(Text.START_STOP, LANG));
	private static final JLabel rewindLabel=new JLabel(Texts.getText(Text.REWIND, LANG));
	private static final JLabel tuneLabel=new JLabel(Texts.getText(Text.TUNE_UP, LANG));
	private static final JLabel barSelectionLabel=new JLabel(Texts.getText(Text.GO_TO_BAR, LANG));
	private static final void setUpHelpLabel(JLabel label){
		FrameWorkUtilities.makeTransparentAndUnfocusable(label);
		label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
		final int height=11;
		label.setPreferredSize(new Dimension(label.getPreferredSize().width, height));
		label.setMaximumSize(new Dimension(label.getMaximumSize().width, height));
		label.setMinimumSize(new Dimension(label.getMinimumSize().width, height));
	}
	static final Box helpLabelBox=Box.createVerticalBox();
	static{
		setUpHelpLabel(startStopLabel);
		setUpHelpLabel(rewindLabel);
		setUpHelpLabel(tuneLabel);
		setUpHelpLabel(barSelectionLabel);
		helpLabelBox.add(startStopLabel);
		helpLabelBox.add(Box.createVerticalGlue());
		helpLabelBox.add(rewindLabel);
		helpLabelBox.add(Box.createVerticalGlue());
		helpLabelBox.add(tuneLabel);
		helpLabelBox.add(Box.createVerticalGlue());
		helpLabelBox.add(barSelectionLabel);
	}
	//statusLabel
	private static String standingBy=Texts.getText(Text.STANDING_BY, LANG);
	private static String preCounting=Texts.getText(Text.PRECOUNTING, LANG);
	private static String playing=Texts.getText(Text.PLAYING, LANG);
	private static String inPause=Texts.getText(Text.PAUSED, LANG);
	private static String tuning=Texts.getText(Text.TUNING_UP, LANG);
	private static Color standingByColor=Color.BLACK;
	private static Color preCountingColor=Color.RED;
	private static Color playingColor=new Color(0, 150, 0);	
	private static Color inPauseColor=new Color(150, 150, 0);
	private static Color tuningColor=Color.RED;	
	private static final JLabel statusLabel=new JLabel(standingBy);
	static{
		statusLabel.setFocusable(false);
		setDimension(statusLabel, new Dimension(180, 36));
		statusLabel.setHorizontalAlignment(JLabel.CENTER);
		statusLabel.setVerticalAlignment(JLabel.BOTTOM);
		statusLabel.setFont(new Font("SansSerif", Font.BOLD, 16));		
		setBorder(statusLabel, Texts.getText(Text.STATE, LANG));
	}	
	//buttons
	private static Icon playIcon=new ImageIcon(new BufferedImage(15, 15, BufferedImage.TYPE_INT_ARGB){
		{
			Graphics g=getGraphics();
			g.setColor(Color.BLACK);
			int width=getWidth();
			int height=getHeight();
			Polygon p=new Polygon(new int[]{0, width, 0}, new int[]{0, height/2, height}, 3);
			g.fillPolygon(p);
		}		
	});
	private static Icon pauseIcon=new ImageIcon(new BufferedImage(12, 15, BufferedImage.TYPE_INT_ARGB){
		{			
			Graphics g=getGraphics();
			g.setColor(Color.BLACK);
			int width=getWidth();
			int height=getHeight();
			int thickness=5;
			g.fillRect(0, 0, thickness, height);
			g.fillRect(width-thickness, 0, width, height);
		}		
	});
	private static Icon rwIcon=new ImageIcon(new BufferedImage(30, 15, BufferedImage.TYPE_INT_ARGB){
		{			
			Graphics g=getGraphics();
			g.setColor(Color.BLACK);
			int width=getWidth()/2;
			int height=getHeight();
			g.drawRect(0, 0, 1, height-1);
			Polygon p=new Polygon(new int[]{width, 0, width}, new int[]{0, height/2, height-1}, 3);
			g.fillPolygon(p);
			g.translate(14, 0);
			g.fillPolygon(p);			
		}		
	});
	private static final JButton tuneButton=new JButton(Texts.getText(Text.TUNE, LANG));
	private static final JButton rwButton=new JButton(rwIcon){
		{
			setEnabled(false);
		}
	};
	private static final JToggleButton startStopButton=new JToggleButton(playIcon);
	private static final void setUpButton(AbstractButton button, Dimension dim, String actionCmd, int mnemonic, String toolTipText){
		setDimension(button, dim);;
		button.setAlignmentY(1.0f);
		button.addActionListener(PlayerManager.THE);
		button.setActionCommand(actionCmd);
		button.setFocusable(false);
		button.setMnemonic(mnemonic);
		button.setToolTipText(toolTipText);
		buttonBox.add(button);
	}
	private static final Box buttonBox=Box.createHorizontalBox();
	static{
		Dimension dim=new Dimension(86, 28);
		setUpButton(tuneButton, dim, TransportPane.tuneUp, KeyEvent.VK_T, Texts.getText(Text.TUNE_TIP, LANG));
		setUpButton(rwButton, dim, TransportPane.rewind, KeyEvent.VK_R, Texts.getText(Text.RW_TIP, LANG));
		setUpButton(startStopButton, dim, TransportPane.startStop, KeyEvent.VK_P, Texts.getText(Text.START_STOP_TIP, LANG));
		tuneButton.setAlignmentY(1.0f);
	}
	//sliderBox
	private static final JSlider tempoSlider=new JSlider(SwingConstants.HORIZONTAL, tempo-20, tempo+20, tempo);
	private static final JSlider volumeSlider=new JSlider(SwingConstants.HORIZONTAL, MIN_VOL, MAX_VOL, DEFAULT_VOL);
	private static final void setUpJSlider(JSlider slider, Dimension dim, boolean snap, String name, String label){
		setDimension(slider, dim);
		setBorder(slider, label);
		FrameWorkUtilities.makeTransparentAndUnfocusable(slider);
		slider.setAlignmentY(0.5f);
		slider.setMajorTickSpacing(10);
		slider.setSnapToTicks(snap);
		slider.setPaintTicks(false);
		slider.setPaintTrack(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(PlayerManager.THE);
		slider.setName(name);
		sliderBox.add(slider);
	}
	private static final Box sliderBox=Box.createHorizontalBox();
	static{
		Dimension dim=new Dimension(130, 52);
		setUpJSlider(tempoSlider, dim, true, TransportPane.tempoText, "Tempo");
		setUpJSlider(volumeSlider, dim, false, TransportPane.volume, Texts.getText(Text.VOLUME, LANG));
		Dictionary<Integer, JLabel> labels=new Hashtable<Integer, JLabel>(){
			{
				put(MIN_VOL, new JLabel("Min"));
				put(MAX_VOL, new JLabel("Max"));
			}
		};		
		volumeSlider.setLabelTable(labels);
	}	
	//intermediate boxes
	
	private static final Box helpAndStatusBox=Box.createVerticalBox();
	private static final Box buttonAndSliderBox=Box.createVerticalBox();	
	//pane
	static final Box pane=Box.createHorizontalBox();
	static{
		instrumentBox.setAlignmentY(0.0f);
		
		helpAndStatusBox.setAlignmentY(0.0f);
		buttonAndSliderBox.setAlignmentY(0.0f);
		optionBox.setAlignmentY(0.0f);
		helpLabelBox.setAlignmentX(0.0f);
		statusLabel.setAlignmentX(0.0f);
		sliderBox.setAlignmentX(0.5f);
		buttonBox.setAlignmentX(0.5f);
		pane.setAlignmentX(0.5f);
		
		helpAndStatusBox.add(helpLabelBox);
		helpAndStatusBox.add(statusLabel);
		buttonAndSliderBox.add(buttonBox);
		buttonAndSliderBox.add(Box.createVerticalGlue());
		buttonAndSliderBox.add(sliderBox);
		pane.add(instrumentBox);
		pane.add(Box.createHorizontalGlue());
		pane.add(optionBox);
		pane.add(Box.createHorizontalGlue());
		pane.add(helpAndStatusBox);	
		pane.add(Box.createHorizontalGlue());	
		pane.add(buttonAndSliderBox);
	}	
	static void enableRw(){
		rwButton.setEnabled(MidiProxy.THE.getBarIndex()>0);
	}
	public static Observer observer=new Observer(){
		public void update(Observable obs, Object obj){
			switch((Status)obj){
			case STAND_BY:
				startStopButton.setEnabled(true);
				startStopButton.setSelected(false);
				startStopButton.setIcon(playIcon);
				statusLabel.setText(standingBy);
				statusLabel.setForeground(standingByColor);
				enableRw();
				tuneButton.setEnabled(true);
				break;
			case PRECOUNTING:
				startStopButton.setIcon(pauseIcon);
				statusLabel.setText(preCounting);
				statusLabel.setForeground(preCountingColor);
				rwButton.setEnabled(false);
				tuneButton.setEnabled(false);
				break;
			case SINGING:
				startStopButton.setIcon(pauseIcon);
				statusLabel.setText(playing);
				statusLabel.setForeground(playingColor);
				rwButton.setEnabled(false);
				tuneButton.setEnabled(false);
				break;
			case PAUSED:
				startStopButton.setIcon(playIcon);
				startStopButton.setEnabled(true);
				statusLabel.setText(inPause);
				statusLabel.setForeground(inPauseColor);
				enableRw();
				tuneButton.setEnabled(true);
				break;
			case TUNING:
				startStopButton.setEnabled(false);
				tuneButton.setEnabled(false);
				rwButton.setEnabled(false);
				statusLabel.setText(tuning);
				statusLabel.setForeground(tuningColor);
				break;
			}
		}
	};	
	private static final int getMinimumJComponentPreferredWidth(JComponent[] components){
		int minimum=Integer.MIN_VALUE;
		int compWidth;
		for(JComponent comp : components){
			compWidth=comp.getPreferredSize().width;
			minimum=compWidth>minimum?compWidth:minimum;
		}
		return minimum;
	}	
	private static final void setDimension(JComponent component, Dimension dim){
		component.setMinimumSize(dim);
		component.setPreferredSize(dim);
		component.setMaximumSize(dim);
	}	
	private static final void setBorder(JComponent component, String borderTitle){
		Border lineBorder=BorderFactory.createLineBorder(new Color(0x90, 0x60, 0x30), 1);
		Border titleBorder=BorderFactory.createTitledBorder(lineBorder, borderTitle);
		component.setBorder(titleBorder);
	}	
}