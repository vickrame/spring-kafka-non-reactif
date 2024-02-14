package zenika.entretien.spring.reactif.springkafka.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import zenika.entretien.spring.reactif.springkafka.model.Facture;

import java.util.Calendar;
import java.util.UUID;

@Service
public class ProductionService implements
        ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    private KafkaTemplate<String, Facture> producer;


    public void sendMessage(Facture facture){
        String id = UUID.randomUUID().toString();
        producer.send(new ProducerRecord<>("test-facture",id,facture));
    }

    public void sendMessage(){
        String id = UUID.randomUUID().toString();
        producer.send(new ProducerRecord<>("test-facture",id,Facture.builder().id(id).sens("C").date(Calendar.getInstance().getTime()).build()));
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

    }
}
