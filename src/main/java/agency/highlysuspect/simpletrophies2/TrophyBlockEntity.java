package agency.highlysuspect.simpletrophies2;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TrophyBlockEntity extends BlockEntity {
	public TrophyBlockEntity(BlockPos pos, BlockState state) {
		super(SimpleTrophies2.INSTANCE.trophyBe.get(), pos, state);
	}

	public @NotNull Trophy trophy = Trophy.newDefault();

	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		trophy.save(tag);
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		trophy.load(tag);

		//Since changing the trophy might update the tint color, which gets baked into the chunk
		if(level != null && level.isClientSide()) SimpleTrophies2.INSTANCE.proxy.forceChunkRerender(level, getBlockPos());
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.saveWithoutMetadata();
	}
}
