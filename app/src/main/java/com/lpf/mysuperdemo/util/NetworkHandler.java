package com.lpf.mysuperdemo.util;

import java.util.ArrayList;
import java.util.List;

import com.lpf.mysuperdemo.interfaces.INetObserver;

public class NetworkHandler {
	private static NetworkHandler instance = new NetworkHandler();
	public static NetworkHandler getInstance(){
		return instance;
	}
	private List<INetObserver> listenerList;
	private NetworkHandler(){
		listenerList = new ArrayList<INetObserver>();	
	}
	
	public void addNetObserver(INetObserver observer){
		if(observer!=null){
			listenerList.add(observer);
		}
	}
	
	public void removeObserver(INetObserver observer){
		if(observer!=null){
			listenerList.remove(observer);
		}
	}
	
	public void updateNetwork(boolean connected){
		if(listenerList!=null){
			for(INetObserver listener:listenerList){
				listener.updateNetState(connected);
			}
		}
	}
}
