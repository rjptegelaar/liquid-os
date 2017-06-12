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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Predicate;
import com.pte.liquid.relay.model.Message;
import com.pte.liquid.relay.model.QMessage;
import com.pte.liquid.repo.MessageRepository;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;

@RestController
@Api(value = "liquid", description = "Liquid Opensource Data API")
public class MessageServiceController {

	@Autowired
	private MessageRepository messageRepository;

	private GsonBuilder gsonBuilder = new GsonBuilder();
	private Gson gson;
	
	public MessageServiceController(){		
		
		//Setup GSON
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
		gson = gsonBuilder.create();
	}
	

	/**
	 * Method is used to retrieve messages from the store
	 * 
	 * 
	 * @return String containing JSON array of messages
	 */
	@RequestMapping(method = RequestMethod.GET, path="/liquid/list", produces="application/json")
	@ApiOperation(value = "List Messages", notes = "Method is used to retrieve messages from the store")
	public String list(@RequestParam(value="page", required=true, name="page") int page, @RequestParam(value="size", required=true, name="size") int size){
		
		PageRequest pg = new PageRequest(page, size);		
		Page<Message> messagePage = messageRepository.findAll(pg);			
		return gson.toJson(messagePage.getContent());
	}	
	
	/**
	 * Method is used to a single message from the store based on ID
	 * 
	 * 
	 * @return String containing JSON array of messages
	 */
	@RequestMapping(method = RequestMethod.GET, path="/liquid/get", produces="application/json")
	@ApiOperation(value = "Get single Message", notes = "Method is used to a single message from the store based on ID")
	public String get(@RequestParam(value="id", required=true, name="id") String ID){
		
		Message msg = messageRepository.findOne(ID);
		
		if(msg!=null){
			return gson.toJson(msg);	
		}else{
			return "[]";
		}
		
	}	
	
	
	/**
	 * Method is used to retrieve messages from the store based on a filter
	 * 
	 * 
	 * @return String containing JSON array of messages
	 */
	@RequestMapping(method = RequestMethod.GET, path="/liquid/filter", produces="application/json")
	@ApiOperation(value = "Filter Messages", notes = "Method is used to retrieve messages from the store based on a filter")
	public String filter(@RequestParam(value="page", required=true, name="page") int page, 
						@RequestParam(value="size", required=true, name="size") int size, 
						@RequestParam(value="location", required=false, name="location") String location, 
						@RequestParam(value="beforetime", required=false, name="beforetime") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime beforetime, 
						@RequestParam(value="correlationid", required=false, name="correlationid") String correlationID,
						@RequestParam(value="parentid", required=false, name="parentid") String parentID,						  
						@RequestParam(value="messageorder", required=false, name="messageorder") Integer messageOrder,
						@RequestParam(value="aftertime", required=false, name="aftertime") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime aftertime){
		
		PageRequest pg = new PageRequest(page, size);		
		
		Page<Message> messagePage = messageRepository.findAll(createPredicate(location, correlationID, beforetime, aftertime, messageOrder, parentID), pg);
		return gson.toJson(messagePage.getContent());
	}		
	
	

	public Predicate createPredicate(String location, String correlationID, LocalDateTime beforetime, LocalDateTime aftertime, Integer messageOrder, String parentID) {
	    QMessage qMsg = QMessage.message;
	    BooleanBuilder booleanBuilder = new BooleanBuilder();
	    
	    if (correlationID!=null) {
	      booleanBuilder.and(qMsg.correlationID.eq(correlationID));
	    }
	    
	    if (location!=null) {
	      booleanBuilder.and(qMsg.location.contains(location));
	    }
	    
	    if (beforetime!=null) {	    	
	    	ZonedDateTime zdt = beforetime.atZone(ZoneId.systemDefault());
	    	Date beforeDate = Date.from(zdt.toInstant());
	    		    	
		    booleanBuilder.and(qMsg.snapshotTime.before(beforeDate));
		}
	    
	    if (aftertime!=null) {	    	
	    	ZonedDateTime zdt = aftertime.atZone(ZoneId.systemDefault());
	    	Date afterDate = Date.from(zdt.toInstant());
	    		    	
		    booleanBuilder.and(qMsg.snapshotTime.after(afterDate));
		}
	    
	    if (messageOrder!=null) {
	    	booleanBuilder.and(qMsg.order.eq(messageOrder));
	    }
	    
	    if (parentID!=null) {
		      booleanBuilder.and(qMsg.parentID.eq(parentID));
		    }
	    
	    return booleanBuilder.getValue();
	  }

	

	public MessageRepository getMessageRepository() {
		return messageRepository;
	}

	public void setMessageRepository(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	
	
	
	
}
