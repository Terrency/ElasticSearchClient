package com.cxy.redisclient.integration.list;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;

public class RemoveValue extends JedisCommand {
	private String index;
	private String key;
	private boolean headTail;
	
	public RemoveValue(int id, String index, String key, boolean headTail) {
		super(id);
		this.index = index;
		this.key = key;
		this.headTail = headTail;
	}

	@Override
	protected void command() {
		if(headTail){
			jedis.lpop(key);
		} else {
			jedis.rpop(key);
		}
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_1_0;
	}

}
