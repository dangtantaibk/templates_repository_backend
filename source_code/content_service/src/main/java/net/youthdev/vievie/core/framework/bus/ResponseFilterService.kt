package net.youthdev.vievie.core.framework.bus

import org.glassfish.grizzly.http.server.Request
import org.jvnet.hk2.annotations.Contract
import java.util.concurrent.Callable
import javax.ws.rs.core.Response

@Deprecated("Legacy response filter is deprecated")
interface ResponseFilterService {
  fun <T> filter(request: Request, action: Callable<T>): Response
  fun <T> filter(request: Request, accountId: Int, jwt: String, action: Callable<T>): Response
  fun <T> filter(request: Request, jwt: String, action: Callable<T>): Response
}