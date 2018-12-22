package com.cxy.redisclient.integration.key;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;

public class GetIdletime extends JedisCommand {
	private String index;
	private String key;
	private Long idleTime;
	
	public Long getIdleTime() {
		return idleTime;
	}

	public GetIdletime(int id, String index, String key) {
		super(id);
		this.index = index;
		this.key = key;
	}

	@Override
	protected void command() {
		idleTime = jedis.objectIdletime(key);
		
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_2_2;
	}

}
