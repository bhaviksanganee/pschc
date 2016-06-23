package com.vmware.model;

import java.util.ArrayList;

public class PSCNodeModal {
	private String cn;
	private ArrayList<String> replicationList;
	private ArrayList<String> services;
	
	
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public ArrayList<String> getReplicationList() {
		return replicationList;
	}
	public void setReplicationList(ArrayList<String> replicationList) {
		this.replicationList = replicationList;
	}
	public ArrayList<String> getServices() {
		return services;
	}
	public void setServices(ArrayList<String> services) {
		this.services = services;
	}

}
