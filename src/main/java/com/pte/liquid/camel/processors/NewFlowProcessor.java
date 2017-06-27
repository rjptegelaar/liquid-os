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
import org.springframework.stereotype.Component;

import com.pte.liquid.relay.model.Flow;

@Component
public class NewFlowProcessor implements Processor{
	@Override
	public void process(Exchange exchange) throws Exception {
			
		
		//Get flowkey from header, so we can create Flow object for JPA step
		String flowKey = exchange.getIn().getHeader("FLOWKEY", String.class);
		Flow flow = new Flow(flowKey, null);		
		flow.setWarning(true);
		
		//Loop through results to get flowsteps
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> results = exchange.getIn().getBody(List.class);				
		for (Map<String, Object> map : results) {
			//Create step per unique message
			flow.createFlowStep((String)map.get("LOCATION"), (Integer)map.get("MESSAGEORDER"));			
		}

		
		exchange.getIn().setBody(flow);
	}
}
