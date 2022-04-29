package mk.ukim.finki.wpproekt.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
public class Lekovi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lek_id;
    private String ime_lek;
    private String genericko_ime;

    @OneToMany(mappedBy = "lekovi")
    private List<PrepisaniLekovi> prepisaniLekovi;

    public Lekovi() {
    }

    public Lekovi(String ime_lek, String genericko_ime) {
        this.ime_lek = ime_lek;
        this.genericko_ime = genericko_ime;
    }
}
