package com.doremitutor.midi;

import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;

import com.doremitutor.midi.MidiProxy.Status;

class MidiMetaEventListener implements MetaEventListener{
	
	static MidiMetaEventListener THE=null;	
	static{THE=new MidiMetaEventListener();}
	
	private MidiProxy proxy=MidiProxy.THE;	
	
	private MidiMetaEventListener(){
		assert THE==null:"Singleton case";
		proxy.showCursor(0, true);
	}
		
		public void meta(MetaMessage msg) {//
		final int type=msg.getType();
		final byte[] data=msg.getData();
		switch(type){
		case 71: //figure lighting
		case 72:					
			int index=data[0]+data[1]*256;
			proxy.enlightFigure(index, type==71?true:false);
			break;
		case 73: // bar lighting
		case 74:
			proxy.barIndex=(int)data[0];
			proxy.enlightBar((int)data[0], type==73?true:false);			
			break;
		case 47: // end of track ???
			assert data.length==0:"Illegal data length";
			if(proxy.status==Status.PRECOUNTING){			
				proxy.stopSequencer();
				proxy.play(false);				
			}else{
				proxy.cleanUp();
			}			
			break;
		default:
			throw new AssertionError("Invalid metamessage type");
		}		
	}
}