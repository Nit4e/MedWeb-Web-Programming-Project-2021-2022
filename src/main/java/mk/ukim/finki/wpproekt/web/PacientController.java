package mk.ukim.finki.wpproekt.web;


import mk.ukim.finki.wpproekt.model.*;
import mk.ukim.finki.wpproekt.sevice.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@Controller
@RequestMapping("/pacient")
@SessionAttributes({"upat", "termin", "rezervacija"})
public class PacientController {

    private final PrepisaniLekoviService prepisaniLekoviService;
    private final KorisnikService korisnikService;
    private final UpatService upatService;
    private final RezervacijaService rezervacijaService;
    private final TransakcijaService transakcijaService;
    private final TerminService terminService;

    public PacientController(PrepisaniLekoviService prepisaniLekoviService, KorisnikService korisnikService,
                             UpatService upatService, RezervacijaService rezervacijaService,
                             TransakcijaService transakcijaService, TerminService terminService) {
        this.prepisaniLekoviService = prepisaniLekoviService;
        this.korisnikService = korisnikService;
        this.upatService = upatService;
        this.rezervacijaService = rezervacijaService;
        this.transakcijaService = transakcijaService;
        this.terminService = terminService;
    }

    @GetMapping("/{username}/lekovi")
    public String getLekoviPage (@RequestParam(required = false) String error,
                                 @PathVariable String username, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        if (this.korisnikService.findByUsername(username).isPresent()){
            List<PrepisaniLekovi> prepisaniLekovi = this.prepisaniLekoviService.findAll();
            model.addAttribute("prepisaniLekovi", prepisaniLekovi);
            model.addAttribute("bodyContent", "prepisaniLekovi");
            model.addAttribute("bodyContent", "lekovi");
            return "master-template";
        }
        else {
            return "redirect:/pacient/{username}/lekovi?error=LekoviNotFound";
        }
    }

    @GetMapping("/zakazi-termin/{id}")
    @PreAuthorize("hasRole('ROLE_PACIENT')")
    public String zakaziTermin (@PathVariable Integer id, Model model) {

        if (this.upatService.findById(id).isPresent()) {
            Upat upat = this.upatService.findById(id).get();
            String dijagnoza = upat.getDijagnoza();
            String oddel = upat.getOddel().getNaziv();

            List<Korisnik> korisnici = upat.getOddel().getKorisnikList();
            model.addAttribute("upat", upat);
            model.addAttribute("dijagnoza", dijagnoza);
            model.addAttribute("oddel", oddel);
            model.addAttribute("korisnici", korisnici);
            model.addAttribute("bodyContent", "zakazi-termin");
            return "master-template";
        }
        else {
            return "redirect:/pacient/zakazi-termin/{id}?error=UpatNotFound";
        }
    }

    @GetMapping("/dostapni-termini/{id}")
    @PreAuthorize("hasRole('ROLE_PACIENT')")
    public String getDostapniTerminiPage (@PathVariable Integer id, Model model) {
        Upat upat = (Upat) model.getAttribute("upat");

        if (this.korisnikService.findById(id).isPresent()) {
            Korisnik korisnik = this.korisnikService.findById(id).get();

            List<Termin> terminList = this.terminService
                    .findOnlyFutureAndFreeAndByDoktor(ZonedDateTime.now(), id);
            model.addAttribute("upat", upat);
            model.addAttribute("korisnik", korisnik);
            model.addAttribute("terminList", terminList);
            model.addAttribute("bodyContent", "dostapni-termini");
            return "master-template";
        }
        else {
            return "redirect:/dostapni-termini/{id}?error=UpatNotFound";
        }
    }

    @PostMapping("/rezervacija/{termin_id}")
    @PreAuthorize("hasRole('ROLE_PACIENT')")
    public String rezervacija (@PathVariable Integer termin_id, Model model){
        Upat upat = (Upat) model.getAttribute("upat");

        if (this.terminService.findOneTerminByTerminId(termin_id) != null) {
            Termin termin = this.terminService.findOneTerminByTerminId(termin_id);
            model.addAttribute("termin", termin);
            Rezervacija rezervacija = new Rezervacija(upat, termin);
            this.rezervacijaService.save(rezervacija);

            model.addAttribute("rezervacija", rezervacija);
            model.addAttribute("bodyContent", "rezervacija");
            return "master-template";
        }
        else {
            return "redirect:/dostapni-termini/{id}?error=TerminNotFound";
        }
    }

    @GetMapping("/validiraj/rezervacija/{id}")
    @PreAuthorize("hasRole('ROLE_PACIENT')")
    public String getTransakcijaPage (@PathVariable Integer id, Model model) {
        Upat upat = (Upat) model.getAttribute("upat");

        if (this.rezervacijaService.findById(id).isPresent()) {
            Rezervacija rezervacija = this.rezervacijaService.findById(id).get();
            model.addAttribute("rezervacija", rezervacija);
            model.addAttribute("bodyContent", "transakcija");
            return "master-template";
        }
        else {
            return "redirect:/validiraj/rezervacija/{id}?error=RezervacijaNotFound";
        }
    }

    @PostMapping("/validiraj/rezervacija/{id}")
    @PreAuthorize("hasRole('ROLE_PACIENT')")
    public String transakcija (@PathVariable Integer id, Model model) {

        Upat upat = (Upat) model.getAttribute("upat");
        Termin termin = (Termin) model.getAttribute("termin");

        if (this.rezervacijaService.findById(id).isPresent()) {
            Rezervacija rezervacija = this.rezervacijaService.findById(id).get();
            String smetka_bolnica = upat.getOddel().getBolnica_id().getSmetka_bolnica();
            Integer suma = 50;
            Transakcija transakcija = new Transakcija(suma, smetka_bolnica, rezervacija);
            this.transakcijaService.save(transakcija);
            model.addAttribute("rezervacija", rezervacija);
            model.addAttribute("smetka_bolnica", smetka_bolnica);
            model.addAttribute("suma", suma);
            model.addAttribute("transakcija", transakcija);
            model.addAttribute("bodyContent", "prikaz-rezervacija");
            return "master-template";
        }
        else {
            return "redirect:/validiraj/rezervacija/{id}?error=RezervacijaNotFound";
        }
    }

    @GetMapping("/prikazi-rezervacii")
    @PreAuthorize("hasRole('ROLE_PACIENT')")
    public String getMyReservationPage (@RequestParam(required = false) String error, Model model) {

        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Transakcija> transakcii = this.transakcijaService.findAll();
        model.addAttribute("transakcii", transakcii);
        model.addAttribute("bodyContent", "prikazi-rezervacii");
        return "master-template";

    }
}
