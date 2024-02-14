package zenika.entretien.spring.reactif.springkafka.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import zenika.entretien.spring.reactif.springkafka.model.Facture;

import java.util.List;
import java.util.concurrent.CountDownLatch;

@ActiveProfiles("test")
@Component
@Slf4j
public class ConsumerFacture {

    public static final String PROP_JSON_VALUE_DESERIALIZER = ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG
            + "=org.springframework.kafka.support.serializer.JsonDeserializer";

    public static final String PROP_JSON_DEFAULT_CLASS_DESERIALIZER = JsonDeserializer.VALUE_DEFAULT_TYPE
            + "=zenika.entretien.spring.reactif.springkafka.model.Facture";

    public CountDownLatch getLatch() {
        return latch;
    }

    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public Facture getPayload() {
        return payload;
    }

    public void setPayload(Facture payload) {
        this.payload = payload;
    }

    public CountDownLatch latch;


    public Facture payload = null;

    @KafkaListener(topics = "test-facture", clientIdPrefix = "json",
            properties = {PROP_JSON_DEFAULT_CLASS_DESERIALIZER,
                    PROP_JSON_VALUE_DESERIALIZER
                    , "JsonDeserializer.USE_TYPE_INFO_HEADERS=true"
            }
    )
    public void receive(List<ConsumerRecord<?, ?>> consumerRecords) {
        //log.info("Facture recuperée");
        assert (consumerRecords.size() == 1);
        if (consumerRecords.get(0).value() instanceof Facture) {
            payload = (Facture) consumerRecords.get(0).value();
        }

        //log.info("Erreur recuperee {}", payload);
        if (latch != null) {
            latch.countDown();
        }
    }
}
