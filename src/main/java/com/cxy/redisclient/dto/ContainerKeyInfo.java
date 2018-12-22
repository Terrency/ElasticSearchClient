package com.cxy.redisclient.dto;

import com.cxy.redisclient.domain.ContainerKey;

public class ContainerKeyInfo {
	private int id;
	private String serverName;
	private String index;
	private ContainerKey container;
	
	public ContainerKeyInfo() {
		super();
		this.id = -1;
		this.index = "";
		this.container = new ContainerKey("");
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

    public ContainerKey getContainer() {
		return container;
	}
	public String getContainerStr() {
		return container == null?"":container.getContainerKey();
	}
	public String getContainerOnly() {
		return container == null?"":container.getContainerOnly();
	}
	public void setContainer(ContainerKey container) {
		this.container = container;
	}
	public void setContainer(ContainerKey container, String key) {
		String con = container == null?"":container.getContainerKey();
		this.container = new ContainerKey(con + key);
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
}
