package com.vmware.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import com.unboundid.ldap.sdk.BindResult;
import com.unboundid.ldap.sdk.DN;
import com.unboundid.ldap.sdk.Filter;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPConnectionPool;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPSearchException;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.SearchRequest;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;
import com.vmware.model.PSCNodeModal;
import com.vmware.model.SiteModal;


//private static final int  max_numbof_connection = 2;

public class PSCLdapConnection {
	
	public static final String OBJECT_CLASS = "objectClass";
	public static final String BASE_DN = "dc=vSphere,dc=local";
	public static LDAPConnection connection = null;
	
	public void setLDAPConnection()
	{	
	try {

//		connection = new LDAPConnection();
//		connection.connect("10.162.41.122", 389);
//		 
//		connection.bind("cn=Administrator,cn=users,dc=vSphere,dc=local", "Admin!23");
		connection = new LDAPConnection();
		connection.connect("wdc-esxcpd-dhcp-24255", 389);
		 
		connection.bind("cn=Administrator,cn=users,dc=vSphere,dc=local", "VMware1!");
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
		
		//objectClass = vmwLKUPServiceRegistration
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
					}
					siteModal.setServices(arrServices);
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
					System.out.println("inside if");
				}else{
					psc_replication = new HashMap<String,ArrayList<String>>();
					psc_replication.put(psc_cn[1], replicationList);
					siteModal.setPscReplicationServers(psc_replication);
					arrPSCServers.add(siteModal);
					System.out.println(" Site = " + siteModal.toString());
					System.out.println(" array = " + arrPSCServers.toString());
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
}
