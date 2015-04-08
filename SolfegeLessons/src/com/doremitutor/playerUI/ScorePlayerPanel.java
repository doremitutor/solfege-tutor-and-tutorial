package com.doremitutor.playerUI;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;








import com.doremitutor.data.LessonData;
import com.doremitutor.scoreDisplay.Score;
import com.doremitutor.support.Utilities;

public class ScorePlayerPanel extends JPanel{
	
	private static ScorePlayerPanel instance=null;
	static ScorePlayerPanel getInstance(boolean isJFrame, Dimension dim){
		assert instance==null:"Singleton case";
		return instance=new ScorePlayerPanel(isJFrame, dim);
	}
	static ScorePlayerPanel getInstance(){
		assert instance!=null:"Too early";
		return instance;
	}
	
	private ScorePlayerPanel(boolean isJFrame, Dimension dim){
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		if(isJFrame){
	        add(new JLabel(LessonData.getJFrameTitle()){
	        	{
	        		setFont(new Font(Font.SERIF, Font.BOLD, 24));
	        		setAlignmentX(0.5f);
	        	}
	        });
		}else{
			Utilities.setComponentSize(this, dim);
		}
        add(Box.createVerticalGlue());
        add(Score.staffPanel);
        add(Box.createVerticalGlue());
        add(TransportPane.pane);
	}
}
