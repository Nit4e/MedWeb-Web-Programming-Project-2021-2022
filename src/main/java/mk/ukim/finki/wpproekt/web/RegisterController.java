package mk.ukim.finki.wpproekt.web;

import mk.ukim.finki.wpproekt.model.Oddel;
import mk.ukim.finki.wpproekt.model.Specijalnost;
import mk.ukim.finki.wpproekt.model.TelefonskiBroevi;
import mk.ukim.finki.wpproekt.model.enumerations.Role;
import mk.ukim.finki.wpproekt.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wpproekt.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.wpproekt.sevice.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/register")
public class RegisterController {


    private final AuthService authService;
    private final KorisnikService korisnikService;
    private final SpecijalnostService specijalnostService;
    private final OddelService oddelService;
    private final TelefonskiBroeviService telefonskiBroeviService;

    public RegisterController(AuthService authService, KorisnikService korisnikService,
                              SpecijalnostService specijalnostService, OddelService oddelService,
                              TelefonskiBroeviService telefonskiBroeviService) {
        this.authService = authService;
        this.korisnikService = korisnikService;
        this.specijalnostService = specijalnostService;
        this.oddelService = oddelService;
        this.telefonskiBroeviService = telefonskiBroeviService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Specijalnost> specijalnostList = this.specijalnostService.findAll();
        List<Oddel> oddelList = this.oddelService.findAll();
        model.addAttribute("specijalnostList", specijalnostList);
        model.addAttribute("oddelList", oddelList);
        model.addAttribute("bodyContent", "specijalnostList");
        model.addAttribute("bodyContent", "oddelList");
        model.addAttribute("bodyContent","register");
        return "master-template";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam (required = false)String embg,
                           @RequestParam String ime,
                           @RequestParam String prezime,
                           @RequestParam Role role,
                           @RequestParam (required = false)Integer specijalnostId,
                           @RequestParam (required = false)Integer oddelId,
                           @RequestParam (required = false)String telefonski_broj) {

        try {
            this.korisnikService.register(username, password, repeatedPassword, embg, ime, prezime, role,
                    specijalnostId, oddelId);
            if (!telefonski_broj.isEmpty() || !telefonski_broj.equals("") || !(telefonski_broj == null)) {
                TelefonskiBroevi telefonskiBroevi = new TelefonskiBroevi();
                Integer covek_broj_id = this.korisnikService.findByUsername(username).get().getCovek_id();
                telefonskiBroevi.setCovek_broj_id(covek_broj_id);
                telefonskiBroevi.setTelefonski_broj(telefonski_broj);
                this.telefonskiBroeviService.save(telefonskiBroevi);
                return "redirect:/login";
            }
            return "redirect:/login";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
}
