package com.cxy.redisclient.integration.string;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;
import com.cxy.redisclient.integration.key.Expire;
import com.cxy.redisclient.integration.key.TTLs;

public class UpdateString extends JedisCommand {
	private String index;
	private String key;
	private String value;
	
	public UpdateString(int id, String index, String key, String value) {
		super(id);
		this.index = index;
		this.key = key;
		this.value = value;
	}

	@Override
	protected void command() {
		TTLs command1 = new TTLs(id, index, key);
		command1.executeJedis(jedis);
		int ttl = (int) command1.getSecond();
		
		jedis.set(key, value);
		
		Expire command2 = new Expire(id, index, key, ttl);
		command2.executeJedis(jedis);
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_1_0;
	}

}
