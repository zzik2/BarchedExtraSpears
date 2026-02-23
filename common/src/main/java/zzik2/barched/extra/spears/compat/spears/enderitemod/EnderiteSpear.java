package zzik2.barched.extra.spears.compat.spears.enderitemod;

import net.enderitemc.enderitemod.misc.EnderiteDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import zzik2.barched.minecraft.world.item.SpearItem;

import java.util.List;

//copy from EnderiteSword
public class EnderiteSpear extends SpearItem {

    public EnderiteSpear(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack itemStack, LivingEntity target, LivingEntity attacker) {
        Level level = attacker.level();

        if (attacker instanceof Player player) {
            if (player.getCooldowns().isOnCooldown(this) || level.isClientSide()) {
                return super.hurtEnemy(itemStack, target, attacker);
            }

            double distance = 8.0d; // 8 blocks
            double yRot = (double) attacker.getYRot();
            double xRot = (double) attacker.getXRot();

            double temp = Math.cos(Math.toRadians(xRot));
            double dX = temp * -Math.sin(Math.toRadians(yRot));
            double dY = -Math.sin(Math.toRadians(xRot));
            double dZ = temp * Math.cos(Math.toRadians(yRot));

            Vec3 position = attacker.position().add(0, attacker.getEyeY() - attacker.getY(), 0);
            Vec3 endPosition = new Vec3(
                    position.x - dX * distance,
                    position.y - dY * distance,
                    position.z - dZ * distance
            );
            BlockPos blockPos = BlockPos.containing(endPosition.x, endPosition.y, endPosition.z);
            BlockPos[] blockPoses = { blockPos, blockPos.above(), blockPos };

            double down = endPosition.y;
            double maxDown = down - distance - 1 > level.getMinBuildHeight() ? down - distance - 1 : level.getMinBuildHeight();
            double up = endPosition.y + 1;

            double maxUp = 128;
            if (level.dimensionType().respawnAnchorWorks()) {
                maxUp = up + distance - 1 < 127 ? up + distance - 1 : 127;
            } else {
                maxUp = up + distance - 1 < level.getMaxBuildHeight() ? up + distance - 1 : level.getMaxBuildHeight();
            }

            double near = distance;
            int slot = itemStack.getOrDefault(EnderiteDataComponents.TELEPORT_CHARGE.get(), 0);

            if (level.hasChunkAt(blockPos) && (slot > 0 || player.getAbilities().instabuild)) {
                int foundSpace = 0;
                while (foundSpace == 0 && (blockPoses[0].getY() > maxDown || blockPoses[1].getY() < maxUp)) {
                    if (blockPoses[0].getY() > maxDown) {
                        if (checkBlocks(level, blockPoses[0])) { foundSpace = 1; }
                        else { --down; blockPoses[0] = blockPoses[0].below(); }
                    }
                    if (blockPoses[1].getY() < maxUp) {
                        if (checkBlocks(level, blockPoses[1])) { foundSpace = 2; }
                        else { ++up; blockPoses[1] = blockPoses[1].above(); }
                    }
                    if (near > 2) {
                        if (checkBlocks(level, blockPoses[2])) { foundSpace = 3; }
                        else {
                            --near;
                            blockPoses[2] = blockPoses[2].offset(
                                    (int) Math.floor(dX),
                                    (int) Math.floor(dY),
                                    (int) Math.floor(dZ)
                            );
                        }
                    }
                }
                if (foundSpace == 0 && !level.getBlockState(blockPos).blocksMotion() && !level.getBlockState(blockPos.above()).blocksMotion()) {
                    foundSpace = 4;
                }
                if (foundSpace > 0 && (position.y - dY * near) < maxUp && (position.y - dY * near) > maxDown) {

                    switch (foundSpace) {
                        case 1:
                            attacker.teleportTo(endPosition.x, down > maxDown ? down : maxDown, endPosition.z);
                            break;
                        case 2:
                            attacker.teleportTo(endPosition.x, up < maxUp ? up : maxUp, endPosition.z);
                            break;
                        case 4:
                            near = distance / 2;
                        case 3:
                            down = position.y - dY * near;
                            down = down > level.getMinBuildHeight() ? down : level.getMinBuildHeight() + 1;
                            attacker.teleportTo(position.x - dX * near, down, position.z - dZ * near);
                            break;
                    }
                    level.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0f, 1.0f);

                    if (!player.getAbilities().instabuild) {
                        itemStack.set(EnderiteDataComponents.TELEPORT_CHARGE.get(), slot - 1);
                    }

                    player.getCooldowns().addCooldown(this, 30);
                    level.broadcastEntityEvent(player, (byte) 46);

                    itemStack.setDamageValue(itemStack.getDamageValue() + 1);
                    if (itemStack.getDamageValue() >= itemStack.getMaxDamage()) {
                        itemStack.shrink(1);
                    }
                }
            }
        }

        return super.hurtEnemy(itemStack, target, attacker);

    }

    protected boolean checkBlocks(Level world, BlockPos pos) {
        return world.getBlockState(pos.below()).blocksMotion() && !world.getBlockState(pos).blocksMotion() && !world.getBlockState(pos.above()).blocksMotion();
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
        String charge = itemStack.getOrDefault(EnderiteDataComponents.TELEPORT_CHARGE.get(), 0).toString();
        list.add(Component.translatable("item.enderitemod.enderite_spear.charge").withStyle(new ChatFormatting[] { ChatFormatting.DARK_AQUA }).append(Component.literal(": " + charge)));
        list.add(Component.translatable("item.enderitemod.enderite_spear.tooltip1").withStyle(new ChatFormatting[] { ChatFormatting.GRAY, ChatFormatting.ITALIC }));
        list.add(Component.translatable("item.enderitemod.enderite_spear.tooltip2").withStyle(new ChatFormatting[] { ChatFormatting.GRAY, ChatFormatting.ITALIC }));
        list.add(Component.translatable("item.enderitemod.enderite_spear.tooltip3").withStyle(new ChatFormatting[] { ChatFormatting.GRAY, ChatFormatting.ITALIC }));
    }
}
