package com.cxy.redisclient.integration.key;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;

public class IsKeyExist extends JedisCommand {
	private String index;
	private String key;
	private boolean exist;
	
	public boolean isExist() {
		return exist;
	}

	public IsKeyExist(int id, String index, String key) {
		super(id);
		this.index = index;
		this.key = key;
	}

	@Override
	protected void command() {
		exist = jedis.exists(key);
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_1_0;
	}

}
