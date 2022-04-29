package mk.ukim.finki.wpproekt.model;


import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@IdClass(PrepisaniLekoviId.class)
@Table(name = "prepishani_lekovi")
public class PrepisaniLekovi {

    @Id
    private Integer covek_id;
    @Id
    private Integer lek_id;
    @Id
    private String pacient_username;

    @Id
    @ManyToOne
    @JoinColumn(name = "covek_id", referencedColumnName = "covek_id", insertable = false, updatable = false)
    private Korisnik korisnik;

    @Id
    @ManyToOne
    @JoinColumn(name = "lek_id", referencedColumnName = "lek_id", insertable = false, updatable = false)
    private Lekovi lekovi;

    public PrepisaniLekovi() {

    }

    public PrepisaniLekovi(Korisnik korisnik, Lekovi lekovi, String pacient_username) {
        this.korisnik = korisnik;
        this.lekovi = lekovi;
        this.pacient_username = pacient_username;
    }

}
