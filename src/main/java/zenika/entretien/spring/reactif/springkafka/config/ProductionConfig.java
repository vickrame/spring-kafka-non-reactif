package zenika.entretien.spring.reactif.springkafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import zenika.entretien.spring.reactif.springkafka.model.Facture;

import java.util.Map;

@Configuration
public class ProductionConfig {


    @Autowired
    private KafkaProperties properties;


    @Bean
    public ProducerFactory<String, Facture> producerFactory() {
        Map<String, Object> configProps = properties.buildProducerProperties(null);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.springframework.kafka.support.serializer.JsonSerializer");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Facture> producer() {
        return new KafkaTemplate<>(producerFactory());
    }
}
