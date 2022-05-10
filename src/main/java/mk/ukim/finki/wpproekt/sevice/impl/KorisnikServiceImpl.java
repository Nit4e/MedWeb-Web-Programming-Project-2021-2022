package mk.ukim.finki.wpproekt.sevice.impl;

import mk.ukim.finki.wpproekt.model.Korisnik;
import mk.ukim.finki.wpproekt.model.Oddel;
import mk.ukim.finki.wpproekt.model.Specijalnost;
import mk.ukim.finki.wpproekt.model.enumerations.Role;
import mk.ukim.finki.wpproekt.model.exceptions.InvalidUsernameOrPasswordException;
import mk.ukim.finki.wpproekt.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.wpproekt.model.exceptions.UsernameAlreadyExistsException;
import mk.ukim.finki.wpproekt.repository.KorisnikRepository;
import mk.ukim.finki.wpproekt.sevice.KorisnikService;
import mk.ukim.finki.wpproekt.sevice.OddelService;
import mk.ukim.finki.wpproekt.sevice.SpecijalnostService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class KorisnikServiceImpl implements KorisnikService {

    private final KorisnikRepository korisnikRepository;
    private final PasswordEncoder passwordEncoder;
    private final SpecijalnostService specijalnostService;
    private final OddelService oddelService;

    public KorisnikServiceImpl(KorisnikRepository korisnikRepository, PasswordEncoder passwordEncoder,
                               SpecijalnostService specijalnostService, OddelService oddelService) {
        this.korisnikRepository = korisnikRepository;
        this.passwordEncoder = passwordEncoder;
        this.specijalnostService = specijalnostService;
        this.oddelService = oddelService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return this.korisnikRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public Korisnik register(String username, String password, String repeatPassword,
                             String embg, String ime, String prezime, Role role, Long specijalnostId,
                             Long oddelId) {
        if (username==null || username.isEmpty()  || password==null || password.isEmpty())
            throw new InvalidUsernameOrPasswordException();
        if (!password.equals(repeatPassword))
            throw new PasswordsDoNotMatchException();
        if(this.korisnikRepository.findByUsername(username).isPresent())
            throw new UsernameAlreadyExistsException(username);
        Specijalnost specijalnost = new Specijalnost();
        Oddel oddel = new Oddel();

        Role role1 = role;
        if ((role1.toString().equals("ROLE_PACIENT")) || (role1.toString().equals("ROLE_ADMIN"))){
            specijalnost = null;
            oddel = null;
            if (role1.toString().equals("ROLE_ADMIN")){
                embg = null;
            }
        }
        else {
            specijalnost = this.specijalnostService.findById(specijalnostId).get();
            oddel = this.oddelService.findByOddelId(oddelId).get();
        }
        Korisnik korisnik = new Korisnik(username, passwordEncoder.encode(password),
                embg, ime, prezime, role, specijalnost, oddel);
        return korisnikRepository.save(korisnik);
    }

    @Override
    public Optional<Korisnik> findByUsername (String username) {

        return this.korisnikRepository.findByUsername(username);
    }

    @Override
    public List<Korisnik> findAll() {
        return this.korisnikRepository.findAll();
    }

    @Override
    public Optional<Korisnik> findById(Long id) {
        return this.korisnikRepository.findById(id);
    }

    @Override
    public List<Korisnik> findByNotLoggedInUsernameAndRoleNotAdmin(String doktor_username) {
        return this.korisnikRepository.findByNotLoggedInUsernameAndRoleNotAdmin(doktor_username);
    }
}
