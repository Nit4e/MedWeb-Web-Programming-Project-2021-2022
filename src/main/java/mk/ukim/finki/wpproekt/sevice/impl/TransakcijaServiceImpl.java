package mk.ukim.finki.wpproekt.sevice.impl;

import mk.ukim.finki.wpproekt.model.Transakcija;
import mk.ukim.finki.wpproekt.repository.TransakcijaRepository;
import mk.ukim.finki.wpproekt.sevice.TransakcijaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransakcijaServiceImpl implements TransakcijaService {
    private final TransakcijaRepository transakcijaRepository;

    public TransakcijaServiceImpl(TransakcijaRepository transakcijaRepository) {
        this.transakcijaRepository = transakcijaRepository;
    }

    @Override
    public void save(Transakcija transakcija) {

        this.transakcijaRepository.save(transakcija);
    }

    @Override
    public List<Transakcija> findAll() {
        return this.transakcijaRepository.findAll();
    }
}
