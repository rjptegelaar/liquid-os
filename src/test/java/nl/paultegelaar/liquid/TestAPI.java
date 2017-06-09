package nl.paultegelaar.liquid;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class TestAPI{

	@Rule
	public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
	
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@Before
	public void setUp() {
		System.out.println("He");
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
				.apply(documentationConfiguration(this.restDocumentation)) 
				.build();
	}
	
	@Test
	public void testList() throws Exception{
		System.out.println("Hallo");
		this.mockMvc.perform(get("/liquid/list").accept(MediaType.APPLICATION_JSON)) 
		.andExpect(status().isOk())
		.andDo(document("index"));
	}
	
}
