package me.otomir23.clockwork.core

import me.otomir23.clockwork.ClockworkClient.LOGGER
import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.client.network.ServerAddress
import net.minecraft.text.Text
import java.util.*


object Reconnect {
    private var connection: ConnectionState? = null
    private var timer = Timer()
    var delay: Long? = null

    fun onConnect(handler: ClientPlayNetworkHandler, ignoredPacketSender: PacketSender, ignoredMinecraftClient: MinecraftClient) {
        handler.serverInfo?.let {
            connection = ConnectionState(
                handler.connection,
                it,
                ServerAddress.parse(it.address)
            )
        }
    }

    fun onLeave(clientPlayNetworkHandler: ClientPlayNetworkHandler, minecraftClient: MinecraftClient) {
        connection = null
    }

    fun scheduleReconnect(delay: Long) {
        if (delay < 0) return
        connection?.let {
            if (this.delay != null) {
                LOGGER.warn("Task already scheduled, recreating Timer. This is not supposed to happen.")
                timer.cancel()
                timer = Timer()
            }
            this.delay = delay
            it.connection.disconnect(Text.empty())
            timer.schedule(ReconnectTask(it), delay)
        }
    }

    fun cancelReconnect() {
        timer.cancel()
        timer = Timer()
        delay = null
    }
}