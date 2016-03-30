package com.sampleAuth.utility;

import org.springframework.stereotype.Service;

import com.sampleAuth.service.SessionsService;

@Service
public class BeanHolder {

	private static SessionsService sessionsService;

	public static SessionsService getSessionsService() {
		return sessionsService;
	}

	public static void setSessionsService(SessionsService sessionsService) {
		BeanHolder.sessionsService = sessionsService;
	}
}