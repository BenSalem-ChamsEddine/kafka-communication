package fr.enedis.consumerkafka;

import static org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.ISOLATION_LEVEL_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG;
import static org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

   public Map<String, Object> consumerConfigs() {
      Map<String, Object> props = new HashMap<>();
      props.put(BOOTSTRAP_SERVERS_CONFIG, "EFW87YL6:9092");
      props.put(GROUP_ID_CONFIG, "group_id");
      props.put(KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      props.put(VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
      props.put(AUTO_OFFSET_RESET_CONFIG, "earliest");
      props.put(ENABLE_AUTO_COMMIT_CONFIG, false);
      props.put(ISOLATION_LEVEL_CONFIG, "read_committed");

      return props;
   }


   @Bean
   public ConsumerFactory<String, User> consumerFactory() {
      return new DefaultKafkaConsumerFactory<>(
          consumerConfigs(),
          new StringDeserializer(),
          new ErrorHandlingDeserializer<>(new UserDeserializer()));
   }

   @Bean
   public ConcurrentKafkaListenerContainerFactory<String, User> kafkaListenerContainerFactory() {
      ConcurrentKafkaListenerContainerFactory<String, User> factory =
          new ConcurrentKafkaListenerContainerFactory<>();
      factory.setConsumerFactory(consumerFactory());
      factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
      return factory;
   }
}
