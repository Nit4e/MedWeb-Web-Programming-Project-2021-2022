package mk.ukim.finki.wpproekt.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@IdClass(OddelId.class)
public class Oddel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long oddel_id;

    @ManyToOne
    @JoinColumn(name = "bolnica_id", referencedColumnName = "bolnica_id", nullable = false)
    @Id
    private Bolnica bolnica_id;

    private String naziv;

    @ManyToOne
    @JoinColumn(name = "spec_id", referencedColumnName = "spec_id", nullable = true)
    private Specijalnost specijalnost;

    @OneToMany(mappedBy = "oddel", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Korisnik> korisnikList;

    public Oddel() {
    }

    public Oddel(String naziv, Bolnica bolnica_id, Specijalnost specijalnost) {
        this.bolnica_id = bolnica_id;
        this.naziv = naziv;
        this.specijalnost = specijalnost;
    }

    public Oddel(String naziv, Specijalnost specijalnost, List<Korisnik> korisnikList) {
        this.naziv = naziv;
        this.specijalnost = specijalnost;
        this.korisnikList = korisnikList;
    }
}
