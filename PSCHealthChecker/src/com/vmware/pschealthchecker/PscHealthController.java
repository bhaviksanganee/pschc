package com.vmware.pschealthchecker;

import javax.servlet.http.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vmware.services.PSCLdapConnection;

@Controller
public class PscHealthController{
	
	@RequestMapping("/welcome")
	public ModelAndView welcomepage() throws Exception {
		
		ModelAndView modelandview = new ModelAndView("connectionpage");
		modelandview.addObject("welcomeMessage" , "Hey, just started");
		return modelandview;
	}
	
	@RequestMapping(value = "/connect" , method = RequestMethod.POST)
	public ModelAndView connectVMDIR() throws Exception {
		
		ModelAndView modelandview = new ModelAndView("HealthChecker");
		modelandview.addObject("welcomeMessage" , "Hey, just started");
		
		PSCLdapConnection conn = new PSCLdapConnection();
		conn.setLDAPConnection();
		System.out.println("sys-" + conn.genPSCNodes());
		return modelandview;
	}

}
