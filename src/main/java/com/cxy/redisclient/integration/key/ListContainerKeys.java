package com.cxy.redisclient.integration.key;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.cxy.redisclient.domain.DataNode;
import com.cxy.redisclient.domain.Node;
import com.cxy.redisclient.domain.NodeType;
import com.cxy.redisclient.domain.RedisVersion;
import com.cxy.redisclient.dto.Order;
import com.cxy.redisclient.dto.OrderBy;
import com.cxy.redisclient.integration.ConfigFile;
import com.cxy.redisclient.integration.JedisCommand;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class ListContainerKeys extends JedisCommand {
	private String index;
	private String key;
	private Set<DataNode> keys = new TreeSet<DataNode>();
	private Order order;
	private OrderBy orderBy;
	private boolean flat;
	
	public Set<DataNode> getKeys() {
		return keys;
	}

	public ListContainerKeys(int id, String index, String key, boolean flat, Order order, OrderBy orderBy) {
		super(id);
		this.index = index;
		this.key = key;
		this.order = order;
		this.flat = flat;
		this.orderBy = orderBy;
	}
	
	public ListContainerKeys(int id, String index, String key, boolean flat, Order order) {
		super(id);
		this.index = index;
		this.key = key;
		this.order = order;
		this.flat = flat;
		this.orderBy = OrderBy.NAME;
	}
	
	public ListContainerKeys(int id, String index, String key, boolean flat) {
		super(id);
		this.index = index;
		this.key = key;
		this.flat = flat;
		this.order = Order.Ascend;
		this.orderBy = OrderBy.NAME;
	}

	@Override
	public void command() {
        SearchResponse response = null;
        key = key.replace(":", "");
        response = esclient.prepareSearch(index)
                .setTypes(key)
                .setExplain(true)
                .execute().actionGet();
        SearchHits searchHits = response.getHits();
        Iterator<SearchHit> iterator = searchHits.iterator();
        while(iterator.hasNext()){
            NodeType nodeType = NodeType.STRING;
            SearchHit searchHit = iterator.next();
            DataNode node = new DataNode(searchHit.getId(), index, searchHit.getSourceAsString(), nodeType, searchHit.getSourceAsString().length(), true, order, orderBy);
            keys.add(node);
        }
	}

	@Override
	public RedisVersion getSupportVersion() {
		return RedisVersion.REDIS_1_0;
	}

}
