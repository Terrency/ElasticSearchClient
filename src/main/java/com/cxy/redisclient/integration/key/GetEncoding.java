package com.cxy.redisclient.integration.key;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;

public class GetEncoding extends JedisCommand {
	private String index;
	private String key;
	private String encoding;
	
	public String getEncoding() {
		return encoding;
	}

	public GetEncoding(int id, String index, String key) {
		super(id);
		this.index = index;
		this.key = key;
	}

	@Override
	protected void command() {
		encoding = jedis.objectEncoding(key);
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_2_2;
	}

}
