package com.good.base.mvc;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * 自定义数据绑定
 * 
 *
 */
public class CustomerBinding implements WebBindingInitializer {
	
	public void initBinder(WebDataBinder binder, WebRequest request) {
		
		/* 日期数据绑定 */
		dateBinding(binder);
		
		/* 整形数据绑定 */
		intBinding(binder);
		
		/* 双精度数据绑定 */
		doubleBinding(binder);
	}
	
	/**
	 * 双精度数据绑定
	 * @param binder
	 */
	private void doubleBinding(WebDataBinder binder) {
		binder.registerCustomEditor(Float.class, new CustomNumberEditor(Float.class, true));
		binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
		binder.registerCustomEditor(float.class, new CustomNumberEditor(Float.class, true));
		binder.registerCustomEditor(double.class, new CustomNumberEditor(Double.class, true));
	}
	
	/**
	 * 整形数据绑定
	 * @param binder
	 */
	private void intBinding(WebDataBinder binder) {
		binder.registerCustomEditor(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
		binder.registerCustomEditor(Long.class, new CustomNumberEditor(Long.class, true));
		binder.registerCustomEditor(int.class, new CustomNumberEditor(Integer.class, true));
		binder.registerCustomEditor(long.class, new CustomNumberEditor(Long.class, true));
	}
	
	/**
	 * 日期数据绑定
	 * @param binder
	 */
	private void dateBinding(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
		binder.registerCustomEditor(Timestamp.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
}