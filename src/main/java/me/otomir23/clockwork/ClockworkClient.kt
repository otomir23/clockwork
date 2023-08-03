package me.otomir23.clockwork

import me.otomir23.clockwork.commands.ReconnectCommand
import me.otomir23.clockwork.core.Reconnect
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents

object ClockworkClient : ClientModInitializer {
    val MOD_ID = "clockwork"

    override fun onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register(Reconnect::onConnect)
        ReconnectCommand.register()
    }
}
