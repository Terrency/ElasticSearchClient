package com.cxy.redisclient.service;

import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Tuple;

import com.cxy.redisclient.integration.key.Expire;
import com.cxy.redisclient.integration.key.IsKeyExist;
import com.cxy.redisclient.integration.zset.AddZSet;
import com.cxy.redisclient.integration.zset.AddZSetFactory;
import com.cxy.redisclient.integration.zset.ListZSet;
import com.cxy.redisclient.integration.zset.ListZSetPage;
import com.cxy.redisclient.integration.zset.RemoveMembers;

public class ZSetService {
	public void add(int id, String index, String key, Map<String, Double> values, int ttl) {
		AddZSet command = (AddZSet) new AddZSetFactory(id, index, key, values).getCommand();
		command.executeJedis();
		
		if(ttl != -1){
			Expire command1 = new Expire(id, index, key, ttl);
			command1.executeJedis();
		}
	}
	public Set<Tuple> list(int id, String index, String key){
		IsKeyExist command1 = new IsKeyExist(id, index, key);
		command1.executeJedis();
		if(!command1.isExist())
			throw new KeyNotExistException(id, index, key);
		
		ListZSet command = new ListZSet(id, index, key);
		command.executeJedis();
		return command.getValues();
	}
	public Set<Tuple> getPage(int id, String index, String key, int start, int end){
		ListZSetPage command = new ListZSetPage(id, index, key, start, end);
		command.executeJedis();
		return command.getPage();
	}
	public void addValues(int id, String index, String key, Map<String, Double> values) {
		AddZSet command = (AddZSet) new AddZSetFactory(id, index, key, values).getCommand();
		command.executeJedis();
	}
	public void removeMembers(int id, String index, String key, String[] members){
		RemoveMembers command = new RemoveMembers(id, index, key, members);
		command.executeJedis();
	}
}
