package mods.cloudmaster;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.WorldEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void initClient() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @ForgeSubscribe
    public void worldChange(WorldEvent.Load event) {
        if (event.world.provider.dimensionId == 0) {
            event.world.provider.setCloudRenderer(CloudRenderer.INSTANCE);
        }
    }

}
