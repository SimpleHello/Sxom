package com.kongtrolink.interweb.entity.cone;

import java.util.Date;

import com.kongtrolink.cone.message.body.device.TAlarm;
import com.kongtrolink.cone.message.enumeration.EnumState;
import com.kongtrolink.interweb.entity.cone.common.ConeBaseEntity;

public class ConeTAlarm extends ConeBaseEntity {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2538505337229949273L;
	
	public  static final String NAMESPACE="_c1_talarm";
	
	private long c1_id;                  // 数据标识ID, 监控对象ID数据格式为：BBBB.CC.DDD
    private EnumState status; //状态
    private String description;       //告警的事件描述 ; BodyStatic.EVENT_LENGTH

    
    public ConeTAlarm(TAlarm talarm,String fsuId) {
		this.c1_id = talarm.getId();
		this.status = talarm.getStatus();
		this.description = talarm.getDescription();
		super.setCtime(new Date());
    	super.setFsuId(fsuId);
	}

	public ConeTAlarm() { }


    public void setStatus(EnumState status) {
        this.status = status;
    }
	public long getC1_id() {
		return c1_id;
	}

	public void setC1_id(long c1_id) {
		this.c1_id = c1_id;
	}
	public EnumState getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return " ID:" + c1_id  +  " status:" + status + " description: " + description;
    }


}
