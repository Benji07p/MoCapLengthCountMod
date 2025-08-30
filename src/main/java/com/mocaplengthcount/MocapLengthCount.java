package com.mocaplengthcount;

import com.mocaplengthcount.command.SetRecordLengthCommand;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@Mod("mocaplengthcount")
@EventBusSubscriber
public class MocapLengthCount {

    public MocapLengthCount() {}

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        SetRecordLengthCommand.register(event.getDispatcher());
    }
}