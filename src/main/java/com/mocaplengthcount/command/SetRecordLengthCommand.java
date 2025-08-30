package com.mocaplengthcount.command;

import com.mt1006.mocap.command.CommandInfo;
import com.mt1006.mocap.mocap.playing.RecordingData;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ObjectiveArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class SetRecordLengthCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("setrecordlength")
                .then(Commands.argument("player", EntityArgument.player())
                    .then(Commands.argument("recordName", StringArgumentType.string())
                        .then(Commands.argument("objective", ObjectiveArgument.objective())
                            .executes(SetRecordLengthCommand::execute))))
        );
    }

    private static int execute(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(ctx, "player");
        String recordName = StringArgumentType.getString(ctx, "recordName");
        Objective objective = ObjectiveArgument.getObjective(ctx, "objective");

        RecordingData data = new RecordingData();
        CommandInfo info = new CommandInfo(ctx);
        boolean success = data.load(info, recordName);

        if (!success) {
            info.sendFailure("Failed to load recording or unsupported version.");
            return 0;
        }

        int tickCount = (int) data.tickCount;

        // Ajoute la valeur au scoreboard
        Scoreboard scoreboard = ctx.getSource().getServer().getScoreboard();
        scoreboard.getOrCreatePlayerScore(player.getScoreboardName(), objective).setScore(tickCount);

        info.sendSuccess("Score mis à jour : " + player.getName().getString() + " → " + tickCount + " ticks");
        return 1;
    }

}