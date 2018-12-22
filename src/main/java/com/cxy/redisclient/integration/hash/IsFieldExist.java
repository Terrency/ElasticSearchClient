package com.cxy.redisclient.integration.hash;

import java.util.Set;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;

public class IsFieldExist extends JedisCommand {
	private String db;
	private String key;
	private String field;
	private boolean exist;
	
	public IsFieldExist(int id, String db, String key, String field) {
		super(id);
		this.db = db;
		this.key = key;
		this.field = field;
	}

	@Override
	protected void command() {
		Set<String> fields = jedis.hkeys(key);
		exist = fields.contains(field);
	}

	public boolean isExist() {
		return exist;
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_2_0;
	}

}
