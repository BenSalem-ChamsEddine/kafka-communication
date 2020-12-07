package fr.enedis.producerkafka;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("kafka")
public class KafkaProducerController {

	private final KafkaProducerService producer;

	private static final String USER_TOPIC = "user_topic";
	private static final String MALFORMED_MESSAGES_TOPIC = "malformed_messages_topic";

	@Autowired
	public KafkaProducerController(KafkaProducerService producer) {
		this.producer = producer;
	}

	@GetMapping(value = "/publish")
	public void sendMessageToKafkaTopic() {
		Faker faker = new Faker();
		this.producer.sendMessage(new User(faker.number().randomDigit(), faker.name().firstName(), faker.name().lastName()), USER_TOPIC);
	}

	@GetMapping(value = "/publish_malformed")
	public void sendMalformedMessageToKafkaTopic() {
		Faker faker = new Faker();
		this.producer.sendMessage(new User(null, faker.name().firstName(), faker.name().lastName()), USER_TOPIC);
	}

	@PostMapping(value = "/send_to_malformed", consumes = "application/json")
	public void sendToDeadLitteredTopic(@RequestBody User user) {
		this.producer.sendMessage(user, MALFORMED_MESSAGES_TOPIC);
	}

}
