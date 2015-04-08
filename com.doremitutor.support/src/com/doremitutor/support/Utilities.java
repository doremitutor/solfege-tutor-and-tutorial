package com.doremitutor.support;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;

public final class Utilities {
	private Utilities(){assert false:"Monostate case";}
	public static final Color PROJECT_COLOR=new Color(184, 228, 250);//0xff, 0xcc, 0x99, aqua: 183, 221, 232 //
	public static Point getAppLocation(Dimension appSize){
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		return new Point(screenSize.width/2-appSize.width/2, screenSize.height/2-appSize.height/2);		
	}
	public static void setUpApp(Container topContainer, Dimension dim, JComponent contentPane){
		contentPane.setFocusable(false);
		contentPane.setOpaque(true);
		contentPane.setBackground(PROJECT_COLOR);
		if(topContainer instanceof JFrame){
			JFrame jFrame=(JFrame)topContainer;
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Dimension jFrameDim=new Dimension(dim.width+6, dim.height+28/* formerly +32*/);
			setComponentSize(jFrame, jFrameDim);
			jFrame.setResizable(false);
			jFrame.setLocation(Utilities.getAppLocation(jFrameDim));
			jFrame.setContentPane(contentPane);
			jFrame.pack();
			jFrame.setVisible(true);	
		}
		else if(topContainer instanceof JApplet){
			((JApplet)topContainer).setContentPane(contentPane);
		}else{
			throw new AssertionError("Invalid Container");
		}			
	}
	public static void setComponentSize(Component comp, Dimension dim){
		comp.setSize(dim);
		comp.setMinimumSize(dim);
		comp.setPreferredSize(dim);
		comp.setMaximumSize(dim);		
	}
	public static ResourceBundle getResourceBundle(String lang){
		return ResourceBundle.getBundle("Messages", new Locale(lang));
	}
}