package com.doremitutor.scoreDisplay;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.doremitutor.persistence.ProtoFigure;
import com.doremitutor.persistence.ProtoFigure.ProtoNote;
import com.doremitutor.scoreDisplay.Figure.BarNote;
import com.doremitutor.scoreDisplay.Figure.GroupNote;
import com.doremitutor.scoreDisplay.Figure.Note;
import com.doremitutor.support.GraphicShape;

public class FrameWorkUtilities{
	private FrameWorkUtilities(){assert false:"Monostate case";}
	static void springComponents(Container container, boolean bothAxis){
		SpringLayout lo=(SpringLayout)container.getLayout();
		Component[] comp=container.getComponents();
		for(int i=0;i<comp.length;i++){
			lo.putConstraint("West", comp[i], 0, i==0?"West":"East", i==0?container:comp[i-1]);
			if(bothAxis){
				lo.putConstraint("North", comp[i], 0, "North", container);
				lo.putConstraint("South", comp[i], 0, "South", container);				
			}	
		}
		lo.putConstraint("East", container, 0, "East", comp[comp.length-1]);
		container.validate();
	}
	static void makeTransparentAndUnfocusableSpringContainer(JComponent c){
		makeTransparentAndUnfocusable(c);
		c.setLayout(new SpringLayout());		
	}
	public static void makeTransparentAndUnfocusable(JComponent c){
		c.setOpaque(false);
		c.setFocusable(false);
	}
	static void drawLigatures(JPanel glassPane, List<Figure> staffFigures){
		final int xMargin=6;
		final int yOffset=3;
		glassPane.removeAll();
		SpringLayout lo=(SpringLayout)glassPane.getLayout();
		int lastIndex=staffFigures.size()-1;
		for(int i=0;i<=lastIndex;i++){
			if(staffFigures.get(i) instanceof Note){
				Note note=(Note)staffFigures.get(i);
				Point noteCenter=getNoteLocationInStaff(note);
				int noteCenterX=noteCenter.x;
				int noteCenterY=noteCenter.y;
				int linkY;
				int linkWidth;
				boolean up=noteCenterY<51;
				if(i==0){
					List<Figure> figures=Score.readFigures();
					int noteIndex=figures.indexOf(note);
					if(noteIndex>0){
						ProtoFigure previousProtoFigure=figures.get(noteIndex-1).protoFigure;
						if(previousProtoFigure instanceof ProtoNote){
							if(((ProtoNote)previousProtoFigure).isLinked){
							linkWidth=noteCenterX-2*xMargin;
							GraphicShape leftLink=new GraphicShape.Ligature(up, linkWidth);
							glassPane.add(leftLink);
							lo.putConstraint(SpringLayout.WEST, leftLink, xMargin, SpringLayout.WEST, glassPane);
							linkY=noteCenterY+(up?(-leftLink.getHeight()-yOffset):yOffset);
							lo.putConstraint(SpringLayout.NORTH, leftLink, linkY, SpringLayout.NORTH, glassPane);
							}
						}
					}
				}
				if(i<lastIndex){
					if(((ProtoNote)note.protoFigure).isLinked){
						Figure nextFigure=staffFigures.get(i+1);
						if(nextFigure instanceof Note){
							int nextNoteCenterX=getNoteLocationInStaff((Note)nextFigure).x;
							linkWidth=nextNoteCenterX-noteCenterX-2*xMargin;
							GraphicShape link=new GraphicShape.Ligature(up, linkWidth);
							glassPane.add(link);
							lo.putConstraint(SpringLayout.WEST, link, noteCenterX+xMargin, SpringLayout.WEST, glassPane);
							linkY=noteCenterY+(up?(-link.getHeight()-yOffset):yOffset);
							lo.putConstraint(SpringLayout.NORTH, link, linkY, SpringLayout.NORTH, glassPane);
						}else{							
							linkWidth=25;
							GraphicShape link=new GraphicShape.Ligature(up, linkWidth);
							glassPane.add(link);
							lo.putConstraint(SpringLayout.WEST, link, noteCenterX+xMargin, SpringLayout.WEST, glassPane);
							linkY=noteCenterY+(up?(-link.getHeight()-yOffset):yOffset);
							lo.putConstraint(SpringLayout.NORTH, link, linkY, SpringLayout.NORTH, glassPane);
						}
					}
				}
				if(i==lastIndex){
					if(((ProtoNote)note.protoFigure).isLinked){
						linkWidth=lo.getConstraints(glassPane).getWidth().getValue()-noteCenterX-2*xMargin;
						GraphicShape link=new GraphicShape.Ligature(up, linkWidth);
						glassPane.add(link);
						lo.putConstraint(SpringLayout.WEST, link, noteCenterX+xMargin, SpringLayout.WEST, glassPane);
						linkY=noteCenterY+(up?(-link.getHeight()-yOffset):yOffset);
						lo.putConstraint(SpringLayout.NORTH, link, linkY, SpringLayout.NORTH, glassPane);						
					}
				}			
			}
		}
		glassPane.validate();
		glassPane.repaint();
	}	
	private static Point getNoteLocationInStaff(Note note){
		int xLocation=0;
		if(note instanceof BarNote){
			Bar bar=getContainerBar(note);
			xLocation+=bar.getX();
			xLocation+=note.getPane().getX();
		}else{
			Group group=getFigureContainerGroup(note);
			Bar bar=getContainerBar(group);
			xLocation+=bar.getX();
			xLocation+=group.getX();
			xLocation+=note.getPane().getX();
		}
		Point shapeLocationInNote=note.getShapeCenterPoint();
		xLocation+=shapeLocationInNote.x;
		return new Point(xLocation, shapeLocationInNote.y);
	}
	public static Bar getContainerBar(Component component){
		Container parent=component.getParent();
		while(parent!=null&&!(parent instanceof Bar)){
			parent=parent.getParent();
		}
		if(parent==null){
			throw new AssertionError("Orphan BarFigure");
		}
		return (Bar)parent;
	}
	public static final Group getFigureContainerGroup(Figure figure){
		assert figure instanceof GroupNote:"Non groupNote Figure";
		Container parent=figure.getPane().getParent();
		while(parent!=null){
			parent=parent.getParent();
			if(parent instanceof Group){
				break;
			}
		}
		if(parent==null){
			throw new AssertionError("Orphan BarFigure");
		}
		return (Group)parent;
	}
	public static void rectifyStaffWidth(Staff staff){
		if(staff.getWidthAllowance()<0||staff.hasPrematureLineFeed()){
			List<Staff> staves;
			Staff nextStaff=null;
			while(staff.getWidthAllowance()<0||staff.hasPrematureLineFeed()){
				StaffPosition staffPosition=getStaffPosition(staff);
				if(staffPosition==StaffPosition.FIRST_OF_SEVERAL||staffPosition==StaffPosition.INTER){
					staves=Score.readStaves();
					(nextStaff=staves.get(staves.indexOf(staff)+1)).addBar(staff.trimBar(false), 0);
				}else{
					Score.staffPanel.add(nextStaff=new Staff(staff.trimBar(false)));
					Score.staffPanel.revalidate();
				}
			}
			rectifyStaffWidth(nextStaff);
		}
	}
	public enum StaffPosition{FIRST_OF_SEVERAL, INTER, LAST_OF_SEVERAL, ONLY}

	public static StaffPosition getStaffPosition(Staff staff){
		List<Staff> staves=Score.readStaves();
		int numStaves=staves.size();
		if(numStaves==1){
			return StaffPosition.ONLY;
		}
		int staffIndex=staves.indexOf(staff);
		if(staffIndex==0){
			return StaffPosition.FIRST_OF_SEVERAL;
		}
		if(staffIndex>numStaves-2){
			return StaffPosition.LAST_OF_SEVERAL;
		}
		return StaffPosition.INTER;
	}
}
