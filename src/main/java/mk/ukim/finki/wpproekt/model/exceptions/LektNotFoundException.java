package mk.ukim.finki.wpproekt.model.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LektNotFoundException extends RuntimeException {

    public LektNotFoundException(Long id) {
        super(String.format("Drug with id: %d was not found", id));
    }
}
