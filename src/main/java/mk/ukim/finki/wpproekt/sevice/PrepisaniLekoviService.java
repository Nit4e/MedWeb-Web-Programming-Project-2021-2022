package mk.ukim.finki.wpproekt.sevice;

import mk.ukim.finki.wpproekt.model.Lekovi;
import mk.ukim.finki.wpproekt.model.PrepisaniLekovi;

import java.util.List;

public interface PrepisaniLekoviService {

    void save (PrepisaniLekovi prepisaniLekovi);
    PrepisaniLekovi save (String doktor_username, Long lek_id, Long pacient_id);
    List<Lekovi> findLekoviZaPacient();
    List<PrepisaniLekovi> findAll();
    PrepisaniLekovi save (String doktor_username, Long lek_id, String pacient_username);
}
