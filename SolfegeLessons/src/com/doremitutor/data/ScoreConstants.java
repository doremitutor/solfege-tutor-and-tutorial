package com.doremitutor.data;

public final class ScoreConstants {
	private ScoreConstants(){assert false:"Monostate case";}
	public static final int STAFF_HEIGHT = 101;
	public static final int LINE_SEP = 10;
	public static final int STEM_HIGHT = 34;

	public static enum BasicShape{WHOLE_SHAPE, HALF_SHAPE, QUARTER_SHAPE, EIGHT_SHAPE, SIXTEENTH_SHAPE}
	public static enum Modifier{NONE, DOT, DOUBLE_DOT, TRIPLET}
	public static enum Octave{ONE, TWO, THREE, FOUR, FIVE, SIX}
	public static enum PitchClass{C, D, E, F, G, A, B, REST}	
}