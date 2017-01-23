package com.falcon.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.falcon.messenger.database.DatabaseClass;
import com.falcon.messenger.exception.DataNotFoundException;
import com.falcon.messenger.model.Message;

public class MessageService {
	
	Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		messages.put(1l, new Message(1, "Hi there!", "Priten"));
		messages.put(2l, new Message(2l, "Hello World!", "Pikachu"));
	}

	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year) {
		List<Message> returnMessages = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for(Message message : messages.values()) {
			cal.setTime(message.getCreatedDate());
			if(cal.get(Calendar.YEAR) == year)
				returnMessages.add(message);
		}
		return returnMessages;
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size) {
		return ((start + size) > messages.size()) ? null : new ArrayList<Message>(messages.values()).subList(start, size);
	}
	
	public Message getMessage(long id) {
		Message message = messages.get(id);
		if(message == null) { 
			throw new DataNotFoundException("Message with message id "+ id +" does not exists.");
		}
		return message;
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		return (message.getId() <= 0) ? null : messages.put(message.getId(), message);
	}
	
	public Message removeMessage(long id) {
		return messages.remove(id);
	}
}
