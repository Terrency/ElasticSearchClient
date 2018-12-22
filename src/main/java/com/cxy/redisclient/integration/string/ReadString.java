package com.cxy.redisclient.integration.string;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;


public class ReadString extends JedisCommand {
	private String index;
	private String key;
	private String value;
	
	public String getValue() {
		return value;
	}

	public ReadString(int id, String index, String key) {
		super(id);
		this.index = index;
		this.key = key;
	}

	@Override
	public void command() {
		value = jedis.get(key);
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_1_0;
	}

}
