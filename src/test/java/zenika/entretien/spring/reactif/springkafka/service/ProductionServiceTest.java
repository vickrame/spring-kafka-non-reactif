package zenika.entretien.spring.reactif.springkafka.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;
import zenika.entretien.spring.reactif.springkafka.JeuDonnees;
import zenika.entretien.spring.reactif.springkafka.model.Facture;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        consumerFacture.setLatch(latch);
        service.sendMessage(f);
        assertTrue(latch.await(30, TimeUnit.SECONDS));

        // test entree et de sortie
        assertThat(consumerFacture.getPayload().getId()).isEqualTo(f.getId());
    }
}