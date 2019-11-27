package at.htl.testtraining.boundary;

import at.htl.testtraining.entity.Driver;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("result")
public class ResultEndpoint {

    @PersistenceContext
    EntityManager em;

    @GET
    @Path("name")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDriverByName(@QueryParam("name") String name) {
        try {
            TypedQuery<Driver> query = em.createNamedQuery("Driver.findDriverByName", Driver.class)
                    .setParameter("NAME", name);
            Driver driver = query.getSingleResult();
            return Response.ok(driver).build();
        } catch (NoResultException ex) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }
}
