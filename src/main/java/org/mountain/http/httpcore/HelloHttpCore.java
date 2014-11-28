package org.mountain.http.httpcore;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloHttpCore {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(HelloHttpCore.class);

	public static void main(String[] args) {
		printBasicHttpRequest();
		printBasicHttpResponse();
		printBasicEntity();
	}
	
	public static void printBasicHttpRequest()
	{
		HttpRequest request = new BasicHttpRequest("GET", "/",
				HttpVersion.HTTP_1_1);
		
		LOGGER.info(request.getRequestLine().getMethod());
		LOGGER.info(request.getRequestLine().getUri());
		LOGGER.info(request.getProtocolVersion().toString());
		LOGGER.info(request.getRequestLine().toString());
	}
	
	public static void printBasicHttpResponse()
	{
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, "OK");
		response.addHeader("Set-Cookie", "c1=a; path=\"/\"; domain=localhost");
		response.addHeader("Set-Cookie", "c2=b; path=\"/\"; domain=localhost");
		
		Header h1 = response.getFirstHeader("Set-Cookie");
		LOGGER.info(h1.toString());
		Header h2 = response.getLastHeader("Set-Cookie");
		LOGGER.info(h2.toString());
		Header[] hs = response.getHeaders("Set-Cookie");
		LOGGER.info(hs.toString());
		
		HeaderIterator it = response.headerIterator("Set-Cookie");
		while (it.hasNext())
		{
			LOGGER.info(it.next().toString());
		}
		
		
		HeaderElementIterator eit = new BasicHeaderElementIterator(response.headerIterator("Set-Cookie"));
		while (eit.hasNext())
		{
			HeaderElement elem = eit.nextElement();
		}
	}
	
	public static void printBasicEntity()
	{
		StringEntity myEntity = new StringEntity("important message", Consts.UTF_8);
		LOGGER.info("{}", myEntity.getContentType().toString());
		LOGGER.info("{}", myEntity.getContentLength());
	}
}
