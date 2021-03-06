package com.good.base.properties;

public class Property {
	private static java.util.Properties property;

	private Property() {
	}

	static void init(java.util.Properties props) {
		property = props;
	}

	public static String getProperty(String key) {
		return property.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return property.getProperty(key, defaultValue);

	}
}
