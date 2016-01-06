//Copyright 2015 Paul Tegelaar
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
public class FanLiquidMessageRoute extends RouteBuilder{	

	
    @Override
    public void configure() throws Exception {    	    	
    	from("jms:queue:com.pte.liquid.relay.in").multicast().to("direct:liquidPersist", "direct:liquidIndex");
    	from("direct:liquidPersist").to("jms:queue:com.pte.liquid.store.in");
    	from("direct:liquidIndex").to("jms:queue:com.pte.liquid.index.in");    
    }

}
