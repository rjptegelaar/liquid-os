//Copyright 2017 Paul Tegelaar
//
//Licensed under the Apache License, Version 2.0 (the "License");
//you may not use this file except in compliance with the License.
//You may obtain a copy of the License at
//
//http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software
//distributed under the License is distributed on an "AS IS" BASIS,
//WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//See the License for the specific language governing permissions and
//limitations under the License.
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
