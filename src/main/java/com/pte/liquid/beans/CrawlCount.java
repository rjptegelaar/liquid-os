package com.pte.liquid.beans;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Used to store JPA query results, no table behind this class.
 * 
 * 
 * @author Paul Tegelaar
 *
 */
@Entity
@XmlRootElement(name="CrawlCount")
public class CrawlCount {

	
	private long nrcrawled;
	private String correlationid;
	private String flowKey;
	
		
	public CrawlCount(){
		
	}

	@XmlElement(name="Nrcrawled")
	public long getNrcrawled() {
		return nrcrawled;
	}


	public void setNrcrawled(long nrcrawled) {
		this.nrcrawled = nrcrawled;
	}


	@Id
	@XmlElement(name="Correlationid")
	public String getCorrelationid() {
		return correlationid;
	}


	public void setCorrelationid(String correlationid) {
		this.correlationid = correlationid;
	}	

	@XmlElement(name="FlowKey")
	public String getFlowKey() {
		return flowKey;
	}

	public void setFlowKey(String flowKey) {
		this.flowKey = flowKey;
	}

	@Override
	public String toString() {
		return "CrawlCount [nrcrawled=" + nrcrawled + ", correlationid="
				+ correlationid + "]";
	}	
	
	
	
	
}
