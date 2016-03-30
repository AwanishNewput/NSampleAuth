package com.sampleAuth.utility;

import java.io.Serializable;

import com.sampleAuth.mybatis.bean.User;
import com.sampleAuth.utility.BeanHolder;


public class Context {

	
	public static class SessionObject implements Serializable { 
		
		private static final long serialVersionUID = 4509797860938997072L;
		User user;
		String sessionKey;
		SessionObject() {
		}

		public SessionObject(User user, String key) {
			this.user = user;
			sessionKey = key;
		} 
		public User getUser() {
			return user;
		}
		public String getSessionKey() {
			return sessionKey;
		} 

	}

	private static Context context = new Context();

	public static Context getContext() {
		return context;
	} 
	private static final ThreadLocal<SessionObject> session = new ThreadLocal<Context.SessionObject>(); 

	public User getUser() throws Exception {
		if (session == null || session.get() == null) {
			throw new Exception("Session token is not valid");
		}		 
		try {
			return session.get().getUser();
		} catch (Exception e) {
			return null;
		}  
	}	

	public String getSessionKey() throws Exception {
		if (session == null || session.get() == null) {
			throw new Exception();
		}
		return session.get().getSessionKey();
	}
 
	void clearSession() {
		session.set(null);  
	} 
	
	void updateSession(String sessionKey) {  
		clearSession();
		User u = BeanHolder.getSessionsService().validate(sessionKey); 
		if(u != null){ 
			SessionObject so = new SessionObject(u, sessionKey);
			session.set(so); 
		}
	}
}
