package me.otomir23.clockwork.mixins;

import me.otomir23.clockwork.core.Reconnect;
import me.otomir23.clockwork.screens.ReconnectionScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class DisconnectScreenChoiceMixin {
    @Inject(at = @At("TAIL"), method = "onDisconnected")
    private void onDisconnected(CallbackInfo info) {
        Long d = Reconnect.INSTANCE.getDelay();
        if (d != null) {
            MinecraftClient.getInstance().setScreen(new ReconnectionScreen(d / 1000L));
        }
    }
}
