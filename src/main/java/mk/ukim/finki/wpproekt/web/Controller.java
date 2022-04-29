package mk.ukim.finki.wpproekt.web;


import mk.ukim.finki.wpproekt.model.Bolnica;
import mk.ukim.finki.wpproekt.model.Lekovi;
import mk.ukim.finki.wpproekt.model.Oddel;
import mk.ukim.finki.wpproekt.model.Specijalnost;
import mk.ukim.finki.wpproekt.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.wpproekt.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.wpproekt.sevice.BolnicaService;
import mk.ukim.finki.wpproekt.sevice.LekoviService;
import mk.ukim.finki.wpproekt.sevice.OddelService;
import mk.ukim.finki.wpproekt.sevice.SpecijalnostService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@org.springframework.stereotype.Controller
@RequestMapping("/admin")
public class Controller {


    private final SpecijalnostService specijalnostService;
    private final BolnicaService bolnicaService;
    private final OddelService oddelService;
    private final LekoviService lekoviService;

    public Controller(SpecijalnostService specijalnostService, BolnicaService bolnicaService,
                      OddelService oddelService, LekoviService lekoviService) {

        this.specijalnostService = specijalnostService;
        this.bolnicaService = bolnicaService;
        this.oddelService = oddelService;
        this.lekoviService = lekoviService;
    }

// SPECIJALNOST
    @GetMapping("/specijalnost")
    public String listSpecijalnost(@RequestParam(required = false) String error,
                                    Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Specijalnost> specijalnostList = this.specijalnostService.findAll();
        model.addAttribute("specijalnostList", specijalnostList);
        model.addAttribute("bodyContent", "specijalnostList");
        model.addAttribute("bodyContent", "specijalnost");

        return "master-template";
    }

    @GetMapping("/specijalnost/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddSpecijalnostForm(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent","addSpecijalnost");
        return "master-template";
    }

    @PostMapping("/specijalnost/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addSpecijalnost (@RequestParam String naziv) {

        try {
            Specijalnost specijalnost = new Specijalnost(naziv);
            this.specijalnostService.save(specijalnost);
            return "redirect:/admin/specijalnost";
        } catch (RuntimeException exception) {
            return "redirect:/admin/specijalnost/add?error=" + exception.getMessage();
        }
    }

// BOLNICA
    @GetMapping("/bolnica")
    public String listBolnica (@RequestParam(required = false) String error,
                                   Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Bolnica> bolnicaList = this.bolnicaService.findAll();
        model.addAttribute("bolnicaList", bolnicaList);
        model.addAttribute("bodyContent", "bolnicaList");
        model.addAttribute("bodyContent", "bolnica");

        return "master-template";
    }

    @GetMapping("/bolnica/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddBolnicaForm(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent","addBolnica");
        return "master-template";
    }

    @PostMapping("/bolnica/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addBolnica (@RequestParam String naziv,
                              @RequestParam String grad,
                              @RequestParam Integer broj,
                              @RequestParam String ulica,
                              @RequestParam String smetka_bolnica) {

        try {
            Bolnica bolnica = new Bolnica(naziv, grad, broj, ulica, smetka_bolnica);
            this.bolnicaService.save(bolnica);
            return "redirect:/admin/bolnica";
        } catch (RuntimeException exception) {
            return "redirect:/admin/bolnica/add?error=" + exception.getMessage();
        }
    }

// ODDEL
    @GetMapping("/oddel")
    public String listOddel (@RequestParam(required = false) String error,
                               Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Oddel> oddelList = this.oddelService.findAll();
        model.addAttribute("oddelList", oddelList);
        model.addAttribute("bodyContent", "oddelList");
        model.addAttribute("bodyContent", "oddel");

        return "master-template";
    }

    @GetMapping("/oddel/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddOddelForm(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Bolnica> bolnicaList = this.bolnicaService.findAll();
        model.addAttribute("bolnicaList", bolnicaList);
        model.addAttribute("bodyContent", "bolnicaList");
        model.addAttribute("bodyContent","addOddel");

        List<Specijalnost> specijalnostList = this.specijalnostService.findAll();
        model.addAttribute("specijalnostList", specijalnostList);
        model.addAttribute("bodyContent", "specijalnostList");
        model.addAttribute("bodyContent", "addOddel");

        return "master-template";
    }

    @PostMapping("/oddel/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addOddel (@RequestParam String naziv,
                            @RequestParam Integer bolnica_id,
                            @RequestParam Integer specijalnost_id) {

        try {
            this.oddelService.save(naziv, bolnica_id, specijalnost_id);
            return "redirect:/admin/oddel";
        } catch (RuntimeException exception) {
            return "redirect:/admin/oddel/add?error=" + exception.getMessage();
        }
    }

// LEKOVI
    @GetMapping("/lekovi")
    public String listLekovi (@RequestParam(required = false) String error,
                             Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Lekovi> lekoviList = this.lekoviService.listAll();
        model.addAttribute("lekoviList", lekoviList);
        model.addAttribute("bodyContent", "lekoviList");
        model.addAttribute("bodyContent", "lekovi");

        return "master-template";
    }

    @GetMapping("/lekovi/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getAddLekoviForm(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Lekovi> lekoviList = this.lekoviService.listAll();
        model.addAttribute("lekoviList", lekoviList);
        model.addAttribute("bodyContent", "lekoviList");
        model.addAttribute("bodyContent","addLekovi");
        model.addAttribute("bodyContent", "addLekovi");

        return "master-template";
    }

    @PostMapping("/lekovi/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String addLekovi (@RequestParam String imeLek,
                            @RequestParam String generickoIme) {

        try {
            this.lekoviService.save(imeLek, generickoIme);
            return "redirect:/admin/lekovi";
        } catch (RuntimeException exception) {
            return "redirect:/admin/lekovi/add?error=" + exception.getMessage();
        }
    }
}
