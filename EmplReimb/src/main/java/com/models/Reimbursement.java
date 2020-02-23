package com.models;

public class Reimbursement {

	private int reimbID;
	private int amount;
	private String submitted;
	private String resolved;
	private String description;
	private String receipt;
	private int author;
 	private int resolver;
	private int statusID;
	private int typeID;
	
	
	// getters and setters ----------------------------
	public int getReimbID() {
		return reimbID;
	}
	
	public void setReimbID(int reimbID) {
		this.reimbID = reimbID;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public String getSubmitted() {
		return submitted;
	}
	
	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}
	
	public String getResolved() {
		return resolved;
	}
	
	public void setResolved(String resolved) {
		this.resolved = resolved;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getReceipt() {
		return receipt;
	}
	
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	
	public int getAuthor() {
		return author;
	}
	
	public void setAuthor(int author) {
		this.author = author;
	}
	
	public int getResolver() {
		return resolver;
	}
	
	public void setResolver(int resolver) {
		this.resolver = resolver;
	}
	
	public int getStatusID() {
		return statusID;
	}
	
	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}
	
	public int getTypeID() {
		return typeID;
	}
	
	public void setStatusType(int typeID) {
		this.typeID = typeID;
	}
	
	
	// constructors ----------------------------

	public Reimbursement(int reimbID, int amount, String submitted, String resolved, String description, String receipt,
			int author, int resolver, int statusID, int typeID) {
		super();
		this.reimbID = reimbID;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.resolver = resolver;
		this.statusID = statusID;
		this.typeID = typeID;
	}
	
	public Reimbursement() {
		super();
	}
			
	
	// hashcode/equals/ toString----------------------------
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + author;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((receipt == null) ? 0 : receipt.hashCode());
		result = prime * result + reimbID;
		result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
		result = prime * result + resolver;
		result = prime * result + statusID;
		result = prime * result + typeID;
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		if (amount != other.amount)
			return false;
		if (author != other.author)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (receipt == null) {
			if (other.receipt != null)
				return false;
		} else if (!receipt.equals(other.receipt))
			return false;
		if (reimbID != other.reimbID)
			return false;
		if (resolved == null) {
			if (other.resolved != null)
				return false;
		} else if (!resolved.equals(other.resolved))
			return false;
		if (resolver != other.resolver)
			return false;
		if (statusID != other.statusID)
			return false;
		if (typeID != other.typeID)
			return false;
		if (submitted == null) {
			if (other.submitted != null)
				return false;
		} else if (!submitted.equals(other.submitted))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Reimbursement [reimbID=" + reimbID + ", amount=" + amount + ", submitted=" + submitted + ", resolved="
				+ resolved + ", description=" + description + ", receipt=" + receipt + ", author=" + author
				+ ", resolver=" + resolver + ", statusID=" + statusID + ", typeID=" + typeID + "]";
	}
	
}
