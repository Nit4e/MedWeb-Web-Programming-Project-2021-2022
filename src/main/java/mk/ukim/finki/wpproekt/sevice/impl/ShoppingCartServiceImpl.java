package mk.ukim.finki.wpproekt.sevice.impl;

import mk.ukim.finki.wpproekt.model.Korisnik;
import mk.ukim.finki.wpproekt.model.Lekovi;
import mk.ukim.finki.wpproekt.model.PrepisaniLekovi;
import mk.ukim.finki.wpproekt.model.ShoppingCart;
import mk.ukim.finki.wpproekt.model.enumerations.ShoppingCartStatus;
import mk.ukim.finki.wpproekt.model.exceptions.LekAlreadyInShoppingCartException;
import mk.ukim.finki.wpproekt.model.exceptions.LektNotFoundException;
import mk.ukim.finki.wpproekt.model.exceptions.ShoppingCartNotFoundException;
import mk.ukim.finki.wpproekt.model.exceptions.UserNotFoundException;
import mk.ukim.finki.wpproekt.repository.KorisnikRepository;
import mk.ukim.finki.wpproekt.repository.LekoviRepository;
import mk.ukim.finki.wpproekt.repository.PrepisaniLekoviRepositoryCustom;
import mk.ukim.finki.wpproekt.repository.ShoppingCartRepository;
import mk.ukim.finki.wpproekt.sevice.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final KorisnikRepository korisnikRepository;
    private final LekoviRepository lekoviRepository;
    private final PrepisaniLekoviRepositoryCustom prepisaniLekoviRepositoryCustom;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   KorisnikRepository korisnikRepository,
                                   LekoviRepository lekoviRepository,
                                   PrepisaniLekoviRepositoryCustom prepisaniLekoviRepositoryCustom) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.korisnikRepository = korisnikRepository;
        this.lekoviRepository = lekoviRepository;
        this.prepisaniLekoviRepositoryCustom = prepisaniLekoviRepositoryCustom;
    }

    @Override
    public List<Lekovi> listAllLekoviInShoppingCart(Integer cartId) {
        if(!this.shoppingCartRepository.findById(cartId).isPresent())
            throw new ShoppingCartNotFoundException(cartId);
        return this.shoppingCartRepository.findById(cartId).get().getLekoviList();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        Korisnik korisnik = this.korisnikRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return this.shoppingCartRepository
                .findByKorisnikAndStatus(korisnik, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart(korisnik);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public ShoppingCart addLekToShoppingCart(String username, Integer lek_id) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Lekovi lekovi = this.lekoviRepository.findById(lek_id)
                .orElseThrow(() -> new LektNotFoundException(lek_id));
        if(shoppingCart.getLekoviList()
                .stream().filter(i -> i.getLek_id().equals(lek_id))
                .collect(Collectors.toList()).size() > 0)
            throw new LekAlreadyInShoppingCartException(lek_id, username);
        shoppingCart.getLekoviList().add(lekovi);
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
