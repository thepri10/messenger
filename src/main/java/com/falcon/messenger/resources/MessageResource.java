package com.falcon.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.falcon.messenger.model.Link;
import com.falcon.messenger.model.Message;
import com.falcon.messenger.service.MessageService;

@Path("messages")
@Produces(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
@Consumes(value = { MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
public class MessageResource {
	
	private MessageService messageService = new MessageService();

	@GET
	public List<Message> getAllMessages(@QueryParam("year") int year, @QueryParam("start") int start, @QueryParam("size") int size) {
		return (year > 0) ? messageService.getAllMessagesForYear(year) :
			(start > 0 || size > 0) ? messageService.getAllMessagesPaginated(start, size) : messageService.getAllMessages();
	}
	
	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri)
				.entity(newMessage)
				.build();
		//return messageService.addMessage(message);
	}
	
	@GET
	@Path("/{id}")	
	public Message getMessage(@PathParam("id") long id, @Context UriInfo uriInfo) {
		Message message = messageService.getMessage(id);
		message.addLink(getUriForSelf(message, uriInfo), "self");
		message.addLink(getUriForProfile(message, uriInfo), "profile");
		message.addLink(getUriForComments(message, uriInfo), "comments");
		return message;
	}	

	@PUT	
	@Path("/{id}")
	public Message updateMessage(@PathParam("id") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{id}")
	public Message removeMessage(@PathParam("id") long id) {
		return messageService.removeMessage(id);
	}
	
	@GET
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	
	public Link createLink(String link, String rel) {
		return new Link(link, rel);
	}
	
	private String getUriForSelf(Message message, UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder()
					  .path(MessageResource.class)
					  .path(Long.toString(message.getId()))
					  .build()
					  .toString();
	}
	
	private String getUriForProfile(Message message, UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder()
					  .path(ProfileResource.class)
					  .path(message.getAuthor())
					  .build()
					  .toString();
	}
	
	private String getUriForComments(Message message, UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder()
						.path(MessageResource.class)
						.path(MessageResource.class, "getCommentResource")
						.path(CommentResource.class)
						.resolveTemplate("messageId", message.getId())
						.build()
						.toString();
	}
}
