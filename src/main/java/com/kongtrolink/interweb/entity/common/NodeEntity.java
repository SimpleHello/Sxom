package com.kongtrolink.interweb.entity.common;

import java.io.Serializable;

import com.kongtrolink.cone.message.body.device.TNodes;

public class NodeEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	private int nodeId;
	private int parentId;
	
	public NodeEntity(){
		
	}
	public NodeEntity(TNodes tNodes,int id){
		this.nodeId = (int)tNodes.getNodeId();
		this.parentId = (int)tNodes.getParentId();
		this.id= id;
	}
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
