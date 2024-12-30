package agency.highlysuspect.simpletrophies2;

import agency.highlysuspect.simpletrophies2.client.ClientProxy;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SimpleTrophies2.MODID)
public class SimpleTrophies2 {
	public static final String MODID = "simpletrophies2";

	public static SimpleTrophies2 INSTANCE;

	public final Logger log;
	public final CommonProxy proxy;
	public final RegistryObject<TrophyBlock> trophy;
	public final RegistryObject<TrophyItem> trophyItem;
	public final RegistryObject<TrophyEditorItem> trophyEditorItem;
	public final RegistryObject<BlockEntityType<TrophyBlockEntity>> trophyBe;

	public SimpleTrophies2() {
		INSTANCE = this;
		log = LogManager.getLogger(MODID);
		proxy = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> CommonProxy::new);

		//i swear they intentionally made this as convoluted as possible
		IEventBus b = FMLJavaModLoadingContext.get().getModEventBus();

		DeferredRegister<Block> blocks = DeferredRegister.create(Registries.BLOCK, MODID);
		blocks.register(b);
		DeferredRegister<Item> items = DeferredRegister.create(Registries.ITEM, MODID);
		items.register(b);
		DeferredRegister<BlockEntityType<?>> bes = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
		bes.register(b);

		trophy = blocks.register("trophy", () -> new TrophyBlock(TrophyBlock.makeProps()));
		trophyItem = items.register("trophy", () -> new TrophyItem(trophy.get(), TrophyItem.makeProps()));
		trophyBe = bes.register("trophy", () -> BlockEntityType.Builder.of(TrophyBlockEntity::new, trophy.get()).build(null));

		trophyEditorItem = items.register("trophy_editor", () -> new TrophyEditorItem(TrophyEditorItem.makeProps()));

		proxy.init(b, this);
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MODID, path);
	}
}
