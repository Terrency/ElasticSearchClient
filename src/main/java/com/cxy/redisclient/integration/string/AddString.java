package com.cxy.redisclient.integration.string;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;

public class AddString extends JedisCommand {
	private String index;
	private String key;
	private String value;
	
	public AddString(int id, String index, String key, String value) {
		super(id);
		this.index = index;
		this.key = key;
		this.value = value;
	}

	@Override
	public void command() {
		jedis.set(key, value);
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_1_0;
	}

}
