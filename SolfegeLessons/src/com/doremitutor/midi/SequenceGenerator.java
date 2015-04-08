package com.doremitutor.midi;

import java.util.List;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

import com.doremitutor.data.LessonData;
import com.doremitutor.data.PitchData;
import com.doremitutor.data.RhythmData;
import com.doremitutor.persistence.ProtoFigure.ProtoNote;
import com.doremitutor.playerUI.TransportPane;
import com.doremitutor.scoreDisplay.Bar;
import com.doremitutor.scoreDisplay.Figure;
import com.doremitutor.scoreDisplay.Score;
import com.doremitutor.scoreDisplay.Figure.Note;
import com.doremitutor.support.MidiEngine;

class SequenceGenerator{
	private SequenceGenerator(){assert false:"Monostate case";}

	static Sequence mainSequence=null;
	static Sequence preCountSequence=null;
	
	static void getScoreContext(){
		reset();
		try {
			//preCount
			final int barDuration=LessonData.getBarDuration();
			Track precountTrack=preCountSequence.createTrack();
			createTicks(precountTrack, barDuration, true);
			//main
			Track soundTrack=mainSequence.createTrack();
			Track barLightTrack=mainSequence.createTrack();
			Track figureLightTrack=mainSequence.createTrack();
			Track metronomeTrack=mainSequence.createTrack();
			//sound & figure enlightenment
			long tickPosition=0;
			long countOn;
			long countOff;
			boolean noteTurnable=true;
			final List<Figure> figures=Score.readFigures();
			for(Figure figure:figures){
				int duration=RhythmData.getRhythmDuration(figure.rhythm);
				countOn=tickPosition;
				countOff=countOn+duration;
				//sound
				if(figure instanceof Note){
					((Note)figure).showName(true);
					ProtoNote protoNote=(ProtoNote)figure.protoFigure;
					int midiNote=PitchData.midiNote(protoNote.pitch);
					if(noteTurnable){						
						ShortMessage singOnMsg=new ShortMessage();
						singOnMsg.setMessage(ShortMessage.NOTE_ON, 0, midiNote, TransportPane.DEFAULT_VOL);
						soundTrack.add(new MidiEvent(singOnMsg, countOn));
					}
					if(!protoNote.isLinked){
						ShortMessage singOffMsg=new ShortMessage();
						singOffMsg.setMessage(ShortMessage.NOTE_ON, 0, midiNote, 0);
						soundTrack.add(new MidiEvent(singOffMsg, countOff-(protoNote.hasComma?2:0)));
						noteTurnable=true;
					}else{
						noteTurnable=false;
					}				
				}
				//enlightenment
				int figureIndex=figures.indexOf(figure);
				final byte[] data={(byte)(figureIndex%256), (byte)(figureIndex/256)};
				MetaMessage enlightOnMsg=new MetaMessage(71, data, 2);
				MetaMessage enlightOffMsg=new MetaMessage(72, data, 2);
				figureLightTrack.add(new MidiEvent(enlightOnMsg, countOn));
				figureLightTrack.add(new MidiEvent(enlightOffMsg, countOff));
				tickPosition+=duration;
			}	
			//bar enlightenment
			final List<Bar> bars=Score.getBars();
			for(int i=0; i<bars.size();i++){
				assert i<256:"Only 256 bars allowed in this version";
				final byte[] data={(byte)i};
				MetaMessage barEnlightOn=new MetaMessage(73, data, 1);
				MetaMessage barEnlightOff=new MetaMessage(74, data, 1);
				barLightTrack.add(new MidiEvent(barEnlightOn, barDuration*i));
				barLightTrack.add(new MidiEvent(barEnlightOff, barDuration*(i+1)));
			}
			// main sequence metronome
			createTicks(metronomeTrack, mainSequence.getTickLength(), false);
		} catch (InvalidMidiDataException e){
			e.printStackTrace();
		}		
		MidiEngine.singChannel.programChange(73);
		MidiEngine.singChannel.controlChange(7, TransportPane.DEFAULT_VOL);
		MidiEngine.metroChannel.controlChange(7, TransportPane.DEFAULT_VOL);
		MidiEngine.seq.addMetaEventListener(MidiMetaEventListener.THE);
	}

	/*static{
		try {
			mainSequence=new Sequence(Sequence.PPQ, 12, 0);
			preCountSequence=new Sequence(Sequence.PPQ, 12, 0);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}*/
	static private void reset(){		
		try {
			mainSequence=new Sequence(Sequence.PPQ, 12, 0);
			preCountSequence=new Sequence(Sequence.PPQ, 12, 0);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}

	private static void createTicks(Track track, long length, boolean precount){
		final int barTickNote=76;
		final int beatTickNote=77;
		int ticks=0;
		while(ticks<length){
			ShortMessage tickOnMsg=new ShortMessage();
			ShortMessage tickOffMsg=new ShortMessage();
			try {
				if(ticks%LessonData.getBarDuration()==0){
					tickOnMsg.setMessage(ShortMessage.NOTE_ON, 9, barTickNote, TransportPane.MAX_VOL);
					tickOffMsg.setMessage(ShortMessage.NOTE_ON, 9, barTickNote, 0);
					track.add(new MidiEvent(tickOnMsg, ticks));
					track.add(new MidiEvent(tickOffMsg, ticks+2));//arbitrary, for now
				}else{
					tickOnMsg.setMessage(ShortMessage.NOTE_ON, 9, beatTickNote, TransportPane.DEFAULT_VOL);
					tickOffMsg.setMessage(ShortMessage.NOTE_ON, 9, beatTickNote, 0);
					track.add(new MidiEvent(tickOnMsg, ticks));
					track.add(new MidiEvent(tickOffMsg, ticks+LessonData.getBeatDuration()));//arbitrary, for now
				}
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
			ticks+=LessonData.getBeatDuration();
		}
		if(!precount){
			ShortMessage tickOnMsg=new ShortMessage();
			ShortMessage tickOffMsg=new ShortMessage();
			try {
				tickOnMsg.setMessage(ShortMessage.NOTE_ON, 9, barTickNote, TransportPane.MAX_VOL);
				tickOffMsg.setMessage(ShortMessage.NOTE_ON, 9, barTickNote, 0);
			} catch (InvalidMidiDataException e) {
				e.printStackTrace();
			}
			track.add(new MidiEvent(tickOnMsg, ticks));
			track.add(new MidiEvent(tickOffMsg, ticks+2));//arbitrary, for now			
		}
	}
}