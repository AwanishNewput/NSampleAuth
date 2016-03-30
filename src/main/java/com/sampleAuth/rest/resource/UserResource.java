package com.sampleAuth.rest.resource;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sampleAuth.mybatis.bean.Session;
import com.sampleAuth.mybatis.bean.User;
import com.sampleAuth.service.UserService;
import com.sampleAuth.utility.Context;
import com.sampleAuth.utility.JsonResService;

@Component
@Path("user")
public class UserResource {

	@Autowired
	UserService uServ;
	
	@Autowired
	JsonResService jsonResService;

	@GET
	@Path("/{userId}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public JSONObject getUser(@PathParam("userId") Integer userId) throws Exception {
		if(Context.getContext().getUser().getId() != null){
		jsonResService.setData(uServ.getUser(userId));
		return jsonResService.responseSender();
		}else{
			jsonResService.errorResponse("Session is not valid");
			return jsonResService.responseSender();
		}
	}

	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public JSONObject updateProfileName(@FormParam("name") String name, @FormParam("email") String email,
			@FormParam("phone") String phone, @FormParam("isdCode") int isdCode) throws Exception {

		if (name == null || name.isEmpty()) {
			jsonResService.errorResponse("Name can not be null");
			return jsonResService.responseSender();
		}

		User u = uServ.upsertUser(name, email, phone, isdCode);

		Session s = null;
		if (u != null) {
			s = uServ.addSession(u.getId(), phone,email);
			jsonResService.setData(s);
		}else{
			jsonResService.errorResponse("Please verify user");
		}
		
		return jsonResService.responseSender();
	}
}
