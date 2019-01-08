package com.cxy.redisclient.integration.key;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.cxy.redisclient.domain.ContainerKey;
import com.cxy.redisclient.domain.Node;
import com.cxy.redisclient.domain.NodeType;
import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.dto.Order;
import com.cxy.redisclient.integration.ConfigFile;
import com.cxy.redisclient.integration.JedisCommand;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;

public class ListContainers extends JedisCommand {
	private String index;
	private String key;
	private Set<Node> containers = new TreeSet<Node>();
	private Order order;
	private boolean flat;
	private final String separator = ConfigFile.getSeparator();
	
	public ListContainers(int id, String index, String key, boolean flat, Order order) {
		super(id);
		this.index = index;
		this.key = key;
		this.order = order;
		this.flat = flat;
	}
	
	public ListContainers(int id, String index, String key, boolean flat) {
		super(id);
		this.index = index;
		this.key = key;
		this.order = Order.Ascend;
		this.flat = flat;
	}

	@Override
	public void command() {
        GetMappingsResponse res = null;
        try {
            res = esclient.admin().indices().getMappings(new GetMappingsRequest().indices(index)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        ImmutableOpenMap<String, MappingMetaData> mapping = res.mappings().get(index);
        for (ObjectObjectCursor<String, MappingMetaData> c : mapping) {
            NodeType nodeType = NodeType.CONTAINER;
            Node node = new Node(String.valueOf(id), index, c.key, nodeType, order);
            containers.add(node);
        }
	}

	public Set<Node> getContainers() {
		return containers;
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_1_0;
	}

}
