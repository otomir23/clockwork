package me.otomir23.clockwork.commands

import com.mojang.brigadier.exceptions.SimpleCommandExceptionType
import me.otomir23.clockwork.core.Reconnect
import me.otomir23.clockwork.storage.Timing
import me.otomir23.clockwork.storage.TimingsManager
import net.minecraft.text.Text
import net.silkmc.silk.commands.ClientCommandSourceStack
import net.silkmc.silk.commands.LiteralCommandBuilder
import net.silkmc.silk.commands.clientCommand
import net.silkmc.silk.commands.sendSuccess
import kotlin.math.roundToLong

object ReconnectCommand {
    private val ALREADY_ADDED_EXCEPTION = SimpleCommandExceptionType(Text.translatable("clockwork.commands.already_added"))
    private val INVALID_DELAY_EXCEPTION = SimpleCommandExceptionType(Text.translatable("clockwork.commands.invalid_delay"))
    private val UNKNOWN_TIMING_EXCEPTION = SimpleCommandExceptionType(Text.translatable("clockwork.commands.unknown_timing"))

    fun register() {
        val nodeTree: LiteralCommandBuilder<ClientCommandSourceStack>.() -> Unit = {
            runs {
                source.sendSuccess(Text.translatable("clockwork.commands.root.success"))
            }
            literal("add") {
                argument<String>("name") { name ->
                    argument<Float>("secondsDelay") { delay ->
                        runs {
                            if (delay() < 0) throw INVALID_DELAY_EXCEPTION.create()
                            if (TimingsManager.create(
                                    Timing(
                                        name(),
                                        (delay() * 1000L).roundToLong()
                                    )
                                ))
                                source.sendSuccess(Text.translatable("clockwork.commands.add.success"))
                            else
                                throw ALREADY_ADDED_EXCEPTION.create()
                        }
                    }
                }
            }
            literal("delete") {
                argument<String>("name") { name ->
                    suggestList { TimingsManager.names }
                    runs {
                        if (TimingsManager.delete(name()))
                            source.sendSuccess(Text.translatable("clockwork.commands.delete.success"))
                        else
                            throw UNKNOWN_TIMING_EXCEPTION.create()
                    }
                }
            }
            literal("run") {
                argument<String>("name") { name ->
                    suggestList { TimingsManager.names }
                    runs {
                        TimingsManager.get(name())?.let {
                            Reconnect.scheduleReconnect(it.delay)
                        } ?: throw UNKNOWN_TIMING_EXCEPTION.create()
                    }
                }
            }
        }

        clientCommand("reconnect", builder = nodeTree)

        clientCommand("timing", builder = nodeTree)

        clientCommand("clockwork", builder = nodeTree)
    }
}