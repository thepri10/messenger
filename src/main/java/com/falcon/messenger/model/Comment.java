package com.falcon.messenger.model;

import java.util.Date;

public class Comment {
	
	private long id;
	private String comment;
	private Date createdDate;
	private String author;
	
	public Comment() {
	}	
	
	public Comment(long id, String comment, String author) {
		this.id = id;
		this.comment = comment;
		this.author = author;
		this.createdDate = new Date();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	
}
