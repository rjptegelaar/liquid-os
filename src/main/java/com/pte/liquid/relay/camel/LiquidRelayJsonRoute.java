package com.pte.liquid.relay.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class LiquidRelayJsonRoute extends RouteBuilder{	

	
    @Override
    public void configure() throws Exception {    	    	        	
    	from("jms:queue:com.pte.liquid.relay.json.in").convertBodyTo(String.class).to("jms:queue:com.pte.liquid.relay.in");
    }

}
