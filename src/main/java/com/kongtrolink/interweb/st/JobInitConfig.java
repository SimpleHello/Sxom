package com.kongtrolink.interweb.st;

import java.util.ArrayList;
import java.util.List;

import com.kongtrolink.interweb.job.common.JobEntity;

/**
 * 启动  任务配置
 * @author John
 *
 */
public class JobInitConfig {
	
	private static List<JobEntity> list = new ArrayList<JobEntity>();
	/**
	 * JobEntity 参数说明
	 * 1：TestQuartz3 							 ---任务名称-设定为 类名
	 * 2：测试3     								 ---任务描述
	 * 3：com.kongtrolink.scloud.job.TestQuartz3  ---任务所在类的完整路径 (统一放在com.kongtrolink.scloud.job包下 默认 可以不传)
	 * 4：0/4 * * * * ? 						 	 --- 时间粒度 （com.kongtrolink.scloud.mq.util.JonCron）加入了几个常用 时间粒度 可参考
	 */
	public static List<JobEntity> getList(){
		if(list!=null&&list.size()>0){
			return list;
		}
		list.add(new JobEntity("ConeHistoryTask","历史数据更新","0 * * * * ?"));//历史数据更新 -- 1分钟执行1次
		list.add(new JobEntity("ConePropertyTask","数据属性修改轮询","0/30 * * * * ?"));// 数据属性修改轮询 --30秒执行1次
        return list;
	}
}
