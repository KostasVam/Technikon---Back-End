package com.technikon.final_project_ed.resources;

import com.technikon.final_project_ed.dto.OwnerDto;
import com.technikon.final_project_ed.service.OwnerService;
import com.technikon.final_project_ed.service.PropertyService;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("owners")
@Slf4j
@NoArgsConstructor
public class OwnerResource {

    @Inject
    private OwnerService ownerService;
    @Inject
    private PropertyService propertyService;

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok().entity(ownerService.getAll()).build();
    }

    @Path("/vat/{vat}")
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwnerByVat(@PathParam("vat") String ownerVat) {
        return Response.ok().entity(ownerService.searchByVat(ownerVat)).build();
    }

    @Path("/{id}")
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwnerById(@PathParam("id") Long id) {
        return Response.ok().entity(ownerService.searchById(id)).build();
    }

    @Path("/{vat}/properties")
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPropertiesByVat(@PathParam("vat") String ownerVat) {
        return Response.ok().entity(propertyService.searchByVatNumber(ownerVat)).build();
    }

    @Path("/{vat}/repairs")
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRepairsByVat(@PathParam("vat") String ownerVat) {
        return Response.ok().entity(ownerService.searchOwnersRepairs(ownerVat)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
//    @RolesAllowed("ADMIN")
    @PermitAll
    public Response saveOwner(OwnerDto ownerDto) {
        log.info("vat: {}, email: {}", ownerDto.getVat(), ownerDto.getEmail());
        return Response.ok().entity(ownerService.create(ownerDto)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
//    @RolesAllowed("ADMIN")
    @PermitAll
    public Response updateOwner(OwnerDto ownerDto) {
        return Response.ok().entity(ownerService.update(ownerDto)).build();
    }

    @Path("/{id}")
    @DELETE
//    @Consumes(MediaType.APPLICATION_JSON)
//    @RolesAllowed("ADMIN")
    @PermitAll
    public void deleteOwner(@PathParam("id") long id) {
        ownerService.delete(id);
    }

    @DELETE
//    @RolesAllowed("ADMIN")
    @PermitAll
    public void deleteAll() {
        ownerService.deleteAll();
    }
}
