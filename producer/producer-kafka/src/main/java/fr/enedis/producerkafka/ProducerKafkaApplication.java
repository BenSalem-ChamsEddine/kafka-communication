package fr.enedis.producerkafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerKafkaApplication {

	@Autowired
	KafkaProducerService kafkaProducerService;

	public static void main(String[] args) {
		SpringApplication.run(ProducerKafkaApplication.class, args);
	}

}
