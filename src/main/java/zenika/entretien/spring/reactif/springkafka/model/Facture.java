package zenika.entretien.spring.reactif.springkafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Facture {

    private String id;
    private String sens; // 'D' ou 'C'
    private Double montant;
    private Date date;
}
