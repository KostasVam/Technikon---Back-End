package com.technikon.final_project_ed.resources;

import com.technikon.final_project_ed.dto.OwnerDto;
import com.technikon.final_project_ed.service.OwnerService;
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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok().entity(ownerService.getAll()).build();
    }

    @Path("/{vat}")
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwnerByVat(@PathParam("vat") long ownerVat) {
        return Response.ok().entity(ownerService.searchByVat(ownerVat)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public Response saveEmployee(OwnerDto ownerDto) {
        log.info("vat: {}, email: {}", ownerDto.getVat(), ownerDto.getEmail());
        return Response.ok().entity(ownerService.create(ownerDto)).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public Response updateProduct(OwnerDto ownerDto) {
        return Response.ok().entity(ownerService.update(ownerDto)).build();
    }

    @Path("/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("ADMIN")
    public void deleteOwner(@PathParam("id") long id) {
        ownerService.delete(id);
    }

    @DELETE
    @RolesAllowed("ADMIN")
    public void deleteAll() {
        ownerService.deleteAll();
    }
}
