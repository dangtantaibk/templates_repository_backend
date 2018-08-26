package net.youthdev.vievie.core.framework.bus.query

open class BaseQuery<R>: Query<R> {
  override fun getType(): String {
    return this.javaClass.name
  }
}