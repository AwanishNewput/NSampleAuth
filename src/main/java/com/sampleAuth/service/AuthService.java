package com.sampleAuth.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.TinyBitSet;
import org.springframework.stereotype.Service;

import com.sampleAuth.mybatis.bean.Auth;
import com.sampleAuth.mybatis.bean.AuthExample;
import com.sampleAuth.mybatis.bean.AuthExample.Criteria;
import com.sampleAuth.mybatis.mappers.AuthMapper;
import com.sampleAuth.utility.SMSSender;

@Service
public class AuthService {

	@Autowired
	private AuthMapper am;

	@Autowired
	private SMSSender smsSender;

	public boolean register(String phone, Integer isdCode) {

		String key = isdCode + phone;
		
		AuthExample example = new AuthExample();
		Criteria c = example.createCriteria();
		c.andPhoneEqualTo(key);

		List<Auth> lst = am.selectByExample(example);
		if (lst.size() == 0) {
			return this.add(key);
		} else {
			return this.update(lst.get(0));
		}
	}

	public boolean verify(String phone, String secureCode, Integer isdCode) throws Exception {
		boolean status = false;
		Long time = System.currentTimeMillis();

		String key = phone;
		key = isdCode + phone;
		AuthExample example = new AuthExample();
		Criteria c = example.createCriteria();

		c.andPhoneEqualTo(key).andSecurityCodeEqualTo(secureCode);

		List<Auth> lst = am.selectByExample(example);
		if (lst.size() > 0) {
			Auth authRec = lst.get(0);
			if (authRec.getExpiresAt() < time) {
				throw new Exception("Token expires");
			} else {
				status = this.updateStatus(authRec);
			}

		}
		return status;
	}

	public boolean add(String phone) {
		Long time = System.currentTimeMillis();
		Auth auth = new Auth();
		auth.setPhone(phone);
		String code = this.createSecureCode(4);

		auth.setSecurityCode(code);
		auth.setCreated(time);
		auth.setUpdated(time);
		auth.setExpiresAt(time + (4 * 60 * 1000));
		int i = am.insert(auth);
		if (i == 0) {
			return false;
		} else {
			// scSender.send(authKey, code);
			try {

				smsSender.sendSMS("NSampleAuth", phone, code);
			} catch (Exception exc) {
				exc.printStackTrace();
			}
			return true;
		}
	}

	public boolean update(Auth auth) {
		Long time = System.currentTimeMillis();
		String code = this.createSecureCode(4);

		auth.setSecurityCode(code);
		auth.setUpdated(time);
		auth.setExpiresAt(time + (2 * 60 * 60 * 1000));
		int i = am.updateByPrimaryKeySelective(auth);
		if (i == 0) {
			return false;
		} else {
			// asyncServ.doTask(new GenericSMSSender(auth.getAuthKey(), code));
			try {
				smsSender.sendSMS("NSampleAuth", auth.getPhone(), code);
			} catch (Exception exc) {
				exc.printStackTrace();
			}

			return true;
		}
	}

	public String createSecureCode(int numDigits) {
		String code = "";
		for (int i = 0; i < numDigits; i++) {
			Random r = new Random();
			code += Integer.toString(r.nextInt(10));
		}
		return code;
	}

	public boolean updateStatus(Auth authRec) {

		byte b = '0';
		authRec.setStatus(b);
		int i = am.updateByPrimaryKey(authRec);

		if (i > 0) {
			return true;
		} else {
			return false;
		}
	}

}
