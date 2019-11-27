package at.htl.testtraining.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "F1_RACE")
@Entity
@NamedQueries({
        @NamedQuery(
                name = "Race.findRaceById",
                query = "select r from Race r where r.id = :ID"
        )
})
public class Race {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private LocalDate date;

    public Race() {
    }

    public Race(String country, LocalDate date) {
        this.country = country;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    /*public void setId(Long id) {
        this.id = id;
    }*/

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%d: %s %s", id, country, date);
    }
}
