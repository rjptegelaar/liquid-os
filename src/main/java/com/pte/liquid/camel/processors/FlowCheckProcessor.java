package com.pte.liquid.camel.processors;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class FlowCheckProcessor implements Processor{
	@Override
	public void process(Exchange exchange) throws Exception {
			
		@SuppressWarnings("unchecked")
		Map<String, String> results = exchange.getIn().getBody(Map.class);		
		
		//Set nr on header so filtering is easy
		exchange.getIn().setHeader("NR", results.get("NR"));
		
	}
}
