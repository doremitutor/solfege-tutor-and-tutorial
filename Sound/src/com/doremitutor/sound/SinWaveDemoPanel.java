package com.doremitutor.sound;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SinWaveDemoPanel extends JPanel implements ActionListener{
	private final double midY = 50.0;
	private final double maxY = 48.0;
	private final double sinWidth = 190.0;
	private double peak=0.0;
	private final double[] sin;
	{
		sin = new double[36];
		for (int i = 0; i < 36; i++) {
			sin[i] = Math.sin((i + 1) * (Math.PI / 18));
		}
	};
	private int iterator=0;
	private Timer timer;
	private Box buttonBox;
	private VibratingString vibratingString;
	private SinWave sinWave;
	private SinPoint sinPoint;
	private TimePlane timePlane;
	private LinkingLine linkingLine;
	private LabelAndLines labelAndLines;
	private String periodLabel="";

	static private SinWaveDemoPanel instance=null;
	
	static SinWaveDemoPanel getInstance(boolean isJFrame){
		assert instance==null:"Singleton case";
		return instance=new SinWaveDemoPanel(isJFrame);
	}
	
	private SinWaveDemoPanel(boolean isJFrame){
		setLayout(null);
		vibratingString = new VibratingString();
		add(vibratingString);
		sinWave = new SinWave();
		add(sinWave);
		sinPoint = new SinPoint();
		add(sinPoint);
		timePlane=new TimePlane();
		add(timePlane);
		linkingLine = new LinkingLine();
		add(linkingLine);
		labelAndLines=new LabelAndLines();
		add(labelAndLines);
		buttonBox=Box.createHorizontalBox();
		buttonBox.setSize(511, 25);
		buttonBox.setLocation(0, 120);
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(new TriggerButton("1 Hz", "Un segundo", 27));
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(new TriggerButton("1/2 Hz", "Dos segundos", 54));
		buttonBox.add(Box.createHorizontalGlue());
		buttonBox.add(new TriggerButton("1/3 Hz", "Tres segundos", 81));
		buttonBox.add(Box.createHorizontalGlue());
		add(buttonBox);
		JLabel label=new JLabel("Pulse el botón correspondiente a la frecuencia deseada");
		label.setLocation(98, 145);
		Dimension d=new Dimension(318, 20);
		label.setSize(d);
		label.setPreferredSize(d);
		add(label);
		if(isJFrame){
			//do something
		}
	}
	
	public void actionPerformed(ActionEvent ae){
		if (iterator < 36) {
			peak = maxY * sin[iterator];
			iterator++;
		} else {
			timer.stop();
			iterator = 0;
			linkingLine.setVisible(false);
			sinPoint.setVisible(false);
			timePlane.setVisible(false);
			labelAndLines.setVisible(false);
			buttonBox.setVisible(true);
			for (int i=0; i<buttonBox.getComponents().length; i++){
				buttonBox.getComponents()[i].setEnabled(true);
			}
		}
		repaint();			
	}

	class VibratingString extends JComponent {
		public VibratingString() {
			setSize(512, 102);
			setLocation(0, 0);
			setEnabled(false);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			BasicStroke thick = new BasicStroke(5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
			g2.fillOval(0, 45, 10, 10);
			g2.fillOval(501, 45, 10, 10);
			QuadCurve2D curve = new QuadCurve2D.Double(5.0, midY, 251.5, midY - 2 * peak, 504.0, midY);
			g2.setStroke(thick);
			g2.draw(curve);
		}
	}

	class SinWave extends JComponent {
		public SinWave() {
			setSize(200, 100);
			setLocation(530, 0);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			GeneralPath gp = new GeneralPath();
			gp.moveTo(5.0, midY);
			g2.setPaint(new Color(0f, 0f, 0f, 0.1f));
			for (int i = 1; i <= sinWidth; i++) {
				gp.lineTo(5f + (double) i, midY - maxY * Math.sin(2 * Math.PI * i / sinWidth));
			}
			g2.draw(gp);
		}
	}

	class SinPoint extends JComponent {
		public SinPoint() {
			setVisible(false);
			setSize(5, 5);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			setLocation(533 + (int) (sinWidth * (iterator / 36.0)), (int) ((midY - 2) - (maxY * Math.sin(2 * Math.PI
					* iterator / 36))));
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Ellipse2D point = new Ellipse2D.Double(0.0, 0.0, 4.0, 4.0);
			g2.fill(point);
		}
	}
	class TimePlane extends JComponent{
		public TimePlane(){
			setVisible(false);
			setSize(1, 100);			
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(Color.YELLOW);
			setLocation(535 + (int) (sinWidth * (iterator / 36.0)), 0);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Line2D line=new Line2D.Double(0.0, 0.0, 0.0, 99.0);
			g2.draw(line);
		}
	}

	class LinkingLine extends JComponent {
		public LinkingLine() {
			setVisible(false);
			setSize(476, 3);
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setLocation(254, 48 - (int)(maxY * Math.sin(2 * Math.PI* iterator / 36)));
			Graphics2D g2 = (Graphics2D) g;
			g2.setPaint(new Color(0f, 0f, 0f, 0.2f));
			Line2D line=new Line2D.Double(0.0, 2.0, 280.0+sinWidth * (iterator / 36.0), 2.0);
			g2.draw(line);
		}
	}
	class LabelAndLines extends JComponent{
		public LabelAndLines(){
			setVisible(false);
			setSize((int)sinWidth+1, 120);
			setLocation(535, 25);
		}
		public void paintComponent (Graphics g){
			super.paintComponent(g);
			Graphics2D g2=(Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.drawLine(0, 0, 0, getHeight()-1);
			g2.drawLine(getWidth()-1, 0, getWidth()-1, getHeight()-1);
			g2.drawLine(2, 100, 42, 100);
			g2.drawLine(2, 100, 12, 95);
			g2.drawLine(2, 100, 12, 105);
			g2.drawLine(getWidth()-43, 100, getWidth()-3, 100);
			g2.drawLine(getWidth()-13, 95, getWidth()-3, 100);
			g2.drawLine(getWidth()-13, 105, getWidth()-3, 100);
			g2.setFont(new Font("Serif", Font.BOLD, 14));
			FontMetrics fm=g2.getFontMetrics();
			g2.drawString(periodLabel,  (float)(getWidth()-fm.stringWidth(periodLabel))/2, 105f);
		}
	}
	class TriggerButton extends JButton{
			private final int period;
			private final String drawnLabel;
		public TriggerButton(String label, String _drawnLabel, int _period){
			super(label);
			period=_period;
			drawnLabel=_drawnLabel;
			setFocusable(false);
			setPreferredSize(new Dimension(120, 30));
			addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent me){
					for (int i=0; i<buttonBox.getComponents().length; i++){
						buttonBox.getComponents()[i].setEnabled(false);
					}
					buttonBox.getComponents();
					timer=new Timer(period, SinWaveDemoPanel.this);
					periodLabel=drawnLabel;
					linkingLine.setVisible(true);
					sinPoint.setVisible(true);
					timePlane.setVisible(true);
					labelAndLines.setVisible(true);
					timer.start();											
				}
			});
		}
	}
}
