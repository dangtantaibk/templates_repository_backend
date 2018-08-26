package net.youthdev.vievie.core.framework.bus.command

import org.jvnet.hk2.annotations.Contract

@Contract
interface CommandBus {
  @Throws(Exception::class)
  abstract fun <R> dispatch(command: Command<R>): R
}