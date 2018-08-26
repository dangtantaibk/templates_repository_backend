package net.youthdev.vievie.presentation

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import net.youthdev.vievie.application.command.article.DoSomethingCommand
import net.youthdev.vievie.application.exceptions.common.ResourceNotExistedException
import net.youthdev.vievie.application.query.article.GetSomethingQuery
import net.youthdev.vievie.core.exception.common.ApiForbiddenException
import net.youthdev.vievie.core.framework.bus.ResponseFilterService
import net.youthdev.vievie.core.framework.bus.command.CommandBus
import net.youthdev.vievie.core.framework.bus.query.QueryBus
import net.youthdev.vievie.core.global.constant.Headers
import net.youthdev.vievie.model.repsonse.article.SomethingDTO
import net.youthdev.vievie.model.request.article.DoSomethingBody
import org.glassfish.grizzly.http.server.Request
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.validation.Valid
import javax.ws.rs.*
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Api(value = "Article APIs")
@Path("articles")
class ArticleResource @Inject constructor(
    private val queryBus: QueryBus,
    private val commandBus: CommandBus
) {

  @ApiOperation(value = "Get something", response = SomethingDTO::class)
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{somethingId: \\d+}")
  fun getSomething(@Context request: Request,
                   @HeaderParam(Headers.AUTHORIZATION) authToken: String,
                   @PathParam("somethingId") somethingId: Int
  ): Any? {
    return queryBus.dispatch(GetSomethingQuery(somethingId))
  }

  @ApiOperation(value = "Do something")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("{somethingId: \\d+}/do_something")
  fun doSomething(@Context request: Request,
                  @HeaderParam(Headers.AUTHORIZATION) authToken: String,
                  @PathParam("somethingId") somethingId: Int,
                  @Valid doSomethingBody: DoSomethingBody?
  ): Any? {
    return commandBus.dispatch(DoSomethingCommand(somethingId))
  }
}