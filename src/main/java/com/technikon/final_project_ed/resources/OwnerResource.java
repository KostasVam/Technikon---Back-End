package com.technikon.final_project_ed.resources;

import com.technikon.final_project_ed.dto.OwnerDto;
import com.technikon.final_project_ed.service.OwnerService;
import com.technikon.final_project_ed.usecases.OwnerUseCase;
import java.util.List;

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

//    @Inject
//    public OwnerResource(OwnerService ownerService) {
//        this.ownerService = ownerService;
//    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<OwnerDto> getAll() {
        return ownerService.getAll();
    }

    @Path("owner")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwnerByVat(@QueryParam("vat") long ownerVat) {
        return Response.ok().entity(ownerService.searchByVat(ownerVat)).build();
    }

    @Path("useCaseCreate")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response useCaseCreate(@QueryParam("vat") long ownerVat) {
        OwnerUseCase uc = new OwnerUseCase();
        uc.createOwnersUseCases();
        return Response.ok().build();
    }

//    @Path("/")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public OwnerDto getOwnerByEmail(@QueryParam("email") String email) {
//        return ownerService.searchByEmail(email);
//    }
//    @Path("/{ownerVat}")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public OwnerDto getOwner2(@PathParam("ownerVat") long ownerVat) {
//        return ownerService.searchByVat(ownerVat);
//    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public OwnerDto saveEmployee(@FormParam("vat") long ownerVat, @FormParam("email") String email) {
        log.info("vat: {}, email: {}", ownerVat, email);
        return ownerService.create(ownerVat, email);
    }

    @Path("/{id}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteOwner(@PathParam("id") long id) {
        ownerService.delete(id);
    }

}
