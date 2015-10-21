package contentTweaker.content.items;

import contentTweaker.content.fluids.FluidCustom;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFluidBucket extends ItemBucket {

	public IIcon bucket;
	public IIcon fluid;

	public FluidCustom set;

	public ItemFluidBucket( FluidCustom set) {
		super(set);
		this.set = set;
	}

	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
		return par2 == 1 ? this.bucket : fluid;
	}

	public void registerIcons(IIconRegister icon) {
		this.bucket = icon.registerIcon("contenttweaker:bucket");
		this.fluid = icon.registerIcon("contenttweaker:bucket_overlay");

	}

	@Override
	public int getColorFromItemStack(ItemStack stack, int pass) {
		if (pass == 0)
			return set.color;
		return 0xFFFFFF;
	}
}
