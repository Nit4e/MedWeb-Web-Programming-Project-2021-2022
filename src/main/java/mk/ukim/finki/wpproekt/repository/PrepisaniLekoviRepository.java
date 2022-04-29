package mk.ukim.finki.wpproekt.repository;

import mk.ukim.finki.wpproekt.model.PrepisaniLekovi;
import mk.ukim.finki.wpproekt.model.PrepisaniLekoviId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
@Component("main1")
public interface PrepisaniLekoviRepository extends JpaRepository<PrepisaniLekovi, PrepisaniLekoviId>,
        PrepisaniLekoviRepositoryCustom {
}
