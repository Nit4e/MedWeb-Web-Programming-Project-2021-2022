package mk.ukim.finki.wpproekt.web;


import mk.ukim.finki.wpproekt.model.*;
import mk.ukim.finki.wpproekt.sevice.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/doktor")
public class DoktorController {

    private final LekoviService lekoviService;
    private final KorisnikService korisnikService;
    private final PrepisaniLekoviService prepisaniLekoviService;
    private final TerminService terminService;
    private final OddelService oddelService;
    private final UpatService upatService;

    public DoktorController(LekoviService lekoviService, KorisnikService korisnikService,
                            PrepisaniLekoviService prepisaniLekoviService, TerminService terminService,
                            OddelService oddelService, UpatService upatService) {
        this.lekoviService = lekoviService;
        this.korisnikService = korisnikService;
        this.prepisaniLekoviService = prepisaniLekoviService;
        this.terminService = terminService;
        this.oddelService = oddelService;
        this.upatService = upatService;
    }

    // LEKOVI
    @GetMapping("/prepisi-lek")
    public String getPrepisiLekPage(@RequestParam(required = false) String error, Model model,
                                    HttpServletRequest request) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        Korisnik korisnik = this.korisnikService.findByUsername(request.getRemoteUser()).get();
        List<Lekovi> lekoviList = this.lekoviService.listAll();
        List<Korisnik> korisnikList = this.korisnikService
                .findByNotLoggedInUsernameAndRoleNotAdmin(korisnik.getUsername());
        model.addAttribute("lekoviList", lekoviList);
        model.addAttribute("bodyContent", "lekoviList");
        model.addAttribute("korisnikList", korisnikList);
        model.addAttribute("bodyContent", "korisnikList");
        model.addAttribute("bodyContent", "prepisi-lek");
        return "master-template";
    }

    @PostMapping("/prepisi-lek")
    @PreAuthorize("hasRole('ROLE_DOKTOR')")
    public String prepisiLek(@RequestParam String doktorId,
                             @RequestParam Integer lekId,
                             @RequestParam String pacientId) {

        this.prepisaniLekoviService.save(doktorId, lekId, pacientId);
        return "redirect:/doktor/prepisani-lekovi";
    }

    @GetMapping("/prepisani-lekovi")
    public String getPrepisaniLekoviPage(@RequestParam(required = false) String error, Model model,
                                         HttpServletRequest request) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<PrepisaniLekovi> prepisaniLekoviList = this.prepisaniLekoviService.findAll();
        model.addAttribute("prepisaniLekoviList", prepisaniLekoviList);
        model.addAttribute("bodyContent", "prepisaniLekoviList");
        model.addAttribute("bodyContent", "lekovi");
        return "master-template";
    }

    // TERMIN
    @GetMapping("/moi-termini")
    public String getTermini (@RequestParam(required = false) String error, Model model, HttpServletRequest request) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Termin> termini = this.terminService.findOnlyFutureAndFree(ZonedDateTime.now());
        List<Korisnik> korisnikList = this.korisnikService.findAll();
        model.addAttribute("termini", termini);
        model.addAttribute("bodyContent", "termini");
        model.addAttribute("korisnikList", korisnikList);
        model.addAttribute("bodyContent", "korisnikList");
        model.addAttribute("bodyContent", "doktor-termini");
        return "master-template";
    }

    @GetMapping("/vnesi-termin")
    @PreAuthorize("hasRole('ROLE_DOKTOR')")
    public String addTerminPage (Model model) {
        List<Termin> termini = this.terminService.findAll();
        model.addAttribute("termini", termini);
        model.addAttribute("bodyContent","add-termin");
        return "master-template";
    }

    @PostMapping("/save-termin")
    @PreAuthorize("hasRole('ROLE_DOKTOR')")
    public String saveTermin (Model model, HttpServletRequest request,
                              @RequestParam(value = "vreme") String vreme){
        Korisnik korisnik = this.korisnikService.findByUsername(request.getRemoteUser()).get();
        Termin termin = new Termin();
        termin.setCovek_id(korisnik.getCovek_id());
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime vremeZ = LocalDateTime.parse(vreme, DateTimeFormatter.ISO_DATE_TIME).atZone(zoneId);
        termin.setVreme(vremeZ);
        model.addAttribute("termin", termin);
        this.terminService.save(termin);
        return "redirect:/doktor/moi-termini";
    }

    @GetMapping("/termin/{id}/delete")
    @Transactional
    public String delete(@PathVariable Integer id) {
        try {
            Termin termin = this.terminService.findOneTerminByTerminId(id);
            this.terminService.deleteTermin(termin);
            return "redirect:/doktor/moi-termini";
        } catch (RuntimeException exception) {
            return "redirect:/moi-termini?error=" + exception.getMessage();
        }
    }
}
