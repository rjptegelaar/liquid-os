package com.pte.liquid.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FanLiquidMessageRoute extends RouteBuilder{	

	
    @Override
    public void configure() throws Exception {    	    	
    	from("jms:queue:com.pte.liquid.relay.in").multicast().to("direct:liquidPersist", "direct:liquidIndex");
    	from("direct:liquidPersist").to("jms:queue:com.pte.liquid.store.in");
    	from("direct:liquidIndex").to("jms:queue:com.pte.liquid.index.in");    
    }

}
