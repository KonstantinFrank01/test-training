package at.htl.testtraining.control;

import at.htl.testtraining.entity.Driver;
import at.htl.testtraining.entity.Race;
import at.htl.testtraining.entity.Team;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

@Transactional
public class InitBean {

    private static final String TEAMS_DRIVERS_FILE = "teams.csv";
    private static final String RACES_FILE = "races.csv";

    @PersistenceContext
    EntityManager em;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        readRacesFromFile(RACES_FILE);
        readTeamsAndDriversFromFile(TEAMS_DRIVERS_FILE);
    }

    private void readRacesFromFile(String file) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(RACES_FILE);
        try (Stream<String> stream = Files.lines(Paths.get(url.getPath()), StandardCharsets.UTF_8)) {
            stream.skip(1)
                    .map(s -> s.split(";"))
                    .forEach(this::persistRaces);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void persistRaces(String[] line) {
        Race race = null;
        try {
            race = em.createNamedQuery("Race.findRaceById", Race.class)
                    .setParameter("ID", Long.valueOf(line[0]))
                    .getSingleResult();
        } catch (NoResultException e) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            race = new Race(line[1], LocalDate.parse(line[2], formatter));
            em.persist(race);
        }
    }

    private void readTeamsAndDriversFromFile(String file) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(TEAMS_DRIVERS_FILE);
        try (Stream<String> stream = Files.lines(Paths.get(url.getPath()), StandardCharsets.UTF_8)) {
            stream.skip(1).map(s -> s.split(";"))
                    .forEach(this::persistTeamsAndDrivers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void persistTeamsAndDrivers(String[] line) {
        Team team = null;
        try {
            team = em.createNamedQuery("Team.findTeamByName", Team.class)
                    .setParameter("NAME", line[0])
                    .getSingleResult();
        } catch (NoResultException e) {
            team = new Team(line[0]);
            em.persist(team);
        }
        em.persist(new Driver(line[1], team));
        em.persist(new Driver(line[2], team));
    }
}
