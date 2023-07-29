package owo.foliage.legitteleport.handlers

import dev.rollczi.litecommands.command.LiteInvocation
import dev.rollczi.litecommands.handle.InvalidUsageHandler
import dev.rollczi.litecommands.schematic.Schematic
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender

class InvalidUsageHandler : InvalidUsageHandler<CommandSender> {
    override fun handle(sender: CommandSender, invocation: LiteInvocation, schematic: Schematic) {
        val schematics = schematic.schematics
        val baseText = Component.text("Invalid command usage ", NamedTextColor.RED)
        if (schematics.size == 1) {
            sender.sendMessage(
                baseText.append(Component.text(" >> ${schematics[0]}", NamedTextColor.RED))
            )
            return
        }
        sender.sendMessage(baseText)
        schematics.forEach { sender.sendMessage(Component.text(" >> $it ", NamedTextColor.RED)) }
    }
}