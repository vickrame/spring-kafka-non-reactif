package zenika.entretien.spring.reactif.springkafka.service;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit4.SpringRunner;
import zenika.entretien.spring.reactif.springkafka.JeuDonnees;
import zenika.entretien.spring.reactif.springkafka.model.Facture;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.apache.kafka.test.TestUtils.consumerConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@EmbeddedKafka(
        partitions = 1,
        brokerProperties = {"transaction.state.log.replication.factor=1", "transaction.state.log.min.isr=1"},
        controlledShutdown = true
        , topics = {"topic-facture"}
)
@SpringBootTest(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"})
public class ProductionServiceTest {

    @Autowired
    private ProductionService service;
    @Autowired
    private ConsumerFacture consumerFacture;

    @Test
    public void testEnvoie() throws InterruptedException {
        Facture f = JeuDonnees.buidFacture();
        var latch = new CountDownLatch(1);
        consumerFacture.latch = latch;
        service.sendMessage(f);
        assertTrue(latch.await(30, TimeUnit.SECONDS));

        // test entree et de sortie
        assertThat(consumerFacture.payload.getId()).isEqualTo(f.getId());
    }
}