package com.cxy.redisclient.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.cxy.redisclient.domain.ContainerKey;
import com.cxy.redisclient.domain.DataNode;
import com.cxy.redisclient.domain.Node;
import com.cxy.redisclient.domain.NodeType;
import com.cxy.redisclient.domain.RedisObject;
import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.domain.Server;
import com.cxy.redisclient.dto.Order;
import com.cxy.redisclient.dto.OrderBy;
import com.cxy.redisclient.integration.key.DeleteKey;
import com.cxy.redisclient.integration.key.DumpKey;
import com.cxy.redisclient.integration.key.Expire;
import com.cxy.redisclient.integration.key.FindContainerKeys;
import com.cxy.redisclient.integration.key.FindContainerKeysFactory;
import com.cxy.redisclient.integration.key.GetEncoding;
import com.cxy.redisclient.integration.key.GetIdletime;
import com.cxy.redisclient.integration.key.GetRefcount;
import com.cxy.redisclient.integration.key.GetSize;
import com.cxy.redisclient.integration.key.IsKeyExist;
import com.cxy.redisclient.integration.key.ListContainerKeys;
import com.cxy.redisclient.integration.key.ListContainers;
import com.cxy.redisclient.integration.key.ListKeys;
import com.cxy.redisclient.integration.key.RenameKey;
import com.cxy.redisclient.integration.key.RestoreKey;
import com.cxy.redisclient.integration.key.TTLs;
import com.cxy.redisclient.integration.server.QueryServerVersion;
import com.cxy.redisclient.integration.string.AddString;
import com.cxy.redisclient.integration.string.ReadString;
import com.cxy.redisclient.integration.string.UpdateString;

public class NodeService {
	public void addString(int id, String index, String key, String value, int ttl) {
		AddString command = new AddString(id, index, key ,value);
		command.executeEs();
	}
	
	public String readString(int id, String index, String key) {
		IsKeyExist command1 = new IsKeyExist(id, index, key);
		command1.executeEs();
		if(!command1.isExist())
			throw new KeyNotExistException(id, index, key);
		
		ReadString command = new ReadString(id, index, key);
		command.executeEs();
		return command.getValue();
	}
	
	public void updateString(int id, String index, String key, String value) {
		UpdateString command = new UpdateString(id, index, key, value);
		command.executeEs();
	}
	public void deleteKey(int id, String index, String key) {
		DeleteKey command = new DeleteKey(id, index, key);
		command.executeEs();
	}
	
	public boolean renameKey(int id, String index, String oldKey, String newKey, boolean overwritten) {
		RenameKey command = new RenameKey(id, index, oldKey, newKey, overwritten);
		command.executeEs();
		if(!overwritten && command.getResult() == 0)
			return false;
		else
			return true;
	}
	
	public Set<Node> listKeys(int id, String index) {
		ListKeys command = new ListKeys(id, index);
		command.executeEs();
		return command.getNodes();
	}
	
	public Set<Node> listContainers(int id, String index, String key, boolean flat, Order order) {
		ListContainers command = new ListContainers(id, index, key, flat, order);
		command.executeEs();
		return command.getContainers();
		
	}
	
	public Set<Node> listContainers(int id, String index, String key, boolean flat) {
		ListContainers command = new ListContainers(id, index, key, flat);
		command.executeEs();
		return command.getContainers();
		
	}
	
	public Set<DataNode> listContainerKeys(int id, String index, String key, boolean flat, Order order, OrderBy orderBy) {
		ListContainerKeys command = new ListContainerKeys(id, index, key, flat, order, orderBy);
		command.executeEs();
		return command.getKeys();
	}
	
	public Set<DataNode> listContainerKeys(int id, String index, String key, boolean flat) {
		ListContainerKeys command = new ListContainerKeys(id, index, key, flat);
		command.executeEs();
		return command.getKeys();
	}
	
	public Set<Node> listContainerAllKeys(int id, String index, String container) {
		FindContainerKeys command = new FindContainerKeysFactory(id, index, container, "*").getListContainerAllKeys();
		command.executeEs();
		return command.getKeys();
	}
	
	public Set<String> renameContainer(int id, String index, String oldContainer, String newContainer, boolean overwritten, boolean renameSub) {
		Set<String> failContainer = new HashSet<String>();
		
		if(renameSub){
			FindContainerKeys command = new FindContainerKeysFactory(id, index, oldContainer, "*").getListContainerAllKeys();
			command.executeEs();
			Set<Node> nodes = command.getKeys();
			
			for(Node node: nodes) {
				renameKey(id, index, oldContainer, newContainer, overwritten,
						failContainer, node);
			}
		}else{
			Set<DataNode> nodes = listContainerKeys(id, index, oldContainer, true);
			
			for(Node node: nodes) {
				renameKey(id, index, oldContainer, newContainer, overwritten,
						failContainer, node);
			}
		}
		
		
		return failContainer;
	}

	private void renameKey(int id, String index, String oldContainer,
			String newContainer, boolean overwritten,
			Set<String> failContainer, Node node) {
		String newKey = node.getKey().replaceFirst(oldContainer, newContainer);
		RenameKey command1 = new RenameKey(id, index, node.getKey(), newKey, overwritten);
		command1.executeEs();
		if(!overwritten && command1.getResult() == 0)
			failContainer.add(newKey);
	}
	
	public void deleteContainer(int id, String index, String container, boolean deleteSub) {
		if(deleteSub){
			FindContainerKeys command = new FindContainerKeysFactory(id, index, container, "*").getListContainerAllKeys();
			command.executeEs();
			Set<Node> nodes = command.getKeys();
			
			for(Node node: nodes) {
				deleteKey(id, index, node.getKey());
			}
		}else{
			Set<DataNode> nodes = listContainerKeys(id, index, container, true);
			
			for(Node node: nodes) {
				deleteKey(id, index, node.getKey());
			}
		}
	}
	
	public RedisVersion listServerVersion(int id) {
		QueryServerVersion command = new QueryServerVersion(id);
		command.executeEs();
		return command.getVersionInfo();
	}
	
	public String pasteContainer(int sourceId, String sourceIndex, String sourceContainer, int targetId, String targetIndex, String targetContainer, boolean copy, boolean overwritten) {
		Set<Node> nodes = listContainerAllKeys(sourceId, sourceIndex, sourceContainer);
		
		if(sourceId == targetId && sourceIndex == targetIndex && targetContainer.equals(new ContainerKey(sourceContainer).getUpperContainer())){
			if(!copy)
				return null;
			if(sourceContainer.equals(""))
				return null;
			else {
				String target = new ContainerKey(sourceContainer).appendLastContainer(String.valueOf(System.currentTimeMillis()));
				
				for(Node node: nodes) {
					String targetKey = node.getKey().replaceFirst(sourceContainer, target);
					pasteKey(sourceId, sourceIndex, node.getKey(), targetId, targetIndex, targetKey, copy, overwritten);
				}
				return target;
			}
		} else {
			for(Node node: nodes) {
				String targetKey = targetContainer + new ContainerKey(node.getKey()).getRelativeContainer(sourceContainer);
				pasteKey(sourceId, sourceIndex, node.getKey(), targetId, targetIndex, targetKey, copy, overwritten);
			}
			return null;
		}
	}
	

	public String pasteKey(int sourceId, String sourceIndex, String sourceKey, int targetId, String targetIndex, String targetKey, boolean copy, boolean overwritten) {
		boolean changeTarget = false;
		
		if(sourceId == targetId && sourceIndex == targetIndex && sourceKey.equals(targetKey)) {
			if(!copy)
				return null;
			String key = new ContainerKey(sourceKey).getKeyOnly();
			
			String source = key + String.valueOf(System.currentTimeMillis());
			targetKey = sourceKey.replaceFirst(key, source);
			changeTarget = true;
		}
			
		if(overwritten && isKeyExist(targetId, targetIndex, targetKey)){
			deleteKey(targetId, targetIndex, targetKey);
		}
		DumpKey command1 = new DumpKey(sourceId, sourceIndex, sourceKey);
		command1.executeEs();
		byte[] value = command1.getValue();
		
		RestoreKey command2 = new RestoreKey(targetId, targetIndex, targetKey, value);
		command2.executeEs();
		
		if(!copy)
			deleteKey(sourceId, sourceIndex, sourceKey);
		
		if(changeTarget)
			return targetKey;
		else
			return null;
	}
	
	public boolean isKeyExist(int id, String index, String key) {
		IsKeyExist command = new IsKeyExist(id, index, key);
		command.executeEs();
		return command.isExist();
	}
	
	public Node findNext(Node findNode, NodeType searchFrom, int id, String index, String container, List<NodeType> searchNodeType, String pattern, boolean forward){
		Set<Node> nodes = find(searchFrom, id, index, container, searchNodeType, pattern, forward);
		boolean find = false;
		
		for(Node node: nodes) {
			if(find)
				return node;
			else if(findNode.equals(node))
				find = true;
		}
		return null;
	}
	public Set<Node> find(NodeType searchFrom, int id, String index, String container, List<NodeType> searchNodeType, String pattern, boolean forward) {
		switch(searchFrom) {
		case ROOT:
			ServerService service = new ServerService();
			List<Server> servers = service.listAll();
			Set<Node> nodes = new TreeSet<Node>();
			if(forward){
				for(Server server:servers)
					nodes.addAll(findKeysFromServer(server.getId(), searchNodeType, pattern, true));
			} else {
				for(int i = servers.size(); i > 0 ; i --){
					Server server1 = servers.get(i-1);
					nodes.addAll(findKeysFromServer(server1.getId(), searchNodeType, pattern, false));
				}
			}
			return nodes;
			
		case SERVER:
			return findKeysFromServer(id, searchNodeType, pattern, forward);
		
		case DATABASE:
			return findKeys(id, index, "", searchNodeType, pattern, forward);
		
		case CONTAINER:
			return findKeys(id, index, container, searchNodeType, pattern, forward);
		default:
			throw new IllegalArgumentException();
		}
	}

	private Set<Node> findKeysFromServer(int id, List<NodeType> searchNodeType,
			String pattern, boolean forward) {
		ServerService service = new ServerService();
		String[] amount = service.listDBs(id);
		
		Set<Node> nodes = new TreeSet<Node>();
		
		/*if(forward){
			for(int i = 0; i < amount.length; i ++) {
				nodes.addAll(findKeys(id, i, "", searchNodeType, pattern, true));
			}
		}else{
			for(int i = amount.length; i > 0 ; i--){
				nodes.addAll(findKeys(id, i-1, "", searchNodeType, pattern, false));
			}
		}*/
		return nodes;
	}
	
	
	public Set<Node> findKeys(int id, String index, String container, List<NodeType> searchNodeType, String keyPattern, boolean forward) {
		FindContainerKeys command = new FindContainerKeysFactory(id, index, container, searchNodeType, keyPattern, forward).getListContainerAllKeys();
		command.executeEs();
		return command.getKeys();
	}
	
	public long getSize(int id, String index, String key) {
		GetSize command = new GetSize(id, index, key);
		command.executeEs();
		return command.getSize();
		
	}
	public long getTTL(int id, String index, String key) {
		if(!isKeyExist(id, index, key))
			throw new KeyNotExistException(id, index, key);
		
		TTLs command = new TTLs(id, index, key);
		command.executeEs();
		long ttl = command.getSecond();
		
		if(ttl == -2)
			throw new KeyNotExistException(id, index, key);
		return ttl;
	}
	public RedisObject getObjectInfo(int id, String index, String key){
		GetRefcount command1 = new GetRefcount(id, index, key);
		command1.executeEs();
		GetIdletime command2 = new GetIdletime(id, index, key);
		command2.executeEs();
		GetEncoding command3 = new GetEncoding(id, index, key);
		command3.executeEs();
		
		return new RedisObject(command1.getCount(), command3.getEncoding(), command2.getIdleTime());
	}
	
	
}
