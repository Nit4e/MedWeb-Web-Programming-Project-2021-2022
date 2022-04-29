package mk.ukim.finki.wpproekt.repository;

import mk.ukim.finki.wpproekt.model.TelefonskiBroevi;
import mk.ukim.finki.wpproekt.model.TelefonskiBroeviId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefonskiBroeviRepository extends JpaRepository<TelefonskiBroevi, TelefonskiBroeviId> {
}
