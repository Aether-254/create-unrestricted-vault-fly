package awa.Aether_254.create_unrestricted_vault.mixin;

import com.zurrtum.create.content.logistics.vault.ItemVaultBlock;
import com.zurrtum.create.content.logistics.vault.ItemVaultItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemVaultItem.class)
abstract class ItemVaultItemMixin {
    @Redirect(
        method = "tryMultiPlace(Lnet/minecraft/world/item/context/BlockPlaceContext;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;offset(III)Lnet/minecraft/core/BlockPos;")
    )
    private BlockPos createUnrestrictedVault$offsetVerticalLayer(
        BlockPos origin,
        int x,
        int y,
        int z,
        BlockPlaceContext context
    ) {
        BlockPos placedOnPos = context.getClickedPos().relative(context.getClickedFace().getOpposite());
        BlockState placedOn = context.getLevel().getBlockState(placedOnPos);
        if (ItemVaultBlock.getVaultBlockAxis(placedOn) == Direction.Axis.Y)
            return origin.offset(x, z, y);
        return origin.offset(x, y, z);
    }
}
