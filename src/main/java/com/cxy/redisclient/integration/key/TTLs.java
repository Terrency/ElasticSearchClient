package com.cxy.redisclient.integration.key;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;

public class TTLs extends JedisCommand {
	private String index;
	private String key;
	private long second;
	
	public long getSecond() {
		return second;
	}

	public TTLs(int id, String index, String key) {
		super(id);
		this.index = index;
		this.key = key;
	}

	@Override
	protected void command() {
		second = jedis.ttl(key);
		
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_1_0;
	}

}
