package agency.highlysuspect.simpletrophies2;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class Trophy {
	public Trophy(ItemStack icon, int color) {
		this.icon = icon;
		this.color = color;
	}

	public static Trophy newDefault() {
		return new Trophy(ItemStack.EMPTY, 0xFFFFFF);
	}

	public ItemStack icon;
	public int color;

	public CompoundTag save(CompoundTag target) {
		target.put("TrophyIcon", icon.save(new CompoundTag()));
		target.putInt("TrophyColor", color);
		return target;
	}

	public void load(CompoundTag tag) {
		icon = ItemStack.of(tag.getCompound("TrophyIcon"));
		color = tag.getInt("TrophyColor");
	}
}
