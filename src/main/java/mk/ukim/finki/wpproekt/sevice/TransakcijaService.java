package mk.ukim.finki.wpproekt.sevice;

import mk.ukim.finki.wpproekt.model.Transakcija;

import java.util.List;

public interface TransakcijaService {

    void save (Transakcija transakcija);
    List<Transakcija> findAll();
}
