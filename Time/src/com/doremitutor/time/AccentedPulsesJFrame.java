package com.doremitutor.time;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.doremitutor.support.Texts;
import com.doremitutor.support.Utilities;

public class AccentedPulsesJFrame extends JFrame{
	private static final int JFRAME_WIDTH=550;
	private static final int JFRAME_HEIGHT=150;
	static public void main(String[] args){
		AccentedPulsesPanel.LANG=Texts.getLang(args[0]);
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					new AccentedPulsesJFrame();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private AccentedPulsesJFrame(){
        setTitle("Unset "+Texts.TITLE_TRAILER);
        Utilities.setUpApp(this, new Dimension(JFRAME_WIDTH, JFRAME_HEIGHT), AccentedPulsesPanel.getInstance(true));				
	}
}