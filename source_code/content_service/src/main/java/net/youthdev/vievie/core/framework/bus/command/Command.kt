package net.youthdev.vievie.core.framework.bus.command

interface Command<R> {
  fun getCommandType(): String
  fun withConfidentialInfoRemoved(): Command<R>
}