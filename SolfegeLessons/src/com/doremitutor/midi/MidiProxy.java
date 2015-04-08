package com.doremitutor.midi;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Observable;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.doremitutor.data.LessonData;
import com.doremitutor.playerUI.PlayerManager;
import com.doremitutor.scoreDisplay.Bar;
import com.doremitutor.scoreDisplay.Figure;
import com.doremitutor.scoreDisplay.Score;
import com.doremitutor.support.MidiEngine;

public class MidiProxy extends Observable{
	public static enum Status{STAND_BY, PRECOUNTING, SINGING, PAUSED, TUNING}
	
	public static MidiProxy THE=null;	
	static{THE=new MidiProxy();}
	
	private MidiProxy(){
		assert THE==null:"Singleton case";
	}
	
	private int tempo;
	{
		tempo=LessonData.getTempo();
	}
	Status status=Status.STAND_BY;
	int barIndex;
	public void getScoreContext(){
		SequenceGenerator.getScoreContext();
	}
	public int getBarIndex(){
		return barIndex;
	}
	public void goTo(Bar bar){
		if(MidiProxy.THE.status==Status.STAND_BY||MidiProxy.THE.status==Status.PAUSED){
			List<Bar> bars=Score.getBars();
			bars.get(barIndex).showCursor(false);
			bar.showCursor(true);
			int newIndex=bars.indexOf(bar);
			barIndex=newIndex;
			if(newIndex>0){
				MidiProxy.THE.setStatus(Status.PAUSED);
			}else{
				MidiProxy.THE.setStatus(Status.STAND_BY);
			}
		}
	}
	public void setTempo(int newTempo){
		tempo=newTempo;
		updateTempo();
	}
	public void tuneUp(){
		final Status rememberedStatus=MidiProxy.THE.status;
		MidiProxy.THE.setStatus(Status.TUNING);
		MidiEngine.singChannel.noteOn(60, 100);
		Timer timer=new Timer(2000, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MidiEngine.singChannel.allNotesOff();
				MidiProxy.THE.setStatus(rememberedStatus);
			}
		});
		timer.setRepeats(false);
		timer.start();
	}
	public void play(boolean preCount){
		if(preCount){
			precount();
			setStatus(Status.PRECOUNTING);
		}else{
			sing();
			setStatus(Status.SINGING);
		}
	}
	public void pause(){
		stopSequencer();
		setStatus(Status.PAUSED);
		enlightBar(barIndex, false);
		shutFiguresInBar();
	}
	public void rw(){
		showCursor(barIndex, false);
		barIndex=0;
		showCursor(0, true);
		if(status!=Status.STAND_BY){
			setStatus(Status.STAND_BY);			
		}
	}
	void stopSequencer(){		
		MidiEngine.seq.stop();
	}
	void showCursor(int index, boolean on){
		Score.getBars().get(index).showCursor(on);
	}
	void cleanUp(){
		stopSequencer();
		showCursor(barIndex, false);
		barIndex=0;
		showCursor(0, true);
		setStatus(Status.STAND_BY);
	}
	void enlightBar(int index, final boolean turningOn){
		final Bar bar=Score.getBars().get(index);		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){		
				bar.showLight(turningOn);
				if(turningOn){
					if(PlayerManager.enlightBars){
						bar.showLight(true);
					}
					bar.showCursor(true);
				}else{
					bar.showLight(false);
					if(status==Status.SINGING){
						bar.showCursor(false);				
					}
				}		
			}
		});
	}
	void enlightFigure(int index, boolean on){
		Score.readFigures().get(index).enlight(on);
	}
	private void shutFiguresInBar(){
		for(Figure figure:Score.getBars().get(barIndex).readFigures()){
			figure.enlight(false);
		}
	}
	private void setStatus(Status newStatus){
		status=newStatus;
		setChanged();
		notifyObservers(status);
	}
	private void updateTempo(){
		MidiEngine.seq.setTempoFactor(1.0f);
		MidiEngine.seq.setTempoInBPM(tempo);	
	}
	private void precount(){
		try {
			MidiEngine.seq.setSequence(SequenceGenerator.preCountSequence);
		} catch (InvalidMidiDataException e){
			e.printStackTrace();
		}
		MidiEngine.seq.setTickPosition(0);
		startSequencer();
	}
	private void sing(){		
		try {
			MidiEngine.seq.setSequence(SequenceGenerator.mainSequence);
		} catch (InvalidMidiDataException e){
			e.printStackTrace();
		}
		MidiEngine.seq.setTickPosition(barIndex*LessonData.getBarDuration());
		startSequencer();
	}
	private void startSequencer(){
		updateTempo();
		MidiEngine.seq.start();
	}
}