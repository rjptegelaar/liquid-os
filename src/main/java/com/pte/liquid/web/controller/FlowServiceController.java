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
package com.pte.liquid.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import com.pte.liquid.relay.model.Flow;
import com.pte.liquid.relay.model.QFlow;
import com.pte.liquid.repo.FlowRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
@Api(value = "liquid", description = "Liquid Opensource Data API")
public class FlowServiceController {

	@Autowired
	private FlowRepository flowRepository;

	private GsonBuilder gsonBuilder = new GsonBuilder();
	private Gson gson;
	
	public FlowServiceController(){		
		
		//Setup GSON
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
		gson = gsonBuilder.create();
	}
	


	 /**
	 * Method is used to retrieve flows from the store
	 *  
	 * @param page, Page number
	 * @param size, Size of the page
	 * @return String, JSON array of messages
	 */
	@RequestMapping(method = RequestMethod.GET, path="/liquid/flows", produces="application/json")
	@ApiOperation(value = "List Flows", notes = "Method is used to retrieve flows from the store")
	public String list(@RequestParam(value="page", required=true, name="page") int page, @RequestParam(value="size", required=true, name="size") int size){
		
		PageRequest pg = new PageRequest(page, size);		
		Page<Flow> flowPage = flowRepository.findAll(pg);			
		return gson.toJson(flowPage.getContent());
	}	
	
	/**
	 * Method is used to name a flow. Pass unique flowkey and name.
	 * 
	 * 
	 * @param flowKey, FlowKey is the Unique Flow identifier
	 * @param name, Name of the flow
	 * @return ResponseEntity<String>, Affected flow
	 */
	@RequestMapping(method = RequestMethod.POST, path="/liquid/flows/name")
	@ApiOperation(value = "Set flow name", notes = "Method is used to name flows")
	public ResponseEntity<String> nameFlow(@RequestParam(value="flowkey", required=true, name="flowkey") String flowKey, @RequestParam(value="name", required=true, name="name") String name){
		
		Flow flow = flowRepository.findOne(flowKey);
		
		if(flow!=null){
			flow.setName(name);
			flowRepository.save(flow);
			return new ResponseEntity<>(gson.toJson(flow),HttpStatus.OK);
		
		}else{
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}	
	
	/**
	 * Method is used to set the error status on a flow.
	 * 
	 * 
	 * @param flowKey, FlowKey is the Unique Flow identifier
	 * @param errorStatus, Boolean indicating if a flow is an error flow. 
	 * @return ResponseEntity<String>, Affected flow
	 */
	@RequestMapping(method = RequestMethod.POST, path="/liquid/flows/errorstatus")
	@ApiOperation(value = "Set errorstatus", notes = "Method is used to set the errorstatus on a flow.")
	public ResponseEntity<String> setErrorStatus(@RequestParam(value="flowkey", required=true, name="flowkey") String flowKey, @RequestParam(value="errorstatus", required=true, name="errorstatus") boolean errorStatus){
		
		Flow flow = flowRepository.findOne(flowKey);
		
		if(flow!=null){
			flow.setError(errorStatus);
			flowRepository.save(flow);
			return new ResponseEntity<>(gson.toJson(flow),HttpStatus.OK);
		
		}else{
			
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}	
	
	/**
	 * Method is used to set the warning status on a flow.
	 * 
	 * 
	 * @param flowKey, FlowKey is the Unique Flow identifier
	 * @param warningStatus, Boolean indicating if a flow is an warning flow.
	 * @return ResponseEntity<String>, Affected flow
	 */
	@RequestMapping(method = RequestMethod.POST, path="/liquid/flows/warningstatus")
	@ApiOperation(value = "Set errorstatus", notes = "Method is used to set the errorstatus on a flow")
	public ResponseEntity<String> setWarningStatus(@RequestParam(value="flowkey", required=true, name="flowkey") String flowKey, @RequestParam(value="warningstatus", required=true, name="warningstatus") boolean warningStatus){
		
		Flow flow = flowRepository.findOne(flowKey);
		
		if(flow!=null){
			flow.setWarning(warningStatus);
			flowRepository.save(flow);
			return new ResponseEntity<String>(gson.toJson(flow),HttpStatus.OK);
		
		}else{
			
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
	}	
	

	/**
	 * Method is used to retrieve flows from the store based on a filter
	 * 
	 * 
	 * @param page, Page number
	 * @param size, Size of the page
	 * @param flowkey, FlowKey is the Unique Flow identifier
	 * @param name, Name of the flow
	 * @param errorStatus, Boolean indicating if a flow is an error flow.
	 * @param warningStatus, Boolean indicating if a flow is an warning flow.
	 * @return String containing JSON array of flows
	 */
	@RequestMapping(method = RequestMethod.GET, path="/liquid/flows/filter", produces="application/json")
	@ApiOperation(value = "Filter flows", notes = "Method is used to retrieve messages from the store based on a filter")
	public String filter(@RequestParam(value="page", required=true, name="page") int page, 
						@RequestParam(value="size", required=true, name="size") int size, 
						@RequestParam(value="flowkey", required=false, name="flowkey") String flowkey, 					
						@RequestParam(value="name", required=false, name="name") String name,
						@RequestParam(value="errorstatus", required=false, name="errorstatus") Boolean errorStatus,						  
						@RequestParam(value="warningstatus", required=false, name="warningstatus") Boolean warningStatus){
		
		PageRequest pg = new PageRequest(page, size);		
		
		Page<Flow> flowPage = flowRepository.findAll(createPredicate(flowkey, name, errorStatus, warningStatus), pg);
		return gson.toJson(flowPage.getContent());
	}
	
	
	public Predicate createPredicate(String flowKey, String name, Boolean errorStatus, Boolean warningStatus) {
	    QFlow qFlow = QFlow.flow;
	    BooleanBuilder booleanBuilder = new BooleanBuilder();
	    
	    if (flowKey!=null) {
	      booleanBuilder.and(qFlow.id.eq(flowKey));
	    }
	    
	    if (name!=null) {
	      booleanBuilder.and(qFlow.name.contains(name));
	    }
	    	 
	    
	    if (errorStatus!=null) {
	    	booleanBuilder.and(qFlow.error.eq(errorStatus));
	    }
	    
	    if (warningStatus!=null) {
		      booleanBuilder.and(qFlow.warning.eq(warningStatus));
		    }
	    
	    return booleanBuilder.getValue();
	  }

	public FlowRepository getFlowRepository() {
		return flowRepository;
	}


	public void setFlowRepository(FlowRepository flowRepository) {
		this.flowRepository = flowRepository;
	}

	


	
	
	
	
	
}
