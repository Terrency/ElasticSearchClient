package com.cxy.redisclient.integration.hash;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;

public class DelField extends JedisCommand {
	private String index;
	private String key;
	private String[] fields;
	public DelField(int id, String index, String key, String[] fields) {
		super(id);
		this.index = index;
		this.key = key;
		this.fields = fields;
	}
	@Override
	protected void command() {
		jedis.hdel(key, fields);
		
	}
	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_2_0;
	}

}
