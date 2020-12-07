package fr.enedis.producerkafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

	private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);


	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void sendMessage(User user, String topic) {
		logger.info(String.format("$$ -> Producing user --> %s  / in topic --> %s", user.toString(), topic));
		this.kafkaTemplate.send(topic, user);
		logger.info(String.format("$$ -> Producing user --> %s  / in topic --> %s", user.toString(), topic));
	}

}
