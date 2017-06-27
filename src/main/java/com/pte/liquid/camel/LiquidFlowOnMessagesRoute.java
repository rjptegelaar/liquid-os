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
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LiquidFlowOnMessagesRoute extends RouteBuilder {

	@Autowired
	private Processor batchFlowProcessor;
	
	@Autowired
	private AggregationStrategy queryAggregationStrategy;
	

	@Override
    public void configure() throws Exception {
                 	    			
    	
    	//Update flowkey on all the messages
    	from("jms:queue:com.pte.liquid.flow.message.update.in?concurrentConsumers={{liquid.flow.create.threads}}")
    	.routeId("AggregateAndUpdateSubRoute")
    	.process(batchFlowProcessor)
    	.aggregate(constant(true), queryAggregationStrategy).completionInterval(10000).completionSize(500)
    	.to("sql:UPDATE etmmessages as msg SET nrcrawled = (# + 1), flowkey = # WHERE msg.correlationid = #?dataSource=#liquidDataSource&batch=true")
    	.log("Set flow key on a block of messages");    	    
    	
    }
}
