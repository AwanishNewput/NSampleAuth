package com.sampleAuth.rest.resource;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sampleAuth.service.AuthService;
import com.sampleAuth.utility.JsonResService;

@Controller
@Path("auth")
public class AuthResource {

	@Autowired
	private AuthService authServ;

	@Autowired
	private JsonResService jsonResService;

	
	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public JSONObject register(@FormParam("phone") String phone, @FormParam("isdCode") Integer isdCode) {

		if (phone.trim().length() < 10) {
			jsonResService.errorResponse("Phone number is too short");
			return jsonResService.responseSender();
		}
		boolean b = authServ.register(phone, isdCode);
		
		if (b)
			 jsonResService.setData("Please verify with sms token");
		else
			jsonResService.setData("Getting an error to register");
		
		return jsonResService.responseSender();
	}

	@POST
	@Path("/verify")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public JSONObject verify(@FormParam("phone") String phone, @FormParam("smsCode") String code,
			@FormParam("isdCode") Integer isdCode) {

		if (phone.trim().length() < 10) {
			jsonResService.errorResponse("Phone number is too short");
			return jsonResService.responseSender();
		}
		boolean  s = false;
		try {

			s = authServ.verify(phone.trim(), code.trim(), isdCode);
			if (s) {
				jsonResService.setData(s+"");

			} else {
				jsonResService.errorResponse("incorrect security code");
			}
		} catch (Exception e) {
			jsonResService.errorResponse(e.getMessage());
			return jsonResService.responseSender();
		}
		return jsonResService.responseSender();
	}
}
