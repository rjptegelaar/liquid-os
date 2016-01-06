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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pte.liquid.relay.model.Message;
import com.pte.liquid.repo.MessageRepository;
import com.pte.liquid.search.SearchBean;

@RestController
public class MessageServiceController {

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private SearchBean liquidSearchBean;
	
	private GsonBuilder gsonBuilder = new GsonBuilder();
	private Gson gson;
	
	public MessageServiceController(){		
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
		gson = gsonBuilder.create();
	}

	
	@RequestMapping("/liquid/list")
	public String list(){
		PageRequest pg = new PageRequest(1, 10);
		Page<Message> messagePage = messageRepository.findAll(pg);		
		return gson.toJson(messagePage.getContent());
	}	
	

	public SearchBean getLiquidSearchBean() {
		return liquidSearchBean;
	}


	public void setLiquidSearchBean(SearchBean liquidSearchBean) {
		this.liquidSearchBean = liquidSearchBean;
	}


	public MessageRepository getMessageRepository() {
		return messageRepository;
	}

	public void setMessageRepository(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	
	
	
	
}
