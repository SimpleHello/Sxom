package com.kongtrolink.interweb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kongtrolink.cone.message.body.AlarmModeAck;
import com.kongtrolink.cone.message.body.AlarmModeCmd;
import com.kongtrolink.cone.message.body.SetDynAccessMode;
import com.kongtrolink.cone.message.body.SetDynAccessModeAck;
import com.kongtrolink.cone.message.body.SetNodes;
import com.kongtrolink.cone.message.body.SetPoint;
import com.kongtrolink.cone.message.body.SetPointAck;
import com.kongtrolink.cone.message.body.SetProperty;
import com.kongtrolink.cone.message.body.comm.Body;
import com.kongtrolink.cone.message.body.device.TA;
import com.kongtrolink.cone.message.body.device.TAIC;
import com.kongtrolink.cone.message.body.device.TAOC;
import com.kongtrolink.cone.message.body.device.TD;
import com.kongtrolink.cone.message.body.device.TDIC;
import com.kongtrolink.cone.message.body.device.TDOC;
import com.kongtrolink.cone.message.body.device.TDevice;
import com.kongtrolink.cone.message.body.device.TNodes;
import com.kongtrolink.cone.message.body.device.TS;
import com.kongtrolink.cone.message.body.device.TSC;
import com.kongtrolink.cone.message.body.device.TStation;
import com.kongtrolink.cone.message.body.login.LoginAck;
import com.kongtrolink.cone.message.enumeration.EnumState;
import com.kongtrolink.cone.message.enumeration.EnumType;
import com.kongtrolink.cone.service.InterfaceFactory;
import com.kongtrolink.cone.test.GetTestData;
import com.kongtrolink.cone.util.CInterfaceConfig;
import com.kongtrolink.interweb.all.ConeStaticMessageMap;
import com.kongtrolink.interweb.entity.common.ConnectEntity;
import com.kongtrolink.interweb.entity.common.LoginEntity;
import com.kongtrolink.interweb.entity.common.NodeEntity;
import com.kongtrolink.interweb.entity.common.NodeIdEntity;
import com.kongtrolink.interweb.entity.common.PointEntityh;
import com.kongtrolink.interweb.entity.common.PropertyEntity;
import com.kongtrolink.interweb.entity.cone.device.ConeTAIC;
import com.kongtrolink.interweb.entity.cone.device.ConeTAOC;
import com.kongtrolink.interweb.entity.cone.device.ConeTDIC;
import com.kongtrolink.interweb.entity.cone.device.ConeTDOC;
import com.kongtrolink.interweb.entity.cone.device.ConeTDevice;
import com.kongtrolink.interweb.entity.cone.device.ConeTSC;
import com.kongtrolink.interweb.entity.cone.device.ConeTStation;
import com.kongtrolink.interweb.mqtt.ConeMqttService;
import com.kongtrolink.scloud.core.util.JsonResult;

@Controller
@RequestMapping("/inter/test")
public class TestDemo {

	@Autowired
	ConeMqttService coneMqttService;
	
	@RequestMapping("/connect.do")
	public @ResponseBody JsonResult  connect(ConnectEntity entity) throws Exception {
		String host = entity.getHost();
		int port = entity.getPort();
		String key = host + "#" + port;
		if (ConeStaticMessageMap.configMap.containsKey(key)) {
			CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
			config.disableServer();
			ConeStaticMessageMap.configMap.remove(key);
		}
		try{
			CInterfaceConfig config = new CInterfaceConfig();
			config.setFsuId("59408e3615bf135433292485"); //此设定 要在 initServer之前
			config.setUniqueCode("TDYS"); //此设定 要在 initServer之前
			config.initServer(host, port, 10000);// IP与端口从FSU里面获取以及设置接口响应时间默认10秒
			config.enbaleServer();// 启动 TCP连接
			ConeStaticMessageMap.configMap.put(key, config);
			return new JsonResult(key);
		}catch(Exception e){
			  return new JsonResult(e.getMessage(), false);
		}
	}
	
	@RequestMapping("/disconnect.do")
	public @ResponseBody JsonResult  disconnect(ConnectEntity entity) throws Exception {
		String host = entity.getHost();
		int port = entity.getPort();
		String key = host + "#" + port;
		if (ConeStaticMessageMap.configMap.containsKey(key)) {
			CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
			config.disableServer();
			ConeStaticMessageMap.configMap.remove(key);
			return new JsonResult("断开成功");
		}
		return new JsonResult("未连接");
	}
	
	@RequestMapping("/login.do")
	public @ResponseBody JsonResult  login(LoginEntity entity) throws Exception {
		String name = entity.getUsername();
		String password = entity.getPassword();
		String key =entity.getKey();
		if (!ConeStaticMessageMap.configMap.containsKey(key)) {
			return new JsonResult("该设备未连接", false);
		}
		try{
			CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
			InterfaceFactory factory = config.getInterfaceFactory();
			LoginAck loginAck = factory.Login(name, password);
			return new JsonResult(loginAck);
		}catch(Exception e){
			  return new JsonResult(e.getMessage(), false);
		}
	}
	
	@RequestMapping("/getNode.do")
	public @ResponseBody JsonResult  getNode(LoginEntity entity) throws Exception {
		String key =entity.getKey();
		System.out.println(key);
		if (!ConeStaticMessageMap.configMap.containsKey(key)) {
			return new JsonResult("该设备未连接", false);
		}
		try{
			CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
			InterfaceFactory factory = config.getInterfaceFactory();
			SetNodes nodes = factory.getNodes(0);
			int count = nodes.getCount();
			if(count==-1){
				return new JsonResult("请求系统结构信息节点数过多,报文过长", false);
			}else if(count==-2){
				return new JsonResult("请求系统结构信息无相应的ID号", false);
			}else{
				List<NodeEntity> list = new ArrayList<NodeEntity>();
				int i =1;
				for (TNodes tNodes:nodes.getValues()) {
					list.add(new NodeEntity(tNodes,i));
					i++;
				}
				return new JsonResult(list);
			}
		}catch(Exception e){
			  return new JsonResult(e.getMessage(), false);
		}
	}
	
	@RequestMapping("/getProperty.do")
	public @ResponseBody JsonResult  getProperty(NodeIdEntity entity) throws Exception {
		String key =entity.getKey();
		System.out.println(key);
		if (!ConeStaticMessageMap.configMap.containsKey(key)) {
			return new JsonResult("该设备未连接", false);
		}
		try{
			int nodeId = entity.getNodeId();
			CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
			InterfaceFactory factory = config.getInterfaceFactory();
			int[] ids = {nodeId};
			SetProperty property  = factory.getProperty(ids);
			int count = property.getCount();
			if(count==-1){
				return new JsonResult("请求系统结构信息节点数过多,报文过长", false);
			}else if(count==-2){
				return new JsonResult("请求系统结构信息无相应的ID号", false);
			}else{
				List<PropertyEntity> map = saveProperty(property.getValues());
				return new JsonResult(map);
			}
		}catch(Exception e){
			  return new JsonResult(e.getMessage(), false);
		}
	}
	
	@RequestMapping("/accessMode.do")
	public @ResponseBody JsonResult  accessMode(NodeIdEntity entity) throws Exception {
		String key =entity.getKey();
		System.out.println(key);
		if (!ConeStaticMessageMap.configMap.containsKey(key)) {
			return new JsonResult("该设备未连接", false);
		}
		try{
			int nodeId = entity.getNodeId();
			CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
			InterfaceFactory factory = config.getInterfaceFactory();
			SetDynAccessMode sx = GetTestData.getSetDynAccessMode(nodeId);
			SetDynAccessModeAck property= factory.setDynAccessMode(sx);
			int count = property.getCount();
			if(count==-1){
				return new JsonResult("请求系统结构信息节点数过多,报文过长", false);
			}else if(count==-2){
				return new JsonResult("请求系统结构信息无相应的ID号", false);
			}else{
				List<PropertyEntity> map = saveAccess(property.getValues());
				return new JsonResult(map);
			}
		}catch(Exception e){
			  return new JsonResult(e.getMessage(), false);
		}
	}
	/**
	 * FSU 登录 并 同步数据的属性值 
	 * @param id
	 */
	@RequestMapping("/getNodeAndPro.do")
	public @ResponseBody JsonResult  getData() throws Exception {
		String fsuId = "59408e3615bf135433292485";
		String uniqueCode = "TDYS";
		try{
			coneMqttService.Login(fsuId, uniqueCode);
			return new JsonResult("同步成功 - 请查看 响应数据 ");
		}catch(Exception e){
			  return new JsonResult(e.getMessage(), false);
		}
	}
	
	@RequestMapping("/setPoint.do")
	public @ResponseBody JsonResult  setPoint(PointEntityh entity) throws Exception {
		String key =entity.getKey();
		int id = entity.getId();
		System.out.println(key);
		if (!ConeStaticMessageMap.configMap.containsKey(key)) {
			return new JsonResult("该设备未连接", false);
		}
		try{
			float value =Float.valueOf(entity.getValue());
			CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
			InterfaceFactory factory = config.getInterfaceFactory();
			SetPoint setPoint = new SetPoint();
			setPoint.setData(new TA(EnumType.AO,id,value,EnumState.MAIN));
			SetPointAck result09 = factory.SetPoint(setPoint);
			return new JsonResult(result09.toString());
		}catch(Exception e){
			  return new JsonResult(e.getMessage(), false);
		}
	}
	
	@RequestMapping("/alarmMode.do")
	public @ResponseBody JsonResult  alarmMode(NodeIdEntity entity) throws Exception {
		String key =entity.getKey();
		System.out.println(key);
		if (!ConeStaticMessageMap.configMap.containsKey(key)) {
			return new JsonResult("该设备未连接", false);
		}
		try{
	
			CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
			InterfaceFactory factory = config.getInterfaceFactory();
			AlarmModeCmd alarmModeCmd = GetTestData.getAlarmModeCmd();
			AlarmModeAck result08 = factory.setAlarmMode(alarmModeCmd);
			return new JsonResult(result08.toString());
		}catch(Exception e){
			  return new JsonResult(e.getMessage(), false);
		}
	}
	
	/**
	 * 
	 */
	/**
	 * 保存信号点 到数据库中
	 */
	public List<PropertyEntity> saveProperty(Body[] bodys) {
		// TODO Auto-generated method stub
		List<PropertyEntity> list1 = new ArrayList<PropertyEntity>();

		try{
			if (bodys != null && bodys.length > 0) {
				int i = 1;
				String str = "";
				for (Body data : bodys) {
					if (data instanceof TAIC) {
						ConeTAIC entity = savePropertyTAIC((TAIC)data);
						str = entity.toString();
					} else if (data instanceof TAOC) {
						ConeTAOC entity = savePropertyTAOC((TAOC)data);
						str = entity.toString();
					} else if (data instanceof TDevice) {
						ConeTDevice entity = savePropertyTDevice((TDevice)data);
						str = entity.toString();
					} else if (data instanceof TDIC) {
						ConeTDIC entity = savePropertyTDIC((TDIC)data);
						str = entity.toString();
					} else if (data instanceof TDOC) {
						ConeTDOC entity = savePropertyTDOC((TDOC)data);
						str = entity.toString();
					} else if (data instanceof TSC) {
						ConeTSC entity = new ConeTSC((TSC) data,null);
						str = entity.toString();
					} else if (data instanceof TStation) {
						ConeTStation entity = savePropertyTStation((TStation)data);
						str = entity.toString();
					}
					PropertyEntity entityPro = new PropertyEntity(i,str);
					list1.add(entityPro);
					i++;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return list1;
	}
	
	/**
	 * 保存信号点 到数据库中
	 */
	public List<PropertyEntity> saveAccess(Body[] bodys) {
		// TODO Auto-generated method stub
		List<PropertyEntity> list1 = new ArrayList<PropertyEntity>();
		try{
			if (bodys != null && bodys.length > 0) {
				int i = 1;
				String str = "";
				for (Body data : bodys) {
					if (data instanceof TA) {
						TA entity = (TA)data;
						str = entity.toString();
					} else if (data instanceof TD) {
						TD entity = (TD)data;
						str = entity.toString();
					} else if (data instanceof TS) {
						TS entity = (TS)data;
						str = entity.toString();
					}
					PropertyEntity entityPro = new PropertyEntity(i,str);
					list1.add(entityPro);
					i++;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return list1;
	}
	
	private ConeTStation savePropertyTStation(TStation data){
		ConeTStation entity = new ConeTStation((TStation) data, null);//取得 站点信息 只进行更新操作
		return entity;
	}
	
	private ConeTDevice savePropertyTDevice(TDevice data){
		ConeTDevice entity = new ConeTDevice(data, null);
		return entity;
	}

	private ConeTAIC savePropertyTAIC(TAIC data){
		ConeTAIC entity = new ConeTAIC(data, null );
		return entity; 
	}
	private ConeTAOC savePropertyTAOC(TAOC data){
		ConeTAOC entity = new ConeTAOC((TAOC) data,  null);
		return entity;
	}
	private ConeTDIC savePropertyTDIC(TDIC data){
		ConeTDIC entity = new ConeTDIC(data,  null);
		return entity;
	}
	private ConeTDOC savePropertyTDOC(TDOC data){
		ConeTDOC entity = new ConeTDOC((TDOC) data,  null);
		return entity;
	}
	
}
