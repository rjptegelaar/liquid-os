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
package com.pte.liquid.search;

import java.util.Date;

import org.apache.log4j.Logger;
import org.compass.core.CompassDetachedHits;
import org.compass.core.CompassHit;
import org.compass.core.CompassQuery;
import org.compass.core.CompassQueryBuilder;
import org.compass.spring.CompassDaoSupport;

import com.pte.liquid.relay.model.Message;
import com.pte.liquid.relay.model.Messages;


public class SearchBean extends CompassDaoSupport{
	private static final Logger logger = Logger.getLogger(SearchBean.class);
	
	public void index(Message message){	
		
		getCompassTemplate().save(message);	
	}
	
	
	public Messages search(String query){
		Messages msgs = new Messages();
		if(query!=null){
			CompassDetachedHits hits = getCompassTemplate().findWithDetach(query);
			
			logger.info("Nr. of hits for query " + query +": " + hits.length());
			for (CompassHit compassHit : hits) {
				msgs.addMessage((Message) compassHit.data());
			}				
		}	
		return msgs;
	}
	
	
	public void deleteMessage(Message message){
		getCompassTemplate().delete(message);
	}
	
	public void delete(long daysInPast){
		long millisInPast = daysInPast * 86400000L;
		logger.info("Deleting messages older then " + daysInPast + " days from search index.");
		CompassQueryBuilder queryBuilder = getCompassTemplate().getCompass().queryBuilder();								 
		CompassQuery query = queryBuilder.lt("snapshotTime",new Date(new Date().getTime() - millisInPast));		
		getCompassTemplate().delete(query);		
	}
		
}
