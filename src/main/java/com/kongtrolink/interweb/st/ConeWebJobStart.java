package com.kongtrolink.interweb.st;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kongtrolink.cone.message.body.login.LoginAck;
import com.kongtrolink.cone.service.InterfaceFactory;
import com.kongtrolink.cone.util.CInterfaceConfig;
import com.kongtrolink.interweb.all.ConeStaticMessageMap;
import com.kongtrolink.interweb.job.common.JobEntity;
import com.kongtrolink.interweb.job.common.JobService;
import com.kongtrolink.interweb.job.common.QuartzManager;
import com.kongtrolink.interweb.service.InterfaceEnterpriseService;
import com.kongtrolink.interweb.service.InterfaceFsuService;
import com.kongtrolink.scloud.core.entity.cone.ConeFsu;
import com.kongtrolink.scloud.core.entity.enterprise.Enterprise;

/**
 * 接口服务程序 启动后 需要执行的程序
 * 
 * @author John
 *
 */
public class ConeWebJobStart {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConeWebJobStart.class);
	
	@Autowired
	QuartzManager quartzManager;
	@Autowired
	JobService jobService;
	
	@Value("${c1.timeout}")
	private String timeout;

	@Autowired
	private InterfaceEnterpriseService interfaceEnterpriseService;

	@Autowired
	private InterfaceFsuService interfaceFsuService;

	/**
	 * 服务启动 加载 预设 跑批任务
	 * 任务JOb数  由 JobInitConfig 设定
	 * 任务Job详情 若 db存在数据 取db数据。
	 */
	@PostConstruct  
    public void  init(){  
		LOGGER.info("##接口服务 定时任务 启动");
//		
		startConnectFsu();//连接所有 c1_id不为0 FSU
//		
//		List<JobEntity> list = JobInitConfig.getList();
//		List<JobEntity> dblist = jobService.findAll();
//		Map<String,JobEntity> jobMap = new HashMap<String,JobEntity>();
//		if(dblist!=null){
//			for(JobEntity entity:dblist){
//				jobMap.put(entity.getJobName(),entity);
//			}
//		}
//		for(JobEntity entity:list){
//			String jobName = entity.getJobName();
//			if(jobMap.containsKey(jobName)){
//				quartzManager.addJob(jobMap.get(jobName));//定时任务 启动 配置信息调用数据库内容
//			}else{
//				quartzManager.addJob(entity);//定时任务 启动 配置信息调用初始化配置信息
//				jobService.addJob(entity);//数据库保存配置
//			}
//		}
//		jobService.updateAllStartOrStop("启动");//修改任务状态 为 启动状态
		LOGGER.info("##服务开始 定时任务 结束");
    }
	
	@PreDestroy  
    public void  dostory(){ 
		LOGGER.info("服务停止 - 修改状态为 停止");
		jobService.updateAllStartOrStop("停止");
    }
	
	/**
	 * 连接所有 c1_id不为0 FSU
	 */
	private void startConnectFsu() {
		LOGGER.info("接口服务启动-对已经存在C1_ID的FSU进行 用户登录--开始......");
		try {
			// 对所有企业执行 FSU 统计并存储操作
			List<Enterprise> enterpriseList = interfaceEnterpriseService.queryAllCustomers();
			if (enterpriseList == null || enterpriseList.isEmpty()) {
				return;
			}
			for (Enterprise enterprise : enterpriseList) {
				String uniqueCode = enterprise.getUniqueCode();
				List<ConeFsu> fsuList = interfaceFsuService.queryConeFsu(uniqueCode);
				if (fsuList != null && fsuList.size() > 0) {
					for (ConeFsu fsu : fsuList) {
						String ip = fsu.getIp();// FSU IP
						int port = fsu.getPort();// FSU 端口
						String name = fsu.getUsername();// FSU 用户名
						String password = fsu.getPassword();// FSU 密码
						String key = ip + "#" + port;
						if (!ConeStaticMessageMap.configMap.containsKey(key)) { // 对未登录的设备进行连接
							try{
								LOGGER.info(">>> 接口服务启动 FSU:"+ip+"用户登录");
								CInterfaceConfig config = new CInterfaceConfig();
								config.setUniqueCode(uniqueCode);
								config.setFsuId(fsu.getId());
								config.initServer(ip, port, Integer.valueOf(timeout == null ? "3000" : timeout));// IP与端口从FSU里面获取以及设置接口响应时间默认3秒
								config.enbaleServer();// 启动 TCP连接
								ConeStaticMessageMap.configMap.put(key, config);
								InterfaceFactory factory = config.getInterfaceFactory();
								LoginAck loginAck = factory.Login(name, password);//进行用户登录操作
							}catch(Exception e){ //连接不上 FSU 修改 FSU 属性 为离线
								LOGGER.error(">>> 接口服务启动 FSU:"+ip+" 无法连接 服务器");
								fsu.setState("离线");
								interfaceFsuService.updateFsu(uniqueCode, fsu);
							}
							
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("接口服务启动-对已经存在C1_ID的FSU进行 用户登录--结束 !!!!!!");
	}
}
