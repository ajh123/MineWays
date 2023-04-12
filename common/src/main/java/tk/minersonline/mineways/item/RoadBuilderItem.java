package tk.minersonline.mineways.item;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tk.minersonline.mineways.MineWaysMod;

public class RoadBuilderItem extends Item {

    public RoadBuilderItem() {
        super(new Item.Settings().group(MineWaysMod.MAIN_TAB).maxDamage(256));
    }
    

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }
        ServerPlayerEntity player = (ServerPlayerEntity) context.getPlayer();
        
        if (player != null && player.canModifyAt(world, pos)) {
            ItemStack mainStack = player.getStackInHand(Hand.MAIN_HAND);
            ItemStack stack = player.getStackInHand(Hand.OFF_HAND);
            if (!(stack.getItem() instanceof BlockItem)) {
                player.sendMessageToClient(
                    Text.translatable("mineways.gui.text.road_builder.blockIncompatible", Text.translatable(stack.getTranslationKey())), 
                    true
                );
                return ActionResult.FAIL;
            }
            Block block = Block.getBlockFromItem(stack.getItem());

            int radius = 1; // Change this to adjust the radius
            for (BlockPos target : BlockPos.iterateOutwards(pos, radius, 0, radius)) {
                if (world.getBlockState(target).isOf(block)) {
                    continue;
                }
                if (!stack.isEmpty()) {
                    if (!player.isCreative()) {
                        stack.decrement(1);
                        mainStack.damage(1, player, (p) -> {
                            p.sendToolBreakStatus(Hand.MAIN_HAND);
                        });
                        world.breakBlock(target, true, player);
                    }
                    world.setBlockState(target, block.getDefaultState());
                }
            }

            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }
}
