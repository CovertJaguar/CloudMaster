package mods.cloudmaster;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void initClient() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void worldChange(WorldEvent.Load event) {
        if (event.world.provider.dimensionId == 0) {
            event.world.provider.setCloudRenderer(CloudRenderer.INSTANCE);
        }
    }

}
