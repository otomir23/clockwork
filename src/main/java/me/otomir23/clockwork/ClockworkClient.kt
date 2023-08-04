package me.otomir23.clockwork

import me.otomir23.clockwork.commands.ReconnectCommand
import me.otomir23.clockwork.core.Reconnect
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents
import org.slf4j.LoggerFactory

object ClockworkClient : ClientModInitializer {
    const val MOD_ID = "clockwork"
    val LOGGER = LoggerFactory.getLogger(javaClass)

    override fun onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register(Reconnect::onConnect)
        ClientPlayConnectionEvents.DISCONNECT.register(Reconnect::onLeave)
        ReconnectCommand.register()
    }
}
