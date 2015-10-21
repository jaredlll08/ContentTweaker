package contentTweaker.content.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import contentTweaker.Tweaker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FluidCustom extends BlockFluidClassic {
	public IIcon stillIcon;
	public IIcon flowingIcon;
	private String stillIconTexture = "liquid_gray";
	private String flowIconTexture = "liquid_gray_flow";
	private Fluid fluid;

	public String unlocalizedName;
	public int density;
	public boolean gaseous;
	public int luminosity;
	public int temperature;
	public int viscosity;
	public int color;

	public FluidCustom(Fluid fluid, Material mat, String stillIconTexture, String flowIconTexture, String unlocalizedName, int density, boolean gaseous, int luminosity, int temperature, int viscosity, int color) {
		super(fluid, mat);
		setLightLevel(luminosity);
		setHardness(1.0F);
		setBlockName(fluid.getName());
		this.stillIconTexture = ("contenttweaker:" + stillIconTexture);
		this.flowIconTexture = ("contenttweaker:" + flowIconTexture);
		this.color = color;
	}

	@Override
	public void registerBlockIcons(IIconRegister icon) {
		this.stillIcon = icon.registerIcon(this.stillIconTexture);
		this.flowingIcon = icon.registerIcon(this.flowIconTexture);

		getFluid().setIcons(this.stillIcon, this.flowingIcon);
	}

	public IIcon getStillIcon() {
		return this.stillIcon;
	}

	@Override
	public int getRenderColor(int p_149741_1_) {
		return color;
	}

	@Override
	public int getBlockColor() {
		return color;
	}

	@Override
	public int colorMultiplier(IBlockAccess p_149720_1_, int p_149720_2_, int p_149720_3_, int p_149720_4_) {

		return color;
	}

	@Override
	public int getRenderType() {
		return Tweaker.liquidID;
	}

	public IIcon getFlowingIcon() {
		return this.flowingIcon;
	}

	@Override
	public IIcon getIcon(int meta, int side) {
		if (side <= 1) {
			return this.stillIcon;
		}
		return this.flowingIcon;
	}

}