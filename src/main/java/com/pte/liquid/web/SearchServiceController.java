package com.pte.liquid.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pte.liquid.search.SearchBean;

@RestController
public class SearchServiceController {

	@Autowired
	private SearchBean liquidSearchBean;
	
	private GsonBuilder gsonBuilder = new GsonBuilder();
	private Gson gson;
	
	public SearchServiceController(){		
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		gsonBuilder.setDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
		gson = gsonBuilder.create();
	}
	
	@RequestMapping("/liquid/search")
	public String search(@RequestParam(value="searchQuery", defaultValue="%") String searchQuery){				
		return gson.toJson(liquidSearchBean.search(searchQuery));
		
	}

	public SearchBean getLiquidSearchBean() {
		return liquidSearchBean;
	}

	public void setLiquidSearchBean(SearchBean liquidSearchBean) {
		this.liquidSearchBean = liquidSearchBean;
	}


	
	
	
	
}
