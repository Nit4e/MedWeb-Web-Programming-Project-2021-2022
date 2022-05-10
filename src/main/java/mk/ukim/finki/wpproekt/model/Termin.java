package mk.ukim.finki.wpproekt.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;


@Data
@Entity
@IdClass(TerminId.class)
@Table(name = "termin")
public class Termin implements Serializable {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "termin_id", insertable = false, updatable = false)
    private Long termin_id;

    @Id
    private Long covek_id;

    @Id
    @ManyToOne
    @JoinColumn(name = "covek_id", referencedColumnName = "covek_id", insertable = false, updatable = false)
    private Korisnik korisnik;

    private ZonedDateTime vreme;

    @OneToOne(mappedBy = "termin")
    private Rezervacija rezervacija;

    public Termin() {
    }

    public Termin(ZonedDateTime vreme) {

        this.vreme = vreme;
    }

}
