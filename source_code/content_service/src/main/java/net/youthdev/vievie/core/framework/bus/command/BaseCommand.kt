package net.youthdev.vievie.core.framework.bus.command

open class BaseCommand<R>: Command<R> {
  override fun getCommandType(): String {
    return this.javaClass.name
  }

  override fun withConfidentialInfoRemoved(): Command<R> {
    return this
  }
}