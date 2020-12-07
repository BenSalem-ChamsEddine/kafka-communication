package fr.enedis.producerkafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

public class UserSerializer implements Serializer {

	@Override
	public void configure(Map configs, boolean isKey) {

	}

	@Override
	public byte[] serialize(String s, Object o) {
		byte[] retval = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			retval = objectMapper.writeValueAsString(o).getBytes();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return retval;
	}

	@Override
	public byte[] serialize(String topic, Headers headers, Object data) {
		byte[] retval = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			retval = objectMapper.writeValueAsString(data).getBytes();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return retval;
	}

	@Override
	public void close() {

	}
}
