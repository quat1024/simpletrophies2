package agency.highlysuspect.simpletrophies2.client;

import java.util.function.Consumer;

import agency.highlysuspect.simpletrophies2.CommonProxy;
import agency.highlysuspect.simpletrophies2.SimpleTrophies2;
import agency.highlysuspect.simpletrophies2.TrophyBlockEntity;
import agency.highlysuspect.simpletrophies2.TrophyItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends CommonProxy {
	@Override
	public void init(IEventBus b, SimpleTrophies2 instance) {
		super.init(b, instance);

		b.addListener((RegisterColorHandlersEvent.Block e) ->
			e.register((state, level, pos, layer) ->
				layer == 1 && level != null && pos != null && level.getBlockEntity(pos) instanceof TrophyBlockEntity tbe ? tbe.trophy.color : 0xFFFFFF,
				instance.trophy.get()));

		b.addListener((FMLClientSetupEvent e) -> BlockEntityRenderers.register(instance.trophyBe.get(), TrophyRenderer::new));

		MinecraftForge.EVENT_BUS.addListener((TickEvent.ClientTickEvent e) -> {
			if(e.phase == TickEvent.Phase.START && !Minecraft.getInstance().isPaused()) Ticky.completeTicksInGame++;
		});

		//todo item colors as well
	}

	@Override
	public void forceChunkRerender(Level level, BlockPos pos) {
		if(level != null && level == Minecraft.getInstance().level) {
			Minecraft.getInstance().levelRenderer.setSectionDirty(
				SectionPos.blockToSectionCoord(pos.getX()),
				SectionPos.blockToSectionCoord(pos.getY()),
				SectionPos.blockToSectionCoord(pos.getZ())
			);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setupTrophyItem(TrophyItem item, Consumer<?> thing) {
		((Consumer<IClientItemExtensions>) thing).accept(new IClientItemExtensions() {
			@Override
			public BlockEntityWithoutLevelRenderer getCustomRenderer() {
				return new TrophyRenderer.Bewlr();
			}
		});
	}
}
