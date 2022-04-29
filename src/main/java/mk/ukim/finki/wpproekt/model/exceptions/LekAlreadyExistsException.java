package mk.ukim.finki.wpproekt.model.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class LekAlreadyExistsException extends RuntimeException {

    public LekAlreadyExistsException (String imeLek, String generickIme) {
        super(String.format("Drug with drug_name: %s and generic_name: %s already exists", imeLek, generickIme));
    }
}
