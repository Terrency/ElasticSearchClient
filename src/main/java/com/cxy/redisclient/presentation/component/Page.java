package com.cxy.redisclient.presentation.component;

import com.cxy.redisclient.service.NodeService;

public abstract class Page implements IPage {
	protected int id;
	protected String index;
	protected String key;
		
	public Page(int id, String index, String key){
		this.id = id;
		this.index = index;
		this.key = key;
	}
	
	public long getCount() {
		NodeService service = new NodeService();
		return service.getSize(id, index, key);
	}
}
