package zenika.entretien.spring.reactif.springkafka;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@EmbeddedKafka(
        partitions = 1,
        brokerProperties = {"transaction.state.log.replication.factor=1", "transaction.state.log.min.isr=1"},
        controlledShutdown = true
        , topics = {"topic-facture"}
)
@SpringBootTest(properties = {"spring.kafka.bootstçrap-servers=${spring.embedded.kafka.brokers}"})
class SpringKafkaApplicationTests {

    @Test
    void contextLoads() {
    }

}
