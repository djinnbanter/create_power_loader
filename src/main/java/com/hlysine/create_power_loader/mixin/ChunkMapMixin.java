package com.hlysine.create_power_loader.mixin;

import com.hlysine.create_power_loader.config.CPLConfigs;
import com.hlysine.create_power_loader.content.ChunkLoadManager;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChunkMap.class)
public abstract class ChunkMapMixin {

    @Shadow
    @Final
    ServerLevel level;

    @Inject(method = "anyPlayerCloseEnoughForSpawning", at = @At("HEAD"), cancellable = true)
    private void create_power_loader$forceRandomTicks(ChunkPos pChunkPos, CallbackInfoReturnable<Boolean> cir) {
        if (CPLConfigs.server().andesite.enableRandomTicks.get() || CPLConfigs.server().brass.enableRandomTicks.get()) {
            if (ChunkLoadManager.isTickingForSpawning(this.level, pChunkPos)) {
                cir.setReturnValue(true);
            }
        }
    }
}
