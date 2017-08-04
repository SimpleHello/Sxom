package com.good.base;

import java.util.HashMap;
import java.util.Map;

public class JsonMap {
	protected Integer error;
	protected String message;
	protected Object rows;
	
	public JsonMap(Integer error,String message,Object rows){
		this.error=error;
		this.message=message;
		this.rows=rows;
	}
	
	public static Map<String,Object> resultMap(Integer error,String message,Object rows){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("error", error);
		map.put("message", message);
		map.put("rows", rows);
		return map;
	}

	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getRows() {
		return rows;
	}

	public void setRows(Object rows) {
		this.rows = rows;
	}
	
	
	
}
