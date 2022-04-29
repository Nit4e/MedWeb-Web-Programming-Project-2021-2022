package mk.ukim.finki.wpproekt.sevice;

import mk.ukim.finki.wpproekt.model.Korisnik;

public interface AuthService {

    Korisnik login (String username, String password);
}
