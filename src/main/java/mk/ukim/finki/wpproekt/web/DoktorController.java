package mk.ukim.finki.wpproekt.web;


import mk.ukim.finki.wpproekt.model.*;
import mk.ukim.finki.wpproekt.sevice.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private final RezervacijaService rezervacijaService;

    public DoktorController(LekoviService lekoviService, KorisnikService korisnikService,
                            PrepisaniLekoviService prepisaniLekoviService, TerminService terminService,
                            OddelService oddelService, UpatService upatService,
                            RezervacijaService rezervacijaService) {
        this.lekoviService = lekoviService;
        this.korisnikService = korisnikService;
        this.prepisaniLekoviService = prepisaniLekoviService;
        this.terminService = terminService;
        this.oddelService = oddelService;
        this.upatService = upatService;
        this.rezervacijaService = rezervacijaService;
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
                             @RequestParam Long lekId,
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

    @GetMapping("/termin-{id}-delete")
    @PreAuthorize("hasRole('ROLE_DOKTOR')")
    @Transactional
    public String delete(@PathVariable Long id) {
        try {
            int flag = 0;
            Termin termin = this.terminService.findOneTerminByTerminId(id);
            List<Termin> terminList = this.terminService.findOnlyFutureFree(ZonedDateTime.now());
            for (Termin t : terminList) {
                if (termin.getTermin_id().equals(t.getTermin_id())){
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                this.terminService.deleteTermin(termin);
            }
            else {
                this.terminService.deleteTerminWithInvalidReservation(termin);
            }
            return "redirect:/doktor/moi-termini";
        } catch (RuntimeException exception) {
            return "redirect:/doktor/moi-termini?error=" + exception.getMessage();
        }
    }

    @GetMapping("/{username}-kalendar")
    public String getKalendarPage (@RequestParam(required = false) String error,
                                   @PathVariable String username, Model model, HttpServletRequest request) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        Korisnik korisnik = this.korisnikService.findByUsername(request.getRemoteUser()).get();;
        List<Rezervacija> validReservations = this.rezervacijaService
                .findAllValidReservationsForDoktor(korisnik.getCovek_id());
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
