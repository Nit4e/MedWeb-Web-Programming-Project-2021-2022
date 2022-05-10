package mk.ukim.finki.wpproekt.model.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class LekAlreadyInShoppingCartException extends RuntimeException {

    public LekAlreadyInShoppingCartException(Long id, String username) {
        super(String.format("Drug with id: %d already exists in shopping cart for user with username %s", id, username));
    }
}
