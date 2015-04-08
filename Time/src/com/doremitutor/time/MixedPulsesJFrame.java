package com.doremitutor.time;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.doremitutor.support.Texts;
import com.doremitutor.support.Utilities;

public class MixedPulsesJFrame extends JFrame{
	static public void main(String[] args){
		MixedPulsesPanel.LANG=Texts.getLang(args[0]);
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					new MixedPulsesJFrame();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	private MixedPulsesJFrame(){
        setTitle("Still unset "+Texts.TITLE_TRAILER);
        Utilities.setUpApp(this, new Dimension(550, 150), MixedPulsesPanel.getInstance(true));		
	}
}