package com.doremitutor.sound;

import java.awt.Dimension;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.doremitutor.support.Texts;
import com.doremitutor.support.Utilities;
import com.doremitutor.support.Texts.Text;

public class ConsonanceJFrame extends JFrame{
	public static void main(String[] args){
		ConsonancePanel.LANG=Texts.getLang(args[0]);
		try {
			SwingUtilities.invokeAndWait(new Runnable(){
				public void run(){
					new ConsonanceJFrame();
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	private ConsonanceJFrame(){
        setTitle(Texts.getText(Text.CONSONANCE_TITLE, ConsonancePanel.LANG)+Texts.TITLE_TRAILER);
        Utilities.setUpApp(this, new Dimension(750, 480), ConsonancePanel.getInstance());
	}
}