package net.youthdev.vievie.core.framework.bus.command

import net.youthdev.vievie.application.command.article.DoSomethingCommand
import net.youthdev.vievie.application.command.article.handler.DoSomethingCommandHandler
import net.youthdev.vievie.core.exception.RestException
import net.youthdev.vievie.core.exception.bus.NoCommandHandlerMatchedException
import net.youthdev.vievie.core.utils.JsonUtils
import org.apache.commons.logging.LogFactory
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApplicationCommandBus @Inject constructor(
    doSomethingCommandHandler: DoSomethingCommandHandler
): CommandBus {

  private val commandHandlerMap = HashMap<String, CommandHandler<*, *>>()

  init {
    // article context
    commandHandlerMap[DoSomethingCommand::class.java.name] = doSomethingCommandHandler
  }

  override fun <R> dispatch(command: Command<R>): R {
    val handler = commandHandlerMap[command.getCommandType()] ?: throw NoCommandHandlerMatchedException()
    val serializedCommand = JsonUtils.toJson(command.withConfidentialInfoRemoved())

    try {
      LOG.info("Received command: ${command.getCommandType()}")
      val result = (handler as CommandHandler<R, Command<R>>).handle(command) as R
      LOG.debug("Command ${command.getCommandType()} has been handled successfully.")
      return result
    } catch (e: RestException) {
      // Rest exception are errors on api consumer side, so they need to be treated as warning instead of error
      // (not backend error)
      LOG.warn("Exception while handling command ${command.getCommandType()} ($serializedCommand)", e)
      throw e
    } catch (e: Exception) {
      LOG.error("Exception while handling command ${command.getCommandType()} ($serializedCommand)", e)
      throw e
    }
  }

  companion object {
    private val LOG = LogFactory.getLog(ApplicationCommandBus::class.java)
  }
}