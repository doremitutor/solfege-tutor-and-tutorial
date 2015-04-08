package com.doremitutor.sound;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.doremitutor.support.Texts;
import com.doremitutor.support.Utilities;
import com.doremitutor.support.Texts.Text;

public class SoundWavesJFrame extends JFrame{
	private static final int JFRAME_WIDTH=750;
	private static final int JFRAME_HEIGHT=485;
	public static void main(String[] args){	
		SoundWavesPanel.LANG=Texts.getLang(args[0]);
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					new SoundWavesJFrame();
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	private SoundWavesJFrame(){
        setTitle(Texts.getText(Text.SOUND_WAVES_TITLE, SoundWavesPanel.LANG)+Texts.TITLE_TRAILER);
        Utilities.setUpApp(this, new Dimension(JFRAME_WIDTH, JFRAME_HEIGHT), SoundWavesPanel.getInstance());
	}
}
