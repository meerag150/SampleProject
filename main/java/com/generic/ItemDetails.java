/**
 * 
 */
package com.generic;

import java.util.List;

/**
 *  
 *
 */
public class ItemDetails {
	
	private String itemId;
	private String itemName;
	private String releaseStatus;
	public String getReleaseStatus() {
		return releaseStatus;
	}
	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}
	private List<String> itemRevision;
	
	public List<String> getItemRevision() {
		return itemRevision;
	}
	public void setItemRevision(List<String> itemRevision) {
		this.itemRevision = itemRevision;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	
	
	

}
