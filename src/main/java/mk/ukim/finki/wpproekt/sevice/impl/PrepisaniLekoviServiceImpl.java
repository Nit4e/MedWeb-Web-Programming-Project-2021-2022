package mk.ukim.finki.wpproekt.sevice.impl;

import mk.ukim.finki.wpproekt.model.Korisnik;
import mk.ukim.finki.wpproekt.model.Lekovi;
import mk.ukim.finki.wpproekt.model.PrepisaniLekovi;
import mk.ukim.finki.wpproekt.repository.KorisnikRepository;
import mk.ukim.finki.wpproekt.repository.LekoviRepository;
import mk.ukim.finki.wpproekt.repository.PrepisaniLekoviRepository;
import mk.ukim.finki.wpproekt.repository.PrepisaniLekoviRepositoryCustom;
import mk.ukim.finki.wpproekt.sevice.PrepisaniLekoviService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PrepisaniLekoviServiceImpl implements PrepisaniLekoviService {

    @Qualifier("main1")
    private final PrepisaniLekoviRepository prepisaniLekoviRepository;
    @Qualifier("custom1")
    private final PrepisaniLekoviRepositoryCustom prepisaniLekoviRepositoryCustom;
    private final LekoviRepository lekoviRepository;
    private final KorisnikRepository korisnikRepository;

    public PrepisaniLekoviServiceImpl(PrepisaniLekoviRepository prepisaniLekoviRepository,
                                      PrepisaniLekoviRepositoryCustom prepisaniLekoviRepositoryCustom,
                                      LekoviRepository lekoviRepository,
                                      KorisnikRepository korisnikRepository) {

        this.prepisaniLekoviRepository = prepisaniLekoviRepository;
        this.prepisaniLekoviRepositoryCustom = prepisaniLekoviRepositoryCustom;
        this.lekoviRepository = lekoviRepository;
        this.korisnikRepository = korisnikRepository;
    }

    @Override
    public void save(PrepisaniLekovi prepisaniLekovi) {

        this.prepisaniLekoviRepository.save(prepisaniLekovi);
    }

    @Override
    public PrepisaniLekovi save(String doktor_username, Long lek_id, Long pacient_id) {
        Korisnik korisnik = this.korisnikRepository.findByUsername(doktor_username).get();
        Lekovi lek = this.lekoviRepository.findById(lek_id).get();
        Korisnik pacient = this.korisnikRepository.findById(pacient_id).get();
        Long pacientId = pacient.getCovek_id();
        PrepisaniLekovi prepisaniLekovi = new PrepisaniLekovi();
        prepisaniLekovi.setCovek_id(korisnik.getCovek_id());
        prepisaniLekovi.setLek_id(lek.getLek_id());
        //prepisaniLekovi.setPacient_id(pacientId);
        return this.prepisaniLekoviRepository.save(prepisaniLekovi);
    }

    @Override
    public List<Lekovi> findLekoviZaPacient() {
        return this.prepisaniLekoviRepositoryCustom.findPacientLekovi();
    }

    @Override
    public List<PrepisaniLekovi> findAll() {
        return this.prepisaniLekoviRepository.findAll();
    }

    @Override
    public PrepisaniLekovi save(String doktor_username, Long lek_id, String pacient_username) {
        Korisnik korisnik = this.korisnikRepository.findByUsername(doktor_username).get();
        Lekovi lek = this.lekoviRepository.findById(lek_id).get();
        Korisnik pacient = this.korisnikRepository.findByUsername(pacient_username).get();
        String pacientId = pacient.getUsername();
        PrepisaniLekovi prepisaniLekovi = new PrepisaniLekovi();
        prepisaniLekovi.setCovek_id(korisnik.getCovek_id());
        prepisaniLekovi.setLek_id(lek.getLek_id());
        prepisaniLekovi.setPacient_username(pacientId);
        return this.prepisaniLekoviRepository.save(prepisaniLekovi);
    }

}
