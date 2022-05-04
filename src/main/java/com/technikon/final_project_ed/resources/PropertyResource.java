package com.technikon.final_project_ed.resources;

import com.technikon.final_project_ed.dto.PropertyDto;
import com.technikon.final_project_ed.service.PropertyService;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("properties")
@Slf4j
@NoArgsConstructor
public class PropertyResource {

    @Inject
    private PropertyService propertyService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok().entity(propertyService.getAll()).build();
    }

    @Path("/{propertyId}")
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPropertyByPropertyId(@PathParam("propertyId") long propertyId) {
        return Response.ok().entity(propertyService.searchByPropertyId(propertyId)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public Response saveProperty(PropertyDto propertyDto) {
        if (propertyService == null) {
            log.info("null service");
        }
        if (propertyDto == null) {
            log.info("null propertyDto");
        }
        return Response.ok().entity(propertyService.create(propertyDto)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public Response updateProperty(PropertyDto propertyDto) {
        return Response.ok().entity(propertyService.update(propertyDto)).build();
    }

    @Path("/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public void deleteProperty(@PathParam("id") long id) {
        propertyService.delete(id);
    }

    @DELETE
    @RolesAllowed("ADMIN")
    public void deleteAll() {
        propertyService.deleteAll();
    }
}
