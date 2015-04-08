package com.doremitutor.support;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import javax.swing.JPanel;

public abstract class GraphicShape extends JPanel{
	protected final Dimension dimension;
	private GraphicShape(Dimension d){
		this.dimension=d;
		setSize(d);
		setMinimumSize(d);
		setPreferredSize(d);
		setMaximumSize(d);
		setLayout(null);
		setOpaque(false);
		setFocusable(false);			
	}
	public static class BassCleff extends GraphicShape{
		private static final int width=25;
		private static final int height=30;
		public BassCleff(){
			super(new Dimension(width, height));
		}
		public void paintComponent(Graphics g){
			Graphics2D g2=setGraphics2D(g);
			GeneralPath gp = new GeneralPath();			
			gp.moveTo(0.1*width, 0.44*width);
			gp.quadTo(0.0, 0.4*width, 0.0, 0.25*width);
			gp.quadTo(0.02*width, 0.0, 0.35*width, 0.0*width);
			gp.quadTo(0.7*width, 0.0, 0.7*width, 0.4*height);
			gp.curveTo(0.7*width, 0.75*height, 0.35*width, height, 0.0, height);
			gp.curveTo(0.3*width, height-1, 0.55*width, 0.7*height, 0.55*width, 0.4*height);
			gp.curveTo(0.55*width, 0.35*width, 0.55*width, 0.05*width, 0.35*width, 0.05*width);
			gp.curveTo( 0.175*width, 0.05*width, 0.125*width, 0.15*width,0.125*width, 0.24*width);
			gp.closePath();
			gp.append(new Ellipse2D.Double(0.05*width, 0.2*height, 0.25*width, 0.25*width), false);
			gp.append(new Ellipse2D.Double(0.75*width, 0.15*width, 0.15*width, 0.15*width), false);
			gp.append(new Ellipse2D.Double(0.75*width, 0.45*width, 0.15*width, 0.15*width), false);
			gp.setWindingRule(Path2D.WIND_NON_ZERO);
			g2.fill(gp);
		}		
	}
	public static class TrebleCleff extends GraphicShape{
		private static final int width=32;
		private static final int height=80;
		
		public TrebleCleff(){
			super(new Dimension(width, height));
		}
		public void paintComponent(Graphics g){
			Graphics2D g2=setGraphics2D(g);
			GeneralPath gp = new GeneralPath();
			gp.moveTo(0.2182 * width, 0.876 * height);
			gp.curveTo(0.2182 * width, 1.012 * height, 0.764 * width, 1.012 * height, 0.764 * width, 0.876 * height);
			gp.curveTo(0.764 * width, 0.8 * height, 0.2182 * width, 0.276 * height, 0.2182 * width, 0.2 * height);
			gp.quadTo(0.2182 * width, 0.08 * height, 0.491 * width, 0.0); // top
			gp.quadTo(0.627 * width, 0.08 * height, 0.627 * width, 0.176 * height);
			gp.curveTo(0.627 * width, 0.296 * height, 0.176 * width, 0.436 * height, 0.176 * width, 0.556 * height);
			gp.curveTo(0.173 * width, 0.796 * height, 0.854 * width, 0.796 * height, 0.854 * width, 0.636 * height);
			gp.curveTo(0.854 * width, 0.516 * height, 0.445 * width, 0.516 * height, 0.445 * width, 0.636 * height);
			gp.quadTo(0.445 * width, 0.696 * height, 0.536 * width, 0.696 * height); // turn around:
			gp.quadTo(0.364 * width, 0.676 * height, 0.364 * width, 0.596 * height);
			gp.curveTo(0.364 * width, 0.444 * height, 0.991 * width, 0.444 * height, 0.991 * width, 0.636 * height);
			gp.curveTo(0.991 * width, 0.836 * height, 0.0, 0.836 * height, 0.0, 0.556 * height);
			gp.curveTo(0.0, 0.436 * height, 0.573 * width, 0.276 * height, 0.573 * width, 0.156 * height);
			gp.quadTo(0.573 * width, 0.116 * height, 0.491 * width, 0.096 * height);
			gp.quadTo(0.318 * width, 0.14 * height, 0.318 * width, 0.196 * height);
			gp.curveTo(0.318 * width, 0.276 * height, 0.809 * width, 0.796 * height, 0.809 * width, 0.876 * height);
			gp.curveTo(0.809 * width, 1.032 * height, 0.218 * width, 1.032 * height, 0.218 * width, 0.876 * height);
			gp.append(new Ellipse2D.Double(0.2182 * width, 0.836 * height, 0.25 * width, 0.1 * height), false);
			gp.setWindingRule(Path2D.WIND_NON_ZERO);
			g2.fill(gp);
		}
	}		
	public static abstract class NoteHead extends GraphicShape{
		protected static final int width=15;
		protected static final int height=11;
		protected static final double centerX;
		protected static final double centerY;
		static{			
			centerX=width*0.5;
			centerY=height*0.5;
		}
		private NoteHead(){
			super(new Dimension(width, height));
		}
	}
	public static final class WholeNoteHead extends NoteHead{
		public void paintComponent(Graphics g){
			Graphics2D g2=setGraphics2D(g);
			Ellipse2D e=new Ellipse2D.Double(centerX-8, centerY-5, 16, 10);
			Area a1=new Area(e);
			e.setFrame(centerX-5, centerY-3, 10, 6);
			GeneralPath p=new GeneralPath();
			p.append(e, false);
			AffineTransform t=new AffineTransform();
			t.rotate(0.25*Math.PI, centerX, centerY);
			p.transform(t);
			Area a2=new Area(p);
			a1.subtract(a2);
			g2.fill(a1);					
		}
	}
	public static final class HalfNoteHead extends NoteHead{
		public void paintComponent(Graphics g){
			Graphics2D g2=setGraphics2D(g);
			Ellipse2D e=new Ellipse2D.Double(centerX-8, centerY-5, 16, 10);
			Area a1=new Area(e);
			e.setFrame(centerX-6, centerY-2, 12, 4);
			Area a2=new Area(e);
			a1.subtract(a2);
			g2.rotate(-0.125*Math.PI, centerX, centerY);
			g2.fill(a1);			
		}
	}
	
	public static final class SolidNoteHead extends NoteHead{
		public void paintComponent(Graphics g){
			Graphics2D g2=setGraphics2D(g);
			Ellipse2D e=new Ellipse2D.Double(centerX-8, centerY-5, 16, 10);
			g2.rotate(-0.125*Math.PI, centerX, centerY);
			g2.fill(e);
		}
	}
	public static final class RectangularRest extends GraphicShape{
		private static final int width=16;
		private static final int height=5;
		public RectangularRest(){
			super(new Dimension(width, height));
		}
		public void paintComponent(Graphics g){
			g.fillRect(0, 0, width-1, height-1);
			g.drawRect(0, 0, width-1, height-1);
		}
	}
	public static final class QuarterRest extends GraphicShape{
		private static final int width=11;
		private static final int height=33;
		public QuarterRest(){
			super(new Dimension(width, height));
		}
		public void paintComponent(Graphics g){
			Graphics2D g2=setGraphics2D(g);
			GeneralPath gp=new GeneralPath();
			double minStrokeWidth=width*0.05;
			Point2D contactPoint=new Point2D.Double(width-1, height-width*0.8);
			Point2D lastPoint=new Point2D.Double(width*0.1-minStrokeWidth*Math.sin(Math.PI*0.25), minStrokeWidth*Math.sin(Math.PI*0.25));
			gp.moveTo(width*0.1, 1);
			drawLineAtAngle(gp, width*1.15, Math.PI*0.25);
			drawQuadTo(gp, contactPoint.getX(), contactPoint.getY(), width*0.8, Math.PI*0.5);
			gp.lineTo(gp.getCurrentPoint().getX()-minStrokeWidth/Math.sin(Math.PI*0.25), gp.getCurrentPoint().getY());
			drawLineAtAngle(gp, width*1.15, Math.PI*1.25);
			drawQuadTo(gp, lastPoint.getX(), lastPoint.getY(), width*0.8, Math.PI*0.5);
			gp.lineTo(lastPoint.getX(), lastPoint.getY());
			gp.closePath();
			g2.fill(gp);
			g2.draw(gp);
			gp.reset();
			gp.moveTo(contactPoint.getX(), contactPoint.getY());
			drawCurveTo(gp, width*0.5, height-1, width*0.95, Math.PI*0.5);
			drawQuadTo(gp, contactPoint.getX(), contactPoint.getY(), width*0.8, Math.PI*1.5);
			g2.fill(gp);
			g2.draw(gp);
		}
	}
	public static final class EightRest extends GraphicShape{
		private static final int width=13;
		private static final int height=19;
		public EightRest(){
			super(new Dimension(width, height));
		}
		public void paintComponent(Graphics g){
			Graphics2D g2=setGraphics2D(g);
			Shape stem=getEightRestStem().createTransformedShape(AffineTransform.getTranslateInstance(12, 0));
			g2.fill(stem);
			g2.draw(stem);
			Shape volute=getVolute().createTransformedShape(AffineTransform.getTranslateInstance(0, 0));
			g2.fill(volute);
			g2.draw(volute);
		}
	}
	public static final class SixteenthRest extends GraphicShape{
		private static final int width=16;
		private static final int height=28;
		public SixteenthRest(){
			super(new Dimension(width, height));
		}
		public void paintComponent(Graphics g){
			Graphics2D g2=setGraphics2D(g);
			Shape stem=getSixteenthRestStem().createTransformedShape(AffineTransform.getTranslateInstance(15, 0));
			g2.fill(stem);
			g2.draw(stem);
			Shape volute1=getVolute().createTransformedShape(AffineTransform.getTranslateInstance(3, 0));
			Shape volute2=getVolute().createTransformedShape(AffineTransform.getTranslateInstance(0, 10));
			g2.fill(volute1);
			g2.draw(volute1);
			g2.fill(volute2);
			g2.draw(volute2);
		}
	}
	public static final class StandardStem extends GraphicShape{
		public StandardStem(){
			super(new Dimension(1, 32));
		}
		public void paintComponent(Graphics g){
			g.drawLine(0, 0, 0, dimension.height-1);
		}
	}
	public static final class VariableStem extends GraphicShape{
		public VariableStem(int height){
			super(new Dimension(1, height));
		}
		public void paintComponent(Graphics g){
			g.drawLine(0, 0, 0, dimension.height-1);
		}
	}
	public static final class Beam extends GraphicShape{
		public Beam(int width){
			super(new Dimension(width, 4));
		}
		public void paintComponent(Graphics g){
			g.fillRect(0, 0, getWidth()-1, getHeight()-1);
			g.drawRect(0, 0, getWidth()-1, getHeight()-1);
		}
	}
	public static final class Flag extends GraphicShape{
		public final boolean inverted;
		public Flag(boolean inverted){
			super(new Dimension(12, 23));
			this.inverted=inverted;
		}
		public void paintComponent(Graphics g){
			Graphics2D g2=setGraphics2D(g);
			GeneralPath p=new GeneralPath();
			double returnOffsetY=2.5;			
			double refX=6;
			double refY=7.5;			
			p.moveTo(0, 0);
			p.quadTo(0, refY*0.4, +refX, refY);
			p.quadTo(refX*2.5, refY*1.5, refX, refY*3);
			p.quadTo(refX*2.0, refY*1.5+returnOffsetY*1.5, refX, refY+returnOffsetY);
			p.quadTo(refX, refY*1.5, 0, returnOffsetY*3);
			p.closePath();
			if(inverted){					
				AffineTransform at=AffineTransform.getTranslateInstance(0.0, dimension.height);
				at.scale(1.0, -1.0);
				p.transform(at);
			}
			g2.fill(p);
			g2.draw(p);
		}
	}
	public static final class Dot extends GraphicShape{
		public Dot(){
			super(new Dimension(8, 5));			
		}
		public void paintComponent(Graphics g){
			final int height=dimension.height;
			g.fillOval(3, 0, height-1, height-1);
			g.drawOval(3, 0, height-1, height-1);
		}
	}
	public static final class LedgerLine extends GraphicShape{
		public LedgerLine(){
			super(new Dimension(30, 1));
		}
		public void paintComponent(Graphics g){
			g.drawLine(0, 0, dimension.width-1, dimension.height-1);
		}
	}
	public static final class Sharp extends GraphicShape{
		public Sharp(){
			super(new Dimension(10, 25));
		}
		public void paintComponent(Graphics g){
			final double width=dimension.width;
			final double height=dimension.height;
			Graphics2D g2=setGraphics2D(g);
			BasicStroke horiz=new BasicStroke((float)width/3);
			g2.setStroke(horiz);
			g2.draw(new Line2D.Double(0, 3*height/9, width, 2*height/9));
			g2.draw(new Line2D.Double(0, 7*height/9, width, 6*height/9));
			BasicStroke vert=new BasicStroke((float)width/10);
			g2.setStroke(vert);
			g2.draw(new Line2D.Double(width/4, height/15, width/4, height));
			g2.draw(new Line2D.Double(3*width/4, 0f, 3*width/4, 14*height/15));
		}
	}
	public static final class Flat extends GraphicShape{
		public Flat(){
			super(new Dimension(10, 25));		
		}
		public void paintComponent(Graphics g){
			final double width=dimension.width;
			final double height=dimension.height;
			Graphics2D g2=setGraphics2D(g);
			GeneralPath gp=new GeneralPath();
			gp.moveTo(0.0, 0.0);
			gp.lineTo(0.0, height-1);
			gp.lineTo(0.10*width, 0.92*height);
			gp.quadTo(0.09*width, 0.5*height, 0.15*width, 0.0);
			gp.lineTo(0.0, 0.0);
			gp.moveTo(0.0, height-1);
			gp.lineTo(0.7*width, 0.81*height);
			gp.curveTo(1.5*width, 0.55*height, 0.5*width,0.24*height , 0.09*width, 0.53*height);
			gp.lineTo(0.09*width, 0.62*height);
			gp.curveTo(0.4*width, 0.3*height, 1.2*width, 0.65*height,0.09*width, 0.91*height);
			gp.lineTo(0.0, height-1);			
			g2.fill(gp);
		}
	}
	public static final class Ligature extends GraphicShape {
		boolean upward;	
		public Ligature(boolean upward, int width){
			super(new Dimension(width, 9));
			this.upward=upward;
		}	
		public void paintComponent(Graphics g) {
			Graphics2D g2=setGraphics2D(g);
			GeneralPath p=new GeneralPath();
			p.moveTo(0.0, 0.0);
			drawQuadTo(p, dimension.width-1, 0.0, 11.0, Math.toRadians(90));
			drawQuadTo(p, 0.0, 0.0, 15, Math.toRadians(270));
			if(upward){					
				AffineTransform at=AffineTransform.getTranslateInstance(0.0, dimension.height-1);
				at.scale(1.0, -1.0);
				p.transform(at);				
			}
			g2.draw(p);
			g2.fill(p);
		}
	}
	private static Graphics2D setGraphics2D(Graphics g){
		Graphics2D g2=(Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		return g2;
	}
	private static GeneralPath getEightRestStem(){
		GeneralPath p=new GeneralPath();
		p.moveTo(0, 0);
		p.lineTo(-7, 18);
		p.lineTo(-5, 18);
		p.closePath();
		return p;
	}
	private static GeneralPath getSixteenthRestStem(){
		GeneralPath p=new GeneralPath();
		p.moveTo(0, 0);
		p.lineTo(-10, 27);
		p.lineTo(-8, 27);
		p.closePath();
		return p;
	}
	private static GeneralPath getVolute(){
		GeneralPath gp=new GeneralPath();
		gp.append(new Ellipse2D.Double(0, 0, 4, 4), false);
		gp.moveTo(2, 5);
		gp.quadTo(6, 8, 10, 5);
		gp.quadTo(6, 9, 2, 5);
		return gp;		
	}
	private static void drawCurveTo(Path2D path, double nextX, double nextY, double deviation, double deviationAngle){
		double currentX=path.getCurrentPoint().getX();
		double currentY=path.getCurrentPoint().getY();
		double incremX=nextX-currentX;
		double incremY=nextY-currentY;
		assert !(incremX==0.0&&incremY==0.0):"Same coordinates on origin and destination";
		double currentAngleSin=(incremY)/Math.hypot((incremX), (incremY));
		boolean angleIsSimple=incremX>0?true:false;
		double currentAngle=angleIsSimple?Math.asin(currentAngleSin):Math.PI-Math.asin(currentAngleSin);
		double controlAngle=currentAngle+deviationAngle;
		controlAngle-=controlAngle<Math.PI*2?0:Math.PI*2;
		double controlAngleSin=Math.sin(controlAngle);
		double controlAngleCos=Math.cos(controlAngle);		
		double controlX1=currentX+deviation*controlAngleCos;
		double controlY1=currentY+deviation*controlAngleSin;
		double controlX2=nextX+deviation*controlAngleCos;
		double controlY2=nextY+deviation*controlAngleSin;				
		path.curveTo(controlX1, controlY1, controlX2, controlY2, nextX, nextY);
	}
	private static void drawQuadTo(Path2D path, double nextX, double nextY, double deviation, double deviationAngle){
		double currentX=path.getCurrentPoint().getX();
		double currentY=path.getCurrentPoint().getY();
		double incremX=nextX-currentX;
		double incremY=nextY-currentY;
		double midX=currentX+incremX*0.5;
		double midY=currentY+incremY*0.5;
		assert !(incremX==0.0&&incremY==0.0):"Same coordinates on origin and destination";
		double currentAngleSin=(incremY)/Math.hypot((incremX), (incremY));
		boolean angleIsSimple=incremX>0?true:false;
		double currentAngle=angleIsSimple?Math.asin(currentAngleSin):Math.PI-Math.asin(currentAngleSin);
		double controlAngle=currentAngle+deviationAngle;
		controlAngle-=controlAngle<Math.PI*2?0:Math.PI*2;
		double controlAngleSin=Math.sin(controlAngle);
		double controlAngleCos=Math.cos(controlAngle);
		double controlX=midX+deviation*controlAngleCos;
		double controlY=midY+deviation*controlAngleSin;				
		path.quadTo(controlX, controlY, nextX, nextY);
	}
	private static void drawLineAtAngle(Path2D path, double lenght, double angle){
		double currentX=path.getCurrentPoint().getX();
		double currentY=path.getCurrentPoint().getY();
		while(angle>=Math.PI*2){
			angle-=Math.PI*2;
		}
		double nextX=currentX+lenght*Math.cos(angle);
		double nextY=currentY+lenght*Math.sin(angle);
		path.lineTo(nextX, nextY);
	}
}