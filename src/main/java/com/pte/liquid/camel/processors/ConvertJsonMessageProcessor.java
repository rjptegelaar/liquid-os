//Copyright 2015 Paul Tegelaar
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
package com.pte.liquid.camel.processors;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pte.liquid.relay.model.Message;

@Component
public class ConvertJsonMessageProcessor implements Processor{
	
	private GsonBuilder gsonBuilder = new GsonBuilder();
	private Gson gson;
	
	@Value("${liquid.purge.data.days}")
	private long daysInPast;
	
	public ConvertJsonMessageProcessor(){
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		gson = gsonBuilder.create();
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
	
		
		Message msg = gson.fromJson(exchange.getIn().getBody(String.class),Message.class);
		//Set time to live based on property
		Date now = new Date();
		
		long ttl = now.getTime() + (1000*60*60*24*daysInPast); 
		
		Date ttlDate = new Date(ttl);
		msg.setTtlMillis(ttl);		
		msg.setTtl(ttlDate);
		
		exchange.getIn().setBody(msg);
	}

	public long getDaysInPast() {
		return daysInPast;
	}

	public void setDaysInPast(long daysInPast) {
		this.daysInPast = daysInPast;
	}
	
	
	
}
