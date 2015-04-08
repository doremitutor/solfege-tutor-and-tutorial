package com.doremitutor.scoreDisplay;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

import com.doremitutor.data.ScoreConstants;
import com.doremitutor.persistence.ProtoBar.BarLineType;



class BarLine extends JPanel {
	BarLineType type;
	private int width;

	BarLine(BarLineType type) {
		setOpaque(false);
		setFocusable(false);
		setLayout(null);
		this.type=type;
		setDimension();
	}
	private void setDimension(){
		switch (this.type) {
		case SINGLE:
			width = 1;
			break;
		case DOUBLE:
			width = 4; // though I don't need it yet...
			break;
		case START_REPEAT:
			// I don't need it yet...
			break;
		case END_REPEAT:
			// I don't need it yet...
			break;
		case FINAL:
			width = 7;
			break;
		}
		Dimension size = new Dimension(width, ScoreConstants.STAFF_HEIGHT);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		setSize(size);		
	}
	int getFixedWidth(){
		return width;
	}
	void setType(BarLineType type){
		this.type=type;
		setDimension();
	}
	BarLineType getType(){
		return type;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		double upperY=ScoreConstants.STAFF_HEIGHT * 0.3;
		double lowerY=ScoreConstants.STAFF_HEIGHT * 0.7;
		Line2D.Double slimLine;
		Line2D.Double fatLine;
		switch (type) {
		case SINGLE:
			slimLine = new Line2D.Double(0, upperY, 0, lowerY);
			g2.draw(slimLine);
			break;
		case DOUBLE:
			// I don't need it yet...
			break;
		case START_REPEAT:
			// I don't need it yet...
			break;
		case END_REPEAT:
			// I don't need it yet...
			break;
		case FINAL:
			slimLine = new Line2D.Double(0, upperY, 0, lowerY);
			g2.draw(slimLine);
			BasicStroke bs = new BasicStroke(4f, BasicStroke.CAP_BUTT, BasicStroke.CAP_BUTT);
			g2.setStroke(bs);
			fatLine = new Line2D.Double(5, upperY, 5, lowerY);
			g2.draw(fatLine);
			break;
		}
	}
}