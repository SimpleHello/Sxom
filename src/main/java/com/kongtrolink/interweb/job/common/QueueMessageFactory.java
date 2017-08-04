package com.kongtrolink.interweb.job.common;

import java.text.SimpleDateFormat;

import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public class QueueMessageFactory{

	@Autowired
	QuartzManager quartzManager;
    
	@Autowired
	JobService jobService;
    /**
     * 定时任务 监听消息队列== 当收到消息后，自动调用该方法
     */

    public void updateConeJob(JobMessageEntity mesEntity) {
        try {
            String jobName = mesEntity.getJobName();
        	String type = mesEntity.getType();
        	String cron = mesEntity.getCron();
            System.out.println("--####-- 接收到："
            						+" id：" + mesEntity.getId()+" jobName：" + jobName
            						+" type：" + type +"  Cron：" + cron);
            JobEntity queryEntity = new JobEntity(jobName,mesEntity.getJobDescript(),mesEntity.getCron());
            JobEntity dbEntity = jobService.find(queryEntity);//根据job名称 从数据库中查找数据
            if(type==null){
            	type = "";
            }
            /**
             * type(00=新增 ;01=启动 10=停止 11=删除 状态的修改。 30 = 时间表达式的修改)
             */
            switch(type){
            	case "00":addJob(queryEntity,dbEntity,mesEntity);break;//新增 JOB 。。暂时不需要此功能
            	case "01":startJob(dbEntity,mesEntity);break;//启动 JOB
            	case "30":updateJob(dbEntity,mesEntity);break;//修改 job 的时间粒度
            	case "10":stopJob(dbEntity,mesEntity);break;//停止 job 
            	case "11":delJob(dbEntity,mesEntity);break;//停止并删除 job 。。暂时不需要此功能

            }
            //do something ...
        } catch (Exception e) {
            e.printStackTrace();
            String mes = "处理发生异常:"+e.getMessage();
        }
    }
    /**
     * 00 新增 JOB
     */
    private void addJob(JobEntity queryEntity,JobEntity dbEntity,JobMessageEntity entity){
    	String mes = "新增 启动Job 成功";
    	boolean success = true;
    	String jobName = entity.getJobName();
    	String cron = entity.getCron();
    	boolean cronOk = CronExpression.isValidExpression(cron);//验证时间格式 是否完成
    	JobEntity detail = new JobEntity(jobName,entity.getJobDescript(),entity.getCron());
    	if(dbEntity!=null||!cronOk){
    		mes = "该任务已存在无需创建"+"或 时间粒度不符合规范:"+cronOk;
    		success = false;
    	}else{
    		boolean ifJobExist  =quartzManager.ifJobExist(queryEntity);//判断当前是否在运行
    		if(ifJobExist){
    			mes = "该任务正在运行中 但数据库未创建=新增数据 ";
    		}else{
    			quartzManager.addJob(detail);	
    		}
        	jobService.addJob(detail);	
    	}
    }
    /**
     * 01 启动 job
     */
    private void startJob(JobEntity dbEntity,JobMessageEntity entity){
    	String mes = "启动成功";
    	boolean success = true;
    	if(dbEntity==null){
    		mes = "该任务 不存在 请先进行创建 ";
    		success = false;
    	}else{
    		boolean ifJobExist  =quartzManager.ifJobExist(dbEntity);//判断当前是否在运行
    		if("启动".equals(dbEntity.getStatus())&&ifJobExist){
    			mes = "该任务正在运行中 ";
    			success = false;
    		}else{
    			dbEntity.setStatus("启动");
        		quartzManager.addJob(dbEntity);
            	jobService.update(dbEntity);
    		}
    	}
    }
    /**
     * 30 修改 JOB 时间粒度
     */
    private void updateJob(JobEntity dbEntity,JobMessageEntity entity){
    	boolean cronOk = CronExpression.isValidExpression(entity.getCron());//验证时间格式 是否完成
        String mes="修改时间粒度成功";
        boolean success = true;
    	if(dbEntity==null||!cronOk){
    		mes = "该任务 不存在 请先进行创建"+" 或  时间粒度不符合规范:"+entity.getCron();
    		success = false;
    	}else{
    		dbEntity.setCron(entity.getCron());
    		quartzManager.modifyJobTime(dbEntity);
        	jobService.update(dbEntity);
    	}
    }
    /**
     * 01 停止  JOB
     */
    private void stopJob(JobEntity dbEntity,JobMessageEntity entity){
    	String mes = "";
    	boolean success = true;
    	if(dbEntity==null){
    		mes ="该任务 不存在 请先进行创建 ";
    		success = false;
    	}else{
    		boolean ifJobExist  =quartzManager.ifJobExist(dbEntity);//判断当前是否在运行
    		if(!ifJobExist){
    			mes = mes+" 该任务 不在运行中 ";
    			success = false;
    		}else{
    			dbEntity.setStatus("停止");
        		quartzManager.removeJob(dbEntity);
            	jobService.update(dbEntity);
            	mes = "停止 job 成功";
    		}
    	}
    }
    /**
     * 11 停止并 删除  JOB
     */
    private void delJob(JobEntity dbEntity,JobMessageEntity entity){
    	String mes = "";
    	boolean success = true;
    	if(dbEntity==null){
    		mes="该任务 不存在 无需删除操作";
    		success = false;
    	}else{
    		boolean ifJobExist  =quartzManager.ifJobExist(dbEntity);//判断当前是否在运行
    		if(!ifJobExist){
    			mes = mes+"该任务 不在运行中, 只删除数据库数据";
    			success = false;
        		jobService.deleteJob(dbEntity);
    		}else{
    			quartzManager.removeJob(dbEntity);
            	jobService.deleteJob(dbEntity);
            	mes = "删除 job 成功";
    		}
    		
    	}
    }
    
}