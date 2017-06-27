//Copyright 2017 Paul Tegelaar
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
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
