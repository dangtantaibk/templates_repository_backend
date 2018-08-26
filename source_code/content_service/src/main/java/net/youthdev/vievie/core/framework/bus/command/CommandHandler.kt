package net.youthdev.vievie.core.framework.bus.command

interface CommandHandler<R, C: Command<R>> {
  @Throws(Exception::class)
  fun handle(command: C): R
}