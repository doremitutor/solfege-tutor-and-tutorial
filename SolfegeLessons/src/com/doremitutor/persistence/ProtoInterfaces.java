package com.doremitutor.persistence;

import java.io.Serializable;

public final class ProtoInterfaces{
	private ProtoInterfaces(){assert false:"Monostate case";}

	public interface ProtoCluster extends Serializable{}
	public interface BarProtoFigure extends ProtoCluster{}
}
