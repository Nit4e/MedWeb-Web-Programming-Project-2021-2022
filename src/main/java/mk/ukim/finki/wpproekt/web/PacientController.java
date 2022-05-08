package mk.ukim.finki.wpproekt.web;


import mk.ukim.finki.wpproekt.model.*;
import mk.ukim.finki.wpproekt.sevice.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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

    @GetMapping("/{username}-lekovi")
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

    @GetMapping("/zakazi-termin-{id}")
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
            return "redirect:/pacient/zakazi-termin-{id}?error=UpatNotFound";
        }
    }

    @GetMapping("/dostapni-termini-{id}")
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
            return "redirect:/dostapni-termini-{id}?error=UpatNotFound";
        }
    }

    @PostMapping("/rezervacija-{termin_id}")
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
            return "redirect:/dostapni-termini-{id}?error=TerminNotFound";
        }
    }

    @GetMapping("/validiraj-rezervacija-{id}")
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
            return "redirect:/validiraj-rezervacija-{id}?error=RezervacijaNotFound";
        }
    }

    @PostMapping("/validiraj-rezervacija-{id}")
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
            return "redirect:/validiraj-rezervacija-{id}?error=RezervacijaNotFound";
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

    @GetMapping("/{username}-kalendar")
    public String getKalendarPage (@RequestParam(required = false) String error,
                                   @PathVariable String username, Model model, HttpServletRequest request) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        Korisnik korisnik = this.korisnikService.findByUsername(request.getRemoteUser()).get();;
        List<Rezervacija> validReservations = this.rezervacijaService.findAllValidReservations(korisnik.getCovek_id());
        List<LocalDate> localDates = new ArrayList<>();
        List<String> year = new ArrayList<>();
        List<String> month = new ArrayList<>();
        List<String> day = new ArrayList<>();
        List<Integer> hours = new ArrayList<>();
        List<Integer> minutes = new ArrayList<>();
        for (Rezervacija rezervacija : validReservations) {
            LocalDate localDate = rezervacija.getTermin().getVreme().toLocalDate();
            localDate = localDate.minusMonths(1);
            Integer hour = rezervacija.getTermin().getVreme().getHour();
            localDates.add(localDate);
            year.add(String.valueOf(localDate.getYear()));
            month.add(String.valueOf(localDate.getMonthValue()));
            day.add(String.valueOf(localDate.getDayOfMonth()));
            hours.add(hour);
            minutes.add(rezervacija.getTermin().getVreme().getMinute());
        }
        model.addAttribute("year", year);
        model.addAttribute("bodyContent", "year");
        model.addAttribute("month", month);
        model.addAttribute("bodyContent", "month");
        model.addAttribute("day", day);
        model.addAttribute("bodyContent", "day");
        model.addAttribute("hours", hours);
        model.addAttribute("bodyContent", "hours");
        model.addAttribute("minutes", minutes);
        model.addAttribute("bodyContent", "minutes");
        model.addAttribute("bodyContent", "event-calendar");
        return "master-template";
    }
}
