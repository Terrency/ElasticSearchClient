package com.cxy.redisclient.integration.zset;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;

public class RemoveMembers extends JedisCommand {
	private String index;
	private String key;
	private String[] members;
	
	public RemoveMembers(int id, String index, String key, String[] members) {
		super(id);
		this.index = index;
		this.key = key;
		this.members = members;
	}

	@Override
	protected void command() {
		jedis.zrem(key, members);
		
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_1_2;
	}

}
