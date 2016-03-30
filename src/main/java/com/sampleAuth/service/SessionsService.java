package com.sampleAuth.service; 

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sampleAuth.mybatis.bean.Session;
import com.sampleAuth.mybatis.bean.SessionExample;
import com.sampleAuth.mybatis.bean.User;
import com.sampleAuth.mybatis.mappers.SessionMapper;



@Service
public class SessionsService {
	
	@Autowired
	SessionMapper sm; 
	
	@Autowired
	UserService uServ;
	
	public User validate(String token){
		User u = null;
		SessionExample example = new SessionExample();
		example.createCriteria().andTokenEqualTo(token);
		List<Session> lst = sm.selectByExample(example);
		if(lst.size() > 0){
			Session s = lst.get(0);
			if(s.getExpiresAt() == -1 || s.getExpiresAt() > System.currentTimeMillis()){ 
				u = uServ.getUser(s.getUserId());
			}
		}
		return u;
	} 
}
