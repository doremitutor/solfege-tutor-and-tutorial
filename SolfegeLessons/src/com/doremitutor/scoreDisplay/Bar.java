package com.doremitutor.scoreDisplay;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SpringLayout;

import com.doremitutor.data.ScoreConstants;
import com.doremitutor.midi.MidiProxy;
import com.doremitutor.persistence.ProtoBar;
import com.doremitutor.persistence.ProtoGroup;
import com.doremitutor.persistence.ProtoBar.BarLineType;
import com.doremitutor.persistence.ProtoFigure.BarProtoNote;
import com.doremitutor.persistence.ProtoFigure.ProtoRest;
import com.doremitutor.persistence.ProtoInterfaces.ProtoCluster;
import com.doremitutor.scoreDisplay.Figure.BarNote;
import com.doremitutor.scoreDisplay.Figure.GroupNote;
import com.doremitutor.scoreDisplay.Figure.Rest;
import com.doremitutor.scoreDisplay.Interfaces.Cluster;

public class Bar extends JPanel{
	private final JPanel clusterPane=new JPanel();
	private final JPanel barLinePane=new JPanel();
	private final JPanel lightPanel=new JPanel(null);
	private final Cursor cursor=new Cursor();
	private boolean isLineFeed;
	private ProtoBar protoBar;
	
	public Bar(ProtoBar _protoBar){
		this.protoBar=_protoBar;
		FrameWorkUtilities.makeTransparentAndUnfocusableSpringContainer(this);
		FrameWorkUtilities.makeTransparentAndUnfocusableSpringContainer(clusterPane);
		FrameWorkUtilities.makeTransparentAndUnfocusableSpringContainer(barLinePane);
		lightPanel.setFocusable(false);
		lightPanel.setVisible(false);
		lightPanel.setBackground(new Color(255, 255, 0, 50));
		add(clusterPane);
		add(barLinePane);
		FrameWorkUtilities.springComponents(this, true);
		barLinePane.add(new BarLine(protoBar.barLineType));
		FrameWorkUtilities.springComponents(barLinePane, false);
		isLineFeed=_protoBar.isLineFeed;
		for(ProtoCluster protoCluster:protoBar.protoClusters){
			Cluster cluster;
			if(protoCluster instanceof ProtoGroup){
				cluster=new Group((ProtoGroup)protoCluster);
			}else{
				if(protoCluster instanceof BarProtoNote){
					cluster=new BarNote((BarProtoNote)protoCluster);
				}else{
					cluster=new Rest((ProtoRest)protoCluster);
				}			
			}	
			clusterPane.add(cluster.getPane());
		}
		FrameWorkUtilities.springComponents(clusterPane, true);
		addComponentListener(new ComponentAdapter(){
			public void componentResized(ComponentEvent e){
				Staff parentStaff=(Staff)getParent().getParent();
				parentStaff.countBars();
			}
		});
		add(lightPanel);
		SpringLayout lo=(SpringLayout)getLayout();
		lo.putConstraint(SpringLayout.NORTH, lightPanel, 0, SpringLayout.NORTH, this);
		lo.putConstraint(SpringLayout.EAST, lightPanel, 0, SpringLayout.EAST, this);
		lo.putConstraint(SpringLayout.SOUTH, lightPanel, 0, SpringLayout.SOUTH, this);
		lo.putConstraint(SpringLayout.WEST, lightPanel, 0, SpringLayout.WEST, this);		
		add(cursor);
		lo.putConstraint(SpringLayout.NORTH, cursor, 0, SpringLayout.NORTH, this);
		lo.putConstraint(SpringLayout.WEST, cursor, 0, SpringLayout.WEST, this);
		addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				MidiProxy.THE.goTo(Bar.this);
			}
		});
	}
	public boolean isLineFeed(){
		return isLineFeed;
	}
	public ProtoBar getProtoBar(){
		return protoBar;
	}
	public void setLineFeed(boolean on){
		isLineFeed=on;
		protoBar=new ProtoBar(protoBar.barLineType, on, protoBar.protoClusters);
	}
	public List<Figure> readFigures(){
		List<Figure> figures=new ArrayList<Figure>();
		for(Cluster cluster:readClusters()){
			if(cluster instanceof Figure){
				figures.add((Figure)cluster);
			}else{
				Group group=(Group)cluster;
				for(GroupNote groupNote:group.readFigures()){
					figures.add(groupNote);
				}
			}
		}
		return Collections.unmodifiableList(figures);
	}
	public List<Cluster> readClusters(){
		List<Cluster> clusters=new ArrayList<Cluster>();
		for(Component c:clusterPane.getComponents()){
			assert c instanceof Cluster:"Non cluster component in clusterPane";
			clusters.add((Cluster)c);
		}
		return Collections.unmodifiableList(clusters);
	}
	public BarLineType getBarLineType(){
		return protoBar.barLineType;
	}
	public void showCursor(boolean on){
		if(cursor.isVisible()!=on){
			cursor.setVisible(on);
		}
	}
	public void showLight(boolean on){
		if(lightPanel.isVisible()!=on){
			lightPanel.setVisible(on);
		}
	}
	int getMinimumWidth(){
		return ((SpringLayout)getLayout()).minimumLayoutSize(this).width;
	}
	private class Cursor extends JPanel{	
		Cursor(){
			setLayout(null);
			Dimension size = new Dimension(3, ScoreConstants.STAFF_HEIGHT);
			setMinimumSize(size);
			setPreferredSize(size);
			setMaximumSize(size);
			setBackground(new Color(255, 255, 0, 200));
			setOpaque(true);
			setFocusable(false);
			setVisible(false);
		}
	}
}