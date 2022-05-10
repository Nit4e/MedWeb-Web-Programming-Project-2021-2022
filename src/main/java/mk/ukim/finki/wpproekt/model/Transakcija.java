package mk.ukim.finki.wpproekt.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Transakcija {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tran_id;
    private Integer suma;
    private String smetka_bolnica;

    @OneToOne
    @JoinColumn(name = "rezervacija_id", referencedColumnName = "rezervacija_id", nullable = true)
    private Rezervacija rezervacija;

    public Transakcija() {
    }

    public Transakcija(Integer suma, String smetka_bolnica, Rezervacija rezervacija) {
        this.suma = suma;
        this.smetka_bolnica = smetka_bolnica;
        this.rezervacija = rezervacija;
    }
}
