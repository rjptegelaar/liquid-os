package com.pte.liquid.camel;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.pte.liquid.camel.processors.ConvertJsonMessageProcessor;

@Component
public class IndexLiquidMessageRoute extends RouteBuilder{	

	
    @Override
    public void configure() throws Exception {
    	Processor convertJsonMessageProcessor = new ConvertJsonMessageProcessor();
    	
    	from("jms:queue:com.pte.liquid.index.in").process(convertJsonMessageProcessor).beanRef("liquidSearchBean", "index");
    }

}
