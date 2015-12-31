package com.pte.liquid.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class PurgeLiquidMessagesRoute extends RouteBuilder{	


    @Override
    public void configure() throws Exception {  
    	
    	//Purge messages every hour
    	from("jpa://com.pte.liquid.relay.model.Message?consumer.query=select msg from com.pte.liquid.relay.model.Message msg where msg.snapshotTime < DATEADD('DAY', -7, CURRENT_DATE())&transactionManager=#liquidTxManager&maximumResults={{liquid.purge.data.max.messages}}&consumer.delay={{liquid.purge.data.interval.millis}}&consumeLockEntity=true&consumer.initialDelay={{liquid.purge.data.start.delay.millis}}&consumer.transacted=true").setBody().simple("Deleting message with ID: ${body.id}").to("log:log");    	
    	    	
    }
    
    
}
