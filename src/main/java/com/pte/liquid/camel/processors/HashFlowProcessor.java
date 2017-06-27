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
package com.pte.liquid.camel.processors;

import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import com.pte.liquid.beans.CrawlCount;

@Component
public class HashFlowProcessor implements Processor{
	@Override
	public void process(Exchange exchange) throws Exception {
			
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> results = exchange.getIn().getBody(List.class);
		
		//Create large string so flowkey can be generated
		StringBuffer sb = new StringBuffer();
		for (Map<String, Object> map : results) {					
			sb.append((String)map.get("PARENT_LOCATION"));
			sb.append(String.valueOf((Integer)map.get("MESSAGE_ORDER")));
			sb.append((String)map.get("LOCATION"));    			
		}
		
		String flowKey = DigestUtils.md5Hex(sb.toString());
		//Set flowkey for later use
		exchange.getIn().setHeader("FLOWKEY", flowKey);
		
		//Retrieve correlationid and nr crawled as query parameters
		String correlationID = exchange.getIn().getHeader("CORRELATIONID", String.class);
		String nrCrawled = exchange.getIn().getHeader("NRCRAWLED", String.class);
		
		CrawlCount crawlCount = new CrawlCount();
		crawlCount.setNrcrawled(Long.parseLong(nrCrawled));
		crawlCount.setFlowKey(flowKey);
		crawlCount.setCorrelationid(correlationID);
		
		exchange.getIn().setBody(crawlCount);
	}
}
