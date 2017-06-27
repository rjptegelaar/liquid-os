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

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class LiquidParseFlowRoute extends RouteBuilder {

	


	@Override
    public void configure() throws Exception {
                 	    	
		
    	//Get flows, use JPA because locking control, delay a bit to easy the pressure on the database.
    	from("jpa:com.pte.liquid.beans.CrawlCount?consumer.nativeQuery=SELECT correlationid, min(nrcrawled) as nrcrawled, 'dummy' as flowKey FROM etmmessages where nrcrawled <= {{liquid.flow.create.max.num.crawled}} GROUP BY correlationid ORDER BY nrcrawled ASC&consumer.delay={{liquid.flow.create.interval.millis}}&maxMessagesPerPoll={{liquid.flow.create.max.messages}}&consumer.initialDelay={{liquid.flow.create.start.delay.millis}}&consumeLockEntity=false&consumer.resultClass=com.pte.liquid.beans.CrawlCount&consumeDelete=false&consumeLockEntity=false&consumer.lockModeType=NONE&usePersist=false")
    	.routeId(this.getClass().getSimpleName())
    	.delay(5)
    	.to("jms:queue:com.pte.liquid.flow.hash.in?deliveryPersistent=false");    	
    
    }
}
