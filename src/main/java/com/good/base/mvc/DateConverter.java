package com.good.base.mvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {
	@Override    
	public Date convert(String source) {  
		if(source.equals(""))return null;
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
	    dateFormat.setLenient(false);    
	    try {    
	        return dateFormat.parse(source);    
	    } catch (ParseException e) {    
	    	dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	    	dateFormat.setLenient(false);
	    	try{
	    		return dateFormat.parse(source);
	    	}
	    	catch(ParseException ee){
	    		e.printStackTrace();
	    	}
	    }           
	    return null;    
	}    
}
