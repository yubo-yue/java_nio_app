package org.mountain.nio.yahoo;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeatherFormatter {
	private static Logger LOGGER = LoggerFactory
			.getLogger(WeatherFormatter.class);

	public String format(Weather weather) throws Exception {
		LOGGER.info("Formatting Weather Data");
		Reader reader = new InputStreamReader(getClass().getClassLoader()
				.getResourceAsStream("output.vm"));
		VelocityContext context = new VelocityContext();
		context.put("weather", weather);
		StringWriter writer = new StringWriter();
		Velocity.evaluate(context, writer, "", reader);
		return writer.toString();
	}

}
