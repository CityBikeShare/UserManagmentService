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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("registerUser")
@ApplicationScoped
public class UsersRegisterSource {
    @Inject
    private UsersBean usersBean;

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
                            responseCode = "404",
                            description = "User registration failed",
                            content = @Content(schema = @Schema(implementation = Error.class))
                    )
            }
    )
    @PUT
    public Response registerUser(@RequestBody Users user) {
        if (usersBean.getUserByUsername(user.getUsername()) != null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            user = usersBean.insertUser(user);
            return Response.ok(user).build();
        }
    }

}
