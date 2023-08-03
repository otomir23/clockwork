package me.otomir23.clockwork.screens

import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.LoadingDisplay
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ClickableWidget
import net.minecraft.client.gui.widget.GridWidget
import net.minecraft.client.gui.widget.SimplePositioningWidget
import net.minecraft.client.gui.widget.TextWidget
import net.minecraft.text.Text
import net.minecraft.util.Util

class ReconnectionScreen(delaySeconds: Long) : Screen(Text.translatable("clockwork.reconnecting", delaySeconds)) {
    private val grid = GridWidget()

    override fun init() {
        this.grid.mainPositioner.alignHorizontalCenter().margin(10)
        val adder = this.grid.createAdder(1)
        adder.add(TextWidget(title, textRenderer))
        this.grid.refreshPositions()
        this.grid.forEachChild { drawableElement: ClickableWidget? ->
            addDrawableChild(
                drawableElement
            )
        }
        initTabNavigation()
    }

    override fun initTabNavigation() {
        SimplePositioningWidget.setPos(grid, this.navigationFocus)
    }

    override fun shouldCloseOnEsc() = false

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(context)
        super.render(context, mouseX, mouseY, delta)
        val string = LoadingDisplay.get(Util.getMeasuringTimeMs())
        context.drawText(
            client!!.textRenderer,
            string,
            client!!.currentScreen!!.width / 2 - client!!.textRenderer.getWidth(string) / 2,
            client!!.textRenderer.fontHeight,
            0x808080,
            false
        )
    }
}