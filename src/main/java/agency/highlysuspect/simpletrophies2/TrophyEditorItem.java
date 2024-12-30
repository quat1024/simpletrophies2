package agency.highlysuspect.simpletrophies2;

import net.minecraft.world.item.Item;

public class TrophyEditorItem extends Item {
	public TrophyEditorItem(Properties props) {
		super(props);
	}

	public static Item.Properties makeProps() {
		return new Item.Properties().stacksTo(1);
	}
}
