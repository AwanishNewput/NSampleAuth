package com.sampleAuth.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.SmsFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Sms;

@Service
public class SMSSender {

	public static final String ACCOUNTSID = "ACc4fd05e197e58a4ccaf2d03aedd9d091";
	public static final String AUTHTOKEN = "6b885a33f987d872956ed0af6df0534d";
	public static final String TWILIO_FROM_NUMBER = "+13312096786";

	public void sendSMS(String appName, String toNumber,
			String code) {

		TwilioRestClient client = new TwilioRestClient(ACCOUNTSID, AUTHTOKEN);
 	

		Account acct = client.getAccount();
		SmsFactory smsFactory = acct.getSmsFactory();  
		Map<String, String> params = new HashMap<String, String>();
		params.put("From", TWILIO_FROM_NUMBER);
		params.put("To", "+"+toNumber);
		params.put("Body", "Your "+appName+" confirmation code is " + code  );

		try {
			Sms sms = smsFactory.create(params);
		} catch (TwilioRestException e) {
			e.printStackTrace();
		}
	}
}
