package com.doremitutor.data;

import java.io.IOException;
import java.io.ObjectInputStream;

import com.doremitutor.midi.MidiProxy;
import com.doremitutor.persistence.ProtoLesson;
import com.doremitutor.persistence.Constants.Cleff;
import com.doremitutor.persistence.Constants.Rhythm;
import com.doremitutor.persistence.Constants.Time;
import com.doremitutor.playerUI.ScorePlayerPanel;
import com.doremitutor.playerUI.TempoLabel;
import com.doremitutor.playerUI.TransportPane;
import com.doremitutor.scoreDisplay.Score;
import com.doremitutor.support.Texts;
import com.doremitutor.support.Texts.Lang;

public class LessonData {
	private static String LESSON_PATH=null;
	private static Lang LANG=null;
	private static ProtoLesson LOADED_LESSON=null;
	private static int SCORE_WIDTH;
	
	private static Cleff CLEF;
	private static Time TIME;
	private static Rhythm BEAT_RHYTHM;
	private static int BEAT_DURATION;
	private static int SUB_BEAT_DURATION;
	private static int BEATS_PER_BAR;
	private static int BAR_DURATION;
	private static int TEMPO;
	
	public static void setLessonPath(String lessonPath){
		LESSON_PATH=lessonPath;
	}	
	public static void setLang(String lang){
		LANG=Texts.getLang(lang.toUpperCase());
	}
	public static void setScoreWidth(int scoreWidth){
		SCORE_WIDTH=scoreWidth;
	}
	public static int getScorePlayerPanelHeight(){
		assert LESSON_PATH!=null:"LESSON_PATH not set yet";
		return Integer.valueOf(LESSON_PATH.substring(LESSON_PATH.lastIndexOf("-")+1))*101+90;
	}
	public static void loadLessonParameters(boolean midiOk){
		ObjectInputStream in=null;
		Class<?> c=(Class<?>)ScorePlayerPanel.class;
		try {
			in=new ObjectInputStream(c.getResourceAsStream("../z_lessons/"+LESSON_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			LOADED_LESSON=(ProtoLesson)in.readObject();	
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} finally{			
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		assert LOADED_LESSON!=null:"No lesson to load";
		if(!midiOk){
			return;
		}
		CLEF=LOADED_LESSON.cleff;	
		TEMPO=LOADED_LESSON.tempo;
		setTiming(LOADED_LESSON.time);
	}
	public static void loadLessonParameters(ProtoLesson protolesson){
		LOADED_LESSON=protolesson;
		CLEF=LOADED_LESSON.cleff;	
		TEMPO=LOADED_LESSON.tempo;
		setTiming(LOADED_LESSON.time);
	}
	public static void setUpLessonContent(){
		Score.loadLessonContent(LOADED_LESSON);
		TempoLabel.setTempoText(getTempo());
		MidiProxy.THE.getScoreContext();
		MidiProxy.THE.addObserver(TransportPane.observer);			
	}
	public static String getJFrameTitle(){
		final String lessonName=LESSON_PATH.substring(LESSON_PATH.lastIndexOf("/")+1);		
		return LessonTitleMap.getTitle(lessonName, LANG);
	}	
	public static Lang getLang(){
		return LANG;
	}
	public static int getScoreWidth(){
		return SCORE_WIDTH;
	}
	
	public static void setClef(Cleff newClef){
		CLEF=newClef;
	}
	public static Cleff getClef(){
		return CLEF;
	}
	public static void setTiming(Time newTime){
		TIME=newTime;
		BEAT_RHYTHM=TimeData.beatRhythm(TIME);
		BEAT_DURATION=RhythmData.getRhythmDuration(BEAT_RHYTHM);
		SUB_BEAT_DURATION=BEAT_DURATION/TimeData.subBeatsPerBeat(TIME);
		BEATS_PER_BAR=TimeData.beatsPerBar(TIME);
		BAR_DURATION=BEAT_DURATION*BEATS_PER_BAR;
	}
	public static Time getTime(){
		return TIME;
	}
	public static Rhythm getBeatRhythm(){
		return BEAT_RHYTHM;
	}
	public static int getBeatDuration(){
		return BEAT_DURATION;
	}
	public static int getSubBeatDuration(){
		return SUB_BEAT_DURATION;
	}
	public static int getBeatsPerBar(){
		return BEATS_PER_BAR;
	}
	public static int getBarDuration(){
		return BAR_DURATION;
	}
	public static int getTempo(){
		return TEMPO;
	}
	public static void setTempo(int newTempo){
		TEMPO=newTempo;
	}
}
