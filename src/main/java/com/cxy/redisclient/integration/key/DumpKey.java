package com.cxy.redisclient.integration.key;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;

public class DumpKey extends JedisCommand {
	private String index;
	private String key;
	private byte[] value;
	
	public DumpKey(int id, String index, String key) {
		super(id);
		this.index = index;
		this.key = key;
	}

	public byte[] getValue() {
		return value;
	}

	@Override
	protected void command() {
		value = jedis.dump(key);
		
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_2_6;
	}

}
