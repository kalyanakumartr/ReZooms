package org.hbs.event.service;

import org.hbs.rezoom.security.resource.IPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.google.gson.Gson;

// @Service
public class SenderProducer implements IPath
{
	private static final long				serialVersionUID	= 1643040639961382216L;
	private static final Logger				logger				= LoggerFactory.getLogger(SenderProducer.class);

	// @Autowired
	private KafkaTemplate<String, String>	kafkaTemplate;

	public void sendEmailMessage(ETemplate eTemplate, Object object)
	{
		logger.info("Send Email Message ::: " + eTemplate.name());
		this.kafkaTemplate.send(INTERNAL_TOPIC, EMedia.Email.ordinal(), eTemplate.name(), new Gson().toJson(object));
	}

}
