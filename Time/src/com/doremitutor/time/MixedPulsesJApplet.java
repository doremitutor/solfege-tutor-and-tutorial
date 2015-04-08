package com.doremitutor.time;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import com.doremitutor.support.MidiEngine;
import com.doremitutor.support.Texts;
import com.doremitutor.support.Utilities;

public class MixedPulsesJApplet extends JApplet{
	public void init(){
		MixedPulsesPanel.LANG=Texts.getLang(getParameter("lang"));
		final JApplet applet=this;
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					Utilities.setUpApp(applet, null, MixedPulsesPanel.getInstance(false));
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void destroy(){
		MidiEngine.releaseResources();
		System.exit(0);
	}
}
