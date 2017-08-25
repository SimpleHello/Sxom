package com.kongtrolink.interweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kongtrolink.cone.message.body.ReqSetPropertyAck;
import com.kongtrolink.cone.message.body.SetPointAck;
import com.kongtrolink.cone.message.body.login.LoginAck;
import com.kongtrolink.cone.message.body.login.LogoutAck;
import com.kongtrolink.interweb.entity.common.QueryEntity;
import com.kongtrolink.interweb.job.common.JobEntity;
import com.kongtrolink.interweb.job.common.QuartzManager;
import com.kongtrolink.interweb.mqtt.ConeMqttService;
import com.kongtrolink.scloud.core.util.JsonResult;

@Controller
@RequestMapping("/inter/outer")
public class TestDemo2 {

	private static final String fsuId = "59408e3615bf135433292485";

	private static final String uniqueCode = "TDYS";

	@Autowired
	ConeMqttService coneMqttService;

	@Autowired
	QuartzManager quartzManager;

	@RequestMapping("/login.do")
	public @ResponseBody JsonResult login() throws Exception {
		try {
			LoginAck loginAck = coneMqttService.LoginOnly(fsuId, uniqueCode);
			return new JsonResult(loginAck);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(e.getMessage(), false);
		}
	}

	@RequestMapping("/loginOut.do")
	public @ResponseBody JsonResult loginOut() throws Exception {
		try {
			LogoutAck longout = coneMqttService.Logout(fsuId, uniqueCode);
			return new JsonResult(longout.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(e.getMessage(), false);
		}
	}

	@RequestMapping("/synchronousProperty.do")
	public @ResponseBody JsonResult synchronousProperty() throws Exception {

		try {
			boolean flag = coneMqttService.synchronousProperty(fsuId, uniqueCode);
			if (flag) {
				return new JsonResult("同步数据成功-详情查看数据库");
			} else {
				return new JsonResult("同步失败", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(e.getMessage(), false);
		}
	}

	@RequestMapping("/getAccessDate.do")
	public @ResponseBody JsonResult getAccessDate(QueryEntity entity) throws Exception {

		try {
			String deviceId = entity.getId();
			boolean flag = coneMqttService.getAccessDate(deviceId, uniqueCode);
			if (flag) {
				return new JsonResult("同步数据成功-详情查看数据库");
			} else {
				return new JsonResult("同步失败", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(e.getMessage(), false);
		}
	}

	@RequestMapping("/setPoint.do")
	public @ResponseBody JsonResult setPoint(QueryEntity entity) throws Exception {
		String signalId = entity.getId();
		try {
			double value = Double.valueOf(entity.getValue());
			SetPointAck result = coneMqttService.setPoint(uniqueCode, signalId, value, fsuId);
			return new JsonResult(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(e.getMessage(), false);
		}
	}

	@RequestMapping("/setProperty.do")
	public @ResponseBody JsonResult setProperty(QueryEntity entity) throws Exception {
		String signalId = entity.getId();
		try {
			double value = Double.valueOf(entity.getValue());
			ReqSetPropertyAck result = coneMqttService.setProperty(uniqueCode, signalId, value, fsuId);
			return new JsonResult(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(e.getMessage(), false);
		}
	}

	/**
	 * FSU 登录 并 同步数据的属性值
	 * 
	 * @param id
	 */
	@RequestMapping("/getNodeAndPro.do")
	public @ResponseBody JsonResult getData() throws Exception {
		try {
			coneMqttService.Login(fsuId, uniqueCode);
			return new JsonResult("同步成功 - 请查看 响应数据 ");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(e.getMessage(), false);
		}
	}

	@RequestMapping("/startHistory.do")
	public @ResponseBody JsonResult startHistory() throws Exception {
		try {
			quartzManager.addJob(new JobEntity("ConeHistoryTask", "历史数据更新", "0/20 * * * * ?"));
			return new JsonResult("开始任务.... ");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(e.getMessage(), false);
		}
	}

	@RequestMapping("/stopHistory.do")
	public @ResponseBody JsonResult stopHistory() throws Exception {
		try {
			quartzManager.removeJob(new JobEntity("ConeHistoryTask", "历史数据更新", "0/20 * * * * ?"));
			return new JsonResult("停止成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(e.getMessage(), false);
		}
	}

	@RequestMapping("/startProperty.do")
	public @ResponseBody JsonResult startProperty() throws Exception {
		try {
			quartzManager.addJob(new JobEntity("ConePropertyTask", "数据属性修改轮询", "0/20 * * * * ?"));
			return new JsonResult("开始任务.... ");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(e.getMessage(), false);
		}
	}

	@RequestMapping("/stopProperty.do")
	public @ResponseBody JsonResult stopProperty() throws Exception {
		try {
			quartzManager.removeJob(new JobEntity("ConePropertyTask", "数据属性修改轮询", "0/20 * * * * ?"));
			return new JsonResult("停止成功 ");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(e.getMessage(), false);
		}
	}

}
