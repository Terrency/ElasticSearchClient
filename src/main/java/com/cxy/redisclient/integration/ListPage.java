package com.cxy.redisclient.integration;


public abstract class ListPage extends JedisCommand {
	protected String index;
	protected String key;
	protected int start;
	protected int end;
	
	public ListPage(int id, String index, String key, int start, int end) {
		super(id);
		this.index = index;
		this.key = key;
		this.start = start;
		this.end = end;
	}
}
