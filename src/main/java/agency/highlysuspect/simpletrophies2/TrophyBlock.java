package agency.highlysuspect.simpletrophies2;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TrophyBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {

	public static final int MIN_VARIANT = 1;
	public static final int MAX_VARIANT = 5;
	public static final Property<Integer> VARIANT = IntegerProperty.create("variant", MIN_VARIANT, MAX_VARIANT);

	private static final VoxelShape VARIANT_ONE_SHAPE = variant1Shape();

	public TrophyBlock(Properties props) {
		super(props);

		registerDefaultState(defaultBlockState()
			.setValue(VARIANT, MIN_VARIANT)
			.setValue(BlockStateProperties.WATERLOGGED, false));
	}

	public static Properties makeProps() {
		StatePredicate no = (lvl, pos, st) -> false;

		return Properties.of()
			.mapColor(MapColor.COLOR_BLACK)
			.strength(1.5f)
			.sound(SoundType.AMETHYST)
			.instrument(NoteBlockInstrument.PLING)
			.isSuffocating(no)
			.isViewBlocking(no);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(VARIANT, BlockStateProperties.WATERLOGGED));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext ctx) {
		//TODO maybe different variants will have different shapes.
		return VARIANT_ONE_SHAPE;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockState spr = super.getStateForPlacement(ctx);
		return spr == null ? null : spr
			.setValue(VARIANT, SimpleTrophies2.INSTANCE.trophyItem.get().getTrophyVariant(ctx.getItemInHand()))
			.setValue(BlockStateProperties.WATERLOGGED, ctx.getLevel().getFluidState(ctx.getClickedPos()).is(Fluids.WATER)); //waterlog boilerplate
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState state, Direction fromDir, BlockState fromState, LevelAccessor level, BlockPos pos, BlockPos fromPos) {
		if(state.getValue(BlockStateProperties.WATERLOGGED))
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level)); //waterlog boilerplate
		return super.updateShape(state, fromDir, fromState, level, pos, fromPos);
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state); //waterlog boilerplate
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TrophyBlockEntity(pos, state);
	}

	//exported with a blockbench plugin
	private static VoxelShape variant1Shape() {
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.1875, 0.875), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0, 0.1875, 0, 1, 0.25, 1), BooleanOp.OR);
		return shape;
	}
}