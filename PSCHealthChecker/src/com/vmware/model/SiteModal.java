package com.vmware.model;

import java.util.ArrayList;
import java.util.HashMap;

public class SiteModal {

		private String site_cn;
		private ArrayList<PSCNodeModal> pscServers;
		private HashMap<String,ArrayList<String>> pscReplicationServers;
		private ArrayList<String> services;
		private HashMap<String, ArrayList<String>> lbPSC;
		

		public HashMap<String, ArrayList<String>> getLbPSC() {
			return lbPSC;
		}

		public void setLbPSC(HashMap<String, ArrayList<String>> lbPSC) {
			this.lbPSC = lbPSC;
		}
	
		@Override
		public String toString() {
			return "SiteModal [site_cn=" + site_cn + ", pscServers="
					+ pscServers + ", pscReplicationServers="
					+ pscReplicationServers + ", services=" + services
					+ ", lbPSC=" + lbPSC + "]";
		}

		public String getJSON(){
			return  "{\"site_cn\":\"" + site_cn + "\", \"pscServers\":\""
					+ pscServers + "\", \"pscReplicationServers\":"
					+ getJsonFromHash(pscReplicationServers) + ", \"services\":\"" + services + "\", \"lbPSC\":" 
					+ getJsonFromHash(lbPSC) + "}";		      
		}
		private String getJsonFromHash(
				HashMap<String, ArrayList<String>> pscRS) {
			StringBuffer strOut = new StringBuffer();
			if (pscRS == null)
				return "null";
			strOut.append("{");
			for (String key : pscRS.keySet()){
				strOut.append("\"" + key + "\": [");
				for( String str:  pscRS.get(key)){
					strOut.append("\"");
					String[] repUrl = str.split("//");
					if (repUrl.length ==1)
						strOut.append(repUrl[0] + "\",");
					else
						strOut.append(repUrl[1] + "\",");
				}
				strOut.replace(strOut.length()-1, strOut.length(), "],");
				
			}
			strOut.replace(strOut.length()-1, strOut.length(), "}");
			
			return strOut.toString();
		}

		public HashMap<String, ArrayList<String>> getPscReplicationServers() {
			return pscReplicationServers;
		}
		public void setPscReplicationServers(
				HashMap<String, ArrayList<String>> pscReplicationServers) {
			this.pscReplicationServers = pscReplicationServers;
		}
		public String getSite_cn() {
			return site_cn;
		}
		public void setSite_cn(String site_cn) {
			this.site_cn = site_cn;
		}
		public ArrayList<PSCNodeModal> getPscServers() {
			return pscServers;
		}
		public void setPscServers(ArrayList<PSCNodeModal> pscServers) {
			this.pscServers = pscServers;
		}
		public ArrayList<String> getServices() {
			return services;
		}
		public void setServices(ArrayList<String> services) {
			this.services = services;
		}
}
