package mk.ukim.finki.wpproekt.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Rezervacija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rezervacija_id;

    @OneToOne
    @JoinColumn(name = "upat_id", referencedColumnName = "upat_id")
    private Upat upat;

    @OneToOne
    @JoinColumns({
            @JoinColumn(name = "covek_id", referencedColumnName = "covek_id"),
            @JoinColumn(name = "termin_id", referencedColumnName = "termin_id")
    })
    private Termin termin;

    @OneToOne(mappedBy = "rezervacija")
    private Transakcija transakcija;

    public Rezervacija() {
    }

    public Rezervacija(Upat upat, Termin termin) {
        this.upat = upat;
        this.termin = termin;
    }
}
