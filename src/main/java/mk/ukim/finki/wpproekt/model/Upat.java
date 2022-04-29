package mk.ukim.finki.wpproekt.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Upat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer upat_id;
    private String dijagnoza;

    @ManyToOne
    @JoinColumn(name = "covek_id", referencedColumnName = "covek_id", nullable = false)
    private Korisnik korisnik;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "bolnica_id", referencedColumnName = "bolnica_id", nullable = false),
            @JoinColumn(name = "oddel_id", referencedColumnName = "oddel_id", nullable = false)
    })
    private Oddel oddel;

    private String doktor;

    @OneToOne(mappedBy = "upat")
    private Rezervacija rezervacija;

    public Upat() {
    }

    public Upat(String dijagnoza) {

        this.dijagnoza = dijagnoza;
    }

    public Upat(Long id) {
    }

    public Upat(String dijagnoza, Korisnik korisnik, Oddel oddel, String doktor) {
        this.dijagnoza = dijagnoza;
        this.korisnik = korisnik;
        this.oddel = oddel;
        this.doktor = doktor;
    }
}
