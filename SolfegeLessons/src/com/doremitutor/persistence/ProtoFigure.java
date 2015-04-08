package com.doremitutor.persistence;

import java.io.Serializable;

import com.doremitutor.persistence.Constants.Pitch;
import com.doremitutor.persistence.Constants.Rhythm;
import com.doremitutor.persistence.ProtoInterfaces.BarProtoFigure;

public class ProtoFigure implements Serializable{
	public final Rhythm rhythm;
	private ProtoFigure(Rhythm rhythm){
		this.rhythm=rhythm;
	}
	public static final class ProtoRest extends ProtoFigure implements BarProtoFigure{
		private Rhythm rhythm;
		public ProtoRest(Rhythm rhythm){
			super(rhythm);
		}
		public Rhythm getRhythm(){
			return rhythm;
		}
	}
	public static abstract class ProtoNote extends ProtoFigure{
		public final Pitch pitch;
		public final boolean hasComma;
		public final boolean isLinked;
		private ProtoNote(Rhythm rhythm, Pitch pitch, boolean hasComma, boolean isLinked){
			super(rhythm);
			this.pitch=pitch;
			this.hasComma=hasComma;
			this.isLinked=isLinked;
		}
		public final Rhythm getRhythm(){
			return rhythm;
		}
		public final Pitch getPitch(){
			return pitch;
		}
	}
	public static final class BarProtoNote extends ProtoNote implements BarProtoFigure{
		public BarProtoNote(Rhythm rhythm, Pitch pitch, boolean hasComma, boolean isLinked){
			super(rhythm, pitch, hasComma, isLinked);
		}
	}
	public static final class GroupProtoNote extends ProtoNote{
		public GroupProtoNote(Rhythm rhythm, Pitch pitch, boolean hasComma, boolean isLinked){
			super(rhythm, pitch, hasComma, isLinked);
		}
	}
}