package com.sampleAuth.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


import org.apache.openjpa.lib.util.Base16Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sampleAuth.mybatis.bean.Auth;
import com.sampleAuth.mybatis.bean.AuthExample;
import com.sampleAuth.mybatis.bean.Session;
import com.sampleAuth.mybatis.bean.SessionExample;
import com.sampleAuth.mybatis.bean.User;
import com.sampleAuth.mybatis.bean.UserExample;
import com.sampleAuth.mybatis.bean.UserExample.Criteria;
import com.sampleAuth.mybatis.mappers.AuthMapper;
import com.sampleAuth.mybatis.mappers.SessionMapper;
import com.sampleAuth.mybatis.mappers.UserMapper;

@Service
public class UserService{

	@Autowired
	SessionMapper sm;

	@Autowired
	UserMapper um;
	
	@Autowired
	private AuthMapper am;

	public User getUser(Integer userId) {
		return um.selectByPrimaryKey(userId);
	}

	public Boolean hasSignedUpAlready(Integer isdCode, String phone) {
		UserExample ue = new UserExample();
		ue.createCriteria().andPhoneEqualTo(phone);
		List<User> users = um.selectByExample(ue);
		if (users.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	
	public User upsertUser(String name, String email, String phone, Integer isdCode) {
		AuthExample example = new AuthExample();
		phone = isdCode+phone;
		example.createCriteria().andPhoneEqualTo(phone);
		List<Auth> authLst = am.selectByExample(example);
		if (authLst.size() > 0 && (authLst.get(0).getStatus() != null && !authLst.get(0).getStatus().equals(""))) {			
		UserExample userexample = new UserExample();
		Criteria ci = userexample.createCriteria();
		if (phone != null) {
			ci.andPhoneEqualTo(phone);
		} else if (email != null) {
			ci.andEmailEqualTo(email);
		}
		List<User> lst = um.selectByExample(userexample);
		if (lst.size() > 0) {
			User u = lst.get(0);
			return this.updateUser(u.getId(), name, email, phone);
		} else {
			addUser(name, email, phone, isdCode);
			List<User> list = um.selectByExample(userexample);
			User u = list.get(0);
			return u;
		}
		}else{
			return null;
		}
	}

	public User updateUser(Integer id, String name, String email, String phone) {
		User record = new User();

		record.setId(id);
		record.setUsername(name);
		record.setPhone(phone);
		record.setEmail(email);
		record.setUpdated(System.currentTimeMillis() + "");
		int i = um.updateByPrimaryKeySelective(record);

		if (i == 0) {
			return null;
		} else {

			return record;
		}
	}

	public User addUser(String name, String email, String phone, Integer isdCode) {
		Long time = System.currentTimeMillis();
		User record = new User();

		record.setUsername(name);
		record.setPhone(phone);
		record.setEmail(email);
		record.setCreated(time + "");
		record.setUpdated(time + "");
		int i = um.insertSelective(record);
		if (i == 0) {
			return null;
		} else {
			return record;
		}
	}


	public Session addSession(Integer userId, String phone,String email) {
		Session record = null;
		int res = 0;
		Long time = System.currentTimeMillis();
		SessionExample sessionEx = new SessionExample();
		sessionEx.createCriteria().andPhoneEqualTo(phone);
		List<Session> lst = sm.selectByExample(sessionEx);
		
		sessionEx.clear();
		sessionEx.createCriteria().andPhoneEqualTo(phone);
		lst = sm.selectByExample(sessionEx);
		if (lst.size() > 0) {
			record = lst.get(0);
			record.setToken(this.createSessionKey(userId,email));
			record.setUpdated(time);
			record.setExpiresAt(time + (1 * 60 * 1000));

			res = sm.updateByPrimaryKey(record);
		}

		if (record == null) {
			record = new Session();
			record.setToken(createSessionKey(userId,email));
			record.setUserId(userId);
			record.setPhone(phone);
			record.setExpiresAt(time + (1 * 60 * 1000));
			record.setCreated(time);
			record.setUpdated(time);
			res = sm.insert(record);
		}

		if (res == 0) {
			return null;
		} else {
			return record;
		}
	}
	
	public String createSessionKey(int userId,String email){
		try {
			return Base16Encoder.encode(MessageDigest.getInstance("MD5")
					.digest((email + "-" + System.currentTimeMillis() + userId).getBytes()));
		} catch (NoSuchAlgorithmException e) {
			return e.getMessage();
		}
	}
}
