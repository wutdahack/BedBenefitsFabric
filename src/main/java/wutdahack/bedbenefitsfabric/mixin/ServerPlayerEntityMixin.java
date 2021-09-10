package wutdahack.bedbenefitsfabric.mixin;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

	private ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
		super(world, pos, yaw, profile);
	}

	@Inject(method = "wakeUp(ZZ)V", cancellable = true, at = @At(value = "INVOKE"))
	private void onWakeUp(boolean bl, boolean updateSleepingPlayers, CallbackInfo info) {
		EntitySleepEvents.STOP_SLEEPING.invoker().onStopSleeping((ServerPlayerEntity)(Object) this, this.getBlockPos());
	}
}
