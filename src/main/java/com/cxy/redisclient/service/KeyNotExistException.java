package com.cxy.redisclient.service;

import com.cxy.redisclient.integration.I18nFile;
import com.cxy.redisclient.presentation.RedisClient;

public class KeyNotExistException extends RuntimeException {
	private static final long serialVersionUID = 958469726423878744L;
	protected int id;
	protected String index;
	private String key;
	
	public KeyNotExistException(int id, String index, String key){
		this.id = id;
		this.index = index;
		this.key = key;
	}

	@Override
	public String getMessage() {
		return RedisClient.i18nFile.getText(I18nFile.KEYNOTEXIST)+": "+key;
	}
}
