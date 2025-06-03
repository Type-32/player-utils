package dev.ctrlneo.playerutils.client.mixin;

import dev.ctrlneo.playerutils.client.PlayerUtilsClient;
import dev.ctrlneo.playerutils.client.modules.content.damageIndicator.utility.DamageIndicatorManager;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(method = "applyDamage", at = @At("HEAD"))
    private void onDamage(ServerWorld world, DamageSource damageSource, float amount, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;

        // Get damage source position
        Vec3d sourcePos = null;

        if (damageSource.getAttacker() != null && !damageSource.getAttacker().getUuid().equals(player.getUuid())) {
            sourcePos = damageSource.getAttacker().getPos();
        }

        if (sourcePos != null) {
            DamageIndicatorManager.INSTANCE.addDamageIndicator(sourcePos, amount);
        } else {
            DamageIndicatorManager.INSTANCE.addDamageIndicator(amount);
        }
    }
}
