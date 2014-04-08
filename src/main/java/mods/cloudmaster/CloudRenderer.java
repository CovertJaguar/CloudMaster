/*
 * Copyright (c) CovertJaguar, 2011 http://railcraft.info
 * 
 * This code is the property of CovertJaguar
 * and may only be used with explicit written
 * permission unless otherwise specified on the
 * license page at railcraft.wikispaces.com.
 */
package mods.cloudmaster;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

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
        if (mc.theWorld.provider.isSurfaceWorld()) {
            if (mc.gameSettings.fancyGraphics) {
                renderCloudsFancy(partialTicks, world, mc);
            } else {
                GL11.glDisable(GL11.GL_CULL_FACE);
                float f1 = (float) (mc.renderViewEntity.lastTickPosY + (mc.renderViewEntity.posY - mc.renderViewEntity.lastTickPosY) * (double) partialTicks);
                byte b0 = 32;
                int i = 256 / b0;
                Tessellator tessellator = Tessellator.instance;
                mc.renderEngine.func_110577_a(CLOUD_TEXTURE);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                Vec3 vec3 = world.getCloudColour(partialTicks);
                float f2 = (float) vec3.xCoord;
                float f3 = (float) vec3.yCoord;
                float f4 = (float) vec3.zCoord;
                float f5;

                if (mc.gameSettings.anaglyph) {
                    f5 = (f2 * 30.0F + f3 * 59.0F + f4 * 11.0F) / 100.0F;
                    float f6 = (f2 * 30.0F + f3 * 70.0F) / 100.0F;
                    float f7 = (f2 * 30.0F + f4 * 70.0F) / 100.0F;
                    f2 = f5;
                    f3 = f6;
                    f4 = f7;
                }

                f5 = 4.8828125E-4F;
                double d0 = (double) ((float) cloudTickCounter + partialTicks);
                double d1 = mc.renderViewEntity.prevPosX + (mc.renderViewEntity.posX - mc.renderViewEntity.prevPosX) * (double) partialTicks + d0 * 0.029999999329447746D;
                double d2 = mc.renderViewEntity.prevPosZ + (mc.renderViewEntity.posZ - mc.renderViewEntity.prevPosZ) * (double) partialTicks;
                int j = MathHelper.floor_double(d1 / 2048.0D);
                int k = MathHelper.floor_double(d2 / 2048.0D);
                d1 -= (double) (j * 2048);
                d2 -= (double) (k * 2048);
                float f8 = getCloudHeight() - f1 + 0.33F;
                float f9 = (float) (d1 * (double) f5);
                float f10 = (float) (d2 * (double) f5);
                tessellator.startDrawingQuads();
                tessellator.setColorRGBA_F(f2, f3, f4, 0.8F);

                for (int l = -b0 * i; l < b0 * i; l += b0) {
                    for (int i1 = -b0 * i; i1 < b0 * i; i1 += b0) {
                        tessellator.addVertexWithUV((double) (l + 0), (double) f8, (double) (i1 + b0), (double) ((float) (l + 0) * f5 + f9), (double) ((float) (i1 + b0) * f5 + f10));
                        tessellator.addVertexWithUV((double) (l + b0), (double) f8, (double) (i1 + b0), (double) ((float) (l + b0) * f5 + f9), (double) ((float) (i1 + b0) * f5 + f10));
                        tessellator.addVertexWithUV((double) (l + b0), (double) f8, (double) (i1 + 0), (double) ((float) (l + b0) * f5 + f9), (double) ((float) (i1 + 0) * f5 + f10));
                        tessellator.addVertexWithUV((double) (l + 0), (double) f8, (double) (i1 + 0), (double) ((float) (l + 0) * f5 + f9), (double) ((float) (i1 + 0) * f5 + f10));
                    }
                }

                tessellator.draw();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_CULL_FACE);
            }
        }
    }

    /**
     * Renders the 3d fancy clouds
     */
    public void renderCloudsFancy(float par1, WorldClient world, Minecraft mc) {
        GL11.glDisable(GL11.GL_CULL_FACE);
        float f1 = (float) (mc.renderViewEntity.lastTickPosY + (mc.renderViewEntity.posY - mc.renderViewEntity.lastTickPosY) * (double) par1);
        Tessellator tessellator = Tessellator.instance;
        float f2 = 12.0F;
        float f3 = 4.0F;
        double d0 = (double) ((float) cloudTickCounter + par1);
        double d1 = (mc.renderViewEntity.prevPosX + (mc.renderViewEntity.posX - mc.renderViewEntity.prevPosX) * (double) par1 + d0 * 0.029999999329447746D) / (double) f2;
        double d2 = (mc.renderViewEntity.prevPosZ + (mc.renderViewEntity.posZ - mc.renderViewEntity.prevPosZ) * (double) par1) / (double) f2 + 0.33000001311302185D;
        float f4 = getCloudHeight() - f1 + 0.33F;
        int i = MathHelper.floor_double(d1 / 2048.0D);
        int j = MathHelper.floor_double(d2 / 2048.0D);
        d1 -= (double) (i * 2048);
        d2 -= (double) (j * 2048);
        mc.renderEngine.func_110577_a(CLOUD_TEXTURE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Vec3 vec3 = world.getCloudColour(par1);
        float f5 = (float) vec3.xCoord;
        float f6 = (float) vec3.yCoord;
        float f7 = (float) vec3.zCoord;
        float f8;
        float f9;
        float f10;

        if (mc.gameSettings.anaglyph) {
            f8 = (f5 * 30.0F + f6 * 59.0F + f7 * 11.0F) / 100.0F;
            f10 = (f5 * 30.0F + f6 * 70.0F) / 100.0F;
            f9 = (f5 * 30.0F + f7 * 70.0F) / 100.0F;
            f5 = f8;
            f6 = f10;
            f7 = f9;
        }

        f8 = (float) (d1 * 0.0D);
        f10 = (float) (d2 * 0.0D);
        f9 = 0.00390625F;
        f8 = (float) MathHelper.floor_double(d1) * f9;
        f10 = (float) MathHelper.floor_double(d2) * f9;
        float f11 = (float) (d1 - (double) MathHelper.floor_double(d1));
        float f12 = (float) (d2 - (double) MathHelper.floor_double(d2));
        byte b0 = 8;
        byte b1 = 4;
        float f13 = 9.765625E-4F;
        GL11.glScalef(f2, 1.0F, f2);

        for (int k = 0; k < 2; ++k) {
            if (k == 0) {
                GL11.glColorMask(false, false, false, false);
            } else if (mc.gameSettings.anaglyph) {
                if (EntityRenderer.anaglyphField == 0) {
                    GL11.glColorMask(false, true, true, true);
                } else {
                    GL11.glColorMask(true, false, false, true);
                }
            } else {
                GL11.glColorMask(true, true, true, true);
            }

            for (int l = -b1 + 1; l <= b1; ++l) {
                for (int i1 = -b1 + 1; i1 <= b1; ++i1) {
                    tessellator.startDrawingQuads();
                    float f14 = (float) (l * b0);
                    float f15 = (float) (i1 * b0);
                    float f16 = f14 - f11;
                    float f17 = f15 - f12;

                    if (f4 > -f3 - 1.0F) {
                        tessellator.setColorRGBA_F(f5 * 0.7F, f6 * 0.7F, f7 * 0.7F, 0.8F);
                        tessellator.setNormal(0.0F, -1.0F, 0.0F);
                        tessellator.addVertexWithUV((double) (f16 + 0.0F), (double) (f4 + 0.0F), (double) (f17 + (float) b0), (double) ((f14 + 0.0F) * f9 + f8), (double) ((f15 + (float) b0) * f9 + f10));
                        tessellator.addVertexWithUV((double) (f16 + (float) b0), (double) (f4 + 0.0F), (double) (f17 + (float) b0), (double) ((f14 + (float) b0) * f9 + f8), (double) ((f15 + (float) b0) * f9 + f10));
                        tessellator.addVertexWithUV((double) (f16 + (float) b0), (double) (f4 + 0.0F), (double) (f17 + 0.0F), (double) ((f14 + (float) b0) * f9 + f8), (double) ((f15 + 0.0F) * f9 + f10));
                        tessellator.addVertexWithUV((double) (f16 + 0.0F), (double) (f4 + 0.0F), (double) (f17 + 0.0F), (double) ((f14 + 0.0F) * f9 + f8), (double) ((f15 + 0.0F) * f9 + f10));
                    }

                    if (f4 <= f3 + 1.0F) {
                        tessellator.setColorRGBA_F(f5, f6, f7, 0.8F);
                        tessellator.setNormal(0.0F, 1.0F, 0.0F);
                        tessellator.addVertexWithUV((double) (f16 + 0.0F), (double) (f4 + f3 - f13), (double) (f17 + (float) b0), (double) ((f14 + 0.0F) * f9 + f8), (double) ((f15 + (float) b0) * f9 + f10));
                        tessellator.addVertexWithUV((double) (f16 + (float) b0), (double) (f4 + f3 - f13), (double) (f17 + (float) b0), (double) ((f14 + (float) b0) * f9 + f8), (double) ((f15 + (float) b0) * f9 + f10));
                        tessellator.addVertexWithUV((double) (f16 + (float) b0), (double) (f4 + f3 - f13), (double) (f17 + 0.0F), (double) ((f14 + (float) b0) * f9 + f8), (double) ((f15 + 0.0F) * f9 + f10));
                        tessellator.addVertexWithUV((double) (f16 + 0.0F), (double) (f4 + f3 - f13), (double) (f17 + 0.0F), (double) ((f14 + 0.0F) * f9 + f8), (double) ((f15 + 0.0F) * f9 + f10));
                    }

                    tessellator.setColorRGBA_F(f5 * 0.9F, f6 * 0.9F, f7 * 0.9F, 0.8F);
                    int j1;

                    if (l > -1) {
                        tessellator.setNormal(-1.0F, 0.0F, 0.0F);

                        for (j1 = 0; j1 < b0; ++j1) {
                            tessellator.addVertexWithUV((double) (f16 + (float) j1 + 0.0F), (double) (f4 + 0.0F), (double) (f17 + (float) b0), (double) ((f14 + (float) j1 + 0.5F) * f9 + f8), (double) ((f15 + (float) b0) * f9 + f10));
                            tessellator.addVertexWithUV((double) (f16 + (float) j1 + 0.0F), (double) (f4 + f3), (double) (f17 + (float) b0), (double) ((f14 + (float) j1 + 0.5F) * f9 + f8), (double) ((f15 + (float) b0) * f9 + f10));
                            tessellator.addVertexWithUV((double) (f16 + (float) j1 + 0.0F), (double) (f4 + f3), (double) (f17 + 0.0F), (double) ((f14 + (float) j1 + 0.5F) * f9 + f8), (double) ((f15 + 0.0F) * f9 + f10));
                            tessellator.addVertexWithUV((double) (f16 + (float) j1 + 0.0F), (double) (f4 + 0.0F), (double) (f17 + 0.0F), (double) ((f14 + (float) j1 + 0.5F) * f9 + f8), (double) ((f15 + 0.0F) * f9 + f10));
                        }
                    }

                    if (l <= 1) {
                        tessellator.setNormal(1.0F, 0.0F, 0.0F);

                        for (j1 = 0; j1 < b0; ++j1) {
                            tessellator.addVertexWithUV((double) (f16 + (float) j1 + 1.0F - f13), (double) (f4 + 0.0F), (double) (f17 + (float) b0), (double) ((f14 + (float) j1 + 0.5F) * f9 + f8), (double) ((f15 + (float) b0) * f9 + f10));
                            tessellator.addVertexWithUV((double) (f16 + (float) j1 + 1.0F - f13), (double) (f4 + f3), (double) (f17 + (float) b0), (double) ((f14 + (float) j1 + 0.5F) * f9 + f8), (double) ((f15 + (float) b0) * f9 + f10));
                            tessellator.addVertexWithUV((double) (f16 + (float) j1 + 1.0F - f13), (double) (f4 + f3), (double) (f17 + 0.0F), (double) ((f14 + (float) j1 + 0.5F) * f9 + f8), (double) ((f15 + 0.0F) * f9 + f10));
                            tessellator.addVertexWithUV((double) (f16 + (float) j1 + 1.0F - f13), (double) (f4 + 0.0F), (double) (f17 + 0.0F), (double) ((f14 + (float) j1 + 0.5F) * f9 + f8), (double) ((f15 + 0.0F) * f9 + f10));
                        }
                    }

                    tessellator.setColorRGBA_F(f5 * 0.8F, f6 * 0.8F, f7 * 0.8F, 0.8F);

                    if (i1 > -1) {
                        tessellator.setNormal(0.0F, 0.0F, -1.0F);

                        for (j1 = 0; j1 < b0; ++j1) {
                            tessellator.addVertexWithUV((double) (f16 + 0.0F), (double) (f4 + f3), (double) (f17 + (float) j1 + 0.0F), (double) ((f14 + 0.0F) * f9 + f8), (double) ((f15 + (float) j1 + 0.5F) * f9 + f10));
                            tessellator.addVertexWithUV((double) (f16 + (float) b0), (double) (f4 + f3), (double) (f17 + (float) j1 + 0.0F), (double) ((f14 + (float) b0) * f9 + f8), (double) ((f15 + (float) j1 + 0.5F) * f9 + f10));
                            tessellator.addVertexWithUV((double) (f16 + (float) b0), (double) (f4 + 0.0F), (double) (f17 + (float) j1 + 0.0F), (double) ((f14 + (float) b0) * f9 + f8), (double) ((f15 + (float) j1 + 0.5F) * f9 + f10));
                            tessellator.addVertexWithUV((double) (f16 + 0.0F), (double) (f4 + 0.0F), (double) (f17 + (float) j1 + 0.0F), (double) ((f14 + 0.0F) * f9 + f8), (double) ((f15 + (float) j1 + 0.5F) * f9 + f10));
                        }
                    }

                    if (i1 <= 1) {
                        tessellator.setNormal(0.0F, 0.0F, 1.0F);

                        for (j1 = 0; j1 < b0; ++j1) {
                            tessellator.addVertexWithUV((double) (f16 + 0.0F), (double) (f4 + f3), (double) (f17 + (float) j1 + 1.0F - f13), (double) ((f14 + 0.0F) * f9 + f8), (double) ((f15 + (float) j1 + 0.5F) * f9 + f10));
                            tessellator.addVertexWithUV((double) (f16 + (float) b0), (double) (f4 + f3), (double) (f17 + (float) j1 + 1.0F - f13), (double) ((f14 + (float) b0) * f9 + f8), (double) ((f15 + (float) j1 + 0.5F) * f9 + f10));
                            tessellator.addVertexWithUV((double) (f16 + (float) b0), (double) (f4 + 0.0F), (double) (f17 + (float) j1 + 1.0F - f13), (double) ((f14 + (float) b0) * f9 + f8), (double) ((f15 + (float) j1 + 0.5F) * f9 + f10));
                            tessellator.addVertexWithUV((double) (f16 + 0.0F), (double) (f4 + 0.0F), (double) (f17 + (float) j1 + 1.0F - f13), (double) ((f14 + 0.0F) * f9 + f8), (double) ((f15 + (float) j1 + 0.5F) * f9 + f10));
                        }
                    }

                    tessellator.draw();
                }
            }
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
    }

}
