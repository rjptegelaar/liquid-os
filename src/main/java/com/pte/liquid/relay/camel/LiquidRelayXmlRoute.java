package com.pte.liquid.relay.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.springframework.stereotype.Component;

@Component
public class LiquidRelayXmlRoute extends RouteBuilder{	

	
    @Override
    public void configure() throws Exception {
    	JaxbDataFormat jaxb = new JaxbDataFormat();
    	jaxb.setContextPath("com.pte.liquid.relay.model");  
    	from("jms:queue:com.pte.liquid.relay.xml.in").convertBodyTo(String.class).unmarshal(jaxb).to("jms:queue:com.pte.liquid.relay.in");
    }

}
