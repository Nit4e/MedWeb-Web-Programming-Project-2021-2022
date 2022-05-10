package mk.ukim.finki.wpproekt.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UpatNotFoundException extends RuntimeException {
    public UpatNotFoundException(Long id) {
        super(String.format("Referral with id: %d was not found", id));
    }
}
