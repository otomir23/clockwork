package me.otomir23.clockwork.commands

import me.otomir23.clockwork.core.Reconnect
import net.silkmc.silk.commands.clientCommand

object ReconnectCommand {
    fun register() {
        clientCommand("reconnect") {
            argument<Int>("secondsDelay") { delay ->
                runs {
                    Reconnect.scheduleReconnect(delay() * 1000L)
                }
            }
        }
    }
}