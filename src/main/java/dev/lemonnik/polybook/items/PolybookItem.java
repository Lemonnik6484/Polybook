package dev.lemonnik.polybook.items;

import dev.lemonnik.polybook.Polybook;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import eu.pb4.polymer.core.api.item.PolymerItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.nucleoid.packettweaker.PacketContext;

public class PolybookItem extends Item implements PolymerItem {
    public PolybookItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient() && world instanceof ServerWorld serverWorld && user instanceof ServerPlayerEntity serverPlayer) {
            try {
                // get the server from the ServerWorld and run the command (no leading '/')
                serverWorld.getServer().getCommandManager().getDispatcher().execute("polydex", serverPlayer.getCommandSource());
            } catch (CommandSyntaxException e) {
                e.printStackTrace(); // or log to your mod logger
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public @Nullable Identifier getPolymerItemModel(ItemStack stack, PacketContext context) {
        return null;
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, PacketContext packetContext) {
        return Items.KNOWLEDGE_BOOK;
    }
}
