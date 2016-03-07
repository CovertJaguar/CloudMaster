package mods.cloudmaster;

import java.io.File;

import org.apache.commons.lang3.math.NumberUtils;
import gnu.trove.set.TIntSet;
import gnu.trove.set.hash.TIntHashSet;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = CloudMaster.MOD_ID, name = CloudMaster.MOD_ID,
     version = CloudMaster.VERSION,
     clientSideOnly = true,
     acceptedMinecraftVersions = "[1.8.8,1.9)",
     dependencies = "required-after:Forge@[11.15.0.1609,);")
public final class CloudMaster {

    public static final String MOD_ID = "CloudMaster";
    public static final String VERSION = "1.0";
    @Instance(MOD_ID)
    public static CloudMaster instance;
    @SidedProxy(clientSide = "mods.cloudmaster.ClientProxy", serverSide = "mods.cloudmaster.CommonProxy")
    public static CommonProxy proxy;
    public static int cloudheight = 128;

	private static TIntSet allowedDimensions = new TIntHashSet();

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

	public static boolean isDimensionAllowed(int dimensionId) {
		return allowedDimensions.contains(dimensionId);
	}

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(new File(event.getModConfigurationDirectory(), "cloudmaster.cfg"));
        config.load();

        cloudheight = config.get(Configuration.CATEGORY_GENERAL, "cloudheight", 128).getInt(128);

		String dimensionString = config.get(Configuration.CATEGORY_GENERAL, "dimensions", "0", "Format: id,id,id").getString();
		String[] dimensions = dimensionString.split(",");
		for (String dStrO : dimensions) {
			if (dStrO != null) {
				String dStr = dStrO.trim();
				if (NumberUtils.isNumber(dStr)) {
					allowedDimensions.add(new Integer(dStr).intValue());
				}
			}
		}

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
