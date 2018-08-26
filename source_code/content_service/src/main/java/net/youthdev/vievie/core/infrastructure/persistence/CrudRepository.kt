package net.youthdev.vievie.core.infrastructure.persistence

import org.jvnet.hk2.annotations.Contract
import javax.persistence.LockModeType

@Contract
interface CrudRepository {
  fun with(unitOfWork: UnitOfWork): CrudRepository
  fun <E> find(type: Class<E>, id: Any): E
  fun <E> findByIds(type: Class<E>, ids: List<*>): List<E>
  fun <E> findByIds(type: Class<E>, ids: List<*>, idFieldName: String): List<E>
  fun <E> findByIds(type: Class<E>, ids: List<*>, idFieldName: String, prefetches: List<String>): List<E>
  fun <E> find(type: Class<E>, id: Any, lockModeType: LockModeType): E
  fun <E> findAll(type: Class<E>): List<E>
  fun <E> findAll(type: Class<E>, prefetchedRelations: List<String>): List<E>
  fun <E> persist(entity: E)
  fun <E> delete(entity: E)
}