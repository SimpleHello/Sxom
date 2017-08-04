package com.kongtrolink.interweb.entity.cone;

import java.io.Serializable;
import java.util.Date;

import com.kongtrolink.cone.message.body.device.TNodes;

/**
 * 节点信息
 * 存入数据库数据
 * @author John
 *
 */

public class ConeTNodes implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7863347741490314868L;
	public  static final String NAMESPACE="_c1_nodes";
	
	private String fsuId;
	private long nodeId; // [Primary key]
	private long parentId;
	private Date ctime; //数据创建日期
	
	public ConeTNodes(){
		
	}
	
	public ConeTNodes(TNodes node,String fsuId){
		this.nodeId = node.getNodeId();
		this.parentId = node.getParentId();
		this.fsuId = fsuId;
		this.ctime = new Date();
	}
	public String getFsuId() {
		return fsuId;
	}
	public void setFsuId(String fsuId) {
		this.fsuId = fsuId;
	}
	public long getNodeId() {
		return nodeId;
	}
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	
	
}
