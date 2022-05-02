package com.technikon.final_project_ed.resources;

import com.technikon.final_project_ed.dto.OwnerDto;
import com.technikon.final_project_ed.model.Owner;
import com.technikon.final_project_ed.service.OwnerService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;

@Path("/owner")
@Slf4j
//@Produces(MediaType.APPLICATION_JSON)
@Produces("text/plain")
public class OwnerResource {

    @Inject
    private OwnerService ownerService;

    @Path("/hello")
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }

    @Path("/links")
    @GET
//    @Produces("text/html")
    @Produces("text/plain")
    public String links() {
//        return "<a href='http://localhost:8080/jakartaeshop-1.0-SNAPSHOT/employee/1'>links</a>";
        return "This is the place";
//        return "<p>This is the place</p>";

    }

    @Path("/employee")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public OwnerDto getEmployee(@QueryParam("id") long ownerVat, @QueryParam("location") String location) {
        return ownerService.searchByVat(ownerVat);
    }

    @Path("/employee/{employeeId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public OwnerDto getEmployee(@PathParam("employeeId") long ownerVat) {
        return ownerService.searchByVat(ownerVat);
    }

    @Path("/employee")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public OwnerDto saveEmployee(Owner owner) {
        return ownerService.create(owner);
    }

}
