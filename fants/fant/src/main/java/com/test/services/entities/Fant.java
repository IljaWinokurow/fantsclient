package com.test.services.entities;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "fant")
public class Fant {
	/*
	 * DB table fields `id` varchar(45) NOT NULL, `first_name` varchar(45)
	 * DEFAULT NULL, `last_name` varchar(45) DEFAULT NULL, `phone` varchar(45)
	 * DEFAULT NULL, `mail` varchar(45) DEFAULT NULL, `adress` varchar(45)
	 * DEFAULT NULL, `contract_id` varchar(45) DEFAULT NULL,
	 * `contract_expire_date` date DEFAULT NULL
	 */

	private String id;
	private String text;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
