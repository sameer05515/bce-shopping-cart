package com.p.bce.shopping.cart.util;

public enum PropertyFile {
	FIRST_PROPERTY_FILE("prop.properties"), DB_PROPRTY("db.properties");
	private String name;

	PropertyFile(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
