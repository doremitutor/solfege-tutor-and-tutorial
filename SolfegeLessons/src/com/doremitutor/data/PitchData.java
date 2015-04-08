package com.doremitutor.data;

import com.doremitutor.persistence.Constants.Pitch;

public class PitchData {
	private PitchData(){assert false:"Monostate case";}
	public static String noteName(Pitch pitch){
		switch(pitch){
		case B1FLAT: case B1: case B1SHARP: case B2FLAT: case B2: case B2SHARP: case B3FLAT: case B3: case B3SHARP:
		case B4FLAT: case B4: case B4SHARP: case B5FLAT: case B5: case B5SHARP:
			return "Si";
		case C2FLAT: case C2: case C2SHARP: case C3FLAT: case C3: case C3SHARP:	case C4FLAT: case C4: case C4SHARP:
		case C5FLAT: case C5: case C5SHARP: case C6FLAT: case C6: case C6SHARP:
			return "Do";
		case D2FLAT: case D2: case D2SHARP: case D3FLAT: case D3: case D3SHARP:	case D4FLAT: case D4: case D4SHARP:
		case D5FLAT: case D5: case D5SHARP: case D6FLAT: case D6: case D6SHARP:
			return "Re";
		case E2FLAT: case E2: case E2SHARP: case E3FLAT: case E3: case E3SHARP:	case E4FLAT: case E4: case E4SHARP:
		case E5FLAT: case E5: case E5SHARP:
			return "Mi";
		case F2FLAT: case F2: case F2SHARP: case F3FLAT: case F3: case F3SHARP:	case F4FLAT: case F4: case F4SHARP:
		case F5FLAT: case F5: case F5SHARP:
			return "Fa";
		case G2FLAT: case G2: case G2SHARP: case G3FLAT: case G3: case G3SHARP:	case G4FLAT: case G4: case G4SHARP:
		case G5FLAT: case G5: case G5SHARP:
			return "Sol";
		case A2FLAT: case A2: case A2SHARP: case A3FLAT: case A3: case A3SHARP:	case A4FLAT: case A4: case A4SHARP:
		case A5FLAT: case A5: case A5SHARP:
			return "La";
		default: throw new AssertionError("Unknown pitch: "+pitch);
		}	
	}
	public static int midiNote(Pitch pitch){
		switch(pitch){
		case B1FLAT:
			return 34;		
		case B1: case C2FLAT:
			return 35;
		case B1SHARP: case C2:
			return 36;		
		case C2SHARP: case D2FLAT:
			return 37;
		case D2:
			return 38;
		case D2SHARP: case E2FLAT:
			return 39;
		case E2: case F2FLAT:
			return 40;
		case E2SHARP: case F2:
			return 41;
		case F2SHARP: case G2FLAT:
			return 42;
		case G2:
			return 43; 
		case G2SHARP: case A2FLAT:
			return 44;
		case A2:
			return 45;
		case A2SHARP: case B2FLAT:
			return 46;			
		case B2: case C3FLAT:
			return 47;
		case B2SHARP: case C3:
			return 48;		
		case C3SHARP: case D3FLAT:
			return 49;
		case D3:
			return 50;
		case D3SHARP: case E3FLAT:
			return 51;
		case E3: case F3FLAT:
			return 52;
		case E3SHARP: case F3:
			return 53;
		case F3SHARP: case G3FLAT:
			return 54;
		case G3:
			return 55; 
		case G3SHARP: case A3FLAT:
			return 56;
		case A3:
			return 57;
		case A3SHARP: case B3FLAT:
			return 58;			
		case B3: case C4FLAT:
			return 59;
		case B3SHARP: case C4:
			return 60;		
		case C4SHARP: case D4FLAT:
			return 61;
		case D4:
			return 62;
		case D4SHARP: case E4FLAT:
			return 63;
		case E4: case F4FLAT:
			return 64;
		case E4SHARP: case F4:
			return 65;
		case F4SHARP: case G4FLAT:
			return 66;
		case G4:
			return 67;
		case G4SHARP: case A4FLAT:
			return 68;
		case A4:
			return 69;
		case A4SHARP: case B4FLAT:
			return 70;			
		case B4: case C5FLAT:
			return 71;
		case B4SHARP: case C5:
			return 72;		
		case C5SHARP: case D5FLAT:
			return 73;
		case D5:
			return 74;
		case D5SHARP: case E5FLAT:
			return 75;
		case E5: case F5FLAT:
			return 76;
		case E5SHARP: case F5:
			return 77;
		case F5SHARP: case G5FLAT:
			return 78;
		case G5:
			return 79; 
		case G5SHARP: case A5FLAT:
			return 80;
		case A5:
			return 81;
		case A5SHARP: case B5FLAT:
			return 82;	
		case B5: case C6FLAT:
			return 83;
		case B5SHARP: case C6:
			return 84;			
		case C6SHARP: case D6FLAT:
			return 85;
		case D6:
			return 86;
		case D6SHARP:		
			return 87;
		default: throw new AssertionError("Unknown pitch: "+pitch);
		}
	}
	public static int notePosition(Pitch pitch){
		switch(pitch){
		case B1FLAT: case B1: case B1SHARP:
			return 30;
		case C2FLAT: case C2: case C2SHARP:
			return 29;
		case D2FLAT: case D2: case D2SHARP:
			return 28;
		case E2FLAT: case E2: case E2SHARP:
			return 27;
		case F2FLAT: case F2: case F2SHARP:
			return 26;
		case G2FLAT: case G2: case G2SHARP:
			return 25;
		case A2FLAT: case A2: case A2SHARP:
			return 24;
		case B2FLAT: case B2: case B2SHARP:
			return 23;
		case C3FLAT: case C3: case C3SHARP:
			return 22;
		case D3FLAT: case D3: case D3SHARP:
			return 21;
		case E3FLAT: case E3: case E3SHARP:
			return 20;
		case F3FLAT: case F3: case F3SHARP:
			return 19;
		case G3FLAT: case G3: case G3SHARP:
			return 18;
		case A3FLAT: case A3: case A3SHARP:
			return 17;
		case B3FLAT: case B3: case B3SHARP:
			return 16;
		case C4FLAT: case C4: case C4SHARP:
			return 15;
		case D4FLAT: case D4: case D4SHARP:
			return 14;
		case E4FLAT: case E4: case E4SHARP:
			return 13;
		case F4FLAT: case F4: case F4SHARP:
			return 12;		
		case G4FLAT: case G4: case G4SHARP:
			return 11;		
		case A4FLAT: case A4: case A4SHARP:
			return 10;
		case B4FLAT: case B4: case B4SHARP:
			return 9;
		case C5FLAT: case C5: case C5SHARP:
			return 8;
		case D5FLAT: case D5: case D5SHARP:
			return 7;
		case E5FLAT: case E5: case E5SHARP:
			return 6;
		case F5FLAT: case F5: case F5SHARP:
			return 5;
		case G5FLAT: case G5: case G5SHARP:
			return 4;
		case A5FLAT: case A5: case A5SHARP:
			return 3;
		case B5FLAT: case B5: case B5SHARP:
			return 2;
		case C6FLAT: case C6: case C6SHARP:
			return 1;
		case D6FLAT: case D6: case D6SHARP:
			return 0;
		default: throw new AssertionError("Unknown pitch: "+pitch);
		}		
	}
}