package com.vmware.pschealthchecker;

import javax.servlet.http.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.vmware.services.PSCLdapConnection;

public class PscHealthController extends AbstractController {
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ModelAndView modelandview = new ModelAndView("HealthChecker");
		modelandview.addObject("welcomeMessage" , "Hey, just started");
		
		PSCLdapConnection conn = new PSCLdapConnection();
		conn.setLDAPConnection();
		System.out.println("sys-" + conn.genPSCNodes());
		return modelandview;
	}

}
