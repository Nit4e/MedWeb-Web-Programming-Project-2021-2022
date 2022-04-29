package mk.ukim.finki.wpproekt.repository;

import mk.ukim.finki.wpproekt.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {
    Optional<Korisnik> findByUsernameAndPassword(String username, String password);
    Optional<Korisnik> findByUsername(String username);
    @Query("select k from Korisnik k where k.role <> 'ROLE_ADMIN' and k.username not like :doktor_username")
    List<Korisnik> findByNotLoggedInUsernameAndRoleNotAdmin(String doktor_username);
}
