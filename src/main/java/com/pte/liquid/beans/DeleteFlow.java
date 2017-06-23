package com.pte.liquid.beans;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 
 * Used to store JPA query results, no table behind this class.
 * 
 * @author Paul Tegelaar
 *
 */
@Entity
public class DeleteFlow {

	
	private long messagecount;
	private String id, name;
	
	public long getMessagecount() {
		return messagecount;
	}
	public void setMessagecount(long messagecount) {
		this.messagecount = messagecount;
	}
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "DeleteFlow [messagecount=" + messagecount + ", id=" + id
				+ ", name=" + name + "]";
	}	
	
}
