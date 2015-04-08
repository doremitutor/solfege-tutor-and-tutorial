package com.doremitutor.scoreDisplay;

import javax.swing.JPanel;

public class Interfaces{
	private Interfaces(){assert false:"Utility case";}

	public interface Cluster{
		JPanel getPane();
	}
	public interface BarFigure extends Cluster{}
}