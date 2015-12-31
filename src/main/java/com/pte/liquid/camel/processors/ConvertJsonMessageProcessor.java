package com.pte.liquid.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pte.liquid.relay.model.Message;

public class ConvertJsonMessageProcessor implements Processor{
	
	private GsonBuilder gsonBuilder = new GsonBuilder();
	private Gson gson;
	
	public ConvertJsonMessageProcessor(){
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		gson = gsonBuilder.create();
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
		exchange.getIn().setBody(gson.fromJson(exchange.getIn().getBody(String.class),Message.class));
	}
}
