package com.miningmark48.compressedbase.command;

import com.miningmark48.compressedbase.world.StructureBase;
import com.miningmark48.mininglib.utility.ModTranslate;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommandSpawnBase extends CommandBase {

    private final List aliases;

    private final String prefix = "cb";

    public CommandSpawnBase(){
        aliases = new ArrayList();
        aliases.add(String.format("%s_spawn", prefix));
    }

    @Override
    public String getName() {
        return String.format("%s_spawn", prefix);
    }



    @Override
    public String getUsage(ICommandSender sender) {
        return String.format("%s_spawn", prefix);
    }

    @Override
    public List<String> getAliases() {
        return this.aliases;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        World world = sender.getEntityWorld();

        if (!world.isRemote) {

            Random rand = new Random();
            if (!(world instanceof WorldServer))
                return;
            WorldServer worldServer = (WorldServer) world;

            try {
                StructureBase.generateStructure(worldServer, sender.getPosition(), rand, Rotation.NONE, EnumFacing.NORTH, StructureBase.Directions.NONE);
                sender.sendMessage(new TextComponentString(TextFormatting.GREEN + ModTranslate.toLocal("command.spawn_base.spawned")));
            } catch (Exception e) {
                sender.sendMessage(new TextComponentString(TextFormatting.RED + ModTranslate.toLocal("command.spawn_base.error")));
            }

        }
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }

    @Override
    public int compareTo(ICommand o) {
        return 0;
    }

}
