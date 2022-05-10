package mk.ukim.finki.wpproekt.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;


@Data
@Entity
@IdClass(TelefonskiBroeviId.class)
public class TelefonskiBroevi {
    @Id
    private Long covek_broj_id;

    @Id
    private String telefonski_broj;

    public TelefonskiBroevi() {
    }

}
