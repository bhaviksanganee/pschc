package com.vmware.pschealthchecker;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vmware.model.SiteModal;
import com.vmware.services.PSCLdapConnection;

@Controller
public class PscHealthController{
	
	@RequestMapping("/welcome")
	public ModelAndView welcomepage() throws Exception {
		
		ModelAndView modelandview = new ModelAndView("connectionpage");
		return modelandview;
	}
	
	@RequestMapping(value = "/connect" , method = RequestMethod.POST)
	public ModelAndView connectVMDIR(HttpServletRequest request) throws Exception {
		
		ModelAndView modelandview = new ModelAndView("HealthChecker");
		HashMap<String,String> hmConnect = new HashMap<String,String>();
		
		hmConnect.put("host",request.getParameter("host"));
		hmConnect.put("port",request.getParameter("port"));
		hmConnect.put("username",request.getParameter("username"));
		hmConnect.put("password",request.getParameter("password"));

		PSCLdapConnection conn = new PSCLdapConnection();
		conn.setLDAPConnection(hmConnect);
		ArrayList<SiteModal> siteNodesObject = conn.genPSCNodes();
		StringBuffer nodesObjStr = new StringBuffer();
		nodesObjStr.append("[");
		for (SiteModal arr : siteNodesObject){
			nodesObjStr.append(arr.getJSON() + ",");
		}
		nodesObjStr.replace(nodesObjStr.length()-1, nodesObjStr.length(), "]");
//		System.out.println("nodestr - " + nodesObjStr);
		modelandview.addObject("siteNodesObject" , nodesObjStr);
		modelandview.addObject("siteNodes" , siteNodesObject);
		return modelandview;
	}

}
