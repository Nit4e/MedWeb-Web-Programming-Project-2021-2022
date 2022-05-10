package mk.ukim.finki.wpproekt.model;


import lombok.Data;
import mk.ukim.finki.wpproekt.model.enumerations.ShoppingCartStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCreated;

    @ManyToOne
    private Korisnik korisnik;

    @ManyToMany
    private List<Lekovi> lekoviList;

//    @ManyToMany
//    private List<PrepisaniLekovi> prepisaniLekoviList;

    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus status;

    public ShoppingCart() {
    }

    public ShoppingCart(Korisnik korisnik) {
        this.dateCreated = LocalDateTime.now();
        this.korisnik = korisnik;
        this.lekoviList = new ArrayList<>();
        this.status = ShoppingCartStatus.CREATED;
    }
}
