package com.doremitutor.scoreDisplay;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.doremitutor.data.CleffData;
import com.doremitutor.data.LessonData;
import com.doremitutor.data.ScoreConstants;
import com.doremitutor.data.TimeData;
import com.doremitutor.persistence.ProtoBar;
import com.doremitutor.persistence.Constants.Cleff;



public class Staff extends JPanel{
	public enum BarWidthChange{SAME, EXPANDED, SHRUNK}
	private int numBarsReported=0;
	private int numBarsHosted=0;
	private int scoreWidth;
	public final JPanel barPane=new JPanel(){
		{
			addContainerListener(new ContainerAdapter(){
				public void componentAdded(ContainerEvent e){
					numBarsHosted=getNumBars();
				}
			});
		}
		public Component add(Component c){
			assert c instanceof Bar:"Only Bar instances allowed";
			return super.add(c);
		}
		public Component add(Component c, int index){
			assert c instanceof Bar:"Only Bar instances allowed";
			return super.add(c, index);
		}
	};
	final JPanel glassPane=new JPanel();
	public static boolean FIRST_STAFF=true;
	
	public Staff(Bar initBar){
		scoreWidth=LessonData.getScoreWidth();
		Dimension dim=new Dimension(scoreWidth, ScoreConstants.STAFF_HEIGHT);
		setMinimumSize(dim);
		setPreferredSize(dim);
		setMaximumSize(dim);
		FrameWorkUtilities.makeTransparentAndUnfocusableSpringContainer(this);
		final Cleff clef=LessonData.getClef();
		JPanel cleffPanel=new JPanel(null){
			{
				Dimension dim=new Dimension(CleffData.panelWidth(clef), ScoreConstants.STAFF_HEIGHT);
				setMinimumSize(dim);
				setPreferredSize(dim);
				setMaximumSize(dim);
			}
		};
		FrameWorkUtilities.makeTransparentAndUnfocusable(cleffPanel);
		add(cleffPanel);
		cleffPanel.add(CleffData.getPanel(clef));
		if(FIRST_STAFF){
			add(TimeData.getPanel(LessonData.getTime()));
			FIRST_STAFF=false;
		}
		FrameWorkUtilities.makeTransparentAndUnfocusableSpringContainer(barPane);
		add(barPane);
		FrameWorkUtilities.springComponents(this, true);
		barPane.add(initBar);	
		FrameWorkUtilities.springComponents(barPane, true);
		FrameWorkUtilities.makeTransparentAndUnfocusableSpringContainer(glassPane);
		add(glassPane);
		SpringLayout lo=(SpringLayout)getLayout(); 
		lo.putConstraint(SpringLayout.NORTH, glassPane, 0, SpringLayout.NORTH, barPane);
		lo.putConstraint(SpringLayout.EAST, glassPane, 0, SpringLayout.EAST, barPane);
		lo.putConstraint(SpringLayout.SOUTH, glassPane, 0, SpringLayout.SOUTH, barPane);
		lo.putConstraint(SpringLayout.WEST, glassPane, 0, SpringLayout.WEST, barPane);
	}
	public List<Bar> readBars(){
		return Collections.unmodifiableList(new ArrayList<Bar>(){{for(Component c:barPane.getComponents()){add((Bar)c);}}});
	}
	List<Figure> readFigures(){
		return Collections.unmodifiableList(new ArrayList<Figure>(){
			{				
				for(Bar bar:readBars()){
					for(Figure figure:bar.readFigures()){
						add(figure);
					}
				}
			}
		});
	}
	public int getWidthAllowance(){
		validate();
		return scoreWidth-((SpringLayout)getLayout()).minimumLayoutSize(this).width;
	}
	public BarWidthChange replaceBar(int index, ProtoBar protoBar){
		Bar newBar=new Bar(protoBar);
		int minWidthBefore=((Bar)(barPane.getComponent(index))).getMinimumWidth();
		int minWidthAfter=newBar.getMinimumWidth();
		barPane.remove(index);
		barPane.add(newBar, index);
		reSpringAndValidate();
		if(minWidthAfter==minWidthBefore){
			return BarWidthChange.SAME;
		}
		if(minWidthAfter>minWidthBefore){
			return BarWidthChange.EXPANDED;
		}
		return BarWidthChange.SHRUNK;
	}
	public void addBar(Bar bar, int index){
		barPane.add(bar, index);
		FrameWorkUtilities.springComponents(barPane, true);
		barPane.validate();
	}
	public void appendBarAtTheEnd(Bar bar){
		barPane.add(bar, barPane.getComponentCount());
		FrameWorkUtilities.springComponents(barPane, true);
	}
	public void deleteBar(int index){
		barPane.remove(index);
		if(!isEmpty()){
			reSpringAndValidate();			
		}
	}
	void reSpringAndValidate(){
		FrameWorkUtilities.springComponents(barPane, true);
		validate();
	}
	public int getFirstBarMinWidth(){
		return getExtremeBar(true).getMinimumWidth();
	}
	boolean isLineFeedTerminated(){
		Component[] c=barPane.getComponents();
		return ((Bar)c[c.length-1]).isLineFeed();
	}
	public boolean hasPrematureLineFeed(){
		boolean lineFeed=false;
		Component[] comp=barPane.getComponents();
		for(int i=0;i<comp.length-1;i++){
			assert comp[i] instanceof Bar;
			if(((Bar)comp[i]).isLineFeed()){				
				lineFeed=true;
				break;
			}
		}
		return lineFeed;
	}
	public Bar trimBar(boolean first){
		Bar bar=getExtremeBar(first);
		barPane.remove(bar);
		if(!isEmpty()){
			FrameWorkUtilities.springComponents(barPane, false);
		}		
		return bar;
	}
	public int getNumBars(){
		return barPane.getComponentCount(); 
	}
	public boolean isEmpty(){
		return barPane.getComponentCount()<1;
	}
	public Bar getExtremeBar(boolean first){
		assert !isEmpty():"Empty staff";
		return (Bar)barPane.getComponent(first?0:barPane.getComponentCount()-1);
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 3; i <= 7; i++) {
			g.drawLine(0, i * (ScoreConstants.LINE_SEP), scoreWidth - 1, i * (ScoreConstants.LINE_SEP));
		}
	}
	void countBars(){
		if(++numBarsReported==numBarsHosted){
			FrameWorkUtilities.drawLigatures(glassPane, readFigures());
			numBarsReported=0;
		}		
	}
}