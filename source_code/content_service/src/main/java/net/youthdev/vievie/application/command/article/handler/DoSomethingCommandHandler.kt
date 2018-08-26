package net.youthdev.vievie.application.command.article.handler

import net.youthdev.vievie.application.command.article.DoSomethingCommand
import net.youthdev.vievie.core.framework.bus.command.CommandHandler
import net.youthdev.vievie.core.infrastructure.persistence.UnitOfWork
import org.jvnet.hk2.annotations.Service
import javax.inject.Inject
import javax.inject.Provider

@Service
class DoSomethingCommandHandler @Inject constructor(
    private val unitOfWorkFactory: Provider<UnitOfWork>
): CommandHandler<Void?, DoSomethingCommand> {

  override fun handle(command: DoSomethingCommand): Void? {
    unitOfWorkFactory.get().use { uow ->
      return null
    }
  }
}