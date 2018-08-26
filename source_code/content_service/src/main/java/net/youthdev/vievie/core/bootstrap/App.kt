package net.youthdev.vievie.core.bootstrap

import net.youthdev.vievie.core.exception.WrappedAppError
import org.apache.commons.logging.LogFactory
import org.glassfish.grizzly.http.server.StaticHttpHandler
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory
import java.net.URI

class App(
    private val appName: String
) {

  fun run(baseUri: String) {
    val server = GrizzlyHttpServerFactory.createHttpServer(URI.create(baseUri), AppConfig())
    val staticFileHandler = StaticHttpHandler("/swagger_ui")
    server.serverConfiguration.addHttpHandler(staticFileHandler, "/api_documentation")
    LOG.info("$appName started with WADL available at $baseUri")
    block()
  }

  private fun block() {
    try {
      Thread.currentThread().join()
    } catch (ex: Exception) {
      throw WrappedAppError(ex)
    }
  }

  companion object {
    private val LOG = LogFactory.getLog(App::class.java)
  }
}