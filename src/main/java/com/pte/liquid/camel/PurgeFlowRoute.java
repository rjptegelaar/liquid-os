package com.pte.liquid.camel;

import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PurgeFlowRoute extends RouteBuilder{	

	@Autowired
	private Processor deleteFlowProcessor;
	

	
    @Override
    public void configure() throws Exception {

    	    	    	    	    	
    	//Purge flows
    	from("jpa:com.pte.liquid.beans.DeleteFlow?consumer.nativeQuery=SELECT ID as id, MESSAGECOUNT as messagecount, NAME as name FROM (SELECT flows.id, count(messages.id) AS messageCount, name FROM etmflows as flows LEFT OUTER JOIN etmmessages as messages ON flows.id = messages.flowkey GROUP BY flows.id) as flowcount WHERE messagecount = 0 AND (name = '' OR name is NULL)&consumer.delay={{liquid.purge.data.interval.millis}}&maxMessagesPerPoll={{liquid.purge.data.max.messages}}&consumer.initialDelay={{liquid.purge.data.start.delay.millis}}&consumeLockEntity=false&consumer.resultClass=com.pte.liquid.beans.DeleteFlow&consumeDelete=false&consumeLockEntity=false&consumer.lockModeType=NONE&usePersist=false").routeId(this.getClass().getSimpleName()).log("Purging flows").process(deleteFlowProcessor).to("direct:deleteFlowSteps");
    	
    	//DeleteFlowSteps
    	from("direct:deleteFlowSteps").routeId("DeleteFlowStepsSubRoute").to("sql:DELETE FROM ETMFLOWSTEPS WHERE ETMFLOWSTEPS.FLOWKEY=:#${in.header.ID}?dataSource=#liquidDataSource").to("direct:deleteFlows");
    	
    	//DeleteFlows
    	from("direct:deleteFlows").routeId("DeleteFlowSubRoute").to("sql:DELETE FROM ETMFLOWS WHERE ETMFLOWS.ID=:#${in.header.ID}?dataSource=#liquidDataSource").setBody().simple("Deleting flow with ID: ${in.header.ID}").to("log:deletedMessages");
    	
    	
    	
    }

}
