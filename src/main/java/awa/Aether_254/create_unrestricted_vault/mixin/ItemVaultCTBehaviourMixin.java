package awa.Aether_254.create_unrestricted_vault.mixin;

import com.zurrtum.create.client.AllSpriteShifts;
import com.zurrtum.create.client.content.logistics.vault.ItemVaultCTBehaviour;
import com.zurrtum.create.client.foundation.block.connected.CTSpriteShiftEntry;
import com.zurrtum.create.content.logistics.vault.ItemVaultBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemVaultCTBehaviour.class)
abstract class ItemVaultCTBehaviourMixin {
    @Inject(method = "getShift", at = @At("HEAD"), cancellable = true)
    private void createUnrestrictedVault$selectVerticalTexture(
        BlockState state,
        Direction direction,
        @Nullable TextureAtlasSprite sprite,
        CallbackInfoReturnable<CTSpriteShiftEntry> cir
    ) {
        if (ItemVaultBlock.getVaultBlockAxis(state) != Direction.Axis.Y)
            return;

        boolean large = ItemVaultBlock.isLarge(state);
        if (direction == Direction.UP)
            cir.setReturnValue(large ? AllSpriteShifts.VAULT_FRONT_LARGE : AllSpriteShifts.VAULT_FRONT_MEDIUM);
        else if (direction == Direction.DOWN)
            cir.setReturnValue(large ? AllSpriteShifts.VAULT_BOTTOM_LARGE : AllSpriteShifts.VAULT_BOTTOM_MEDIUM);
        else
            cir.setReturnValue(large ? AllSpriteShifts.VAULT_SIDE_LARGE : AllSpriteShifts.VAULT_SIDE_MEDIUM);
    }

    @Inject(method = "getUpDirection", at = @At("HEAD"), cancellable = true)
    private void createUnrestrictedVault$orientVerticalTextureUp(
        BlockAndTintGetter reader,
        BlockPos pos,
        BlockState state,
        Direction face,
        CallbackInfoReturnable<Direction> cir
    ) {
        if (ItemVaultBlock.getVaultBlockAxis(state) == Direction.Axis.Y && face.getAxis().isHorizontal())
            cir.setReturnValue(Direction.UP);
    }

    @Inject(method = "getRightDirection", at = @At("HEAD"), cancellable = true)
    private void createUnrestrictedVault$orientVerticalTextureRight(
        BlockAndTintGetter reader,
        BlockPos pos,
        BlockState state,
        Direction face,
        CallbackInfoReturnable<Direction> cir
    ) {
        if (ItemVaultBlock.getVaultBlockAxis(state) == Direction.Axis.Y && face.getAxis().isHorizontal())
            cir.setReturnValue(face.getAxis() == Direction.Axis.X ? Direction.SOUTH : Direction.WEST);
    }
}
