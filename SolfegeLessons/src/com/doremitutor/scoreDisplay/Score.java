package com.doremitutor.scoreDisplay;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.doremitutor.persistence.ProtoBar;
import com.doremitutor.persistence.ProtoLesson;

public final class Score{
	private Score(){assert false:"Monostate case";}
	/*public static Cleff cleff;
	public static Time time;
	public static Rhythm beatRhythm;
	public static int beatDuration;
	public static int subBeatDuration;
	public static int beatsPerBar;
	public static int barDuration;
	private static int tempo;*/
		
	public static final JPanel staffPanel=new JPanel(){
		{			
			FrameWorkUtilities.makeTransparentAndUnfocusable(this);
			setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));	
		}
		public Component add(Component c){
			assert c instanceof Staff:"Only Staff instances allowed";
			return super.add(c);
		}
		public Component add(Component c, int index){
			assert c instanceof Staff:"Only Staff instances allowed";
			return super.add(c, index);
		}
	};

	public static void loadLessonContent(final ProtoLesson protoLesson){
		List<ProtoBar> protoBars=new ArrayList<ProtoBar>(){
			{
				for(ProtoBar protoBar:protoLesson.protoBars){
					add(protoBar);
				}
			}
		};
		ListIterator<ProtoBar> iterator=protoBars.listIterator();
		Bar initBar=new Bar(iterator.next());
		Staff initStaff=new Staff(initBar);
		staffPanel.add(initStaff);
		while(iterator.hasNext()){
			Bar newBar=new Bar(iterator.next());
			initStaff.barPane.add(newBar);
		}
		FrameWorkUtilities.springComponents(initStaff.barPane, true);
		FrameWorkUtilities.rectifyStaffWidth(initStaff);
	}
	public static List<Figure> readFigures(){
		List<Figure> figures=new ArrayList<Figure>();
		for(Bar bar:getBars()){
			for(Figure figure:bar.readFigures()){
				figures.add(figure);
			}
		}
		return Collections.unmodifiableList(figures);
	}
	public static List<Bar> getBars(){
		return new ArrayList<Bar>(){{ 
			for(Staff staff:readStaves()){
				for(Bar bar:staff.readBars()){
					add(bar);				
				}
			}
		}};
	}
	public static List<Staff> readStaves(){
		List<Staff> staves=new ArrayList<Staff>();
			for(Component c:Score.staffPanel.getComponents()){
				staves.add((Staff)c);
			}
		return Collections.unmodifiableList(staves);
	}
}