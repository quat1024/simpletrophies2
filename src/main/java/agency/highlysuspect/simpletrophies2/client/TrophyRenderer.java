package agency.highlysuspect.simpletrophies2.client;

import agency.highlysuspect.simpletrophies2.Trophy;
import agency.highlysuspect.simpletrophies2.TrophyBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TrophyRenderer implements BlockEntityRenderer<TrophyBlockEntity> {
	public TrophyRenderer(BlockEntityRendererProvider.Context ctx) {

	}

	//Purposefully bad position hash function - if you place several trophies in a line, their angles correlate and you get a "wave" pattern.
	//The mod is there to try and cut down on float precision issues
	private static int badHash(BlockPos pos) {
		return pos == null ? 0 : ((pos.getX() + pos.getY() + pos.getZ()) * 50) % 16384;
	}

	@Override
	public void render(TrophyBlockEntity be, float partialTicks, PoseStack pose, MultiBufferSource bufs, int light, int overlay) {
		render(be.trophy, badHash(be.getBlockPos()), partialTicks, pose, bufs, light, overlay);
	}

	static void render(Trophy trophy, int positionHash, float partialTicks, PoseStack pose, MultiBufferSource bufs, int light, int overlay) {
		pose.pushPose();

		//todo, mod earlier so you dont run into float precision issues
		float ticksInGame = Ticky.completeTicksInGame + partialTicks;

		float rotation = (ticksInGame * 1.1f + positionHash * 6) % 360;
		float bob = Mth.sin((ticksInGame * 1.25f + positionHash * 47f) / 35.2f) * 0.1f;

		pose.translate(0.5, 0.6, 0.5);
		pose.mulPose(Axis.YP.rotationDegrees(rotation));
		pose.mulPose(Axis.XP.rotation(0.15f)); //pleasant off-axis look
		pose.translate(0, 0.2 + bob, 0);
		pose.scale(2, 2, 2);

		ItemStack icon = new ItemStack(Items.CARROT_ON_A_STICK); //todo read trophy

		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
		if (!icon.isEmpty()) {
			BakedModel bakedmodel = itemRenderer.getModel(icon, null, null, 0);
			//this is just eyeballed to place blocks at around the same size and position as item/generated models
			if(bakedmodel.isGui3d()) {
				pose.translate(0, -0.15, 0);
				pose.scale(1.2f, 1.2f, 1.2f);
			}
			itemRenderer.render(icon, ItemDisplayContext.GROUND, false, pose, bufs, light, OverlayTexture.NO_OVERLAY, bakedmodel);
		}

		pose.popPose();
	}

	public static class Bewlr extends BlockEntityWithoutLevelRenderer {
		public Bewlr() {
			super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
		}

		@Override
		public void renderByItem(ItemStack stack, ItemDisplayContext ctx, PoseStack pose, MultiBufferSource bufs, int light, int overlay) {
			Trophy foo = Trophy.newDefault();
			foo.icon = new ItemStack(Items.APPLE);
			render(foo, 0, Minecraft.getInstance().getPartialTick(), pose, bufs, light, overlay);
		}
	}
}
