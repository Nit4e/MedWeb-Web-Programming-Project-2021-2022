package mk.ukim.finki.wpproekt.config;


import mk.ukim.finki.wpproekt.sevice.KorisnikService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final KorisnikService korisnikService;

    public CustomUsernamePasswordAuthenticationProvider(PasswordEncoder passwordEncoder,
                                                        KorisnikService korisnikService) {
        this.passwordEncoder = passwordEncoder;
        this.korisnikService = korisnikService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if ("" .equals(username) || "" .equals(password)) {
            return (Authentication) new BadCredentialsException("Invalid Credentials");
        }

        UserDetails userDetails = this.korisnikService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Password not correct!");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
                userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
