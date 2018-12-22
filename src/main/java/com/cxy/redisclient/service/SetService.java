package com.cxy.redisclient.service;

import java.util.List;
import java.util.Set;

import com.cxy.redisclient.integration.key.Expire;
import com.cxy.redisclient.integration.key.IsKeyExist;
import com.cxy.redisclient.integration.set.AddSet;
import com.cxy.redisclient.integration.set.AddSetFactory;
import com.cxy.redisclient.integration.set.ListSet;
import com.cxy.redisclient.integration.set.ListSetPage;
import com.cxy.redisclient.integration.set.RemoveSet;
import com.cxy.redisclient.integration.set.RemoveSetFactory;

public class SetService {
	public long add(int id, String db, String key, Set<String> values, int ttl) {
		AddSet command = (AddSet) new AddSetFactory(id, db, key, values).getCommand();
		command.executeJedis();
		
		if(ttl != -1){
			Expire command1 = new Expire(id, db, key, ttl);
			command1.executeJedis();
		}
		return command.getSize();

	}
	public long addValues(int id, String db, String key, Set<String> values) {
		AddSet command = (AddSet) new AddSetFactory(id, db, key, values).getCommand();
		command.executeJedis();
		return command.getSize();
		
	}
	public long addValues(int id, String db, String key, String[] values) {
		AddSet command = (AddSet) new AddSetFactory(id, db, key, values).getCommand();
		command.executeJedis();
		return command.getSize();
	}
	
	public Set<String> list(int id, String db, String key) {
		IsKeyExist command1 = new IsKeyExist(id, db, key);
		command1.executeJedis();
		if(!command1.isExist())
			throw new KeyNotExistException(id, db, key);
		
		ListSet command = new ListSet(id, db, key);
		command.executeJedis();
		return command.getValues();
	}
	public void remove(int id, String db, String key, Set<String> values) {
		RemoveSet command = (RemoveSet) new RemoveSetFactory(id, db, key, values).getCommand();
		command.executeJedis();
	}
	
	public List<String> getPage(int id, String db, String key, int start, int end) {
		IsKeyExist command1 = new IsKeyExist(id, db, key);
		command1.executeJedis();
		if(!command1.isExist())
			throw new KeyNotExistException(id, db, key);
		ListSetPage command = new ListSetPage(id, db, key, start, end);
		command.executeJedis();
		return command.getPage();
	}
}
