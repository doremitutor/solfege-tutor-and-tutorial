package com.doremitutor.data;


import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.doremitutor.persistence.Constants.Cleff;
import com.doremitutor.persistence.Constants.Pitch;
import com.doremitutor.persistence.Constants.Rhythm;
import com.doremitutor.support.GraphicShape;


public final class RhythmData {
	private RhythmData(){assert false:"Monostate case";}
	
	public static int getRhythmDuration(Rhythm rhythm){
		switch(rhythm){
		case WHOLE_DOTTED: return 72;
		case WHOLE: return 48;
		case HALF_DOUBLE_DOTTED: return 42;
		case HALF_DOTTED: return 36;
		case HALF: return 24;
		case QUARTER_DOUBLE_DOTTED: return 21;
		case QUARTER_DOTTED: return 18;
		case QUARTER: return 12;
		case QUARTER_TRIPLET: return 8;
		case EIGHT_DOTTED: return 9;
		case EIGHT: return 6;
		case EIGHT_TRIPLET: return 4;
		case SIXTEENTH: return 3;
		case SIXTEENTH_TRIPLET: return 2;
		default: throw new AssertionError("Unknown rhythm:"+rhythm);
		}
	}	
	public static Rhythm getRhythmByDuration(int duration){
		return rhythmByDuration.get(duration);
	}
	private static Map<Integer, Rhythm> rhythmByDuration=new HashMap<Integer, Rhythm>();
	static{
		for(Rhythm rhythm:Rhythm.values()){
			rhythmByDuration.put(RhythmData.getRhythmDuration(rhythm), Rhythm.valueOf(rhythm.toString()));
		}
	}
	public static PadFactor padFactor(Rhythm rhythm){
		final double minPad=0.008;
		switch(rhythm){
		case WHOLE: return new PadFactor				(minPad,	0.4);
		case HALF_DOUBLE_DOTTED: return new PadFactor	(minPad,	0.35);	
		case HALF_DOTTED: return new PadFactor			(minPad,	0.3);
		case HALF: return new PadFactor					(minPad,	0.2);
		case QUARTER_DOUBLE_DOTTED: return new PadFactor(minPad,	0.175);
		case QUARTER_DOTTED: return new PadFactor		(minPad,	0.15);
		case QUARTER: return new PadFactor				(minPad,	0.1);
		case EIGHT: return new PadFactor				(minPad,	0.1);
		case SIXTEENTH: return new PadFactor			(minPad,	0.05);
		case WHOLE_DOTTED: return new PadFactor(1d/8, 1d/2);
		case QUARTER_TRIPLET: return new PadFactor(1d/20, 1d/4);
		case EIGHT_DOTTED: return new PadFactor(1d/36, 1d/16);
		case EIGHT_TRIPLET: return new PadFactor(1d/20, 1d/4);
		case SIXTEENTH_TRIPLET: return new PadFactor(1d/20, 1d/4);
		default: throw new AssertionError("Unknown rhythm:"+rhythm);
		}
	}	
	public static SilentShapeInfo getShapeInfo(Rhythm rhythm){
		return new SilentShapeInfo(rhythm);
	}
	public static PitchedShapeInfo getShapeInfo(Rhythm rhythm, Pitch pitch, Cleff cleff){
		return new PitchedShapeInfo(rhythm, pitch, cleff);
	}
	public static EnumSet<Rhythm> noteHeadOnly=EnumSet.of(Rhythm.WHOLE_DOTTED, Rhythm.WHOLE);
	public static EnumSet<Rhythm> stemOnly=EnumSet.of(Rhythm.HALF_DOUBLE_DOTTED, Rhythm.HALF_DOTTED, Rhythm.HALF, Rhythm.QUARTER_DOUBLE_DOTTED, Rhythm.QUARTER_DOTTED, Rhythm.QUARTER, Rhythm.QUARTER_TRIPLET);
	public static EnumSet<Rhythm> doubleFlagged=EnumSet.of(Rhythm.SIXTEENTH, Rhythm.SIXTEENTH_TRIPLET);
	public static EnumSet<Rhythm> singleDotted=EnumSet.of(Rhythm.WHOLE_DOTTED, Rhythm.HALF_DOUBLE_DOTTED, Rhythm.HALF_DOTTED, Rhythm.QUARTER_DOUBLE_DOTTED, Rhythm.QUARTER_DOTTED, Rhythm.EIGHT_DOTTED);
	public static EnumSet<Rhythm> dualDotted=EnumSet.of(Rhythm.HALF_DOUBLE_DOTTED, Rhythm.QUARTER_DOUBLE_DOTTED);
	private enum NoteAdornment{NONE, STEM, FLAG, DOUBLE_FLAG}
	public static Map<Rhythm, NoteAdornment> noteAdornments=new EnumMap<Rhythm, NoteAdornment>(Rhythm.class);
	static{
		noteAdornments.put(Rhythm.WHOLE_DOTTED, NoteAdornment.NONE);
		noteAdornments.put(Rhythm.WHOLE, NoteAdornment.NONE);
		noteAdornments.put(Rhythm.HALF_DOUBLE_DOTTED, NoteAdornment.STEM);
		noteAdornments.put(Rhythm.HALF_DOTTED, NoteAdornment.STEM);
		noteAdornments.put(Rhythm.HALF, NoteAdornment.STEM);
		noteAdornments.put(Rhythm.QUARTER_DOUBLE_DOTTED, NoteAdornment.STEM);
		noteAdornments.put(Rhythm.QUARTER_DOTTED, NoteAdornment.STEM);
		noteAdornments.put(Rhythm.QUARTER, NoteAdornment.STEM);
		noteAdornments.put(Rhythm.QUARTER_TRIPLET, NoteAdornment.STEM);
		noteAdornments.put(Rhythm.EIGHT_DOTTED, NoteAdornment.FLAG);
		noteAdornments.put(Rhythm.EIGHT, NoteAdornment.FLAG);
		noteAdornments.put(Rhythm.EIGHT_TRIPLET, NoteAdornment.FLAG);
		noteAdornments.put(Rhythm.SIXTEENTH, NoteAdornment.DOUBLE_FLAG);
		noteAdornments.put(Rhythm.SIXTEENTH_TRIPLET, NoteAdornment.DOUBLE_FLAG);		
	}
	public enum LedgerLine{OVER(5), STUCK_ABOVE(0), ABOVE(-5), FAR_ABOVE(-10), STUCK_BELOW(10), BELOW(15), FAR_BELOW(20);
		public final int y;
		private LedgerLine(int y){
			this.y=y;
		}
	}
	public static final class PadFactor{
		public final double min;
		public final double max;
		private PadFactor(double min, double max){
			this.min=min;
			this.max=max;
		}
	}	
	public static abstract class ShapeInfo {
		protected GraphicShape shape;
		protected int y;
		public GraphicShape getShape(){
			return shape;
		}
		public int getY(){
			return y;
		}
	}
	public static final class SilentShapeInfo extends ShapeInfo{
		private SilentShapeInfo(Rhythm rhythm){
			switch(rhythm){
			case WHOLE_DOTTED:
			case WHOLE:
			case HALF_DOUBLE_DOTTED:
			case HALF_DOTTED:
			case HALF:
				shape=new GraphicShape.RectangularRest();
				y=rhythm==Rhythm.WHOLE_DOTTED||rhythm==Rhythm.WHOLE?41:45;
				break;
			case QUARTER_DOUBLE_DOTTED:
			case QUARTER_DOTTED:
			case QUARTER:
			case QUARTER_TRIPLET:
				shape=new GraphicShape.QuarterRest();
				y=34;
				break;
			case EIGHT_DOTTED:
			case EIGHT:
			case EIGHT_TRIPLET:
				shape=new GraphicShape.EightRest();
				y=40;
				break;
			default:
				shape=new GraphicShape.SixteenthRest();
				y=40;
				break;
			}
		}
	}
	public static final class PitchedShapeInfo extends ShapeInfo{
		public final boolean isUpper;
		public final boolean isOverLine;
		public final LedgerLine[] ledgerLines;
		
		PitchedShapeInfo(Rhythm rhythm, Pitch pitch, Cleff cleff){
			int staffPosition=PitchData.notePosition(pitch)-CleffData.vertOffSet(cleff);
			y=staffPosition*ScoreConstants.LINE_SEP/2;
			isUpper=staffPosition<10;
			isOverLine=staffPosition%2==1;
			switch(rhythm){
			case WHOLE_DOTTED:
			case WHOLE:
				shape=new GraphicShape.WholeNoteHead();
				break;
			case HALF_DOUBLE_DOTTED:
			case HALF_DOTTED:
			case HALF:
				shape=new GraphicShape.HalfNoteHead();
				break;
			default:
				shape= new GraphicShape.SolidNoteHead();//new GraphicShape.;getClonedSolidNoteHead()
				break;
			}
			List<LedgerLine> ledgerLineList=new ArrayList<LedgerLine>();
			switch(staffPosition){
			case 0:
				ledgerLineList.add(LedgerLine.STUCK_BELOW);
				ledgerLineList.add(LedgerLine.FAR_BELOW);
				break;
			case 1:
				ledgerLineList.add(LedgerLine.OVER);
				ledgerLineList.add(LedgerLine.BELOW);
				break;
			case 2:
				ledgerLineList.add(LedgerLine.STUCK_BELOW);
				break;
			case 3:
				ledgerLineList.add(LedgerLine.OVER);
				break;
			case 15:
				ledgerLineList.add(LedgerLine.OVER);
				break;
			case 16:
				ledgerLineList.add(LedgerLine.STUCK_ABOVE);
				break;
			case 17:
				ledgerLineList.add(LedgerLine.OVER);
				ledgerLineList.add(LedgerLine.ABOVE);
				break;
			case 18:
				ledgerLineList.add(LedgerLine.STUCK_ABOVE);
				ledgerLineList.add(LedgerLine.FAR_ABOVE);
				break;
			default:
				break;
			}
			ledgerLines=ledgerLineList.toArray(new LedgerLine[0]);
		}
	}
}