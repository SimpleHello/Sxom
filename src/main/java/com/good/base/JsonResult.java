package com.good.base;

/**
 * 返回json封装对象<br>
 * 如果失败error = -1 ,如果成功 error = 1，特殊情况请自己定义返回码<br >
 * 如果错误信息，error小于0<br >
 * 如果成功信息，error大于0
 */
public class JsonResult {
	protected Integer error;
	protected String message;
	protected Object rows;
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
	public JsonResult(){
		this.error=0;
		this.message=null;
		this.rows=null;
	}
	public JsonResult(Integer error,String message,Object rows){
		this.error=error;
		this.message=message;
		this.rows=rows;
	}
}
