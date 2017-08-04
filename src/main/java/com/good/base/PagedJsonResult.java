package com.good.base;

public class PagedJsonResult extends JsonResult {
	protected Integer total;
	protected Integer pageSize;
	protected Integer pageCount;
	public PagedJsonResult(Object rows,Integer error,String message,Integer total,Integer pageSize){
		this.rows=rows;
		this.error=error;
		this.message=message;
		this.total=total;
		this.pageSize=pageSize;
		
		int totalPage = total/this.pageSize;
		if(total%this.pageSize!=0){
			totalPage++;
		}
		if(totalPage==0)totalPage=1;
		this.pageCount = totalPage;
	}
	
	
	public Integer getTotal() {
		return total;
	}
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}


	public void setTotal(Integer total) {
		this.total = total;
	}


	public void setRows(Integer rows) {
		this.rows = rows;
	}


	public Integer getPageCount() {
		return pageCount;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
}
