package net.youthdev.vievie.core.framework.bus.query

interface Query<R> {
  fun getType(): String
}