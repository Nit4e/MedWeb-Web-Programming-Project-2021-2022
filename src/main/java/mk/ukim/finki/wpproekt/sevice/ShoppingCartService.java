package mk.ukim.finki.wpproekt.sevice;

import mk.ukim.finki.wpproekt.model.Lekovi;
import mk.ukim.finki.wpproekt.model.PrepisaniLekovi;
import mk.ukim.finki.wpproekt.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    List<Lekovi> listAllLekoviInShoppingCart(Integer cartId);
    ShoppingCart getActiveShoppingCart(String username);
    ShoppingCart addLekToShoppingCart(String username, Integer lek_id);
}
