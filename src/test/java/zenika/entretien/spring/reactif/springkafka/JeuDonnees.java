package zenika.entretien.spring.reactif.springkafka;

import zenika.entretien.spring.reactif.springkafka.model.Facture;

import java.util.Calendar;
import java.util.UUID;

public class JeuDonnees {

    public static Facture buidFacture(){
        return Facture.builder().id(UUID.randomUUID().toString()).sens("D").montant(23.30).date(Calendar.getInstance().getTime()).build();
    }
}
