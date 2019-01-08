package com.cxy.redisclient.integration.key;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;

public class RenameKey extends JedisCommand {
	private String index;
	private String oldKey;
	private String newKey;
	private boolean overwritten;
	private Long result;
	
	public Long getResult() {
		return result;
	}

	public RenameKey(int id, String index, String oldKey, String newKey, boolean overwritten) {
		super(id);
		this.index = index;
		this.oldKey = oldKey;
		this.newKey = newKey;
		this.overwritten = overwritten;
				
	}

	@Override
	public void command() {
        IndexRequest indexRequest = new IndexRequest(index, getTypeFromKey(newKey), getKeyObjectFromKey(newKey).get("id").getAsString());
        indexRequest.source(getKeyObjectFromKey(newKey).getAsString());
        esclient.index(indexRequest).actionGet();
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_1_0;
	}

}
