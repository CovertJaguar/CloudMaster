package mods.cloudmaster;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import java.io.File;
import net.minecraftforge.common.Configuration;

@Mod(modid = CloudMaster.MOD_ID, name = CloudMaster.MOD_ID,
     version = CloudMaster.VERSION,
     acceptedMinecraftVersions = "[1.6,1.7)",
     dependencies = "required-after:Forge@[9.10.0.800,);")
public final class CloudMaster {

    public static final String MOD_ID = "CloudMaster";
    public static final String VERSION = "1.0";
    @Instance(MOD_ID)
    public static CloudMaster instance;
    @SidedProxy(clientSide = "mods.cloudmaster.ClientProxy", serverSide = "mods.cloudmaster.CommonProxy")
    public static CommonProxy proxy;
    public static int cloudheight = 128;

    public static CommonProxy getProxy() {
        return proxy;
    }

    public static CloudMaster getMod() {
        return instance;
    }

    public static String getModId() {
        return MOD_ID;
    }

    public static String getVersion() {
        return VERSION;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(new File(event.getModConfigurationDirectory(), "cloudmaster.cfg"));
        config.load();

        cloudheight = config.get(Configuration.CATEGORY_GENERAL, "cloudheight", 128).getInt(128);

        if (config.hasChanged()) {
            config.save();
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.initClient();
    }

}
