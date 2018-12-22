package com.cxy.redisclient.integration.key;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;


public class DeleteKey extends JedisCommand {
	private String index;
	private String key;
	
	public DeleteKey(int id, String index, String key) {
		super(id);
		this.index = index;
		this.key = key;
	}

	@Override
	public void command() {
		jedis.del(key);
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_1_0;
	}

}
