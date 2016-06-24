package com.vmware.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.unboundid.ldap.sdk.Filter;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.SearchRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;
import com.vmware.model.SiteModal;


//private static final int  max_numbof_connection = 2;

public class PSCLdapConnection {
	
	public static final String OBJECT_CLASS = "objectClass";
	public static final String BASE_DN = "dc=vSphere,dc=local";
	public static LDAPConnection connection = null;
	
	public void setLDAPConnection(HashMap<String,String> hmConnect)
	{	
	try {

		connection = new LDAPConnection();
		connection.connect("wdc-esxcpd-dhcp-24255", 389);
		connection.bind("cn=Administrator,cn=users,dc=vSphere,dc=local", "VMware1!");
		
//		connection = new LDAPConnection();
//		connection.connect(hmConnect.get("host"), Integer.valueOf(hmConnect.get("port")));
//		connection.bind(hmConnect.get("username"), hmConnect.get("password"));
	    } catch (LDAPException e) {
	        String es = e.getExceptionMessage();
	        System.out.println(es);
	    }
	}
	
	public ArrayList<SiteModal> genPSCNodes() throws LDAPException {

		ArrayList<SiteModal> arrPSCServers = new ArrayList<SiteModal>();	
		HashMap<String, SiteModal> siteNodes = new HashMap<String, SiteModal>();	
		Filter filter = Filter.createEqualityFilter(OBJECT_CLASS , "vmwDirServer");
		Filter filter2 = 
				Filter.createEqualityFilter(OBJECT_CLASS , "vmwReplicationAgreement");
		
		SearchRequest searchRequest = new SearchRequest(BASE_DN , SearchScope.SUB, filter);
		SearchResult sr;
		String SITE_DN = "cn=Sites,cn=Configuration,dc=vsphere,dc=local";
		
		HashMap<String,ArrayList<String>> lbMap = new HashMap<String,ArrayList<String>>();
		try {
		    sr = connection.search(searchRequest);
			if (sr.getEntryCount() == 0) {
				throw new LDAPException(ResultCode.INVALID_CREDENTIALS);
			}
			SiteModal siteModal;
			HashMap<String,ArrayList<String>> psc_replication = null;
			for (SearchResultEntry entry : sr.getSearchEntries()){
				String[] dn = entry.getDN().split(",");
				String[] psc_cn = dn[0].split("=");
				String[] site_cn = dn[2].split("=");
				
				if (siteNodes.containsKey(site_cn[1])){
					siteModal = siteNodes.get(site_cn[1]);
				}else{
					siteModal = new SiteModal();
					Filter serviceRegistrationFilter = 
							Filter.createEqualityFilter(OBJECT_CLASS , "vmwLKUPServiceRegistration");
					SearchRequest searchRequest3 = new SearchRequest(dn[2] +"," + SITE_DN , SearchScope.SUB, serviceRegistrationFilter);
					SearchResult servicesResult = connection.search(searchRequest3);
					ArrayList<String> arrServices = new ArrayList<String>();
					for (SearchResultEntry entry3 : servicesResult.getSearchEntries()){
						arrServices.add(entry3.getAttributeValue("vmwLKUPType"));
						if(entry3.getAttributeValue("vmwLKUPType").equals("cs.identity")){
								getLBMap(entry3,lbMap);
						}
					}
					siteModal.setServices(arrServices);
					siteModal.setLbPSC(lbMap);
				}
				siteModal.setSite_cn(site_cn[1]);
				
				SearchRequest searchRequest3 = new SearchRequest(entry.getDN() , SearchScope.SUB, filter2);
				SearchResult sr3;
				sr3 = connection.search(searchRequest3);
				ArrayList<String> replicationList = null;
				replicationList = new ArrayList<String>();
				for (SearchResultEntry entry2 : sr3.getSearchEntries()){
			    	String[] replication_dn = entry2.getDN().split(",");
			    	String[] replication_cn = replication_dn[0].split("=");
			    	replicationList.add(replication_cn[1]);
				}
				if (siteNodes.containsKey(site_cn[1])){
					psc_replication = siteModal.getPscReplicationServers();
					psc_replication.put(psc_cn[1], replicationList);
					siteModal.setPscReplicationServers(psc_replication);
				}else{
					psc_replication = new HashMap<String,ArrayList<String>>();
					psc_replication.put(psc_cn[1], replicationList);
					siteModal.setPscReplicationServers(psc_replication);
					arrPSCServers.add(siteModal);
				}
				siteNodes.put(site_cn[1], siteModal);
			}

		}
		catch (Exception lse){
			// The search failed for some reason.
			System.out.println("error - " + lse.getMessage());
		}

		  return arrPSCServers;
		}
	private Map<String,ArrayList<String>> getLBMap(SearchResultEntry ssoEntry, Map<String,ArrayList<String>> lbMap) throws LDAPSearchException{
		 			String ownerId = ssoEntry.getAttributeValue("vmwLKUPOwnerId");
		 			String fqdn = ownerId != null ? ownerId.split("@")[0] : null;
		 			if (fqdn == null) return null;
		 
		 			Filter endpointFilter = 
		 							Filter.createEqualityFilter("vmwLKUPEndpointType","com.vmware.cis.cs.identity.sso");
		 					SearchRequest searchRequest = new SearchRequest(ssoEntry.getDN(), SearchScope.SUB, endpointFilter);
		 			SearchResult endPointResult = connection.search(searchRequest);
		 			String stsUri = null;
		 			for (SearchResultEntry entry3 : endPointResult.getSearchEntries()){
						stsUri = entry3.getAttributeValue("vmwLKUPURI");
					}
		 			String haFqdn = stsUri.split("/")[2];
		 
		 			if(fqdn.equals(haFqdn)){
		 				return lbMap; 
		 			}else{
		 				ArrayList<String> nodesList =  lbMap.get(haFqdn);
		 				nodesList = nodesList == null ? new ArrayList<String>() : nodesList;
		 				nodesList.add(fqdn);
		 				lbMap.put(haFqdn, nodesList);
		 				return lbMap;
		 			}
		 			
		 		}
}
