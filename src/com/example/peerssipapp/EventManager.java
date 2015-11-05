package com.example.peerssipapp;

import java.net.SocketException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import net.sourceforge.peers.Config;
import net.sourceforge.peers.FileLogger;
import net.sourceforge.peers.Logger;
import net.sourceforge.peers.media.AbstractSoundManager;
import net.sourceforge.peers.sip.core.useragent.SipListener;
import net.sourceforge.peers.sip.core.useragent.UserAgent;
import net.sourceforge.peers.sip.syntaxencoding.SipUriSyntaxException;
import net.sourceforge.peers.sip.transport.SipRequest;
import net.sourceforge.peers.sip.transport.SipResponse;

public class EventManager implements SipListener ,IncomingCallListner {

	private static final String TAG = "EventManager";
	private static UserAgent userAgent;
	private Config config;
	private static SipRequest sipRequest;
	static SipResponse sipResponse;
	Context context ;
	Logger log = new FileLogger(null);
	com.google.code.microlog4android.Logger logger = MainActivity.logger;
	public static SipResponse getSipResponse() {
		return sipResponse;
	}

	public static void setSipResponse(SipResponse sipResponse) {
		EventManager.sipResponse = sipResponse;
	}
	AbstractSoundManager soundManager = new AndroidSoundManager(false,log,null);
	
	public EventManager(Context context) throws SocketException {
		Config config = new MyConfig();
		Logger logger = new FileLogger(null);
		userAgent = new UserAgent(this, config, logger,soundManager );
		new Thread() {
			public void run() {
				try {
					userAgent.register();
					
				} catch (SipUriSyntaxException e) {
					e.printStackTrace();
				}
			}	
		}.start();
		this.context = context;
		
	}
	public static UserAgent getUserAgent(){
		return userAgent;
		
	}

	public static void call(final String callee) {
		new Thread() {
			@Override
			public void run() {
				try {
					sipRequest = userAgent.invite(callee, null);
				} catch (SipUriSyntaxException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	@Override
	public void calleePickup(SipResponse arg0) {
		// TODO Auto-generated method stub
		setSipResponse(arg0);
	}

	@Override
	public void error(SipResponse arg0) {
		// TODO Auto-generated method stub
		logger.debug("Error "+arg0.getReasonPhrase());

	}

	@Override
	public void incomingCall(SipRequest arg0, SipResponse arg1) {
		// TODO Auto-generated method stub
		logger.debug("Incoming call"+arg0.getRequestUri());
		

	}

	@Override
	public void registerFailed(SipResponse arg0) {
		// TODO Auto-generated method stub
		
		logger.debug("Registration successfull");

	}

	@Override
	public void registerSuccessful(SipResponse arg0) {
		// TODO Auto-generated method stub
		
		logger.debug("Registration successfull");


	}

	@Override
	public void registering(SipRequest arg0) {
		// TODO Auto-generated method stub
		logger.debug("Registering......");

	}

	@Override
	public void remoteHangup(SipRequest arg0) {
		// TODO Auto-generated method stub
		logger.debug("Remote hangup");
	}

	@Override
	public void ringing(SipResponse arg0) {
		// TODO Auto-generated method stub
		logger.debug("Ringing.....");

	}

	@Override
	public void hangupClicked(SipRequest sipRequest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pickupClicked(SipRequest sipRequest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void busyHereClicked(SipRequest sipRequest) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dtmf(char digit) {
		// TODO Auto-generated method stub
		
	}

}