package com.kongtrolink.interweb.job.common;
/**
 * MQ 消息传递模型 通过 转成 json 进行传输
 * @author John
 *
 */
public class JobMessageEntity {
	
	private String id;
	private String jobName;//任务名称-（类名）
	private String jobDescript;//任务描述
	private String type;//修改类型。01=启动;10=停止;30=时间表达式的修改//00=新增;11=删除 状态的修改 暂不需要
	private String cron;//定时任务 时间粒度
	private String mes;//消息信息
	private boolean success;//操作是否成功

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public String getJobDescript() {
		return jobDescript;
	}
	public void setJobDescript(String jobDescript) {
		this.jobDescript = jobDescript;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
}
