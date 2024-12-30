package agency.highlysuspect.simpletrophies2;

import java.util.function.Consumer;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;

public class CommonProxy {
	public void init(IEventBus b, SimpleTrophies2 instance) {
		//...
	}

	public void forceChunkRerender(Level level, BlockPos pos) {
		//no-op
	}

	public void setupTrophyItem(TrophyItem item, Consumer<?> thing) {
		//no-op
	}
}
