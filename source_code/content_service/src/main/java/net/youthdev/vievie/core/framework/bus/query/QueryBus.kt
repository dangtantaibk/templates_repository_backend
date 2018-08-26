package net.youthdev.vievie.core.framework.bus.query

import org.jvnet.hk2.annotations.Contract

@Contract
interface QueryBus {
  @Throws(Exception::class)
  fun <R> dispatch(query: Query<R>): R
}