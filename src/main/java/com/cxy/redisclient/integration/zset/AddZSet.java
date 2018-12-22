package com.cxy.redisclient.integration.zset;

import java.util.Map;

import com.cxy.redisclient.domain.NodeType;
import com.cxy.redisclient.integration.I18nFile;
import com.cxy.redisclient.integration.JedisCommand;
import com.cxy.redisclient.presentation.RedisClient;

public abstract class AddZSet extends JedisCommand {
	protected String index;
	protected String key;
	protected Map<String, Double> values;
	
	public AddZSet(int id, String index, String key, Map<String, Double> values) {
		super(id);
		this.index = index;
		this.key = key;
		this.values = values;
	}

	@Override
	protected void command() {
		if(jedis.exists(key) && getValueType(key) != NodeType.SORTEDSET)
			throw new RuntimeException(RedisClient.i18nFile.getText(I18nFile.ZSETEXIST)+key);
		addZSet();
			
		
	}
	
	protected abstract void addZSet();

}
