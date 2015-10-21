package contentTweaker.client.render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import contentTweaker.Tweaker;
import contentTweaker.content.fluids.FluidCustom;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class LiquidRenderer implements ISimpleBlockRenderingHandler {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
		renderer.renderBlockAsItem(block, 0, 0);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		FluidCustom fluid = (FluidCustom) block;
		renderer.renderBlockLiquid(fluid, x, y, z);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	@Override
	public int getRenderId() {
		return Tweaker.liquidID;
	}
}
