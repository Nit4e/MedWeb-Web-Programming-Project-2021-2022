package mk.ukim.finki.wpproekt.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
public class Bolnica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bolnica_id;
    private String naziv;
    private String grad;
    private Integer broj;
    private String ulica;
    private String smetka_bolnica;

    public Bolnica() {
    }

    public Bolnica(String naziv, String grad, Integer broj, String ulica, String smetka_bolnica) {
        this.naziv = naziv;
        this.grad = grad;
        this.broj = broj;
        this.ulica = ulica;
        this.smetka_bolnica = smetka_bolnica;
    }
}
