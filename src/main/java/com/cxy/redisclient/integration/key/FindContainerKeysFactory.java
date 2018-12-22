package com.cxy.redisclient.integration.key;

import java.util.List;

import com.cxy.redisclient.domain.NodeType;
import com.cxy.redisclient.integration.JedisCommandFactory;

public class FindContainerKeysFactory extends JedisCommandFactory {

	public FindContainerKeysFactory(int id, String index, String container, String keyPattern) {
		super(id);
		commands.add(new FindContainerKeys28(id, index, container, keyPattern));
		commands.add(new FindContainerKeys10(id, index, container, keyPattern));
	}
	
	public FindContainerKeysFactory(int id, String index, String container, List<NodeType> valueTypes, String keyPattern, boolean forward) {
		super(id);
		commands.add(new FindContainerKeys28(id, index, container, keyPattern, valueTypes, forward));
		commands.add(new FindContainerKeys10(id, index, container, keyPattern, valueTypes, forward));
	}

	public FindContainerKeys getListContainerAllKeys(){
		return (FindContainerKeys) getCommand();
	}
}
