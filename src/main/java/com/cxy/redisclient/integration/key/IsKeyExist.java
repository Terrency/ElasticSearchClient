package com.cxy.redisclient.integration.key;

import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.integration.JedisCommand;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.get.GetResponse;

public class IsKeyExist extends JedisCommand {
	private String index;
	private String key;
	private boolean exist;
	
	public boolean isExist() {
		return exist;
	}

	public IsKeyExist(int id, String index, String key) {
		super(id);
		this.index = index;
		this.key = key;
	}

	@Override
	protected void command() {
		GetResponse getResponse = esclient.prepareGet(index, getTypeFromKey(key), getKeyObjectFromKey(key).get("id").getAsString()).execute().actionGet();
		String result = getResponse.getSourceAsString();
		if(StringUtils.isBlank(result)){
		    exist = false;
        } else {

		    exist = true;
        }
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_1_0;
	}

}
