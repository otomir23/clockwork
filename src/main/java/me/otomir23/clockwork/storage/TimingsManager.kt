package me.otomir23.clockwork.storage

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import me.otomir23.clockwork.ClockworkClient.MOD_ID
import net.fabricmc.loader.api.FabricLoader
import kotlin.io.path.exists
import kotlin.io.path.inputStream
import kotlin.io.path.outputStream

@OptIn(ExperimentalSerializationApi::class)
object TimingsManager {
    private val data: MutableList<Timing> = mutableListOf()
    private val file = FabricLoader.getInstance().configDir.resolve("$MOD_ID.json")
    val names: List<String>
        get() = data.map { it.name }

    init {
        if (!file.exists()) {
            save()
        } else {
            data.addAll(Json.decodeFromStream(file.inputStream()))
        }
    }

    private fun save() {
        Json.encodeToStream(data, file.outputStream())
    }

    fun get(name: String): Timing? = data.firstOrNull { it.name == name }

    fun create(timing: Timing): Boolean {
        if (data.any { it.name == timing.name }) return false
        data.add(timing)
        save()
        return true
    }

    fun delete(name: String): Boolean {
        val result = data.remove(get(name))
        save()
        return result
    }
}