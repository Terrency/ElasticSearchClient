package com.cxy.redisclient.integration.server;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsRequest;
import org.elasticsearch.action.admin.indices.stats.IndicesStatsResponse;
import redis.clients.jedis.exceptions.JedisException;

public class QueryDBAmount extends JedisCommand {
	private String[] dbAmount;

	public String[] getDbAmount() {
		return dbAmount;
	}

	public QueryDBAmount(int id) {
		super(id);
	}

	@Override
	public void command() {
		try{
			ActionFuture<IndicesStatsResponse> isra = esclient.admin().indices().stats(new IndicesStatsRequest().all());
			if(isra.actionGet().getIndices().size() > 0)
				dbAmount = isra.actionGet().getIndices().keySet().toArray(new String[isra.actionGet().getIndices().size()]);
		}catch(JedisException e){
			dbAmount = new String[0];
		}
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_2_0;
	}

}
