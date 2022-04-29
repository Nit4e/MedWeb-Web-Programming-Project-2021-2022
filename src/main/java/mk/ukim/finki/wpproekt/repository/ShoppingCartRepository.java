package mk.ukim.finki.wpproekt.repository;

import mk.ukim.finki.wpproekt.model.Korisnik;
import mk.ukim.finki.wpproekt.model.ShoppingCart;
import mk.ukim.finki.wpproekt.model.enumerations.ShoppingCartStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {

    Optional<ShoppingCart> findByKorisnikAndStatus (Korisnik korisnik, ShoppingCartStatus status);
}
