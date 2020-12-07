package fr.enedis.consumerkafka;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;
import org.apache.http.ConnectionClosedException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Service
public class KafkaConsumerService {

	@Autowired
	private RestHighLevelClient client;

	public String save(User user) throws Exception {

		IndexRequest indexRequest = new IndexRequest("user")
			 .id(user.getIdUser().toString())
			 .source(user, XContentType.JSON);
			IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
			return response.getId();

	}

	@Transactional
	@KafkaListener(topics = "user_topic", groupId = "group_id")
    public void consumeString(@RequestBody User user, Acknowledgment acknowledgment) {
        System.out.println("Consumed user : "+user);
		try {
			save(user);
			acknowledgment.acknowledge();
		}catch (ConnectException | ConnectionClosedException e) {
			e.printStackTrace();
			acknowledgment.nack(TimeUnit.SECONDS.toMillis(10));
		}catch (java.lang.Exception e) {
			e.printStackTrace();
			storeMessageToDeadLitteredTopic(user);
			acknowledgment.acknowledge();
		}
    }

    private void storeMessageToDeadLitteredTopic(User user) {
		 final String baseUrl = "http://localhost:8080/kafka/send_to_malformed";
		 RestTemplate restTemplate = new RestTemplate();
		 restTemplate.postForObject( baseUrl, user, String.class);
	 }

}
