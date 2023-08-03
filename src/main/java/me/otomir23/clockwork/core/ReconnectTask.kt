package me.otomir23.clockwork.core

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.ConnectScreen
import net.minecraft.client.gui.screen.TitleScreen
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen
import java.util.TimerTask

class ReconnectTask(private val target: ConnectionState) : TimerTask() {
    override fun run() {
        val c = MinecraftClient.getInstance()
        c.execute {
            ConnectScreen.connect(
                MultiplayerScreen(TitleScreen()),
                c,
                target.address,
                target.server,
                false
            )
        }
    }
}