package com.good.base.mvc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToListConverter implements Converter<String, List<Object>> {
	 
    @Override
    public List<Object> convert(String source) {
        List<Object> result = new ArrayList<Object>();
        if(!StringUtils.isEmpty(source)) {
            String[] array = source.split(",");
            for(Object obj : array) {
            	result.add(obj);
            }
        }
        return result;
    }
}