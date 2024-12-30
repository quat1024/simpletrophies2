package agency.highlysuspect.simpletrophies2;

import java.util.function.Consumer;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class TrophyItem extends BlockItem {
	public TrophyItem(Block block, Properties props) {
		super(block, props);
	}

	public static Item.Properties makeProps() {
		return new Properties().rarity(Rarity.RARE);
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		SimpleTrophies2.INSTANCE.proxy.setupTrophyItem(this, consumer);
	}

	private static final String VAR_KEY = "TrophyVariant";
	private static final int DEFAULT_VARIANT = TrophyBlock.MIN_VARIANT;

	public void setTrophyVariant(ItemStack stack, int variant) {
		variant = Mth.clamp(variant, TrophyBlock.MIN_VARIANT, TrophyBlock.MAX_VARIANT);

		CompoundTag tag = stack.getTag();
		if(tag == null) {
			if(variant == DEFAULT_VARIANT) return; //no tag and variant == 1: don't add a tag

			tag = new CompoundTag();
			stack.setTag(tag);
		}

		//now tag is not null
		if(variant != DEFAULT_VARIANT) {
			tag.putInt(VAR_KEY, variant);
		} else {
			tag.remove(VAR_KEY);
			if(tag.isEmpty()) stack.setTag(null); //remove the whole tag
		}
	}

	public int getTrophyVariant(ItemStack stack) {
		if(!stack.is(this)) return DEFAULT_VARIANT;

		CompoundTag tag = stack.getTag();
		if(tag == null) return DEFAULT_VARIANT;
		else if(!tag.contains(VAR_KEY)) return DEFAULT_VARIANT;
		else return Mth.clamp(tag.getInt(VAR_KEY), TrophyBlock.MIN_VARIANT, TrophyBlock.MAX_VARIANT);
	}
}
