package com.doremitutor.sound;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import com.doremitutor.support.Texts;
import com.doremitutor.support.Texts.Lang;
import com.doremitutor.support.Texts.Text;

public class SoundWavesPanel extends JPanel implements ActionListener{
	static Lang LANG;
	private SinWave sinWave;
	private Wave[] wave;
	private final int waveSeparation=8;
	private final int numWaves=87;
	private final int waveHeight=220;
	private final int waveLocationY=30;
	private final int firstWaveWidth=5;
	private final int commonWaveWidth=1;
	private final int firstWaveX=27;
	private final int commonWaveOffsetX=firstWaveX+firstWaveWidth-1;
	private final int sinWavePanelY=290;
	private final int sinWaveHeight=110;
	private Timer oscTimer=new Timer(0, this);
	private final int[] offset={-21, -20, -18, -15, -11, -6, 0, 6, 11, 15, 18, 20, 21};
	private final int initIt=6;
	private int tempIt;
	private boolean tempRising;
	private final Font font=new Font(Font.SANS_SERIF, Font.BOLD, 16);
	static private SoundWavesPanel instance=null;
	
	static SoundWavesPanel getInstance(){
		assert instance==null:"Singleton case";
		return instance=new SoundWavesPanel();
	}
	
	private SoundWavesPanel(){
		setLayout(null);
		JLabel soundWavesLabel=new JLabel(Texts.getText(Text.SOUND_WAVES_LINES, LANG)){
			{
				setSize(750, 20);
				setLocation(0, 0);
				setOpaque(false);
				setFont(font);
				setHorizontalAlignment(SwingConstants.CENTER);
			}
		};
		add(soundWavesLabel);
		wave=new Wave[numWaves];
		for(int i=0; i<wave.length; i++){
			wave[i]=new Wave(i);
			add(wave[i]);
		}
		JLabel compresionLabel=new JLabel(Texts.getText(Text.AIR_COMPRESSION, LANG)){
			{
				setSize(750, 20);
				setLocation(0, 260);
				setOpaque(false);
				setFont(font);
				setHorizontalAlignment(SwingConstants.CENTER);
			}
		};
		add(compresionLabel);
		SinWavePanel sinWavePane=new SinWavePanel();
		sinWave=sinWavePane.sinwave;
		add(sinWavePane);
		JLabel label=new JLabel(Texts.getText(Text.KEEP_BUTTON_DOWN_TO_ANIMATE, LANG)){
			{
				setSize(750, 25);				
				setLocation(0, 410);
				setOpaque(false);
				setVisible(true);
				setFont(font);
				setHorizontalAlignment(SwingConstants.CENTER);
			}
		};
		add(label);
		Button button1=new Button(Texts.getText(Text.EVERY_SEC, LANG), 9, 42);
		add(button1);
		Button button2=new Button(Texts.getText(Text.EVERY_TWO_SEC, LANG), 256, 84);
		add(button2);
		Button button3=new Button(Texts.getText(Text.EVERY_FOUR_SEC, LANG), 503, 168);
		add(button3);
	}
	private class Wave extends JPanel{
		private final int initX;
		private Movement currentMove;
		Wave(int index){
			super(null);
			if(index>0){
				initX=commonWaveOffsetX+index*waveSeparation;
				setSize(commonWaveWidth, waveHeight);
				setBackground(index==43?Color.CYAN:Color.BLACK);
			}else{
				initX=firstWaveX;
				setSize(firstWaveWidth, waveHeight);
				setBackground(Color.RED);
			}
			setVisible(true);
			setOpaque(true);
			init();
		}
		private void init(){
			setPosition(new Movement(initIt, true));			
		}
		private void setPosition(Movement _move){
			currentMove=_move;
			setLocation(initX+offset[currentMove.it], waveLocationY);
		}
	}
	private class Movement{
		private int it;
		private boolean rising;
		Movement(int _it, boolean _rising){
			it=_it;
			rising=_rising;
		}
	}
	private Movement getNextMove(Movement movement){
		tempIt=movement.it;
		tempRising=movement.rising;
		if(tempIt<=0||tempIt>=12){
			tempRising=!tempRising;
		}
		tempIt+=tempRising?1:-1;
		return new Movement(tempIt, tempRising);
	}
	private class Button extends JButton{				
		Button(String label, int x, int _period){
			super(label);
			setVisible(true);
			setSize(238, 35);
			setLocation(x, 440);
			setFocusable(false);
			final int period=_period;
			addMouseListener(new MouseListener(){
				public void mouseEntered(MouseEvent me){
				}
				public void mousePressed(MouseEvent me){
					startOscillating(period);
				}
				public void mouseReleased(MouseEvent me){
					stopOscilation();
				}
				public void mouseExited(MouseEvent me){
					stopOscilation();
					for(int i=0; i<wave.length;i++){
						wave[i].init();
					}
					sinWave.reset();
				}
				public void mouseClicked(MouseEvent me){
				}
			});			
		}
	}
	private void startOscillating(int period){
		oscTimer.setDelay(period);
		oscTimer.start();
	}
	private void stopOscilation(){
		if(oscTimer.isRunning()){
			oscTimer.stop();
		}
	}
	int count=0;
	public void actionPerformed(ActionEvent ae){
		for(int i=wave.length-1; i>0;i--){
			wave[i].setPosition(wave[i-1].currentMove);
		}
		wave[0].setPosition(getNextMove(wave[0].currentMove));		
		int tempX=sinWave.getX();
		if(tempX>=0){
			tempX-=waveSeparation*24*3;
		}
		tempX+=waveSeparation;		
		sinWave.setLocation(tempX, 0);
	}
	private class SinWave extends JPanel{
		private final int width=(numWaves-1)*waveSeparation*2+4;
		private final int midY=sinWaveHeight/2-1;
		private final int maxYshift=midY-1;
		private final double widthPeriod=((double)width/2)/((double)(numWaves-1)/((double)(offset.length-1)*2));
		SinWave(){
			setLayout(null);
			setSize(width, 110);
			reset();
			setOpaque(false);
			setFocusable(false);
		}
		private void reset(){			
			setLocation(-width, 0);
		}
		public void paintComponent(Graphics g){
			Graphics2D g2=(Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			GeneralPath gp=new GeneralPath();
			gp.moveTo(width-1, midY-maxYshift);
			for (int i=width;i>=0;i--){
				gp.lineTo(i, midY-maxYshift*(Math.cos(Math.PI*2*((width-i+4)%widthPeriod)/widthPeriod)));
			}
			g2.draw(gp);			
		}
	}
	private class SinWavePanel extends JPanel{
		private SinWave sinwave=new SinWave();
		SinWavePanel(){
			setLayout(null);
			setSize(numWaves*waveSeparation, sinWaveHeight);
			setLocation(firstWaveX, sinWavePanelY);
			setOpaque(false);
			setFocusable(false);
			add(sinwave);
		}
		public void paintComponent(Graphics g){
			Graphics2D g2=(Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			int midY=getHeight()/2;
			g2.drawLine(0, 0, 0, getHeight());
			g2.drawLine(0, midY, getWidth()-1, midY);
		}
	}
}