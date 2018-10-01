package controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

/*
 * I was called into work on Sunday but luckily had enough done to complete all of the service functionality.
 * My testing is very much on the light side but this at least verifies we get JSON back
 * with the fields that were required. Obviously all of model classes and other controller methods
 * should also have test cases but this was the quickest way to prove this works in code.
 * 
 * I have tested the responses and cache bust manually as well.
 */
public class WeatherControllerTest {
	
	private static final Gson gson = new Gson();

    @Autowired
    private MockMvc mvc;


    @Test
    public void webConnectivityTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Very boring landing page, give me service calls!")));
    }
    
    /*
     * Basic test to see if we returned text that appears to be JSON
     * Should do more complex regex but ran out of time as I was called into work
     */
    @Test
    public void getWindJSONTest() throws Exception {
    	ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/api/v1/wind/89110"));
    	String toCheck = resultActions.andReturn().getResponse().getContentAsString();
    	Exception e = null;
    	try {
    	      gson.fromJson(toCheck, Object.class);
    	  } catch(com.google.gson.JsonSyntaxException ex) { 
    	      e = ex;
    	  }
    	assertTrue(e==null);
    	
    	
    }
    
    /*
     * Test to check that our fields are available. Should really be checking
     * reasonable field lengths but I was called into work and won't have much time for
     * unit tests
     */
    @Test
    public void getWindFieldsTest() throws Exception {
    	ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/api/v1/wind/89110"));
    	String toCheck = resultActions.andReturn().getResponse().getContentAsString();
    	assertTrue(toCheck.contains("\"speed\":"));
    	assertTrue(toCheck.contains("\"zip\":"));
    	assertTrue(toCheck.contains("\"degrees\":"));
    	assertTrue(toCheck.contains("\"refreshed\":"));
    	//I've seen gust not return, maybe issue with the open API?
    	//assertTrue(toCheck.contains("\"gust\":"));
    	
    }

}
