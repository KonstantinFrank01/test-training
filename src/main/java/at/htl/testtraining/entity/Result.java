package at.htl.testtraining.entity;

import javax.persistence.*;

@Entity
@Table(name = "F1_RESULT")
public class Result {

    @Transient
    public int[] pointsPerPosition = {0, 25, 18, 15, 12, 10, 8, 6, 4, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Race race;
    private int position;
    private int points;
    @ManyToOne
    private Driver driver;

    public Result() {
    }

    public Result(Race race, int position, Driver driver) {
        this.race = race;
        this.position = position;
        this.driver = driver;
    }

    public Long getId() {
        return id;
    }

    /*public void setId(Long id) {
        this.id = id;
    }*/

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        return String.format("%d: %s %s %s %s %s", id, race, position, points, driver);
    }
}
