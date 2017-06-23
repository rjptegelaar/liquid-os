package com.pte.liquid.camel.processors;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.pte.liquid.beans.CrawlCount;

@Component
public class BatchFlowProcessor implements Processor{
	@Override
	public void process(Exchange exchange) throws Exception {
			
		
		CrawlCount crawlCount = exchange.getIn().getBody(CrawlCount.class);
		
		
		List<Object> queryParams = new ArrayList<Object>();
		queryParams.add(crawlCount.getNrcrawled());
		queryParams.add(crawlCount.getFlowKey());
		queryParams.add(crawlCount.getCorrelationid());
		
		exchange.getIn().setBody(queryParams);
	}
}
