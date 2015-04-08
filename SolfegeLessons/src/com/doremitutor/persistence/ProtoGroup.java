package com.doremitutor.persistence;

import com.doremitutor.persistence.ProtoFigure.GroupProtoNote;
import com.doremitutor.persistence.ProtoInterfaces.ProtoCluster;

public final class ProtoGroup implements ProtoCluster{
	public final GroupProtoNote[] groupProtoNotes;
	public ProtoGroup(GroupProtoNote[] groupProtoNotes){
		this.groupProtoNotes=groupProtoNotes;
	}
}