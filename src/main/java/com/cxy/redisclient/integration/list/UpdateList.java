package com.cxy.redisclient.integration.list;

import java.util.List;

import com.cxy.redisclient.integration.key.DeleteKey;
import com.cxy.redisclient.integration.key.Expire;
import com.cxy.redisclient.integration.key.TTLs;

public class UpdateList extends AddList {
	private int ttl;
	public UpdateList(int id, String db, String key, List<String> values,
			boolean headTail) {
		super(id, db, key, values, headTail, true);
	}

	@Override
	protected void beforeAdd() {
		TTLs command1 = new TTLs(id, index, key);
		command1.executeJedis(jedis);
		ttl = (int) command1.getSecond();
		
		DeleteKey command = new DeleteKey(id, index, key);
		command.executeJedis(jedis);
		
		
	}

	@Override
	protected void afterAdd() {
		Expire command2 = new Expire(id, index, key, ttl);
		command2.executeJedis(jedis);
	}

}
