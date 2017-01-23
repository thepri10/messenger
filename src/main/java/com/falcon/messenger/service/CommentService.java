package com.falcon.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.falcon.messenger.database.DatabaseClass;
import com.falcon.messenger.model.Comment;
import com.falcon.messenger.model.ErrorMessage;
import com.falcon.messenger.model.Message;

public class CommentService {
	Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public List<Comment> getAllComments(long messageId) {
		return new ArrayList<Comment>(messages.get(messageId).getComments().values());
	}
	
	public Comment getComment(long messageId, long commentId) {
		
		ErrorMessage errMsg = new ErrorMessage("Data not found", 404, "www.google.com");
		Response response = Response.status(Status.NOT_FOUND)
									.entity(errMsg)
									.build();
		
		Message message =  messages.get(messageId);
		if(message == null)
			new WebApplicationException(response);
			
		Comment comment = message.getComments().get(commentId);
		if(comment == null)
			new NotFoundException(response);
		return comment;
	}
	
	public Comment addComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment) {
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return (comment.getId() <= 0) ? null : comments.put(comment.getId(), comment);
	}
	
	public Comment removeComment(long messageId, long commentId) {
		return messages.get(messageId).getComments().remove(commentId);
	}
}
