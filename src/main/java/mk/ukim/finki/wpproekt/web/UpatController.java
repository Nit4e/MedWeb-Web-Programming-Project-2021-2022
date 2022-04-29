package mk.ukim.finki.wpproekt.web;

import mk.ukim.finki.wpproekt.model.Korisnik;
import mk.ukim.finki.wpproekt.model.Oddel;
import mk.ukim.finki.wpproekt.model.Upat;
import mk.ukim.finki.wpproekt.sevice.KorisnikService;
import mk.ukim.finki.wpproekt.sevice.OddelService;
import mk.ukim.finki.wpproekt.sevice.UpatService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/upati")
public class UpatController {

    private final UpatService upatService;
    private final KorisnikService korisnikService;
    private final OddelService oddelService;

    public UpatController(UpatService upatService, KorisnikService korisnikService, OddelService oddelService) {
        this.upatService = upatService;
        this.korisnikService = korisnikService;
        this.oddelService = oddelService;
    }

    @GetMapping
    public String getUpatPage(@RequestParam(required = false) String error, Model model){
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Upat> upati = this.upatService.findAll();
        model.addAttribute("upati", upati);
        model.addAttribute("bodyContent", "upati");
        return "master-template";
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_DOKTOR')")
    public String deleteUpat(@PathVariable Integer id) {
        this.upatService.deleteById(id);
        return "redirect:/upati";
    }

    @GetMapping("/add-form")
    @PreAuthorize("hasRole('ROLE_DOKTOR')")
    public String addUpatPage(Model model, HttpServletRequest request) {
        Korisnik korisnik = this.korisnikService.findByUsername(request.getRemoteUser()).get();
        //List<Korisnik> korisnici = this.korisnikService.findAll();
        List<Korisnik> korisnici = this.korisnikService
                .findByNotLoggedInUsernameAndRoleNotAdmin(korisnik.getUsername());
        List<Oddel> oddeli = this.oddelService.findAll();
        model.addAttribute("korisnici", korisnici);
        model.addAttribute("oddeli", oddeli);
        model.addAttribute("bodyContent", "add-upat");
        return "master-template";
    }

    @GetMapping("/edit-form/{id}")
    @PreAuthorize("hasRole('ROLE_DOKTOR')")
    public String editUpatPage(@PathVariable Integer id, Model model, HttpServletRequest request) {
        if (this.upatService.findById(id).isPresent()) {
            Upat upat = this.upatService.findById(id).get();
            Korisnik korisnik = this.korisnikService.findByUsername(request.getRemoteUser()).get();
            //List<Korisnik> korisnici = this.korisnikService.findAll();
            List<Korisnik> korisnici = this.korisnikService
                    .findByNotLoggedInUsernameAndRoleNotAdmin(korisnik.getUsername());
            List<Oddel> oddeli = this.oddelService.findAll();
            model.addAttribute("korisnici", korisnici);
            model.addAttribute("oddeli", oddeli);
            model.addAttribute("upat", upat);
            model.addAttribute("bodyContent", "add-upat");
            return "master-template";
        }
        return "redirect:/upati?error=UpatNotFound";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_DOKTOR')")
    public String saveUpat(@RequestParam (required = false) Integer id,
                           @RequestParam String dijagnoza,
                           @RequestParam Integer korisnik,
                           @RequestParam Integer oddel,
                           @RequestParam String doktor) {
        if (id != null) {
            this.upatService.edit(id, dijagnoza, korisnik, oddel, doktor);
        } else {
            this.upatService.save(dijagnoza, korisnik, oddel, doktor);
        }
        return "redirect:/upati";
    }

}
