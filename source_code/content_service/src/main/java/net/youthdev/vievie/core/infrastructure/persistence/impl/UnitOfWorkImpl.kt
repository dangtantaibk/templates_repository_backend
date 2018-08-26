package net.youthdev.vievie.core.infrastructure.persistence.impl

import net.youthdev.vievie.core.exception.WrappedAppError
import net.youthdev.vievie.core.infrastructure.persistence.UnitOfWork
import org.hibernate.Hibernate
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.EntityTransaction

class UnitOfWorkImpl @Inject constructor(
    entityManagerFactory: EntityManagerFactory
) : UnitOfWork {

  private val entityManager = entityManagerFactory.createEntityManager()

  override fun getEntityManager(): EntityManager {
    return this.entityManager
  }

  override fun <R> doInTransaction(run: Callable<R>): R {
    var transaction: EntityTransaction? = null
    try {
      transaction = entityManager.transaction
      transaction!!.begin()
      val result = run.call()
      transaction.commit()
      return result
    } catch (ex: Exception) {
      if (transaction != null) {
        transaction.rollback()
      }
      throw WrappedAppError(ex)
    }

  }

  override fun doInTransaction(run: Runnable) {
    doInTransaction(
        Callable {
          run.run()
          null
        }
    )
  }

  override fun prefetch(vararg proxies: Any) {
    proxies.forEach { Hibernate.initialize(it) }
  }

  /**
   * Closes this stream and releases any system resources associated
   * with it. If the stream is already closed then invoking this
   * method has no effect.
   *
   *
   *  As noted in [AutoCloseable.close], cases where the
   * close may fail require careful attention. It is strongly advised
   * to relinquish the underlying resources and to internally
   * *mark* the `Closeable` as closed, prior to throwing
   * the `IOException`.
   *
   * @throws IOException if an I/O error occurs
   */
  override fun close() {
    entityManager.close()
  }
}