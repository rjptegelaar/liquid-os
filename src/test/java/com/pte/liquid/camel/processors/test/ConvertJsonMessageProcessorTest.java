package com.pte.liquid.camel.processors.test;

import java.io.IOException;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pte.liquid.camel.processors.ConvertJsonMessageProcessor;
import com.pte.liquid.relay.model.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/test-application-context.xml"})
public class ConvertJsonMessageProcessorTest extends CamelTestSupport{
	
	@Autowired
	private ConvertJsonMessageProcessor convertJsonMessageProcessor;


	
	@EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;
 
    @Produce(uri = "direct:start")
    protected ProducerTemplate template;
    
    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            public void configure() throws IOException {            	
            	from("direct:start").process(convertJsonMessageProcessor).to("mock:result");
            }
        };
    }
    
    @Test
    public void testHappy() throws IOException, InterruptedException{
    	
    	String input = "{\"NumberOfTimesCrawled\":0,\"ID\":\"5eaccf2c-213a-44c6-a577-70475125900e\",\"Order\":0,\"SnapshotTimeMillis\":0,\"Header\":[{\"messageid\":\"5eaccf2c-213a-44c6-a577-70475125900e\",\"key\":\"test\",\"value\":\"123456789\"},{\"messageid\":\"5eaccf2c-213a-44c6-a577-70475125900e\",\"key\":\"test1\",\"value\":\"1234567890\"},{\"messageid\":\"5eaccf2c-213a-44c6-a577-70475125900e\",\"key\":\"test2\",\"value\":\"12345678901\"}],\"Part\":[]}";
    	
    	Message expected = new Message();
    	
    	expected.setHeader("test", "123456789");
    	expected.setHeader("test1", "1234567890");
    	expected.setHeader("test2", "12345678901");
    	
    	resultEndpoint.setExpectedMessageCount(1);        	
    	    	    
    	template.sendBody(input);
 	
    	
    	
    	resultEndpoint.assertIsSatisfied();
    	
    	resultEndpoint.expectedBodiesReceived(expected);
    	
    

    }

	
}
