package com.cxy.redisclient.service;

import java.util.Map;

import com.cxy.redisclient.integration.hash.AddHash;
import com.cxy.redisclient.integration.hash.DelField;
import com.cxy.redisclient.integration.hash.IsFieldExist;
import com.cxy.redisclient.integration.hash.ReadHash;
import com.cxy.redisclient.integration.hash.SetField;
import com.cxy.redisclient.integration.key.Expire;
import com.cxy.redisclient.integration.key.IsKeyExist;

public class HashService {
	public void add(int id, String index, String key, Map<String, String> values, int ttl) {
		AddHash command = new AddHash(id, index, key, values);
		command.executeJedis();
		
		if(ttl != -1){
			Expire command1 = new Expire(id, index, key, ttl);
			command1.executeJedis();
		}
	}
	
	public void update(int id, String index, String key, Map<String, String> values) {
		AddHash command = new AddHash(id, index, key, values);
		command.executeJedis();
	}
	
	public Map<String, String> read(int id, String index, String key) {
		IsKeyExist command1 = new IsKeyExist(id, index, key);
		command1.executeJedis();
		if(!command1.isExist())
			throw new KeyNotExistException(id, index, key);
		
		ReadHash command = new ReadHash(id, index, key);
		command.executeJedis();
		return command.getValue();
	}
	
	public void setField(int id, String index, String key, String field, String value){
		SetField command = new SetField(id, index, key, field, value);
		command.executeJedis();
	}
	
	public void delField(int id, String index, String key, String[] fields){
		DelField command = new DelField(id, index, key, fields);
		command.executeJedis();
	}
	
	public boolean isFieldExist(int id, String index, String key, String field){
		IsFieldExist command = new IsFieldExist(id, index, key, field);
		command.executeJedis();
		return command.isExist();
	}
}
