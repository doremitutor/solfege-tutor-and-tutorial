package com.doremitutor.sound;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.doremitutor.support.Texts;
import com.doremitutor.support.Utilities;

public class SinWaveDemoJFrame extends JFrame{
	static public void main(String[] args){
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					new SinWaveDemoJFrame();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private SinWaveDemoJFrame(){
		setTitle("Unset"+Texts.TITLE_TRAILER);//Texts.getText(Text.SOUND_SAMPLES_TITLE, SoundSamplesPanel.LANG)
		Utilities.setUpApp(this, new Dimension(730, 170), SinWaveDemoPanel.getInstance(true));		
	}
}