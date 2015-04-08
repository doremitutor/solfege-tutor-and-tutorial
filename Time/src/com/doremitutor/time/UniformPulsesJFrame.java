package com.doremitutor.time;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.doremitutor.support.Texts;
import com.doremitutor.support.Utilities;
import com.doremitutor.support.Texts.Text;

public class UniformPulsesJFrame extends JFrame{
	static public void main(String[] args){
		UniformPulsesPanel.LANG=Texts.getLang(args[0]);
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					new UniformPulsesJFrame();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	private UniformPulsesJFrame(){
        setTitle(Texts.getText(Text.PULSES_TITLE, UniformPulsesPanel.LANG)+Texts.TITLE_TRAILER);
        Utilities.setUpApp(this, new Dimension(550, 120), UniformPulsesPanel.getInstance(true));		
	}
}
