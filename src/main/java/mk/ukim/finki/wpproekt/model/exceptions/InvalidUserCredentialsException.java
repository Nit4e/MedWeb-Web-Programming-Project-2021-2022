package mk.ukim.finki.wpproekt.model.exceptions;

public class InvalidUserCredentialsException extends RuntimeException{
    public InvalidUserCredentialsException() {
        super("Invalid Credentials for Korisnik");
    }
}
