package com.cxy.redisclient.integration.key;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;

public class RestoreKey extends JedisCommand {
	private String index;
	private String key;
	private byte[] value;
	
	public RestoreKey(int id, String index, String key, byte[] value) {
		super(id);
		this.index = index;
		this.key = key;
		this.value = value;
	}

	@Override
	protected void command() {
		jedis.restore(key, 0, value);
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_2_6;
	}

}
