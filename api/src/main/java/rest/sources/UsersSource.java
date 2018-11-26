package rest.sources;


import beans.UsersBean;
import core.Users;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("users")
@ApplicationScoped
public class UsersSource {

    @Inject
    private UsersBean usersBean;

    @Operation(
            description = "Get all users",
            tags = "users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users received",
                            content = @Content(schema = @Schema(implementation = Users.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Failed to get the users",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @GET
    public Response registerUser(@RequestBody Users user) {
        List<Users> users = usersBean.getUsers();

        return users == null ? Response.status(Response.Status.NOT_FOUND).build() :
                Response.status(Response.Status.OK).entity(users).build();
    }

    @Operation(
            description = "Get user by id",
            tags = "users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User received",
                            content = @Content(schema = @Schema(implementation = Users.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Failed to get the user",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @Path("{id}")
    @GET
    public Response getBikeById(@PathParam("id") int id) {
        Users user = usersBean.getUserById(id);
        return user == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(user).build();
    }
}
