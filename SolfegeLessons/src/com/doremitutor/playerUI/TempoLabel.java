package com.doremitutor.playerUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import com.doremitutor.scoreDisplay.Score;
import com.doremitutor.scoreDisplay.Staff;


public class TempoLabel{
	private TempoLabel(){assert false:"Monostate case";}
	static private Icon icon=new ImageIcon(new BufferedImage(11, 25, BufferedImage.TYPE_INT_ARGB){
		{			
			Graphics2D g2=(Graphics2D)getGraphics();
			g2.setColor(Color.BLACK);
			AffineTransform savedTransform=g2.getTransform();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Ellipse2D e=new Ellipse2D.Double(1, 17, getWidth(), 7);
			g2.rotate(-0.125*Math.PI, 6, 22);
			g2.fill(e);
			g2.setTransform(savedTransform);
			g2.drawLine(getWidth()-1,0, getWidth()-1, 17);
		}
	});
	private static String prefix="= ";
	private static JLabel label=new JLabel();
	static{
		label.setIcon(icon);
		label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		Staff firstStaff=(Staff)Score.staffPanel.getComponent(0);
		SpringLayout lo=(SpringLayout)firstStaff.getLayout();
		firstStaff.add(label);
		lo.putConstraint(SpringLayout.WEST, label, 60, SpringLayout.WEST, firstStaff);
		lo.putConstraint(SpringLayout.NORTH, label, 0, SpringLayout.NORTH, firstStaff);		
	}
	public static void setTempoText(int tempo){
		label.setText(prefix+tempo);
	}
}