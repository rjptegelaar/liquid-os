package com.pte.liquid.camel;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.pte.liquid.camel.processors.ConvertJsonMessageProcessor;

@Component
public class StoreLiquidMessageRoute extends RouteBuilder{	

	
    @Override
    public void configure() throws Exception {

    	Processor convertJsonMessageProcessor = new ConvertJsonMessageProcessor();
    	
    	
    	from("jms:queue:com.pte.liquid.store.in").process(convertJsonMessageProcessor).to("jpa:com.pte.liquid.relay.model.Message?transactionManager=#liquidTxManager");
    }

    
    

}
