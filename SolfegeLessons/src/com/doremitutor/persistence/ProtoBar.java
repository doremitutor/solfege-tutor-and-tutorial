package com.doremitutor.persistence;

import java.io.Serializable;

import com.doremitutor.persistence.ProtoInterfaces.ProtoCluster;


public final class ProtoBar implements Serializable {
	public enum BarLineType {SINGLE, DOUBLE, START_REPEAT, END_REPEAT, FINAL}
	public final BarLineType barLineType;
	public final boolean isLineFeed;
	public final ProtoCluster[] protoClusters;

	public ProtoBar(BarLineType barLineType, boolean lineFeed, ProtoCluster[] protoClusters) {
		this.barLineType = barLineType;
		this.isLineFeed = lineFeed;
		this.protoClusters = protoClusters;
	}
}