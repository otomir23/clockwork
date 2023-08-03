package me.otomir23.clockwork.core

import net.minecraft.client.network.ServerAddress
import net.minecraft.client.network.ServerInfo
import net.minecraft.network.ClientConnection

data class ConnectionState(
    val connection: ClientConnection,
    val server: ServerInfo,
    val address: ServerAddress
)