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
