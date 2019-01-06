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
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    public Response getUsers(@RequestBody Users user) {
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
    public Response getUserById(@PathParam("id") int userId) {
        Users user = usersBean.getUserById(userId);
        return user == null ? Response.status(Response.Status.NOT_FOUND).build() : Response.ok(user).build();
    }

    @Operation(
            description = "Get user by region",
            tags = "users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Users by region returned",
                            content = @Content(schema = @Schema(implementation = Users.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Failed to get any users",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @Path("region/{region}")
    @GET
    public Response getUserByRegion(@PathParam("region") String region) {
        List<Users> users = usersBean.getUsersByRegion(region);
        return users == null ? Response.status(Response.Status.NOT_FOUND).build() :
                Response.status(Response.Status.OK).entity(users).build();
    }

    @Operation(
            description = "User registration",
            tags = "users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User registration succeeded",
                            content = @Content(schema = @Schema(implementation = Users.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "User registration failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @Path("registerUser")
    @PUT
    public Response registerUser(@RequestBody Users user) {
        if (usersBean.getUserByUsername(user.getUsername()) != null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        user = usersBean.insertUser(user);
        return Response.ok(user).build();
    }

    @Operation(
            description = "User login",
            tags = "users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User login succeeded",
                            content = @Content(schema = @Schema(implementation = Users.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Username or password is not correct",
                            content = @Content(schema = @Schema(implementation = Users.class))
                    )
            }
    )
    @Path("loginUser")
    @POST
    public Response loginUser(@QueryParam("uname") String username, @QueryParam("passwd") String password) {
        Users user = usersBean.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok().build();
    }

    @Operation(
            description = "Update user",
            tags = "user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Update successful",
                            content = @Content(schema = @Schema(implementation = Users.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Update failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @PUT
    @Path("update/{id}")
    public Response updateUser(@PathParam("id") int id, @RequestBody Users user) {
        user = usersBean.updateUser(id, user);
        return Response.ok(user).build();
    }

    @Operation(
            description = "Delete user",
            tags = "users",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User delete succeeded",
                            content = @Content(schema = @Schema(implementation = Users.class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Failed to delete a user",
                            content = @Content(schema = @Schema(implementation = Users.class))
                    )
            }
    )
    @Path("delete/{id}")
    @DELETE
    public Response deleteUser(@PathParam("id") int userId) {
        boolean status = usersBean.deleteUser(userId);
        return status ? Response.status(Response.Status.OK).build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

}
