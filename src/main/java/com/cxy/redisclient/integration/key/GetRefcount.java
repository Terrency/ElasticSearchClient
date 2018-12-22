package com.cxy.redisclient.integration.key;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;

public class GetRefcount extends JedisCommand {
	private String index;
	private String key;
	private Long count;
	
	public Long getCount() {
		return count;
	}

	public GetRefcount(int id, String index, String key) {
		super(id);
		this.index = index;
		this.key = key;
	}

	@Override
	protected void command() {
		count = jedis.objectRefcount(key);
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_2_2;
	}

}
