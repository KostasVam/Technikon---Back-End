package com.technikon.final_project_ed.resources;

import com.technikon.final_project_ed.dto.RepairDto;
import com.technikon.final_project_ed.service.RepairService;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Path("repairs")
@Slf4j
@NoArgsConstructor
public class RepairResource {

    @Inject
    private RepairService repairService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok().entity(repairService.getAll()).build();
    }

    @Path("/{id}")
    @PermitAll
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRepair(@PathParam("id") long id) {
        return Response.ok().entity(repairService.searchById(id)).build();
    }

    @PUT
    @Path("/{repairId}/{propertyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @PermitAll
//    @RolesAllowed("ADMIN")
    public Response updateRepairProperty(@PathParam("repairId") long repairId, @PathParam("propertyId") long propertyId) {
        return Response.ok().entity(repairService.updatePropertiesId(repairId, propertyId)).build();
    }

    @POST
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
//    @RolesAllowed("ADMIN")
    public Response saveRepair(RepairDto repairDto) {
        return Response.ok().entity(repairService.create(repairDto)).build();
    }

    @PUT
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
//    @RolesAllowed("ADMIN")
    public Response updateRepair(RepairDto repairDto) {
        return Response.ok().entity(repairService.update(repairDto)).build();
    }

    @Path("/{id}")
    @DELETE
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
//    @RolesAllowed("ADMIN")
    public void deleteProperty(@PathParam("id") long id) {
        repairService.delete(id);
    }

    @DELETE
    @PermitAll
//    @RolesAllowed("ADMIN")
    public void deleteAll() {
        repairService.deleteAll();
    }
}
