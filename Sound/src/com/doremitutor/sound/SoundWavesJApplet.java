package com.doremitutor.sound;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import com.doremitutor.support.Texts;
import com.doremitutor.support.Utilities;

public class SoundWavesJApplet extends JApplet{	
	public void init(){
		SoundWavesPanel.LANG=Texts.getLang(getParameter("lang"));
		final JApplet applet=this;
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					Utilities.setUpApp(applet, null, SoundWavesPanel.getInstance());
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void destroy(){
		System.exit(0);
	}
}