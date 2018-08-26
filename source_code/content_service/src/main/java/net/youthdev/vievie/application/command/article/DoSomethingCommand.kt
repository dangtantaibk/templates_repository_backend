package net.youthdev.vievie.application.command.article

import net.youthdev.vievie.core.framework.bus.command.BaseCommand

class DoSomethingCommand(
    val articleId: Int
): BaseCommand<Void?>()