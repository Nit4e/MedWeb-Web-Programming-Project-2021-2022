package mk.ukim.finki.wpproekt.model;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(BolnicaBroeviId.class)
public class BolnicaTelefonskiBroevi {
    @Id
    private Integer bolnica_id;

    @Id
    private String telefonski_br_bolnica ;

    public BolnicaTelefonskiBroevi() {
    }
}
