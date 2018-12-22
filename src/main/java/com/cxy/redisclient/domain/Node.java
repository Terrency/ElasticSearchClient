package com.cxy.redisclient.domain;

import com.cxy.redisclient.dto.Order;

public class Node implements Comparable<Node> {
	protected int id;
	protected String index;
	protected String key;
	protected NodeType type;
	protected Order order;
	
	public Node(int id, String index, String key, NodeType type, Order order) {
		super();
		this.id = id;
		this.index = index;
		this.key = key;
		this.type = type;
		this.order = order;
	}
	
	public Node(int id, String index, String key, NodeType type) {
		super();
		this.id = id;
        this.index = index;
        this.key = key;
		this.type = type;
		this.order = Order.Ascend;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public NodeType getType() {
		return type;
	}
	public void setType(NodeType type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		Node node = (Node) obj;
		return node.getKey().equals(this.getKey()) && node.getType().equals(this.getType()) && this.id == node.getId() && this.index.equals(node.getIndex());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    @Override
	public int hashCode() {
		return key.hashCode()+type.hashCode();
	}

	public int compareTo(Node o) {
		Integer id1 = new Integer(id);
		Integer id2 = new Integer(o.getId());
		
		int result = id1.compareTo(id2);
		
		if(result == 0){

			if(index.equals(o.getIndex()))
				result = this.getKey().compareTo(o.getKey());
		}
				
		if(order == Order.Ascend)
			return result;
		return result * -1;
	}
}
