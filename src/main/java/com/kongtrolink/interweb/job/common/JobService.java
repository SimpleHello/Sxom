package com.kongtrolink.interweb.job.common;

import java.util.List;

/**
 * 定时任务的持久化保存
 * 对暂停或
 * @author John
 *
 */
public interface JobService {
	/**
	 * 新增 一个 定时任务
	 * @param entity
	 * @param nameSpace
	 */
	void addJob(JobEntity entity);
	
	/**
	 * 修改 一个定时任务
	 * 启动 /停止 / 修改 状态
	 */
	void update(JobEntity entity);
	/**
	 * 修改所有 定时任务 为 开始/停止状态
	 */
	void updateAllStartOrStop(String status);
	
	/**
	 * 删除一个 定时任务
	 */
	void deleteJob(JobEntity entity);
	/**
	 * 判断 该 定时任务 是否在 列表里面
	 * @param entity
	 * @param nameSpace
	 */
	JobEntity find(JobEntity entity);
	
	/**
	 * 查询出所有任务
	 */
	List<JobEntity> findAll();
}
