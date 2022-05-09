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

    @Path("/propertyId/{propertyId}")
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPropertyByPropertyId(@PathParam("propertyId") String propertyId) {
        return Response.ok().entity(propertyService.searchByPropertyId(propertyId)).build();
    }

    @Path("/{id}")
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPropertyById(@PathParam("id") Long id) {
        return Response.ok().entity(propertyService.searchById(id)).build();
    }

    @Path("/{id}/owner")
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPropertyOwner(@PathParam("id") long id) {
        return Response.ok().entity(propertyService.searchOwner(id)).build();
    }

    @Path("/{id}/repairs")
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPropertyRepairs(@PathParam("id") long id) {
        return Response.ok().entity(propertyService.findRepairsOfProperty(id)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
//    @RolesAllowed("ADMIN")
    public Response saveProperty(PropertyDto propertyDto) {
        return Response.ok().entity(propertyService.create(propertyDto)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
//    @RolesAllowed("ADMIN")
    public Response updateProperty(PropertyDto propertyDto) {
        return Response.ok().entity(propertyService.update(propertyDto)).build();
    }

    @PUT
    @Path("/{id}/{vat}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
//    @RolesAllowed("ADMIN")
    public Response updatePropertyOwner(@PathParam("id") long id, @PathParam("vat") String vat) {
        return Response.ok().entity(propertyService.updateOwner(id, vat)).build();
    }

    @Path("/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @PermitAll
//    @RolesAllowed("ADMIN")
    public void deleteProperty(@PathParam("id") long id) {
        propertyService.delete(id);
    }

    @DELETE
    @PermitAll
//    @RolesAllowed("ADMIN")
    public void deleteAll() {
        propertyService.deleteAll();
    }
}
