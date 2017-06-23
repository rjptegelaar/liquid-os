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
