package net.youthdev.vievie.core.infrastructure.persistence.impl

import net.youthdev.vievie.core.infrastructure.persistence.CrudRepository
import net.youthdev.vievie.core.infrastructure.persistence.UnitOfWork
import org.apache.commons.lang3.StringUtils
import javax.persistence.LockModeType
import javax.persistence.criteria.Fetch
import javax.persistence.criteria.JoinType

class CrudRepositoryImpl: CrudRepository {

  private var unitOfWork: UnitOfWork? = null

  override fun with(unitOfWork: UnitOfWork): CrudRepository {
    this.unitOfWork = unitOfWork
    return this
  }

  override fun <E> find(type: Class<E>, id: Any): E {
    return unitOfWork!!.getEntityManager().find(type, id)
  }

  override fun <E> findByIds(type: Class<E>, ids: List<*>): List<E> {
    return findByIds(type, ids, "id")
  }

  override fun <E> findByIds(type: Class<E>, ids: List<*>, idFieldName: String): List<E> {
    val builder = unitOfWork!!.getEntityManager().criteriaBuilder
    var query = builder.createQuery(type)
    val root = query.from(type)

    query = query.where(root.get<Int>(idFieldName).`in`(ids))
        .select(root)
    return unitOfWork!!.getEntityManager().createQuery(query).resultList
  }

  override fun <E> findByIds(type: Class<E>, ids: List<*>, idFieldName: String, prefetches: List<String>): List<E> {
    val builder = unitOfWork!!.getEntityManager().getCriteriaBuilder()
    var query = builder.createQuery(type)
    val root = query.from(type)

    query = query.where(root.get<Int>(idFieldName).`in`(ids))
        .select(root)

    // Prefetch related entities
    for (prefetch in prefetches) {
      val multiLevelAttrs: Array<String> = StringUtils.split(prefetch, ".")
      if (multiLevelAttrs.size > 1) {
        var fetch: Fetch<*, *> = root.fetch<E, Any>(multiLevelAttrs[0], JoinType.LEFT)
        for (i in 1 until multiLevelAttrs.size) {
          fetch = fetch.fetch<Any, Any>(multiLevelAttrs[i], JoinType.LEFT)
        }
        query.distinct(true)
      } else {
        root.fetch<E, Any>(prefetch, JoinType.LEFT)
      }
    }

    return unitOfWork!!.getEntityManager().createQuery(query).resultList
  }

  override fun <E> find(type: Class<E>, id: Any, lockModeType: LockModeType): E {
    return unitOfWork!!.getEntityManager().find(type, id, lockModeType)
  }

  override fun <E> findAll(type: Class<E>): List<E> {
    return findAll(type, emptyList())
  }

  override fun <E> findAll(type: Class<E>, prefetchedRelations: List<String>): List<E> {
    val builder = unitOfWork!!.getEntityManager().criteriaBuilder
    val query = builder.createQuery(type)
    val root = query.from(type)

    // Prefetch related entities
    for (prefetchedRelation in prefetchedRelations) {
      val multiLevelAttrs = StringUtils.split(prefetchedRelation, ".")
      if (multiLevelAttrs.size > 1) {
        var fetch: Fetch<*, *> = root.fetch<E, Any>(multiLevelAttrs[0], JoinType.LEFT)
        for (i in 1 until multiLevelAttrs.size) {
          fetch = fetch.fetch<Any, Any>(multiLevelAttrs[i], JoinType.LEFT)
        }
        query.distinct(true)
      } else {
        root.fetch<E, Any>(prefetchedRelation, JoinType.LEFT)
      }
    }

    return unitOfWork!!.getEntityManager().createQuery(query.select(root)).resultList
  }

  override fun <E> persist(entity: E) {
    unitOfWork!!.getEntityManager().persist(entity)
  }

  override fun <E> delete(entity: E) {
    unitOfWork!!.getEntityManager().remove(entity)
  }
}