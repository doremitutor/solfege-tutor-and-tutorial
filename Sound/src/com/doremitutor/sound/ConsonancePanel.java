package com.doremitutor.sound;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.doremitutor.support.Texts;
import com.doremitutor.support.Texts.Lang;
import com.doremitutor.support.Texts.Text;

class ConsonancePanel extends JPanel{
	static Lang LANG;
	private JPanel wavePanel;
	private SinWavePanel panel100;
	private SinWavePanel panel200;
	private SinWavePanel panel400;
	private SinWavePanel panel800;
	private ButtonPane buttonPane;
	static private ConsonancePanel instance=null;
	
	static ConsonancePanel getInstance(){
		assert instance==null:"Singleton case";
		return instance=new ConsonancePanel();
	}
		
	private ConsonancePanel(){
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		wavePanel=new JPanel(null);
		wavePanel.setOpaque(false);
		panel100=new SinWavePanel(1, 1, Color.WHITE);
		panel200=new SinWavePanel(2, 2, Color.CYAN);
		panel400=new SinWavePanel(4, 3, Color.YELLOW);
		panel800=new SinWavePanel(8, 4, Color.RED);
		buttonPane=new ButtonPane();
		wavePanel.add(panel100); 
		wavePanel.add(panel200); 
		wavePanel.add(panel400);
		wavePanel.add(panel800);
		add(wavePanel);
		add(buttonPane);
	}
	class SinWavePanel extends JPanel{
		private final int numCycles;
		private final int numOrder;
		private final Color color;
		private final Point origLocation;
		private final double xOrigin;
		private final double yOrigin;
		private final double topY;
		private final int waveWidth;
		private int currentY;
		private int yOffset;
		private final JButton button;
		
		public SinWavePanel (int numCycles, int vertPosition, Color color){
			this.numCycles=numCycles;
			this.numOrder=vertPosition;
			this.color=color;
			setSize(750, 100);
			origLocation=new Point(0, 105*(numOrder-1));
			reset();
			setLayout(null);
			setFocusable(false);
			setOpaque(false);
			xOrigin=5.0;
			yOrigin=50.0;
			topY=45.0;
			waveWidth=725;
			button=new JButton((100*numCycles)+" hz");
			button.setSize(72, 20);
			button.setLocation(655,5);
			button.setFocusable(false);
			button.setBackground(color);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			button.addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent me){
					wavePanel.setComponentZOrder(SinWavePanel.this, 0);
					currentY=(int)getLocation().getY();
					yOffset=me.getYOnScreen();
					repaint();
				}
			});
			button.addMouseMotionListener(new MouseMotionAdapter(){
				public void mouseDragged(MouseEvent me){
					int newY=currentY+me.getYOnScreen()-yOffset;
					if (newY>=0&&newY<=315){
						setLocation(0,newY);
					}
				}
			});
			add(button);
		}
		private void reset(){			
			setLocation(origLocation);
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2=(Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.draw(new Line2D.Double(xOrigin,yOrigin,xOrigin+waveWidth+20,yOrigin));
			g2.draw(new Line2D.Double(waveWidth+10,yOrigin-5,xOrigin+waveWidth+20,yOrigin));
			g2.draw(new Line2D.Double(waveWidth+10,yOrigin+5,xOrigin+waveWidth+20,yOrigin));			
			g2.draw(new Line2D.Double(xOrigin, yOrigin-topY, xOrigin, yOrigin+topY));			
			g2.draw(new Line2D.Double(xOrigin+waveWidth, yOrigin-topY/2, xOrigin+waveWidth, yOrigin+topY));			
			GeneralPath gp=new GeneralPath();
			gp.moveTo(xOrigin, yOrigin);
			for (int i=1; i<=waveWidth;i++){
				gp.lineTo(xOrigin+i, yOrigin-topY*(Math.sin(numCycles*Math.PI*2*i/waveWidth)));
			}
			g2.setPaint(color);
			g2.draw(gp);
		}	
	}
	class ButtonPane extends JPanel{
		JButton button;
		JLabel periodLabel;
		JLabel directionLabel;
		public ButtonPane(){
			setLayout(null);
			setOpaque(false);
			final Dimension dim=new Dimension(750, 60);
			setMinimumSize(dim);
			setPreferredSize(dim);
			setMaximumSize(dim);
			Font font=new Font(Font.SANS_SERIF, Font.BOLD, 16);
			periodLabel=new JLabel(Texts.getText(Text.CENTI_SECOND, LANG));
			periodLabel.setFont(font);
			periodLabel.setSize(290, 20);
			periodLabel.setLocation(210, 0);
			periodLabel.setHorizontalAlignment(SwingConstants.CENTER);
			add(periodLabel);
			directionLabel=new JLabel(Texts.getText(Text.MOVE_SENOIDS, LANG));
			directionLabel.setFont(font);
			directionLabel.setSize(500, 20);
			directionLabel.setLocation(50, 28);
			directionLabel.setHorizontalAlignment(SwingConstants.CENTER);
			add(directionLabel);
			button=new JButton(Texts.getText(Text.RESET_POSITIONS, LANG));
			button.setSize(170, 25);
			button.setLocation(560, 24);
			button.setFocusable(false);
			setCursor(new Cursor(Cursor.HAND_CURSOR));
			button.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					panel100.reset();
					panel200.reset();
					panel400.reset();
					panel800.reset();
				}
			});
			add(button);
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2=(Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.draw(new Line2D.Double(3.0, 0.0, 3.0, 20.0));
			g2.draw(new Line2D.Double(728.0, 0.0, 728.0, 20.0));
			g2.draw(new Line2D.Double(4.0, 10.0, 201.0, 10.0));
			g2.draw(new Line2D.Double(4.0, 10.0, 14.0, 5.0));
			g2.draw(new Line2D.Double(4.0, 10.0, 14.0, 15.0));
			g2.draw(new Line2D.Double(509.0, 10.0, 727.0, 10.0));
			g2.draw(new Line2D.Double(717.0, 5.0, 727.0, 10.0));
			g2.draw(new Line2D.Double(717.0, 15.0, 727.0, 10.0));
		}
	}
}