package com.doremitutor.playerUI;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import com.doremitutor.data.LessonData;
import com.doremitutor.support.MidiEngine;
import com.doremitutor.support.Utilities;

public class ScorePlayerJApplet extends JApplet{
	public void init(){
		final JApplet applet=this;
		final String lessonPath=getParameter("lessonName");
		LessonData.setLessonPath(lessonPath);
		LessonData.setLang(getParameter("lang"));
		final boolean midiOk=MidiEngine.reserveSynth()&&MidiEngine.reserveSeq();
		LessonData.loadLessonParameters(midiOk);
		final int scorePlayerPanelHeight=LessonData.getScorePlayerPanelHeight();
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					if(midiOk){					
						Utilities.setUpApp(applet, null, ScorePlayerPanel.getInstance(false, new Dimension(750, scorePlayerPanelHeight)));
						LessonData.setScoreWidth(ScorePlayerPanel.getInstance().getWidth());
						LessonData.setUpLessonContent();
					}else {
						//temp
						throw new AssertionError("No MIDI still"); 
					}
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