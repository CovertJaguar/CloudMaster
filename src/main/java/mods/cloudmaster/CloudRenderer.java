/*
 * Copyright (c) CovertJaguar, 2011 http://railcraft.info
 * 
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at railcraft.wikispaces.com.
 */
package mods.cloudmaster;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 *
 * @author CovertJaguar <http://www.railcraft.info/>
 */
@SideOnly(Side.CLIENT)
public class CloudRenderer extends IRenderHandler {

    private static final ResourceLocation CLOUD_TEXTURE = new ResourceLocation("textures/environment/clouds.png");
    public static final CloudRenderer INSTANCE = new CloudRenderer();

    private float getCloudHeight() {
        return CloudMaster.cloudheight;
    }

    /**
     * counts the cloud render updates. Used with mod to stagger some updates
     */
    private int cloudTickCounter = 0;

    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc) {
		if (world.provider.isSurfaceWorld())
		{
			if (mc.gameSettings.func_181147_e() == 2)
			{
				this.renderCloudsFancy(partialTicks, world, mc);
			}
			else
			{
				GlStateManager.disableCull();
				float f = (float)(mc.getRenderViewEntity().lastTickPosY + (mc.getRenderViewEntity().posY - mc.getRenderViewEntity().lastTickPosY) * (double)partialTicks);
				int i = 32;
				int j = 8;
				Tessellator tessellator = Tessellator.getInstance();
				WorldRenderer worldrenderer = tessellator.getWorldRenderer();
				mc.renderEngine.bindTexture(CLOUD_TEXTURE);
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
				Vec3 vec3 = world.getCloudColour(partialTicks);
				float f1 = (float)vec3.xCoord;
				float f2 = (float)vec3.yCoord;
				float f3 = (float)vec3.zCoord;

				if (mc.gameSettings.anaglyph)
				{
					float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
					float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
					float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
					f1 = f4;
					f2 = f5;
					f3 = f6;
				}

				float f10 = 4.8828125E-4F;
				double d2 = (double)((float)this.cloudTickCounter + partialTicks);
				double d0 = mc.getRenderViewEntity().prevPosX + (mc.getRenderViewEntity().posX - mc.getRenderViewEntity().prevPosX) * (double)partialTicks + d2 * 0.029999999329447746D;
				double d1 = mc.getRenderViewEntity().prevPosZ + (mc.getRenderViewEntity().posZ - mc.getRenderViewEntity().prevPosZ) * (double)partialTicks;
				int k = MathHelper.floor_double(d0 / 2048.0D);
				int l = MathHelper.floor_double(d1 / 2048.0D);
				d0 = d0 - (double)(k * 2048);
				d1 = d1 - (double)(l * 2048);
				float f7 = getCloudHeight() - f + 0.33F;
				float f8 = (float)(d0 * 4.8828125E-4D);
				float f9 = (float)(d1 * 4.8828125E-4D);
				worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

				for (int i1 = -256; i1 < 256; i1 += 32)
				{
					for (int j1 = -256; j1 < 256; j1 += 32)
					{
						worldrenderer.pos((double)(i1 + 0), (double)f7, (double)(j1 + 32)).tex((double)((float)(i1 + 0) * 4.8828125E-4F + f8), (double)((float)(j1 + 32) * 4.8828125E-4F + f9)).color(f1, f2, f3, 0.8F).endVertex();
						worldrenderer.pos((double)(i1 + 32), (double)f7, (double)(j1 + 32)).tex((double)((float)(i1 + 32) * 4.8828125E-4F + f8), (double)((float)(j1 + 32) * 4.8828125E-4F + f9)).color(f1, f2, f3, 0.8F).endVertex();
						worldrenderer.pos((double)(i1 + 32), (double)f7, (double)(j1 + 0)).tex((double)((float)(i1 + 32) * 4.8828125E-4F + f8), (double)((float)(j1 + 0) * 4.8828125E-4F + f9)).color(f1, f2, f3, 0.8F).endVertex();
						worldrenderer.pos((double)(i1 + 0), (double)f7, (double)(j1 + 0)).tex((double)((float)(i1 + 0) * 4.8828125E-4F + f8), (double)((float)(j1 + 0) * 4.8828125E-4F + f9)).color(f1, f2, f3, 0.8F).endVertex();
					}
				}

				tessellator.draw();
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.disableBlend();
				GlStateManager.enableCull();
			}
		}
    }

    /**
     * Renders the 3d fancy clouds
     */
    public void renderCloudsFancy(float partialTicks, WorldClient world, Minecraft mc) {
		GlStateManager.disableCull();
		float f = (float)(mc.getRenderViewEntity().lastTickPosY + (mc.getRenderViewEntity().posY - mc.getRenderViewEntity().lastTickPosY) * (double)partialTicks);
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		float f1 = 12.0F;
		float f2 = 4.0F;
		double d0 = (double)((float)this.cloudTickCounter + partialTicks);
		double d1 = (mc.getRenderViewEntity().prevPosX + (mc.getRenderViewEntity().posX - mc.getRenderViewEntity().prevPosX) * (double)partialTicks + d0 * 0.029999999329447746D) / 12.0D;
		double d2 = (mc.getRenderViewEntity().prevPosZ + (mc.getRenderViewEntity().posZ - mc.getRenderViewEntity().prevPosZ) * (double)partialTicks) / 12.0D + 0.33000001311302185D;
		float f3 = getCloudHeight() - f + 0.33F;
		int i = MathHelper.floor_double(d1 / 2048.0D);
		int j = MathHelper.floor_double(d2 / 2048.0D);
		d1 = d1 - (double)(i * 2048);
		d2 = d2 - (double)(j * 2048);
		mc.renderEngine.bindTexture(CLOUD_TEXTURE);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		Vec3 vec3 = world.getCloudColour(partialTicks);
		float f4 = (float)vec3.xCoord;
		float f5 = (float)vec3.yCoord;
		float f6 = (float)vec3.zCoord;

		if (mc.gameSettings.anaglyph)
		{
			float f7 = (f4 * 30.0F + f5 * 59.0F + f6 * 11.0F) / 100.0F;
			float f8 = (f4 * 30.0F + f5 * 70.0F) / 100.0F;
			float f9 = (f4 * 30.0F + f6 * 70.0F) / 100.0F;
			f4 = f7;
			f5 = f8;
			f6 = f9;
		}

		float f26 = f4 * 0.9F;
		float f27 = f5 * 0.9F;
		float f28 = f6 * 0.9F;
		float f10 = f4 * 0.7F;
		float f11 = f5 * 0.7F;
		float f12 = f6 * 0.7F;
		float f13 = f4 * 0.8F;
		float f14 = f5 * 0.8F;
		float f15 = f6 * 0.8F;
		float f16 = 0.00390625F;
		float f17 = (float)MathHelper.floor_double(d1) * 0.00390625F;
		float f18 = (float)MathHelper.floor_double(d2) * 0.00390625F;
		float f19 = (float)(d1 - (double)MathHelper.floor_double(d1));
		float f20 = (float)(d2 - (double)MathHelper.floor_double(d2));
		int k = 8;
		int l = 4;
		float f21 = 9.765625E-4F;
		GlStateManager.scale(12.0F, 1.0F, 12.0F);

		for (int i1 = 0; i1 < 2; ++i1)
		{
			if (i1 == 0)
			{
				GlStateManager.colorMask(false, false, false, false);
			}
			else if (mc.gameSettings.anaglyph) {
				if (EntityRenderer.anaglyphField == 0) {
					GlStateManager.colorMask(false, true, true, true);
				} else {
					GlStateManager.colorMask(true, false, false, true);
				}
			} else {
				GlStateManager.colorMask(true, true, true, true);
			}

			for (int j1 = -3; j1 <= 4; ++j1)
			{
				for (int k1 = -3; k1 <= 4; ++k1)
				{
					worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
					float f22 = (float)(j1 * 8);
					float f23 = (float)(k1 * 8);
					float f24 = f22 - f19;
					float f25 = f23 - f20;

					if (f3 > -5.0F)
					{
						worldrenderer.pos((double)(f24 + 0.0F), (double)(f3 + 0.0F), (double)(f25 + 8.0F)).tex((double)((f22 + 0.0F) * 0.00390625F + f17), (double)((f23 + 8.0F) * 0.00390625F + f18)).color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
						worldrenderer.pos((double)(f24 + 8.0F), (double)(f3 + 0.0F), (double)(f25 + 8.0F)).tex((double)((f22 + 8.0F) * 0.00390625F + f17), (double)((f23 + 8.0F) * 0.00390625F + f18)).color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
						worldrenderer.pos((double)(f24 + 8.0F), (double)(f3 + 0.0F), (double)(f25 + 0.0F)).tex((double)((f22 + 8.0F) * 0.00390625F + f17), (double)((f23 + 0.0F) * 0.00390625F + f18)).color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
						worldrenderer.pos((double)(f24 + 0.0F), (double)(f3 + 0.0F), (double)(f25 + 0.0F)).tex((double)((f22 + 0.0F) * 0.00390625F + f17), (double)((f23 + 0.0F) * 0.00390625F + f18)).color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
					}

					if (f3 <= 5.0F)
					{
						worldrenderer.pos((double)(f24 + 0.0F), (double)(f3 + 4.0F - 9.765625E-4F), (double)(f25 + 8.0F)).tex((double)((f22 + 0.0F) * 0.00390625F + f17), (double)((f23 + 8.0F) * 0.00390625F + f18)).color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
						worldrenderer.pos((double)(f24 + 8.0F), (double)(f3 + 4.0F - 9.765625E-4F), (double)(f25 + 8.0F)).tex((double)((f22 + 8.0F) * 0.00390625F + f17), (double)((f23 + 8.0F) * 0.00390625F + f18)).color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
						worldrenderer.pos((double)(f24 + 8.0F), (double)(f3 + 4.0F - 9.765625E-4F), (double)(f25 + 0.0F)).tex((double)((f22 + 8.0F) * 0.00390625F + f17), (double)((f23 + 0.0F) * 0.00390625F + f18)).color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
						worldrenderer.pos((double)(f24 + 0.0F), (double)(f3 + 4.0F - 9.765625E-4F), (double)(f25 + 0.0F)).tex((double)((f22 + 0.0F) * 0.00390625F + f17), (double)((f23 + 0.0F) * 0.00390625F + f18)).color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
					}

					if (j1 > -1)
					{
						for (int l1 = 0; l1 < 8; ++l1)
						{
							worldrenderer.pos((double)(f24 + (float)l1 + 0.0F), (double)(f3 + 0.0F), (double)(f25 + 8.0F)).tex((double)((f22 + (float)l1 + 0.5F) * 0.00390625F + f17), (double)((f23 + 8.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
							worldrenderer.pos((double)(f24 + (float)l1 + 0.0F), (double)(f3 + 4.0F), (double)(f25 + 8.0F)).tex((double)((f22 + (float)l1 + 0.5F) * 0.00390625F + f17), (double)((f23 + 8.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
							worldrenderer.pos((double)(f24 + (float)l1 + 0.0F), (double)(f3 + 4.0F), (double)(f25 + 0.0F)).tex((double)((f22 + (float)l1 + 0.5F) * 0.00390625F + f17), (double)((f23 + 0.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
							worldrenderer.pos((double)(f24 + (float)l1 + 0.0F), (double)(f3 + 0.0F), (double)(f25 + 0.0F)).tex((double)((f22 + (float)l1 + 0.5F) * 0.00390625F + f17), (double)((f23 + 0.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
						}
					}

					if (j1 <= 1)
					{
						for (int i2 = 0; i2 < 8; ++i2)
						{
							worldrenderer.pos((double)(f24 + (float)i2 + 1.0F - 9.765625E-4F), (double)(f3 + 0.0F), (double)(f25 + 8.0F)).tex((double)((f22 + (float)i2 + 0.5F) * 0.00390625F + f17), (double)((f23 + 8.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
							worldrenderer.pos((double)(f24 + (float)i2 + 1.0F - 9.765625E-4F), (double)(f3 + 4.0F), (double)(f25 + 8.0F)).tex((double)((f22 + (float)i2 + 0.5F) * 0.00390625F + f17), (double)((f23 + 8.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
							worldrenderer.pos((double)(f24 + (float)i2 + 1.0F - 9.765625E-4F), (double)(f3 + 4.0F), (double)(f25 + 0.0F)).tex((double)((f22 + (float)i2 + 0.5F) * 0.00390625F + f17), (double)((f23 + 0.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
							worldrenderer.pos((double)(f24 + (float)i2 + 1.0F - 9.765625E-4F), (double)(f3 + 0.0F), (double)(f25 + 0.0F)).tex((double)((f22 + (float)i2 + 0.5F) * 0.00390625F + f17), (double)((f23 + 0.0F) * 0.00390625F + f18)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
						}
					}

					if (k1 > -1)
					{
						for (int j2 = 0; j2 < 8; ++j2)
						{
							worldrenderer.pos((double)(f24 + 0.0F), (double)(f3 + 4.0F), (double)(f25 + (float)j2 + 0.0F)).tex((double)((f22 + 0.0F) * 0.00390625F + f17), (double)((f23 + (float)j2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
							worldrenderer.pos((double)(f24 + 8.0F), (double)(f3 + 4.0F), (double)(f25 + (float)j2 + 0.0F)).tex((double)((f22 + 8.0F) * 0.00390625F + f17), (double)((f23 + (float)j2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
							worldrenderer.pos((double)(f24 + 8.0F), (double)(f3 + 0.0F), (double)(f25 + (float)j2 + 0.0F)).tex((double)((f22 + 8.0F) * 0.00390625F + f17), (double)((f23 + (float)j2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
							worldrenderer.pos((double)(f24 + 0.0F), (double)(f3 + 0.0F), (double)(f25 + (float)j2 + 0.0F)).tex((double)((f22 + 0.0F) * 0.00390625F + f17), (double)((f23 + (float)j2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
						}
					}

					if (k1 <= 1)
					{
						for (int k2 = 0; k2 < 8; ++k2)
						{
							worldrenderer.pos((double)(f24 + 0.0F), (double)(f3 + 4.0F), (double)(f25 + (float)k2 + 1.0F - 9.765625E-4F)).tex((double)((f22 + 0.0F) * 0.00390625F + f17), (double)((f23 + (float)k2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
							worldrenderer.pos((double)(f24 + 8.0F), (double)(f3 + 4.0F), (double)(f25 + (float)k2 + 1.0F - 9.765625E-4F)).tex((double)((f22 + 8.0F) * 0.00390625F + f17), (double)((f23 + (float)k2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
							worldrenderer.pos((double)(f24 + 8.0F), (double)(f3 + 0.0F), (double)(f25 + (float)k2 + 1.0F - 9.765625E-4F)).tex((double)((f22 + 8.0F) * 0.00390625F + f17), (double)((f23 + (float)k2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
							worldrenderer.pos((double)(f24 + 0.0F), (double)(f3 + 0.0F), (double)(f25 + (float)k2 + 1.0F - 9.765625E-4F)).tex((double)((f22 + 0.0F) * 0.00390625F + f17), (double)((f23 + (float)k2 + 0.5F) * 0.00390625F + f18)).color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
						}
					}

					tessellator.draw();
				}
			}
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableBlend();
		GlStateManager.enableCull();
    }

}
