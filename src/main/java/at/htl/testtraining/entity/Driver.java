package at.htl.testtraining.entity;

import javax.persistence.*;

@Entity
@Table(name = "F1_DRIVER")
@NamedQueries({
        @NamedQuery(
                name = "Driver.findDriverByName",
                query = "select d from Driver d where d.name = :NAME"
        )
})
public class Driver {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private Team team;

    public Driver() {
    }

    public Driver(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    /*public void setId(Long id) {
        this.id = id;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return String.format("%d: %s %s", id, name, team);
    }
}
