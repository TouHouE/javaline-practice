package api;

import data.User;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.plugin.openapi.annotations.*;
import res.ErrorResponse;
import j2html.TagCreator.*;

public class UserController {
    @OpenApi(
            summary = "Create user",
            operationId = "create users",
            path = "/users",
            method = HttpMethod.POST,
            tags = {"Users"},
            requestBody = @OpenApiRequestBody(content = {@OpenApiContent(from = NewUserRequest.class)}),
            responses = {
                    @OpenApiResponse(status = "201"),
                    @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)})
            }
    )
    public static void create(Context ctx) {
        NewUserRequest user = ctx.bodyAsClass(NewUserRequest.class);
        UserService.save(user.name, user.email);
        ctx.status(201);
    }

    @OpenApi(
            summary = "Get all users",
            operationId = "getAllUsers",
            path = "/users",
            method = HttpMethod.GET,
            tags = {"User"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = User[].class)})
            }
    )
    public static void getAll(Context ctx) {
        ctx.json(UserService.getAll());
    }

    @OpenApi(
            summary = "Get user by ID",
            operationId = "getUserById",
            path = "/users/:userId",
            method = HttpMethod.GET,
            pathParams = {@OpenApiParam(name = "userId", type = Integer.class, description = "The user ID")},
            tags = {"Users"},
            responses = {
                    @OpenApiResponse(status = "200", content = {@OpenApiContent(from = User.class)}),
                    @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)}),
                    @OpenApiResponse(status = "404", content = {@OpenApiContent(from = ErrorResponse.class)})
            }
    )
    public static void getOne(Context ctx) {
        User user = UserService.findById(validPathParamUserId(ctx));

        if(user == null) {
            throw new NotFoundResponse("User not found");
        } else {
            ctx.json(user);
           // ctx.res.sendRedirect(document());
        }
    }

    @OpenApi(
            summary = "Update user by ID",
            operationId = "updateUserById",
            path = "/users/:userId",
            method = HttpMethod.PATCH,
            pathParams = {@OpenApiParam(name = "userId", type = Integer.class, description = "The user ID")},
            tags = {"User"},
            requestBody = @OpenApiRequestBody(content = {@OpenApiContent(from = NewUserRequest.class)}),
            responses = {
                    @OpenApiResponse(status = "204"),
                    @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)}),
                    @OpenApiResponse(status = "404", content = {@OpenApiContent(from = ErrorResponse.class)})
            }
    )
    public static void update(Context ctx) {
        User user = UserService.findById(validPathParamUserId(ctx));

        if(user == null) {
            throw new NotFoundResponse("User not Found");
        } else {
            NewUserRequest newUser = ctx.bodyAsClass(NewUserRequest.class);
            UserService.update(user.id, newUser.name, newUser.email);
            ctx.status(200);
        }
    }

    @OpenApi(
            summary = "Delete user by ID",
            operationId = "deleteUserById",
            path = "/users/:userId",
            method = HttpMethod.DELETE,
            tags = {"User"},
            responses = {
                    @OpenApiResponse(status = "204"),
                    @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)}),
                    @OpenApiResponse(status = "404", content = {@OpenApiContent(from = ErrorResponse.class)})
            }
    )
    public static void delete(Context ctx) {
        User user = UserService.findById(validPathParamUserId(ctx));

        if(user == null) {
            throw new NotFoundResponse("User not found");
        } else {
            UserService.delete(user.id);
            ctx.status(200);
        }
    }

    private static int validPathParamUserId(Context ctx) {
        return ctx.pathParam("userId", Integer.class).check(id -> id > 0).get();
    }
}
