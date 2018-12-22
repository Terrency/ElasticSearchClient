package com.cxy.redisclient.integration.zset;

import java.util.Set;

import redis.clients.jedis.Tuple;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;

public class ListZSet extends JedisCommand {
	public Set<Tuple> getValues() {
		return values;
	}

	private String index;
	private String key;
	private Set<Tuple> values;
	
	public ListZSet(int id, String index, String key) {
		super(id);
		this.index = index;
		this.key = key;
	}

	@Override
	protected void command() {
		values = jedis.zrangeWithScores(key, 0, -1);
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_1_2;
	}

}
