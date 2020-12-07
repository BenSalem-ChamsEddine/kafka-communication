package fr.enedis.consumerkafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

public class UserDeserializer  implements Deserializer {


	@Override
	public void configure(Map configs, boolean isKey) {

	}



	@Override
	public User deserialize(String s, byte[] bytes) {
		ObjectMapper objectMapper = new ObjectMapper();
		User user = null;
		try {
			user = objectMapper.readValue(bytes,User.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public Object deserialize(String topic, Headers headers, byte[] data) {
		ObjectMapper objectMapper = new ObjectMapper();
		User user = null;
		try {
			user = objectMapper.readValue(data,User.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public void close() {

	}
}
