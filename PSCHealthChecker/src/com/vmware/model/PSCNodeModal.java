package com.vmware.model;

import java.util.ArrayList;

public class PSCNodeModal {
	private String psc_cn;
	private ArrayList<String> replicationList;
	private ArrayList<String> vcNodes;
	private String loadBalancer;
	private String site_cn;
	
	public String getSite_cn() {
		return site_cn;
	}
	public void setSite_cn(String site_cn) {
		this.site_cn = site_cn;
	}
	public String getLoadBalancer() {
		return loadBalancer;
	}
	public void setLoadBalancer(String loadBalancer) {
		this.loadBalancer = loadBalancer;
	}
	public ArrayList<String> getVcNodes() {
		return vcNodes;
	}
	public void setVcNodes(ArrayList<String> vcNodes) {
		this.vcNodes = vcNodes;
	}
	public String getPsc_cn() {
		return psc_cn;
	}
	public void setPsc_cn(String psc_cn) {
		this.psc_cn = psc_cn;
	}
	public ArrayList<String> getReplicationList() {
		return replicationList;
	}
	public void setReplicationList(ArrayList<String> replicationList) {
		this.replicationList = replicationList;
	}
	

}
