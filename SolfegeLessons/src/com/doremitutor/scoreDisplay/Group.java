package com.doremitutor.scoreDisplay;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SpringLayout.Constraints;

import com.doremitutor.data.RhythmData;
import com.doremitutor.persistence.ProtoGroup;
import com.doremitutor.persistence.Constants.Rhythm;
import com.doremitutor.persistence.ProtoFigure.GroupProtoNote;
import com.doremitutor.scoreDisplay.Figure.GroupNote;
import com.doremitutor.scoreDisplay.Interfaces.Cluster;
import com.doremitutor.support.GraphicShape;


public class Group extends JPanel implements Cluster{
	private JPanel figurePane=new JPanel(){			
		@Override
		public Component add(Component c){
			assert c instanceof GroupNote:"Only groupNotes allowed";
			return super.add(c);
		}
		@Override
		public Component add(Component c, int index){
			assert c instanceof GroupNote:"Only groupNotes allowed";
			return super.add(c, index);
		}
	};
	private JPanel beamPane=new JPanel();
	public final ProtoGroup protoGroup;
	Group(ProtoGroup protoGroup){
		this.protoGroup=protoGroup;
		addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e){
				drawStems();
			}
		});
		FrameWorkUtilities.makeTransparentAndUnfocusableSpringContainer(this);
		FrameWorkUtilities.makeTransparentAndUnfocusableSpringContainer(figurePane);
		for(GroupProtoNote groupProtoNote:this.protoGroup.groupProtoNotes){
			GroupNote groupNote=new Figure.GroupNote(groupProtoNote);
			figurePane.add(groupNote);
		}
		FrameWorkUtilities.springComponents(figurePane, true);
		add(figurePane);
		FrameWorkUtilities.springComponents(this, true);
		FrameWorkUtilities.makeTransparentAndUnfocusableSpringContainer(beamPane);
		add(beamPane);		
		SpringLayout lo=(SpringLayout)getLayout();
		lo.putConstraint(SpringLayout.NORTH, beamPane, 0, SpringLayout.NORTH, this);
		lo.putConstraint(SpringLayout.EAST, beamPane, 0, SpringLayout.EAST, this);
		lo.putConstraint(SpringLayout.SOUTH, beamPane, 0, SpringLayout.SOUTH, this);
		lo.putConstraint(SpringLayout.WEST, beamPane, 0, SpringLayout.WEST, this);
	}
	public JPanel getPane(){
		return this;
	}
	public JPanel getFigurePane(){
		return figurePane;
	}
	public int getDuration(){
		int duration=0;
		for(GroupNote note:readFigures()){
			duration+=RhythmData.getRhythmDuration(note.rhythm);
		}
		return duration;
	}
	public List<GroupNote> readFigures(){
		List<GroupNote> groupNotes=new ArrayList<GroupNote>();
		for(Component c:getFigurePane().getComponents()){
			assert c instanceof GroupNote:"Non groupNote component in group figurePane";
			GroupNote gn=(GroupNote)c;
			groupNotes.add(gn);
		}
		return Collections.unmodifiableList(groupNotes);
	}
	private void drawStems(){
		beamPane.removeAll();
		SpringLayout figurePaneLo=(SpringLayout)figurePane.getLayout();
		List<GroupNote> notes=readFigures();
		int size=notes.size();
		Point[] points=new Point[size];
		for(int i=0;i<size;i++){
			points[i]=notes.get(i).getShapeCenterPoint();
		}
		int highest=0;
		int lowest=0;
		for(int i=0;i<points.length;i++){
			if(i==0){
				highest=lowest=points[i].y;
				continue;
			}		
			highest=Math.min(points[i].y, highest);
			lowest=Math.max(points[i].y, lowest);
		}
		final boolean up=highest-50>=50-lowest;
		int minStemHeight=32;
		final int beamY=up?highest-minStemHeight:lowest+minStemHeight;
		int beamInitX=0;
		int beamFinalX=0;
		int beamWidth=0;
		final SpringLayout beamPaneLo=(SpringLayout)beamPane.getLayout();
		Map<GroupNote, Integer> xAnchors=new HashMap<GroupNote, Integer>();
		for(GroupNote note:notes){			
			Point noteCenterPoint=note.getShapeCenterPoint();
			Constraints noteConstraints=figurePaneLo.getConstraints(note);
			int anchorX=noteCenterPoint.x+(up?7:-7)+noteConstraints.getX().getValue();
			xAnchors.put(note, anchorX);
			int anchorY=noteCenterPoint.y;
			int stemHeight=Math.abs(beamY-anchorY);
			GraphicShape stem=new GraphicShape.VariableStem(stemHeight);
			beamPane.add(stem);
			beamPaneLo.putConstraint(SpringLayout.WEST, stem, anchorX, SpringLayout.WEST, beamPane);
			beamPaneLo.putConstraint(up?SpringLayout.SOUTH:SpringLayout.NORTH, stem, anchorY, SpringLayout.NORTH, beamPane);
			int index=notes.indexOf(note);
			if(index==0){
				beamInitX=anchorX;
			}else if(index==notes.size()-1){
				beamFinalX=anchorX;
			}
			if(index!=0&&notes.size()>1){
				beamWidth=beamFinalX-beamInitX;
				GraphicShape beam=new GraphicShape.Beam(beamWidth);
				beamPane.add(beam);
				beamPaneLo.putConstraint(SpringLayout.WEST, beam, beamInitX, SpringLayout.WEST, beamPane);
				beamPaneLo.putConstraint(SpringLayout.NORTH, beam, beamY, SpringLayout.NORTH, beamPane);
				
			}
			if(notes.size()==1){
				int lonelyBeamWidth=10;
				GraphicShape beam=new GraphicShape.Beam(lonelyBeamWidth);
				beamPane.add(beam);
				beamPaneLo.putConstraint(SpringLayout.WEST, beam, beamInitX, SpringLayout.WEST, beamPane);
				beamPaneLo.putConstraint(SpringLayout.NORTH, beam, beamY, SpringLayout.NORTH, beamPane);
				if(notes.get(0).rhythm==Rhythm.SIXTEENTH){
					GraphicShape beam2=new GraphicShape.Beam(lonelyBeamWidth);
					beamPane.add(beam2);
					beamPaneLo.putConstraint(SpringLayout.WEST, beam2, beamInitX, SpringLayout.WEST, beamPane);
					beamPaneLo.putConstraint(SpringLayout.NORTH, beam2, beamY+(up?6:-6), SpringLayout.NORTH, beamPane);
				}
			}
		}
		final int shortBeamWidth=notes.size()>1?beamWidth/(notes.size()-1)/2+1:0;
		class SemiBeamMaker{
			void makeFw(int anchor, String edge){				
				GraphicShape beam2=new GraphicShape.Beam(shortBeamWidth);
				beamPane.add(beam2);
				beamPaneLo.putConstraint(edge, beam2, anchor, SpringLayout.WEST, beamPane);
				beamPaneLo.putConstraint(SpringLayout.NORTH, beam2, beamY+(up?6:-6), SpringLayout.NORTH, beamPane);
			}
			void makeBw(int anchor, String edge){				
				GraphicShape secondBeam=new GraphicShape.Beam(shortBeamWidth);
				beamPane.add(secondBeam);
				beamPaneLo.putConstraint(edge, secondBeam, anchor, SpringLayout.WEST, beamPane);
				beamPaneLo.putConstraint(SpringLayout.NORTH, secondBeam, beamY+(up?6:-6), SpringLayout.NORTH, beamPane);
			}			
		}
		SemiBeamMaker secBM=new SemiBeamMaker();
		for(int i=0;i<notes.size();i++){
			if(notes.get(i).rhythm==Rhythm.SIXTEENTH){
				int numNotes=notes.size();
				if(i==0&&numNotes>1&&notes.get(i).rhythm==Rhythm.SIXTEENTH){
					Rhythm nextRhythm=notes.get(i+1).rhythm;
					if(nextRhythm==Rhythm.EIGHT||nextRhythm==Rhythm.EIGHT_DOTTED){
						secBM.makeFw(xAnchors.get(notes.get(i)), SpringLayout.WEST);
					}
				}
				if(i==numNotes-1&&numNotes>1&&notes.get(i).rhythm==Rhythm.SIXTEENTH){					
					Rhythm nextRhythm=notes.get(i-1).rhythm;
					if(nextRhythm==Rhythm.EIGHT||nextRhythm==Rhythm.EIGHT_DOTTED){
						secBM.makeFw(xAnchors.get(notes.get(i)), SpringLayout.EAST);
					}
				}
				if(i>0){//not first					
					if(numNotes==2||numNotes>2&&notes.get(i-1).rhythm==Rhythm.SIXTEENTH){
						secBM.makeBw(xAnchors.get(notes.get(i)), SpringLayout.EAST);
					}						
				}
				if(i<numNotes-1){//not last	
					if(numNotes==2||numNotes>2&&notes.get(i+1).rhythm==Rhythm.SIXTEENTH){
						secBM.makeFw(xAnchors.get(notes.get(i)), SpringLayout.WEST);
					}	
				}
			}
		}
		validate();
		repaint();
	}
}