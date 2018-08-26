package net.youthdev.vievie.core.framework.bus.query

import net.youthdev.vievie.application.query.article.GetSomethingQuery
import net.youthdev.vievie.application.query.article.handler.GetSomethingQueryHandler
import net.youthdev.vievie.core.exception.RestException
import net.youthdev.vievie.core.exception.bus.NoQueryHandlerMatchedException
import net.youthdev.vievie.core.utils.JsonUtils
import org.apache.commons.logging.LogFactory
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationQueryBus @Inject constructor(
    getSomethingQueryHandler: GetSomethingQueryHandler
): QueryBus {

  private val queryHandlerMap = HashMap<String, QueryHandler<*, *>>()

  init {
    // article context
    queryHandlerMap[GetSomethingQuery::class.java.name] = getSomethingQueryHandler
  }

  override fun <R> dispatch(query: Query<R>): R {
    val handler = queryHandlerMap[query.getType()] ?: throw NoQueryHandlerMatchedException()
    val serializedQuery = JsonUtils.toJson(query)

    try {
      LOG.info("Received query: $serializedQuery")
      val result = (handler as QueryHandler<R, Query<R>>).handle(query) as R
      LOG.debug("Query ${query.getType()} has been handled successfully.")
      return result
    } catch (e: RestException) {
      // Rest exception are errors on api consumer side, so they need to be treated as warning instead of error
      // (not backend error)
      LOG.warn("Exception while handling query $serializedQuery", e)
      throw e
    } catch (e: Exception) {
      LOG.error("Exception while handling query $serializedQuery", e)
      throw e
    }
  }

  companion object {
    private val LOG = LogFactory.getLog(ApplicationQueryBus::class.java)
  }
}