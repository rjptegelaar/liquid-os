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
