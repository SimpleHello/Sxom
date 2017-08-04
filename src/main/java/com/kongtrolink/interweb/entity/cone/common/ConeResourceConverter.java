package com.kongtrolink.interweb.entity.cone.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kongtrolink.cone.message.body.device.TAIC;
import com.kongtrolink.cone.message.body.device.TAOC;
import com.kongtrolink.cone.message.body.device.TDIC;
import com.kongtrolink.cone.message.body.device.TDOC;
import com.kongtrolink.cone.message.body.device.TDevice;
import com.kongtrolink.cone.message.body.device.TStation;
import com.kongtrolink.interweb.entity.cone.device.ConeTAIC;
import com.kongtrolink.interweb.entity.cone.device.ConeTAOC;
import com.kongtrolink.interweb.entity.cone.device.ConeTDIC;
import com.kongtrolink.interweb.entity.cone.device.ConeTDOC;
import com.kongtrolink.scloud.core.entity.cone.ConeDevice;
import com.kongtrolink.scloud.core.entity.cone.ConeFsu;
import com.kongtrolink.scloud.core.entity.cone.ConeSignal;
import com.kongtrolink.scloud.core.entity.cone.ConeSite;

/**
 * C1接口 接收到数据 转换成 scloud 数据
 * @author John
 */
public class ConeResourceConverter {

	/**
     * 将  CI 的局站结构 TStation 转化成  SCloud 的站点结构
     * @param site SCloud 站点
     * @return 
     */
    public static ConeSite convert(TStation station) {
    	ConeSite site = new ConeSite();
    	site.setName(station.getName());
    	site.setAddress(station.getName());
        site.setCoordinate(station.getLongitude()+","+station.getLatitude());
        site.setC1_id((int)station.getId());// c1 返回的ID
        site.setC1_parentId((int)station.getParentId());
        site.setC1_desc(station.getDesc());
        return site;
    }
    
    public static ConeFsu convertConeFsu(TStation station) {
    	ConeFsu fsu = new ConeFsu();
    	fsu.setName(station.getName());
    	fsu.setC1_id((int)station.getId());// c1 返回的ID
    	fsu.setC1_parentId((int)station.getParentId());
        return fsu;
    }
    /**
     * 将  CI 的设备结构 TDevice 转化成 SCloud 的设备结构
     * @param scDevice SCloud 设备
     * @return 
     */
    public static  ConeDevice convert(TDevice tDevice,String siteId,String fsuId,String code) {
    	ConeDevice device = new ConeDevice();
        device.setSiteId(siteId);
        device.setFsuId(fsuId);
        device.setName(tDevice.getName());
        device.setCode(code);
        device.setType(tDevice.getDeviceType().getName());
        device.setTypeCode(String.format("%03d", tDevice.getDeviceType().getType()));//设备类型为3位
//      device.setSystemName(null);
//      device.setCoordinate(null);
        device.setManufactory(tDevice.getProductor());
//      device.setModel(null);
        device.setC1_id((int)tDevice.getId());
        device.setC1_parentId((int)tDevice.getParentId());
        device.setC1_desc(tDevice.getDesc());
        device.setC1_version(tDevice.getVersion());
        device.setC1_beginRunTime(tDevice.getBeginRunTime().getDate());
        return device;
    }
    
    /**
     * TAIC 模拟输入数据属性 
     * 转成 1个AI 6个DI(根据每级告警的上限 下限执行)
     * 4、信号编码：设备编码 + 信号字典码
            * 信号字典码：0sddtttnnn
                0 - 预留位（1位），默认为0
                s - 局站类型（1位），默认为4
                d - 设备类型（2位），与设备编码中的 K 相对应（特殊情况除外）
                t - 信号类型（3位），左起第1位表示信号类型（如下所示）
                    0 - DI，遥信；1 - AI，遥测；2 - DO，遥控；3 - AO，遥调
                n - 上述类型信号的序号（3位）
     * @param taic
     * @return
     */
    public static  List<ConeSignal> convertTAICTo(TAIC taic,String deviceId,String siteId,String deviceCode,int deviceType,List<ConeSignal> listDb) {
    	List<ConeSignal> list = new ArrayList<ConeSignal>();
    	if(listDb!=null&&listDb.size()>0){
    		for(ConeSignal single:listDb){
    			String code = single.getCode();
    			String lastCode = code.substring(code.length()-6,code.length());//取最后6位来辨识 衍生出来的数据
    			if("101001".equals(lastCode)){
    				list.add(convertTAICTo(taic,0,single.getDeviceId(),single.getSiteId(),code,single.getId()));//AI
    			}else if("001002".equals(lastCode)){
    				list.add(convertTAICTo(taic,11,single.getDeviceId(),single.getSiteId(),code,single.getId()));//AI
    			}else if("001003".equals(lastCode)){
    				list.add(convertTAICTo(taic,12,single.getDeviceId(),single.getSiteId(),code,single.getId()));//AI
    			}else if("001004".equals(lastCode)){
    				list.add(convertTAICTo(taic,21,single.getDeviceId(),single.getSiteId(),code,single.getId()));//AI
    			}else if("001005".equals(lastCode)){
    				list.add(convertTAICTo(taic,22,single.getDeviceId(),single.getSiteId(),code,single.getId()));//AI
    			}else if("001006".equals(lastCode)){
    				list.add(convertTAICTo(taic,31,single.getDeviceId(),single.getSiteId(),code,single.getId()));//AI
    			}else if("001007".equals(lastCode)){
    				list.add(convertTAICTo(taic,32,single.getDeviceId(),single.getSiteId(),code,single.getId()));//AI
    			}
    		}
    	}else{
    		String singalCode = deviceCode+"04"+String.format("%02d", deviceType);
        	list.add(convertTAICTo(taic,0,deviceId,siteId,singalCode+"101"+"001",null));//AI
        	list.add(convertTAICTo(taic,11,deviceId,siteId,singalCode+"001"+"002",null));//AI衍生的DI信息
        	list.add(convertTAICTo(taic,12,deviceId,siteId,singalCode+"001"+"003",null));//AI衍生的DI信息
        	list.add(convertTAICTo(taic,21,deviceId,siteId,singalCode+"001"+"004",null));//AI衍生的DI信息
        	list.add(convertTAICTo(taic,22,deviceId,siteId,singalCode+"001"+"005",null));//AI衍生的DI信息
        	list.add(convertTAICTo(taic,31,deviceId,siteId,singalCode+"001"+"006",null));//AI衍生的DI信息
        	list.add(convertTAICTo(taic,32,deviceId,siteId,singalCode+"001"+"007",null));//AI衍生的DI信息
    	}
    	
        return list;
    }
    /**
     * 
     * @param taic 模拟输入数据属性 
     * @param type (0:AI,11:1级上限,12:1级下限,21:2级上限,22:2级下限,31:3级上限,32:3级下限, )
     * @return
     */
    public static  ConeSignal convertTAICTo(TAIC taic,int type,String deviceId,String siteId,String code,String id) {
    	ConeSignal single = new ConeSignal();
    	if(id!=null){
    		single.setId(id);
    	}
    	single.setDeviceId(deviceId);
    	single.setSiteId(siteId);
    	single.setCode(code);
    	single.setType("遥测");
    	single.settRecord(new Date());
    	single.setC1_id((int)taic.getId());
    	single.setC1_parentId((int)taic.getParentId());
    	single.setMeasurement(taic.getUnit());
    	single.setOriginal(true);//是否是 原始数据(true) 还是 衍生数据(false)
    	single.setSourceData(ConeTAIC.NAMESPACE);
    	single.setTaicType(type);
    	String name = taic.getName();
    	single.setName(name);
    	switch(type){
    		case 11:
    			single.setType("遥信");
    			single.setOriginal(false);
    			single.setName(name+"过高一级");
    			single.setAlarmLevel("1");
    			single.setThreshold((double)taic.getHiLimit1());break;
    		case 12:
    			single.setType("遥信");
    			single.setOriginal(false);
    			single.setName(name+"过低一级");
    			single.setAlarmLevel("1");
    			single.setThreshold((double)taic.getLoLimit1());break;
    		case 21:
    			single.setType("遥信");
    			single.setOriginal(false);
    			single.setName(name+"过高二级");
    			single.setAlarmLevel("2");
    			single.setThreshold((double)taic.getHiLimit2());break;
    		case 22:
    			single.setType("遥信");
    			single.setOriginal(false);
    			single.setName(name+"过低二级");
    			single.setAlarmLevel("2");
    			single.setThreshold((double)taic.getLoLimit2());break;
    		case 31:
    			single.setType("遥信");
    			single.setOriginal(false);
    			single.setName(name+"过高三级");
    			single.setAlarmLevel("3");
    			single.setThreshold((double)taic.getHiLimit3());break;
    		case 32:
    			single.setType("遥信");
    			single.setOriginal(false);
    			single.setName(name+"过低三级");
    			single.setAlarmLevel("3");
    			single.setThreshold((double)taic.getLoLimit3());break;
  
    	}
        return single;
    } 
    /**
     * TDIC 数字输入数据属性 
     * @param tdic
     * @return
     */
    public static  ConeSignal convertTDICTo(TDIC tdic,String deviceId,String siteId,String deviceCode,int typeCode) {
    	ConeSignal single = new ConeSignal();
    	String singalCode = deviceCode+"04"+String.format("%02d", typeCode)+"001001";
    	single.setDeviceId(deviceId);
    	single.setSiteId(siteId);
    	single.setCode(singalCode);
    	single.setType("遥信");
    	single.setOriginal(true);
    	single.setSourceData(ConeTDIC.NAMESPACE);
    	single.setTaicType(0);
    	single.settRecord(new Date());
    	single.setC1_id((int)tdic.getId());
    	single.setC1_parentId((int)tdic.getParentId());
    	single.setName(tdic.getName());
		single.setAlarmLevel(String.valueOf(tdic.getAlarmLevel().getType()));
		single.setThreshold((double)tdic.getAlarmThresbhold().getType());
        return single;
    }
    /**
     * TAOC 模拟输出数据属性 
     *  0 - DI，遥信；1 - AI，遥测；2 - DO，遥控；3 - AO，遥调
     * @param taoc
     * @return
     */
    public static  ConeSignal convertTAOCTo(TAOC taoc,String deviceId,String siteId,String deviceCode,int typeCode) {
    	String singalCode = deviceCode+"04"+String.format("%02d", typeCode)+"301001";
    	ConeSignal single = new ConeSignal();
    	single.setDeviceId(deviceId);
    	single.setSiteId(siteId);
    	single.setCode(singalCode);
    	single.setType("遥调");
    	single.setTaicType(0);
    	single.setOriginal(true);
    	single.settRecord(new Date());
    	single.setSourceData(ConeTAOC.NAMESPACE);
    	single.setC1_id((int)taoc.getId());
    	single.setC1_parentId((int)taoc.getParentId());
    	single.setMeasurement(taoc.getUnit());
    	single.setName(taoc.getName());
        return single;
    }
    /**
     * TDOC 数字输出数据属性 
     * @param taic
     * @return
     */
    public  static ConeSignal convertTDOCTo(TDOC tdoc,String deviceId,String siteId,String deviceCode,int typeCode) {
    	String singalCode = deviceCode+"04"+String.format("%02d", typeCode)+"201001";
    	ConeSignal single = new ConeSignal();
    	single.setDeviceId(deviceId);
    	single.setSiteId(siteId);
    	single.setTaicType(0);
    	single.setCode(singalCode);
    	single.setType("遥控");
    	single.setSourceData(ConeTDOC.NAMESPACE);
    	single.setOriginal(true);
    	single.settRecord(new Date());
    	single.setC1_id((int)tdoc.getId());
    	single.setC1_parentId((int)tdoc.getParentId());
    	single.setName(tdoc.getName());
        return single;
    }
    
}

    
