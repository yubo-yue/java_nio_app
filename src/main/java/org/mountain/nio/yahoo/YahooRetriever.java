package org.mountain.nio.yahoo;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YahooRetriever {
	private static Logger LOGGER = LoggerFactory
			.getLogger(YahooRetriever.class);

	public InputStream retrieve(String zipCode) throws Exception {
		LOGGER.info("Retrieving Weather Data");
		String url = "http://weather.yahooapis.com/forecastrss?p=" + zipCode;
		URLConnection conn = new URL(url).openConnection();
		return conn.getInputStream();
	}
}
