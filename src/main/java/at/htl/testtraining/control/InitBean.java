package at.htl.testtraining.control;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

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

    }

    void persistRaces(String[] line) {

    }

    private void readTeamsAndDriversFromFile(String file) {

    }

    void persistTeamsAndDrivers(String[] line) {

    }
}
