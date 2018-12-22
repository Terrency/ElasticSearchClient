package com.cxy.redisclient.integration.key;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;

public class Expire extends JedisCommand {
	private String index;
	private String key;
	private int second;
	
	public Expire(int id, String index, String key, int l) {
		super(id);
		this.index = index;
		this.key = key;
		this.second = l;
	}

	@Override
	protected void command() {
		if(second != -1)
			jedis.expire(key, second);
		else
			jedis.persist(key);
		
	}

	@Override
	public RedisVersion getSupportVersion() {
		if(second != -1)
			return RedisVersion.REDIS_1_0;
		else
			return RedisVersion.REDIS_2_2;
	}

}
