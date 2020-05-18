package lt.vu.rest;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Restaurant;
import lt.vu.persistence.RestaurantDAO;
import lt.vu.rest.contracts.RestaurantDto;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationScoped
@Path("/restaurant")
public class RestaurantController {

    @Inject
    @Setter @Getter
    private RestaurantDAO restaurantDAO;

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") final Integer restaurantId) {
        Restaurant restaurant = restaurantDAO.findOne(restaurantId);
        if (restaurant == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setName(restaurant.getName());
        restaurantDto.setCity(restaurant.getCity());

        return Response.ok(restaurantDto).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(
            @PathParam("id") final Integer restaurantId,
            RestaurantDto restaurantData) {
        try {
            Restaurant existingRestaurant = restaurantDAO.findOne(restaurantId);
            if (existingRestaurant == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            existingRestaurant.setName(restaurantData.getName());
            existingRestaurant.setCity(restaurantData.getCity());

            restaurantDAO.update(existingRestaurant);

            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response create(RestaurantDto restaurantData) {
        try {
            Restaurant newRestaurant = new Restaurant();
            newRestaurant.setName(restaurantData.getName());
            newRestaurant.setCity(restaurantData.getCity());

            restaurantDAO.persist(newRestaurant);

            return Response.ok().build();
        } catch (OptimisticLockException ole) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}
