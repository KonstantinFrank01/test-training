package at.htl.testtraining.boundary;

import at.htl.testtraining.entity.Driver;
import at.htl.testtraining.entity.Race;
import at.htl.testtraining.entity.Result;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ResultRestClient {

    private static final String RESULTS_ENDPOINT = "http://vm90.htl-leonding.ac.at/results";
    private Client client = ClientBuilder.newClient();
    private WebTarget target = client.target(RESULTS_ENDPOINT);

    @PersistenceContext
    EntityManager em;

    public void readResultsFromEndpoint() {
        Response response = this.target.request(MediaType.APPLICATION_JSON).get();
        JsonArray payload = response.readEntity(JsonArray.class);
        persistResult(payload);
    }

    void persistResult(JsonArray resultsJson) {
        for (JsonValue value : resultsJson) {
            JsonObject result = value.asJsonObject();
            Driver driver = em.createNamedQuery("Driver.findDriverByName", Driver.class)
                    .setParameter("NAME", result.getString("driverFullName"))
                    .getSingleResult();
            Race race = em.createNamedQuery("Race.findRaceById", Race.class)
                    .setParameter("ID", Long.valueOf(result.getInt("raceNo")))
                    .getSingleResult();
            em.persist(new Result(race, result.getInt("position"), driver));
        }
    }
}
