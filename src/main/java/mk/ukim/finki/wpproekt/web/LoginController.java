package mk.ukim.finki.wpproekt.web;


import mk.ukim.finki.wpproekt.model.Korisnik;
import mk.ukim.finki.wpproekt.model.exceptions.InvalidUserCredentialsException;
import mk.ukim.finki.wpproekt.sevice.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {

        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage(Model model) {
        model.addAttribute("bodyContent","login");
        return "master-template";
    }

    @PostMapping
    public String login (HttpServletRequest request, Model model) {

        Korisnik korisnik = null;
        try {
            korisnik = this.authService.login(request.getParameter("username"),
                    request.getParameter("password"));
            request.getSession().setAttribute("korisnik", korisnik);
            return "redirect:/home";
        }
        catch (InvalidUserCredentialsException exception) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            return "redirect:/login";
        }
    }
}
