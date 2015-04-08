package com.doremitutor.playerUI;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.doremitutor.data.LessonData;
import com.doremitutor.support.MidiEngine;
import com.doremitutor.support.Texts;
import com.doremitutor.support.Utilities;

public class ScorePlayerJFrame extends JFrame{
	static private int JFRAME_HEIGHT;
	static public void main(String args[]){
		LessonData.setLessonPath(args[0]);
		LessonData.setLang(args[1]);
		final boolean midiOk=MidiEngine.reserveSynth()&&MidiEngine.reserveSeq();
		LessonData.loadLessonParameters(midiOk);
		JFRAME_HEIGHT=LessonData.getScorePlayerPanelHeight()+30;
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					if(midiOk){					
						new ScorePlayerJFrame();
					}else{
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
	private ScorePlayerJFrame(){
        setTitle(LessonData.getJFrameTitle()+Texts.TITLE_TRAILER);
		Utilities.setUpApp(this, new Dimension(750, JFRAME_HEIGHT), ScorePlayerPanel.getInstance(true, null));		
		LessonData.setScoreWidth(ScorePlayerPanel.getInstance().getWidth());
		LessonData.setUpLessonContent();
	}
}