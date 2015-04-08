package com.doremitutor.sound;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.doremitutor.support.Texts;
import com.doremitutor.support.Utilities;
import com.doremitutor.support.Texts.Text;

public class SoundSamplesJFrame extends JFrame{
	public static void main(String[] args){
		SoundSamplesPanel.LANG=Texts.getLang(args[0]);
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					new SoundSamplesJFrame();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	private SoundSamplesJFrame(){
		setTitle(Texts.getText(Text.SOUND_SAMPLES_TITLE, SoundSamplesPanel.LANG)+Texts.TITLE_TRAILER);
		Utilities.setUpApp(this, new Dimension(450, 90), SoundSamplesPanel.getInstance());
	}
}