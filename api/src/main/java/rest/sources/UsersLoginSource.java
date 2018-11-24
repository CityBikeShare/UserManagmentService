package rest.sources;

import beans.UsersBean;
import core.Users;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("loginUser")
@ApplicationScoped
public class UsersLoginSource {
    @Inject
    private UsersBean usersBean;

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
    @POST
    public Response loginUser(@QueryParam("uname") String username, @QueryParam("passwd") String password) {
        Users user = usersBean.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(password)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        
        return Response.ok().build();

    }
}
