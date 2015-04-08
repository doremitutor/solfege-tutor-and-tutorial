package com.doremitutor.persistence;

import java.io.Serializable;

import com.doremitutor.persistence.Constants.Cleff;
import com.doremitutor.persistence.Constants.Time;


public final class ProtoLesson implements Serializable{
	public final Cleff cleff;
	public final Time time;
	public final ProtoBar[] protoBars;
	public final int tempo;
	
	public ProtoLesson(Cleff cleff, Time time, int tempo, ProtoBar[] protoBars){
		this.cleff=cleff;
		this.time=time;
		this.tempo=tempo;
		this.protoBars=protoBars;
	}
}