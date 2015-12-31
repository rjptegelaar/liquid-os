package com.pte.liquid.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PurgeLiquidIndexRoute extends RouteBuilder{	

	
    @Override
    public void configure() throws Exception {
    
    	//Purge index every hour
    	from("timer://purgeIndex?fixedRate=true&period={{liquid.purge.data.interval.millis}}&delay={{liquid.purge.data.start.delay.millis}}").setBody().constant("7").beanRef("liquidSearchBean", "delete");
    }

}
