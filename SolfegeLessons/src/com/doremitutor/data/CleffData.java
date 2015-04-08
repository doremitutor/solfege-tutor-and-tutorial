package com.doremitutor.data;

import java.util.EnumSet;







import javax.swing.JPanel;

import com.doremitutor.data.ScoreConstants.Octave;
import com.doremitutor.persistence.Constants.Cleff;
import com.doremitutor.persistence.Constants.Pitch;
import com.doremitutor.support.GraphicShape;



public class CleffData {
	private CleffData(){assert false:"Monostate case";}
	public static int vertOffSet(Cleff cleff){
		switch(cleff){
		case BASS: return 19;
		case TREBLE: return 0;
		default: throw new AssertionError("Unknown cleff: "+cleff);
		}
	}
	public static Octave initOctave(Cleff cleff){
		switch(cleff){
		case BASS: return Octave.TWO;
		case TREBLE: return Octave.FOUR;
		default: throw new AssertionError("Unknown cleff: "+cleff);
		}		
	}
	public static int panelWidth(Cleff cleff){
		switch(cleff){
		case BASS: return 30;
		case TREBLE: return 40;
		default: throw new AssertionError("Unknown cleff: "+cleff);
		}		
	}
	public static JPanel getPanel(Cleff cleff){
		switch(cleff){
		case BASS:
			return new GraphicShape.BassCleff(){{setLocation(4, 31);}};
		case TREBLE:
			return new GraphicShape.TrebleCleff(){{setLocation(4, 10);}};
		default:
			throw new AssertionError("Unexpected Cleff");
		}
	}
	public static EnumSet<Pitch> getPitchSet(Cleff cleff){
		switch(cleff){
		case TREBLE:
			return EnumSet.range(Pitch.G3FLAT, Pitch.D6SHARP);
		case BASS:
			return EnumSet.range(Pitch.B1FLAT, Pitch.F4SHARP);
			default:
				throw new AssertionError("Unknown cleff");
		}
	}
}