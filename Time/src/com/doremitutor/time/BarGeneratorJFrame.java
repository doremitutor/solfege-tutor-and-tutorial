package com.doremitutor.time;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.doremitutor.support.Texts;
import com.doremitutor.support.Utilities;
import com.doremitutor.support.Texts.Text;

public class BarGeneratorJFrame extends JFrame {
	public static void main(String[] args){
		BarGeneratorPanel.LANG=Texts.getLang(args[0]);
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run(){
					new BarGeneratorJFrame();
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}			
	}
	private BarGeneratorJFrame() {
	    setTitle(Texts.getText(Text.BAR_GENERATOR_TITLE, BarGeneratorPanel.LANG)+Texts.TITLE_TRAILER);
	    Utilities.setUpApp(this, new Dimension(550, 120), BarGeneratorPanel.getInstance(true));     
	}
}