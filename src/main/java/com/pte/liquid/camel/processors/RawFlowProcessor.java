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
package com.pte.liquid.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.pte.liquid.beans.CrawlCount;

@Component
public class RawFlowProcessor implements Processor{

	@Override
	public void process(Exchange exchange) throws Exception {
			
		//Get info needed to start determining the flow
		CrawlCount crawlCount = exchange.getIn().getBody(CrawlCount.class);
		exchange.getIn().setHeader("CORRELATIONID", crawlCount.getCorrelationid());
		exchange.getIn().setHeader("NRCRAWLED", crawlCount.getNrcrawled());
	}

}
