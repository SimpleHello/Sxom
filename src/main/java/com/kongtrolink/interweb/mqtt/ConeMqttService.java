package com.kongtrolink.interweb.mqtt;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kongtrolink.cone.entity.AlarmExt;
import com.kongtrolink.cone.message.body.AlarmModeCmd;
import com.kongtrolink.cone.message.body.MonitorDataCone;
import com.kongtrolink.cone.message.body.NotifyPropertyModify;
import com.kongtrolink.cone.message.body.ReqAckAlarm;
import com.kongtrolink.cone.message.body.ReqAckAlarmAck;
import com.kongtrolink.cone.message.body.ReqSetProperty;
import com.kongtrolink.cone.message.body.ReqSetPropertyAck;
import com.kongtrolink.cone.message.body.SetDynAccessMode;
import com.kongtrolink.cone.message.body.SetDynAccessModeAck;
import com.kongtrolink.cone.message.body.SetNodes;
import com.kongtrolink.cone.message.body.SetPoint;
import com.kongtrolink.cone.message.body.SetPointAck;
import com.kongtrolink.cone.message.body.SetProperty;
import com.kongtrolink.cone.message.body.TTime;
import com.kongtrolink.cone.message.body.comm.Body;
import com.kongtrolink.cone.message.body.device.TDevice;
import com.kongtrolink.cone.message.body.login.LoginAck;
import com.kongtrolink.cone.message.enumeration.DataAccessMode;
import com.kongtrolink.cone.message.enumeration.EnumAlarmMode;
import com.kongtrolink.cone.service.InterfaceFactory;
import com.kongtrolink.cone.util.CInterfaceConfig;
import com.kongtrolink.interweb.all.ConeStaticMessageMap;
import com.kongtrolink.interweb.entity.cone.ConeData;
import com.kongtrolink.interweb.entity.cone.device.ConeTDevice;
import com.kongtrolink.interweb.service.InterfaceAlarmService;
import com.kongtrolink.interweb.service.InterfaceDeviceService;
import com.kongtrolink.interweb.service.InterfaceFsuService;
import com.kongtrolink.interweb.service.InterfaceSignalService;
import com.kongtrolink.interweb.service.cone.ConeDataService;
import com.kongtrolink.interweb.service.cone.ConeNodeService;
import com.kongtrolink.interweb.service.cone.ConePropertyService;
import com.kongtrolink.interweb.util.IDGenerator;
import com.kongtrolink.scloud.core.entity.cone.ConeDevice;
import com.kongtrolink.scloud.core.entity.cone.ConeFsu;
import com.kongtrolink.scloud.core.entity.cone.ConeSignal;

/**
 * C1 接口 接收到 MQTT命令之后的一些列操作
 * 
 * @author John
 *
 */

@Service("coneMqttService")
public class ConeMqttService{

	@Value("${c1.timeout}")
	private String timeout;

	@Autowired
	private ConeNodeService coneNodeService;

	@Autowired
	private ConePropertyService conePropertyService;

	@Autowired
	private InterfaceFsuService interfaceFsuService;

	@Autowired
	private InterfaceDeviceService interfaceDeviceService;

	@Autowired
	private ConeDataService coneDataService;

	@Autowired
	private InterfaceSignalService interfaceSignalService;

	@Autowired
	private InterfaceAlarmService interfaceAlarmService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ConeMqttService.class);

	/**
	 * FSU 101 登录 并 取得ID => 取得ID 的属性值
	 * 
	 * @param id
	 */
	public void Login(String fsuId, String uniqueCode) {
		try {
			ConeFsu fsu = interfaceFsuService.queryFsuById(uniqueCode, fsuId);// 数据库中获取FSU信息
			String ip = fsu.getIp();// FSU IP
			int port = fsu.getPort();// FSU 端口
			String name = fsu.getUsername();// FSU 用户名
			String password = fsu.getPassword();// FSU 密码
			String key = ip + "#" + port;
			if (ConeStaticMessageMap.configMap.containsKey(key)) {
				throw new Exception("已登录");
			}
			CInterfaceConfig config = new CInterfaceConfig();
			config.setUniqueCode(uniqueCode);// 传递 uniqueCode
			config.setFsuId(fsuId);// 传递 FSU id
			config.initServer(ip, port, Integer.valueOf(timeout == null ? "10000" : timeout));// IP与端口从FSU里面获取以及设置接口响应时间默认10秒
			config.enbaleServer();// 启动 TCP连接
			ConeStaticMessageMap.configMap.put(key, config);
			InterfaceFactory factory = config.getInterfaceFactory();
			LoginAck loginAck = factory.Login(name, password);
			if (loginAck.getRigthMode().getType() == 0) {
				return;// 表示没有权限
			}
			boolean falg = getFsuProperty(factory, uniqueCode, fsuId);// 登录之后直接获取全部属性值保存到数据中
			if (falg) {
				Thread.sleep(1000);// 1秒之后设置告警
				setFsuAlarmMode(factory);// 设置 告警发送 模式
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * FSU 101 登录 只登录
	 * 
	 * @param id
	 */
	public void LoginOnly(String fsuId, String uniqueCode) {
		try {
			ConeFsu fsu = interfaceFsuService.queryFsuById(uniqueCode, fsuId);// 数据库中获取FSU信息
			String ip = fsu.getIp();// FSU IP
			int port = fsu.getPort();// FSU 端口
			String name = fsu.getUsername();// FSU 用户名
			String password = fsu.getPassword();// FSU 密码
			String key = ip + "#" + port;
			if (ConeStaticMessageMap.configMap.containsKey(key)) {
				throw new Exception("已登录");
			}
			CInterfaceConfig config = new CInterfaceConfig();
			config.setUniqueCode(uniqueCode);// 传递 uniqueCode
			config.setFsuId(fsuId);// 传递 FSU id
			config.initServer(ip, port, Integer.valueOf(timeout == null ? "10000" : timeout));// IP与端口从FSU里面获取以及设置接口响应时间默认10秒
			config.enbaleServer();// 启动 TCP连接
			ConeStaticMessageMap.configMap.put(key, config);
			InterfaceFactory factory = config.getInterfaceFactory();
			LoginAck loginAck = factory.Login(name, password);
			LOGGER.info("收到服务器返回值:" + loginAck.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * FSU 103 登出
	 * 
	 * @param id
	 */
	public void Logout(String id, String uniqueCode) {
		try {
			ConeFsu fsu = interfaceFsuService.queryFsuById(uniqueCode, id);// 数据库中获取FSU信息
			String ip = fsu.getIp();// FSU IP
			int port = fsu.getPort();// FSU 端口
			String key = ip + "#" + port;
			if (!ConeStaticMessageMap.configMap.containsKey(key)) {
				throw new Exception("该用户未登录");
			}
			CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
			config.disableServer();
			ConeStaticMessageMap.configMap.remove(key);// 进行用户登出
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * FSU 只 同步数据的属性值
	 * 
	 * @param id
	 */
	public void synchronousProperty(String fsuId, String uniqueCode) {
		try {
			ConeFsu fsu = interfaceFsuService.queryFsuById(uniqueCode, fsuId);// 数据库中获取FSU信息
			String ip = fsu.getIp();// FSU IP
			int port = fsu.getPort();// FSU 端口
			String name = fsu.getUsername();// FSU 用户名
			String password = fsu.getPassword();// FSU 密码
			String key = ip + "#" + port;
			if (ConeStaticMessageMap.configMap.containsKey(key)) {
				CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
				InterfaceFactory factory = config.getInterfaceFactory();
				LoginAck loginAck = factory.Login(name, password);
				if (loginAck.getRigthMode().getType() == 0) {
					return;// 表示没有权限
				}
				boolean falg = getFsuProperty(factory, uniqueCode, fsuId);// 登录之后直接获取全部属性值保存到数据中
				if (falg) {
					Thread.sleep(1000);// 1秒之后设置告警
					setFsuAlarmMode(factory);// 设置 告警发送 模式
				}
			} else {
				throw new Exception("该用户未登录");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 401 请求实时数据方式设置 获取当前 实时数据 并保存
	 */
	public void getAccessDate(String deviceId, String uniqueCode) {
		ConeDevice deviceInfo = interfaceDeviceService.queryDeviceById(uniqueCode, deviceId);// 获取数据库中的数据
		String fsuId = deviceInfo.getFsuId();
		ConeFsu fsu = interfaceFsuService.queryFsuById(uniqueCode, fsuId);// 数据库中获取FSU信息
		String ip = fsu.getIp();// FSU IP
		int port = fsu.getPort();// FSU 端口
		String key = ip + "#" + port;
		if (!ConeStaticMessageMap.configMap.containsKey(key)) {
			try {
				throw new Exception("该用户未登录");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
		setFsuAccessMode(config.getInterfaceFactory(), uniqueCode, fsuId, deviceInfo.getC1_id());
	}

	/**
	 * 1001 写数据请求 修改实时数据-signal 中的value值 需要与 具体功能 结合起来
	 */
	public void setPoint(String uniqueCode, String signalId, Double value, String fsuId) {
		ConeSignal signal = interfaceSignalService.querySignalById(uniqueCode, signalId);
		signal.setC1_id(signal.getC1_id());
		signal.setValue(value);
		interfaceSignalService.updateValue(uniqueCode, signal);// 修改数据库中的数据
		ConeFsu fsu = interfaceFsuService.queryFsuById(uniqueCode, fsuId);// 数据库中获取FSU信息
		String ip = fsu.getIp();// FSU IP
		int port = fsu.getPort();// FSU 端口
		String key = ip + "#" + port;
		if (!ConeStaticMessageMap.configMap.containsKey(key)) {
			try {
				throw new Exception("该用户未登录");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
		SetPoint setPoint = new SetPoint();
		MonitorDataCone data = interfaceSignalService.getMonitorDataCone(uniqueCode, signal);
		setPoint.setData(data);
		try {
			SetPointAck result09 = config.getInterfaceFactory().SetPoint(setPoint);
			LOGGER.info("收到服务器返回值:" + result09.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 1401 写数据属性请求 当前就是修改阀值 需要与 具体功能 结合起来
	 * 
	 * @param uniqueCode
	 * @param signalId
	 * @param limit
	 *            修改后的阀值
	 * @param fsuId
	 */
	public void getProperty(String uniqueCode, String signalId, double threshold, String fsuId) {
		ConeSignal signal = interfaceSignalService.querySignalById(uniqueCode, signalId);
		signal.setThreshold(threshold);
		interfaceSignalService.updateSignalById(uniqueCode, signal);// 修改数据库中的数据
		ConeFsu fsu = interfaceFsuService.queryFsuById(uniqueCode, fsuId);// 数据库中获取FSU信息
		String ip = fsu.getIp();// FSU IP
		int port = fsu.getPort();// FSU 端口
		String key = ip + "#" + port;
		if (!ConeStaticMessageMap.configMap.containsKey(key)) {
			try {
				throw new Exception("该用户未登录");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
		Body body = conePropertyService.getPropertyBySignal(uniqueCode, signal);
		try {
			ReqSetProperty reqSetProperty = new ReqSetProperty(body);
			ReqSetPropertyAck result12 = config.getInterfaceFactory().reqSetProperty(reqSetProperty);
			LOGGER.info("收到服务器返回值:" + result12.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 告警确认的请求 1601
	 * 
	 * @param uniqueCode
	 * @param alarmId
	 * @param userName
	 */
	public void verifyAlarm(String uniqueCode, String fsuId, String alarmId, String userName) {
		try {
			AlarmExt alarm = interfaceAlarmService.getAlarmById(uniqueCode, alarmId);// 根据ID获得该告警信息
			ConeFsu fsu = interfaceFsuService.queryFsuById(uniqueCode, fsuId);// 数据库中获取FSU信息
			String ip = fsu.getIp();// FSU IP
			int port = fsu.getPort();// FSU 端口
			String key = ip + "#" + port;
			if (!ConeStaticMessageMap.configMap.containsKey(key)) {
				throw new Exception("该用户未登录");
			}
			CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);

			ReqAckAlarm reqAckAlarm = new ReqAckAlarm(userName, alarm.getC1_id(), new TTime(alarm.gettReport()),
					new TTime(new Date()));
			ReqAckAlarmAck result14 = config.getInterfaceFactory().reqAckAlarm(reqAckAlarm);
			LOGGER.info("收到服务器返回值:" + result14.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 模拟发生 数据变更
	 * 
	 * @param uniqueCode
	 * @param alarmId
	 * @param userName
	 */
	public void testChangeProperty(String uniqueCode, String fsuId) {
		ConeFsu fsu = interfaceFsuService.queryFsuById(uniqueCode, fsuId);// 数据库中获取FSU信息
		String ip = fsu.getIp();// FSU IP
		int port = fsu.getPort();// FSU 端口
		String key = ip + "#" + port;
		if (!ConeStaticMessageMap.configMap.containsKey(key)) {
			try {
				throw new Exception("该用户未登录");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		CInterfaceConfig config = ConeStaticMessageMap.configMap.get(key);
		try {
			NotifyPropertyModify data = new NotifyPropertyModify(134350848);// 将模拟设备1发生了变更
			NotifyPropertyModify result13 = config.getInterfaceFactory().notifyPropertyModify(data);
			LOGGER.info("收到服务器返回值:" + result13.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 取得 FSU 信息 (第一步)201 用户请求系统结构 - 请求节点以下的整个树的ID号 (第二步)循环设备 获取 301 用户请求数据属性 -
	 * 请求数据属性 GET_PROPERTY
	 */
	private boolean getFsuProperty(InterfaceFactory factory, String uniqueCode, String fsuId) {
		try {
			SetNodes nodes = factory.getNodes(0);
			coneNodeService.saveNodes(nodes.getValues(), uniqueCode, fsuId); // 数据库保存相关node信息
			int c1_fsuId = IDGenerator.getFsuId(nodes.getValues());
			if (c1_fsuId == 0) {
				throw new Exception("get Node is Exception !");
			}
			int[] fsuInfoId = { c1_fsuId };
			SetProperty fsuInfo = factory.getProperty(fsuInfoId);// 获取设备本身的信息
			boolean saveFsuFlag = conePropertyService.saveProperty(fsuInfo.getValues(), uniqueCode, fsuId, null, 0);// 将C1获取到的数据完整保存到数据中
			List<Integer> deviceIdList = IDGenerator.getDeviceNodesList(nodes.getValues());// 解析出所有的设备ID多个设备节点AA.BBB.1.0,AA.BBB.2.0.....
			if (deviceIdList != null && deviceIdList.size() > 0 && saveFsuFlag) {
				LOGGER.info("保存 FSU 成功:" + c1_fsuId);
				for (Integer deviceId : deviceIdList) {
					int[] deviceInfoId = { deviceId };
					String deviceSaveId = IDGenerator.getUUID();// 生成数据库ID
					SetProperty deviceInfo = factory.getProperty(deviceInfoId);// 获取设备本身的信息
					if (deviceInfo != null && deviceInfo.getValues() != null) {
						Body body = deviceInfo.getValues()[0];
						if (body instanceof TDevice) {
							ConeTDevice entity = new ConeTDevice((TDevice) body, fsuId);
							int deviceType = entity.getDeviceType().getType();
							boolean savedeviceFlag = conePropertyService.saveProperty(deviceInfo.getValues(),
									uniqueCode, fsuId, deviceSaveId, deviceType);// 将C1获取到的数据完整保存到数据中
							if (savedeviceFlag) {
								LOGGER.info("保存 TDevice 成功:" + deviceInfoId);
								int[] deviceSingalId = { deviceId | 0x000007FF };// 后面11位全为1
								SetProperty deviceSingal = factory.getProperty(deviceSingalId);// 获取设备下信号点的信息
								conePropertyService.saveProperty(deviceSingal.getValues(), uniqueCode, fsuId,
										deviceSaveId, deviceType);// 将C1获取到的数据完整保存到数据中
							}
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * C1接口 实时数据设置模式 默认为 一问一答 保存到数据 并进行更新操作
	 * 
	 * @param factory
	 * @param c1_fsuId
	 */
	private void setFsuAccessMode(InterfaceFactory factory, String uniqueCode, String fsuId, int device_c1Id) {
		try {
			SetDynAccessMode setDynAccessMode = new SetDynAccessMode();
			setDynAccessMode.setGroupID(1);
			setDynAccessMode.setMode(DataAccessMode.ASK_ANSWER);
			setDynAccessMode.setPollingTime(1);
			int[] setDynAccessModeIds = { device_c1Id }; // 取 所有设备的ID:AA.BBB.1.0,AA.BBB.2.0,AA.BBB.3.0
			setDynAccessMode.setCount(1);
			setDynAccessMode.setIds(setDynAccessModeIds);
			SetDynAccessModeAck setDynAccessModeAck = factory.setDynAccessMode(setDynAccessMode);// 取得设备信号点的实时数据
			if (setDynAccessModeAck != null && setDynAccessModeAck.getValues() != null
					&& setDynAccessModeAck.getValues().length > 0) {
				for (Body coneMonitorData : setDynAccessModeAck.getValues()) {
					interfaceSignalService.updateValueData(coneMonitorData,uniqueCode, fsuId);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * C1接口 告警设置 默认为 发生上报
	 * 
	 * @param factory
	 * @param c1_fsuId
	 */
	private void setFsuAlarmMode(InterfaceFactory factory) {
		try {
			AlarmModeCmd alarmModeCmd = new AlarmModeCmd();
			alarmModeCmd.setGroupID(1);
			alarmModeCmd.setMode(EnumAlarmMode.MINOR);
			int[] alarmModeCmdIds = { -1 };
			alarmModeCmd.setCount(1);
			alarmModeCmd.setIds(alarmModeCmdIds);
			factory.setAlarmMode(alarmModeCmd);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
