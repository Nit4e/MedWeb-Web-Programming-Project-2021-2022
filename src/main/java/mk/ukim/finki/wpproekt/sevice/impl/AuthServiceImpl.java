package mk.ukim.finki.wpproekt.sevice.impl;

import mk.ukim.finki.wpproekt.model.Korisnik;
import mk.ukim.finki.wpproekt.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wpproekt.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wpproekt.repository.KorisnikRepository;
import mk.ukim.finki.wpproekt.sevice.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final KorisnikRepository korisnikRepository;

    public AuthServiceImpl(KorisnikRepository korisnikRepository) {

        this.korisnikRepository = korisnikRepository;
    }

    @Override
    public Korisnik login (String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return korisnikRepository.findByUsernameAndPassword(username, password)
                .orElseThrow(InvalidUserCredentialsException::new);
    }
}
