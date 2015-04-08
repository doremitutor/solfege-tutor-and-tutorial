package com.doremitutor.scoreDisplay;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.SpringLayout.Constraints;








import com.doremitutor.data.LessonData;
import com.doremitutor.data.PitchData;
import com.doremitutor.data.RhythmData;
import com.doremitutor.data.RhythmData.LedgerLine;
import com.doremitutor.data.RhythmData.PitchedShapeInfo;
import com.doremitutor.data.RhythmData.ShapeInfo;
import com.doremitutor.persistence.ProtoFigure;
import com.doremitutor.persistence.Constants.Pitch;
import com.doremitutor.persistence.Constants.Rhythm;
import com.doremitutor.persistence.ProtoFigure.BarProtoNote;
import com.doremitutor.persistence.ProtoFigure.GroupProtoNote;
import com.doremitutor.persistence.ProtoFigure.ProtoNote;
import com.doremitutor.persistence.ProtoFigure.ProtoRest;
import com.doremitutor.scoreDisplay.Interfaces.BarFigure;
import com.doremitutor.support.GraphicShape;
import com.doremitutor.support.Utilities;
import com.doremitutor.support.GraphicShape.Flag;

public abstract class Figure extends JPanel{
	public final ProtoFigure protoFigure;
	public final Rhythm rhythm;
	protected final int minPadWidth;
	protected final int maxPadWidth;
	protected final SpringLayout lo;
	protected ShapeInfo shapeInfo;
	protected GraphicShape shape;
	protected SpringPad leftPad;
	
	Figure(ProtoFigure protoFigure){
		this.protoFigure=protoFigure;
		rhythm=protoFigure.rhythm;
		final int scoreWidth=LessonData.getScoreWidth();
		minPadWidth=(int)(RhythmData.padFactor(rhythm).min*scoreWidth);
		maxPadWidth=(int)(RhythmData.padFactor(rhythm).max*scoreWidth);
		FrameWorkUtilities.makeTransparentAndUnfocusableSpringContainer(this);
		lo=(SpringLayout)(getLayout());
		leftPad=new SpringPad(minPadWidth, maxPadWidth);
		add(leftPad);
	}
	protected final void assembleMainPane(){
		shape=shapeInfo.getShape();
		final int dotY;
		if(this instanceof Note){
			dotY=shapeInfo.getY()+(((PitchedShapeInfo)shapeInfo).isOverLine?-2:3);
		}else{
			dotY=shapeInfo.getY();
		}
		add(shape);
		lo.putConstraint(SpringLayout.NORTH, shape, shapeInfo.getY(), SpringLayout.NORTH, this);
		boolean dotted;
		if(RhythmData.singleDotted.contains(rhythm)||RhythmData.dualDotted.contains(rhythm)){
			dotted=true;
			addDot(dotY);
			if(RhythmData.dualDotted.contains(rhythm)){
				addDot(dotY);
			}
		}else{
			dotted=false;
		}
		boolean isFlaggedNote=this instanceof BarNote&&!RhythmData.noteHeadOnly.contains(rhythm);
		add(new SpringPad(minPadWidth, maxPadWidth));
		if(!dotted&&isFlaggedNote){//when flagged & undotted, add filler to balance horizontal pads:				
			add(new JPanel(){
				{
					FrameWorkUtilities.makeTransparentAndUnfocusable(this);
					Dimension dim=new Dimension(12, 1);
					setMinimumSize(dim);
					setPreferredSize(dim);
					setMaximumSize(dim);
				}
			});				
		}
		FrameWorkUtilities.springComponents(this, false);		
		if(isFlaggedNote){
			GraphicShape stem=new GraphicShape.StandardStem();
			boolean up=((PitchedShapeInfo)shapeInfo).isUpper;			
			add(stem);
			lo.putConstraint(up?SpringLayout.EAST:SpringLayout.WEST, stem, up?1:-1, up?SpringLayout.WEST:SpringLayout.EAST, shape);
			lo.putConstraint(up?SpringLayout.NORTH:SpringLayout.SOUTH, stem, up?-4:4, up?SpringLayout.SOUTH:SpringLayout.NORTH, shape);
			if(!RhythmData.stemOnly.contains(rhythm)){
				Flag flag=new GraphicShape.Flag(up);
				add(flag);
				lo.putConstraint(up?SpringLayout.SOUTH:SpringLayout.NORTH, flag, 0, up?SpringLayout.SOUTH:SpringLayout.NORTH, stem);
				lo.putConstraint(SpringLayout.WEST, flag, -1, SpringLayout.EAST, stem);
				if(RhythmData.doubleFlagged.contains(rhythm)){
					Flag flag2=new GraphicShape.Flag(up);
					add(flag2);
					lo.putConstraint(up?SpringLayout.SOUTH:SpringLayout.NORTH, flag2, up?-8:8, up?SpringLayout.SOUTH:SpringLayout.NORTH, stem);
					lo.putConstraint(SpringLayout.WEST, flag2, -1, SpringLayout.EAST, stem);					
				}
			}
		}
		for(Component c:getComponents()){
			c.setForeground(null);
		}
		if(this instanceof Rest){
			return;
		}
		for(LedgerLine line:((PitchedShapeInfo)shapeInfo).ledgerLines){
			GraphicShape.LedgerLine ledgerLine=new GraphicShape.LedgerLine();
			add(ledgerLine);
			lo.putConstraint(SpringLayout.WEST, ledgerLine, -8, SpringLayout.WEST, shape);
			lo.putConstraint(SpringLayout.NORTH, ledgerLine, line.y, SpringLayout.NORTH, shape);
		}
	}
	protected final void addDot(int dotY){
		GraphicShape.Dot dot=new GraphicShape.Dot();		
		add(dot);
		lo.putConstraint(SpringLayout.NORTH, dot, dotY, SpringLayout.NORTH, this);
	}
	public final void enlight(final boolean enlight){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){	
				if(enlight&&(getForeground()!=Color.RED)){
					setForeground(Color.RED);
				}else if(!enlight&&(getForeground()!=Color.BLACK)){
					setForeground(Color.BLACK);
				}				
			}			
		});	
	}
	public JPanel getPane(){
		return this;
	}
	public static class Rest extends Figure implements BarFigure{
		Rest(ProtoRest protoRest){		
			super(protoRest);
			shapeInfo=RhythmData.getShapeInfo(rhythm);
			assembleMainPane();
		}
	}
	public static abstract class Note extends Figure{
		protected final ProtoNote protoNote;
		private final JPanel namePane;
		Note(ProtoNote protoNote){
			super(protoNote);
			this.protoNote=protoNote;
			final Pitch pitch=protoNote.pitch;
			shapeInfo=RhythmData.getShapeInfo(rhythm, pitch, LessonData.getClef());
			assembleMainPane();
			namePane=new JPanel(null){
				private final String label=PitchData.noteName(pitch);
				final Font font=new Font(Font.SANS_SERIF, Font.BOLD, 12);
				private final int width;
				private final int height;
				{
					final FontMetrics fm=getFontMetrics(font);
					width=fm.stringWidth(label)+1;
					height=fm.getAscent()-fm.getDescent();
					final Dimension namePaneDim=new Dimension(width, height+1);
					setMinimumSize(namePaneDim);
					setPreferredSize(namePaneDim);
					setMaximumSize(namePaneDim);
					setOpaque(true);
					setBackground(Utilities.PROJECT_COLOR);
				}
				@Override
				public void paintComponent(Graphics g) {
					super.paintComponent(g);
					Graphics2D g2=(Graphics2D)g;
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setFont(font);
					g2.drawString(label, 1, height);
				}
			};
			add(namePane, getComponentCount()-1);
			boolean up=((PitchedShapeInfo)shapeInfo).isUpper;
			final int namePaneX=up?2:-2;
			final int namePaneY=up?11:-11;			
			final String edgeX=up?SpringLayout.WEST:SpringLayout.EAST;
			lo.putConstraint(edgeX, namePane, namePaneX, edgeX, shape);
			lo.putConstraint(SpringLayout.NORTH, namePane, namePaneY, SpringLayout.NORTH, shape);
			if(!protoNote.hasComma){
				return;
			}
			final JLabel commaLabel=new JLabel(",");
			commaLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 28));
			commaLabel.setOpaque(false);
			Dimension commaLabelDim=commaLabel.getPreferredSize();
			commaLabel.setSize(commaLabelDim);
			JPanel commaPane=new JPanel(null);
			commaPane.setOpaque(false);
			Dimension commaPaneDim=new Dimension(commaLabelDim.width-2, commaLabelDim.height-29);//
			commaPane.setMinimumSize(commaPaneDim);
			commaPane.setPreferredSize(commaPaneDim);
			commaPane.setMaximumSize(commaPaneDim);
			commaLabel.setLocation(-1, -26);
			commaPane.add(commaLabel);
			add(commaPane);
			lo.putConstraint(SpringLayout.EAST, commaPane, -5, SpringLayout.EAST, this);
			lo.putConstraint(SpringLayout.NORTH, commaPane, 20, SpringLayout.NORTH, this);
		}
		public Point getShapeCenterPoint(){
			int shapeWidth=shape.getWidth();
			int shapeHeight=shape.getHeight();
			Constraints shapeConstraints=lo.getConstraints(shape);
			int shapeX=shapeConstraints.getX().getValue();
			int shapeY=shapeConstraints.getY().getValue();
			return new Point(shapeX+shapeWidth/2, shapeY+shapeHeight/2);
		}
		public void showName(boolean on){			
			if(namePane.isVisible()!=on){
				namePane.setVisible(on);
			}
		}
	}
	public static final class BarNote extends Note implements BarFigure{
		BarNote(BarProtoNote barProtoNote){
			super(barProtoNote);
		}
	}
	public static final class GroupNote extends Note {
		GroupNote(GroupProtoNote groupProtoNote){
			super(groupProtoNote);
		}
	}
	private class SpringPad extends JPanel{
		SpringPad(int min, int max){
			FrameWorkUtilities.makeTransparentAndUnfocusable(this);
			Dimension minDim=new Dimension(min, 1);
			setMinimumSize(minDim);
			setPreferredSize(minDim);
			setMaximumSize(new Dimension(max, 1));
		}
	}
}