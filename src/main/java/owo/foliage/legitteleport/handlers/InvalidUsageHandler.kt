package owo.foliage.legitteleport.handlers

import dev.rollczi.litecommands.command.LiteInvocation
import dev.rollczi.litecommands.handle.InvalidUsageHandler
import dev.rollczi.litecommands.schematic.Schematic
import org.bukkit.command.CommandSender
import owo.foliage.legitteleport.LegitTeleport.Companion.mm

class InvalidUsageHandler : InvalidUsageHandler<CommandSender> {
    override fun handle(sender: CommandSender, invocation: LiteInvocation, schematic: Schematic) {
        val schematics = schematic.schematics
        val baseText = mm.deserialize("<red>Invalid command usage</red> ")
        if (schematics.size == 1) {
            sender.sendMessage(baseText.append(mm.deserialize("<red> >> %s</red>".format(schematics[0]))))
            return
        }
        sender.sendMessage(baseText)
        schematics.forEach { sender.sendMessage(mm.deserialize("<red> >> %s</red>".format(it))) }
    }
}