package com.pte.liquid.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pte.liquid.relay.model.Messages;



public class MessagesSearchResult {

	@Expose
	@SerializedName("SearchResults")
	private Messages messages;
	
	@Expose
	@SerializedName("TotalResults")
	private long totalResults;
	
	@Expose
	@SerializedName("ReturnedAmount")
	private long returnedAmount;
	
	@Expose
	@SerializedName("StartIndex")
	private long startIndex; 
	
	@Expose
	@SerializedName("MaxSize")
	private long maxSize;
	
	@Expose
	@SerializedName("CurrentPage")
	private long currentPage; 
	
	public Messages getMessages() {
		return messages;
	}
	public void setMessages(Messages messages) {
		this.messages = messages;
	}
	public long getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(long totalResults) {
		this.totalResults = totalResults;
	}
	public long getReturnedAmount() {
		return returnedAmount;
	}
	public void setReturnedAmount(long returnedAmount) {
		this.returnedAmount = returnedAmount;
	}
	public long getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(long startIndex) {
		this.startIndex = startIndex;
	}
	public long getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}
	public long getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	
	

}
