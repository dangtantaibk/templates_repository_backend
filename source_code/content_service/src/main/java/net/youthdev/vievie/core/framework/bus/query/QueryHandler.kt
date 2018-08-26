package net.youthdev.vievie.core.framework.bus.query

interface QueryHandler<R, Q: Query<R>> {
  @Throws(Exception::class)
  abstract fun handle(query: Q): R
}