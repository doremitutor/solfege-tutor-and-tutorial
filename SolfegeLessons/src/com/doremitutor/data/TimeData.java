package com.doremitutor.data;

import static com.doremitutor.persistence.Constants.Rhythm.*;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import com.doremitutor.persistence.Constants.Rhythm;
import com.doremitutor.persistence.Constants.Time;


public final class TimeData {
	private TimeData(){assert false:"Monostate case";}
	public enum Beat{ONE, TWO, THREE, FOUR}
	public static Rhythm beatRhythm(Time time){
		switch(time){
		case T24: case T34: case T44:
			return QUARTER;
		case T22:
			return HALF;
		case T38:
			return EIGHT;
		case T68: case T98: case T128:
			return QUARTER_DOTTED;
		default: throw new AssertionError("Unknown time :"+time);	
		}
	}
	public static int beatsPerBar(Time time){
		switch(time){
		case T24: case T22:
			return 2;
		case T38: case T34: case T98:
			return 3;
		case T44: case T128:
			return 4;
		default: throw new AssertionError("Unknown time :"+time);
		}		
	}
	public static int subBeatsPerBeat(Time time){
		switch(time){
		case T24: case T34: case T44: case T22: case T38:
			return 2;
		case T68: case T98: case T128:
			return 3;
		default: throw new AssertionError("Unknown time :"+time);		
		}		
	}
	public static JPanel getPanel(Time time){
		switch(time){
		case T24:
			return getPanel(13, "2", "4", 0, 49, 0, 69);
		case T34:
			return getPanel(13, "3", "4", 0, 49, 0, 69);
		case T44:
			return getPanel(13, "4", "4", 0, 49, 0, 69);
		case T22:
			return getPanel(13, "2", "2", 0, 49, 0, 69);
		case T38:
			return getPanel(13, "3", "8", 0, 49, 0, 69);
		case T68:
			return getPanel(13, "6", "8", 0, 49, 0, 69);
		case T98:
			return getPanel(13, "94", "8", 0, 49, 0, 69);
		case T128:
			return getPanel(13, "12", "8", 0, 49, 0, 69);
		}
		throw new AssertionError("Unknown Time: "+time);
	}
	private static JPanel getPanel(final int width, final String top, final String bot, final int topX, final int topY, final int botX, final int botY){
		return new JPanel(){
			final Dimension size=new Dimension(width, ScoreConstants.STAFF_HEIGHT);
			public Dimension getPreferredSize(){return size;}
			public Dimension getMinimumSize(){return size;}
			public Dimension getMaximumSize(){return size;}
			public void paintComponent(Graphics g){
				super.paintComponents(g);
				Graphics2D g2=(Graphics2D)g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24f));
				g2.drawString(top, topX, topY);
				g2.drawString(bot, botX, botY);
			}
		};		
	}
}