package com.pte.liquid.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.pte.liquid.beans.DeleteFlow;


//TODO remove this and replace with standard camel
@Component
public class DeleteFlowProcessor implements Processor{
	@Override
	public void process(Exchange exchange) throws Exception {
		DeleteFlow deleteFlow = exchange.getIn().getBody(DeleteFlow.class);	

		//Get info needed to start determining the flow
		exchange.getIn().setHeader("ID",deleteFlow.getId());
		
	}
}
