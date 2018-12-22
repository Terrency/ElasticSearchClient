package com.cxy.redisclient.domain;


public class Server {
	public Server(int id, String clusterName, String name, String addr, String port, String password) {
		super();
		this.id = id;
		this.clusterName = clusterName;
		this.name = name;
		this.host = addr;
		this.port = port;
		this.password = password;
	}
	
	private int id;
    private String clusterName;
    private String name;
    private String host;
    private String port;
    private String password;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }
}
