package com.pte.liquid.camel;

import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class LiquidCreateNewFlowRoute extends RouteBuilder{	


	@Autowired
	private Processor flowCheckProcessor;
	
	@Autowired
	private Processor newFlowProcessor;
	
	
	
    @Override
    public void configure() throws Exception {    	

    	            	
    	//Check if flow already exists
    	from("jms:queue:com.pte.liquid.flow.in?concurrentConsumers={{liquid.flow.create.threads}}").routeId(this.getClass().getSimpleName()).to("sql:select count(id) as nr from etmflows where etmflows.id = :#${in.header.FLOWKEY}?dataSource=#liquidDataSource").process(flowCheckProcessor).filter(header("NR").isEqualTo(0)).delay(5).to("direct:getFlowSteps");
    	
    	//Get flowsteps
    	from("direct:getFlowSteps").routeId("PersistFlowsSubRoute").to("sql:SELECT msg.location as location, msg.messageorder as messageorder FROM etmmessages msg where msg.correlationid = :#${in.header.CORRELATIONID} group by location, messageorder?dataSource=#liquidDataSource").delay(5).process(newFlowProcessor).log(LoggingLevel.DEBUG,"Creating a new flow").to("jpa:com.pte.liquid..relay.model.Flow?transactionManager=#liquidTxManager");

    	

    }
    


}
