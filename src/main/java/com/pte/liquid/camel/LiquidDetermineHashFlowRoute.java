package com.pte.liquid.camel;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LiquidDetermineHashFlowRoute extends RouteBuilder {

	@Autowired
	private Processor rawFlowProcessor;

	@Autowired
	private Processor hashFlowProcessor;
	


	@Override
    public void configure() throws Exception {
                 	    			
    	//Get data to generate hash for flowkey, use sql for mid route query option. Delay a bit to easy the pressure on the database.
    	from("jms:queue:com.pte.liquid.flow.hash.in?concurrentConsumers={{liquid.flow.create.threads}}").routeId("DetermineHashSubRoute").process(rawFlowProcessor).to("sql:SELECT msg.location as location, msg1.location as parent_location,msg.messageorder as message_order FROM etmmessages as msg LEFT OUTER JOIN etmmessages as msg1 ON msg1.id = msg.parentid where msg.correlationid = :#${in.header.CORRELATIONID} GROUP BY msg.location, msg.messageorder, parent_location ORDER BY  msg.messageorder, msg.location, parent_location?dataSource=#liquidDataSource").delay(5).to("direct:hashData");
    	//Generate hash from data, and perform batch update
    	from("direct:hashData").routeId("AssignFlowSubRoute").process(hashFlowProcessor).multicast().to("jms:queue:com.pte.liquid.flow.in?deliveryPersistent=false", "jms:queue:com.pte.liquid.flow.message.update.in?deliveryPersistent=false");    	    	    	    	
    	
    }
}
