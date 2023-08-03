package me.otomir23.clockwork.core

import net.fabricmc.fabric.api.networking.v1.PacketSender
import net.minecraft.client.MinecraftClient
import net.minecraft.client.network.ClientPlayNetworkHandler
import net.minecraft.client.network.ServerAddress
import net.minecraft.text.Text
import java.util.*


object Reconnect {
    private var connection: ConnectionState? = null
    private val timer = Timer()
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

    fun scheduleReconnect(delay: Long) {
        connection?.let {
            this.delay = delay
            it.connection.disconnect(Text.empty())
            timer.schedule(ReconnectTask(it), delay)
        }
    }
}