package me.otomir23.clockwork.screens

import me.otomir23.clockwork.core.Reconnect
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.*
import net.minecraft.text.Text
import kotlin.math.roundToLong

class ReconnectionScreen(private val delay: Long) : Screen(Text.translatable("clockwork.reconnecting", delay / 1000.0)) {
    private val grid = GridWidget()
    private lateinit var progressText: TextWidget
    private var timePassed = 0L

    override fun init() {
        this.grid.mainPositioner.alignHorizontalCenter().margin(10)
        val adder = this.grid.createAdder(1)
        adder.add(TextWidget(title, textRenderer))
        progressText = TextWidget(Text.empty(), textRenderer)
        adder.add(progressText)
        val buttonWidget = ButtonWidget.builder(Text.translatable("clockwork.reconnect.cancel")) { _ ->
            close()
        }.build()
        adder.add(buttonWidget)
        this.grid.refreshPositions()
        this.grid.forEachChild { drawableElement: ClickableWidget? ->
            addDrawableChild(
                drawableElement
            )
        }
        initTabNavigation()
    }

    override fun close() {
        Reconnect.cancelReconnect()
        super.close()
    }

    override fun initTabNavigation() {
        SimplePositioningWidget.setPos(grid, this.navigationFocus)
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(context)
        super.render(context, mouseX, mouseY, delta)
        timePassed += (delta * 50).roundToLong()
        progressText.message = Text.translatable("clockwork.reconnecting.left", "%.1f".format(((delay - timePassed) / 1000.0)))
    }
}