package mk.ukim.finki.wpproekt.sevice.impl;

import mk.ukim.finki.wpproekt.model.Lekovi;
import mk.ukim.finki.wpproekt.model.exceptions.LekAlreadyExistsException;
import mk.ukim.finki.wpproekt.repository.LekoviRepository;
import mk.ukim.finki.wpproekt.sevice.LekoviService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class LekoviServiceImpl implements LekoviService {

    private final LekoviRepository lekoviRepository;

    public LekoviServiceImpl(LekoviRepository lekoviRepository) {

        this.lekoviRepository = lekoviRepository;
    }

    @Override
    public List<Lekovi> listAll() {
        return this.lekoviRepository.findAll();
    }

    @Override
    public Optional<Lekovi> findById(Long lekId) {
        return this.lekoviRepository.findById(lekId);
    }

    @Override
    public Lekovi save(String imeLek, String generickoIme) {
        List<Lekovi> lekoviList = this.lekoviRepository.findAll();
        for (int i = 0; i < lekoviList.size(); i++) {
            if (lekoviList.get(i).getIme_lek().equals(imeLek)
                    && lekoviList.get(i).getGenericko_ime().equals(generickoIme)) {
                throw new LekAlreadyExistsException(imeLek, generickoIme);
            }
        }
        Lekovi lekovi = new Lekovi(imeLek, generickoIme);
        return lekoviRepository.save(lekovi);
    }
}
