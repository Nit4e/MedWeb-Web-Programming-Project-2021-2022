package mk.ukim.finki.wpproekt.model;

import lombok.Data;
import mk.ukim.finki.wpproekt.model.enumerations.Role;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
public class Korisnik implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer covek_id;
    private String username;
    private String password;
    @Column(columnDefinition = "bpchar(13)")
    private String embg;
    private String ime;
    private String prezime;
    @Enumerated(value = EnumType.STRING)
    private Role role;

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @ManyToOne
    private Specijalnost specijalnost;

    @ManyToOne
    private Oddel oddel;

    @OneToMany(mappedBy = "korisnik", fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Termin> terminList;

    @OneToMany(mappedBy = "korisnik")
    private List<PrepisaniLekovi> prepisaniLekovi;

    public Korisnik() {
    }

    public Korisnik(String username, String password, String embg, String ime, String prezime, Role role,
                    Specijalnost specijalnost, Oddel oddel) {
        this.username = username;
        this.password = password;
        this.embg = embg;
        this.ime = ime;
        this.prezime = prezime;
        this.role = role;
        this.specijalnost = specijalnost;
        this.oddel = oddel;
    }


//    public List<PrepisaniLekovi> getPrepisaniLekovi() {
//        return prepisaniLekovi;
//    }
//
//    public void setPrepisaniLekovi(List<PrepisaniLekovi> prepisaniLekovi) {
//        this.prepisaniLekovi = prepisaniLekovi;
//        this.covek_id = getCovek_id();
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(role);
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
