package contentTweaker.content.fluids;

import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class BlockFluidCustom extends Fluid {

	public int color;

	public BlockFluidCustom(String fluidName, int color) {
		super(fluidName);
		this.color = color;
	}

	@Override
	public int getColor(FluidStack stack) {
		return color;
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public int getColor(World world, int x, int y, int z) {
		return color;
	}
	
}
