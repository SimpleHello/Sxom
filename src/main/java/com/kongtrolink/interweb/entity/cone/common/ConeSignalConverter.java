package com.kongtrolink.interweb.entity.cone.common;

import com.kongtrolink.cone.message.body.device.TAIC;
import com.kongtrolink.cone.message.body.device.TAOC;
import com.kongtrolink.cone.message.body.device.TDIC;
import com.kongtrolink.cone.message.body.device.TDOC;
import com.kongtrolink.cone.message.body.device.TDevice;
import com.kongtrolink.cone.message.body.device.TSC;
import com.kongtrolink.cone.message.body.device.TStation;
import com.kongtrolink.interweb.entity.cone.device.ConeTAIC;
import com.kongtrolink.interweb.entity.cone.device.ConeTAOC;
import com.kongtrolink.interweb.entity.cone.device.ConeTDIC;
import com.kongtrolink.interweb.entity.cone.device.ConeTDOC;
import com.kongtrolink.interweb.entity.cone.device.ConeTDevice;
import com.kongtrolink.interweb.entity.cone.device.ConeTSC;
import com.kongtrolink.interweb.entity.cone.device.ConeTStation;

/**
 * 将 数据库保存的格式 转成 C1接口格式
 * @author John
 *
 */
public class ConeSignalConverter {

	public static TAIC converToTAIC(ConeTAIC taci){
		TAIC  value = new TAIC();
		value.setType(taci.getType());
		value.setId(taci.getC1_id());
		value.setParentId(taci.getParentId());
		value.setName(taci.getName());
		value.setDesc(taci.getDesc());
		value.setMaxVal(taci.getMaxVal());
		value.setMinVal(taci.getMinVal());
		value.setAlarmLevel(taci.getAlarmLevel());
		value.setAlarmEnable(taci.getAlarmEnable());
		value.setHiLimit1(taci.getHiLimit1());
		value.setLoLimit1(taci.getLoLimit1());
		value.setHiLimit2(taci.getHiLimit2());
		value.setLoLimit2(taci.getLoLimit2());
		value.setHiLimit3(taci.getHiLimit3());
		value.setLoLimit3(taci.getLoLimit3());
		value.setStander(taci.getStander());
		value.setPercision(taci.getPercision());
		value.setSaved(taci.getSaved());
		value.setUnit(taci.getUnit());
		return value;
	}

	public static TAOC converToTAOC(ConeTAOC taoc) {
		TAOC value = new TAOC();
		value.setType(taoc.getType());
		value.setId(taoc.getC1_id());
		value.setParentId(taoc.getParentId());
		value.setName(taoc.getName());
		value.setDesc(taoc.getDesc());
		value.setMaxVal(taoc.getMaxVal());
		value.setMinVal(taoc.getMinVal());
		value.setControlEnable(taoc.getControlEnable());
		value.setStander(taoc.getStander());
		value.setPercision(taoc.getPercision());
		value.setSaved(taoc.getSaved());
		value.setUnit(taoc.getUnit());
		return value;
	}
	
	public static TDevice converToTDevice(ConeTDevice tdevice) {
		TDevice value = new TDevice();
		value.setType(tdevice.getType());
		value.setId(tdevice.getC1_id());
		value.setParentId(tdevice.getParentId());
		value.setName(tdevice.getName());
		value.setDesc(tdevice.getDesc());
		value.setDeviceType(tdevice.getDeviceType());
		value.setProductor(tdevice.getProductor());
		value.setVersion(tdevice.getVersion());
		value.setBeginRunTime(tdevice.getBeginRunTime());
		return value;
	}
	
	public static TDIC converToTDIC(ConeTDIC tdic) {
		TDIC value = new TDIC();
		value.setType(tdic.getType());
		value.setId(tdic.getC1_id());
		value.setParentId(tdic.getParentId());
		value.setName(tdic.getName());
		value.setDesc(tdic.getDesc());
		value.setAlarmThresbhold(tdic.getAlarmThresbhold());
		value.setAlarmLevel(tdic.getAlarmLevel());
		value.setAlarmEnable(tdic.getAlarmEnable());
		value.setDesc0(tdic.getDesc0());
		value.setDesc1(tdic.getDesc1());
		value.setSaved(tdic.getSaved());
		return value;
	}
	
	public static TDOC converToTDOC(ConeTDOC tdoc) {
		TDOC value = new TDOC();
		value.setType(tdoc.getType());
		value.setId(tdoc.getC1_id());
		value.setParentId(tdoc.getParentId());
		value.setName(tdoc.getName());
		value.setDesc(tdoc.getDesc());
		value.setControlEnable(tdoc.getControlEnable());
		value.setDesc0(tdoc.getDesc0());
		value.setDesc1(tdoc.getDesc1());
		value.setSaved(tdoc.getSaved());
		return value;
	}
	
	public static TSC converToTSC(ConeTSC tsc) {
		TSC value = new TSC();
		value.setType(tsc.getType());
		value.setId(tsc.getC1_Id());
		value.setParentId(tsc.getParentId());
		value.setName(tsc.getName());
		value.setDesc(tsc.getDesc());
		value.setAlarmEnable(tsc.getAlarmEnable());
		value.setSaved(tsc.getSaved());
		return value;
	}
	
	public static TStation converToTStation(ConeTStation tstation) {
		TStation value = new TStation();
		value.setType(tstation.getType());
		value.setId(tstation.getC1_id());
		value.setParentId(tstation.getParentId());
		value.setName(tstation.getName());
		value.setDesc(tstation.getDesc());
		value.setLongitude(tstation.getLongitude());
		value.setLatitude(tstation.getLatitude());
		return value;
	}
}
