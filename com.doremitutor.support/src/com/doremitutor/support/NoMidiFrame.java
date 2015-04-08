package com.doremitutor.support;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.doremitutor.support.Texts.Lang;
import com.doremitutor.support.Texts.Text;

public class NoMidiFrame extends JFrame{
	private static NoMidiFrame frame;
	static Lang lang;
	
	private NoMidiFrame(Lang lang){
		assert frame==null:"Singleton case";
		final Box box=Box.createVerticalBox();
		box.setOpaque(true);
		final String[] msg=Texts.getText(Text.NO_MIDI_NOTIFICATION, lang).split("-");
		final Label label0=new Label(msg[0]);
		final Label label1=new Label(msg[1]);
		final Label label2=new Label(msg[2]);
		final Label label3=new Label(msg[3]);
		
		box.add(label0);
		box.add(label1);
		box.add(label2);
		box.add(label3);
        setTitle(Texts.getText(Text.NO_MIDI_WARNING_TITLE, lang)+" - www.doremitutor.com");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Utilities.setUpApp(this, new Dimension(500, 160), box);
	}
	private class Label extends JLabel{
		private Label(String text){
			super(text);
			setAlignmentX(Component.CENTER_ALIGNMENT);
			setFont(new Font(Font.SERIF, Font.BOLD, 25));
		}
	}
}
