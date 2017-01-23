package com.falcon.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("injectdemo")
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.TEXT_PLAIN)
public class InjectDemoResource {

	@GET
	@Path("/annotations")
	public String doStuff(@MatrixParam("param") String param,
						   @HeaderParam("sessionAuthId") String authId,
						   @CookieParam("name") String name) {
		return "Matrix Param: "+param+"; auth ID: "+authId+"; Cookie Value: "+name;
	}
	
	@GET
	@Path("/context")
	public String doStufffUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders httpHeaders) {
		return "Full Path: "+uriInfo.getAbsolutePath().toString()
				+ "; Cookies: "+httpHeaders.getCookies().toString();
	}
}
