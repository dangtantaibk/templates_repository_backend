package net.youthdev.vievie.core.infrastructure.persistence

import org.jvnet.hk2.annotations.Contract
import java.io.Closeable
import java.util.concurrent.Callable
import javax.persistence.EntityManager

@Contract
interface UnitOfWork: Closeable {
  fun getEntityManager(): EntityManager
  fun <R> doInTransaction(run: Callable<R>): R
  fun doInTransaction(run: Runnable)
  fun prefetch(vararg proxies: Any)
}