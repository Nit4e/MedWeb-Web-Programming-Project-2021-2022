package mk.ukim.finki.wpproekt.sevice;


import mk.ukim.finki.wpproekt.model.Korisnik;
import mk.ukim.finki.wpproekt.model.enumerations.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface KorisnikService extends UserDetailsService {

    Korisnik register (String username, String password, String repeatPassword, String embg,
                       String ime, String prezime, Role role, Integer specijalnostId, Integer oddelId);

    Optional<Korisnik> findByUsername (String username);

    List<Korisnik> findAll();

    Optional<Korisnik> findById (Integer id);

    List<Korisnik> findByNotLoggedInUsernameAndRoleNotAdmin(String doktor_username);
}
