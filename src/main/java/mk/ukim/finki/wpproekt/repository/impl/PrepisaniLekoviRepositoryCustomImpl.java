package mk.ukim.finki.wpproekt.repository.impl;

import mk.ukim.finki.wpproekt.model.Lekovi;
import mk.ukim.finki.wpproekt.model.PrepisaniLekovi;
import mk.ukim.finki.wpproekt.repository.PrepisaniLekoviRepositoryCustom;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Primary
@Component("custom1")
public class PrepisaniLekoviRepositoryCustomImpl implements PrepisaniLekoviRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Lekovi> findPacientLekovi() {
        return entityManager.createQuery("select distinct p.lekovi from Korisnik k join PrepisaniLekovi p on k.username = p.pacient_username",
                Lekovi.class).getResultList();
    }

    @Override
    public Optional<PrepisaniLekovi> findById(Integer lek_id) {
        return Optional.ofNullable(entityManager.createQuery("select p from PrepisaniLekovi p where p.lek_id = :lek_id",
                PrepisaniLekovi.class).setParameter("lek_id", lek_id).getSingleResult());
    }

}
