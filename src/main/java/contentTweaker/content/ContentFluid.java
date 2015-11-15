package contentTweaker.content;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import contentTweaker.content.fluids.BlockFluidCustom;
import contentTweaker.content.fluids.FluidCustom;
import contentTweaker.content.items.ItemFluidBucket;
import contentTweaker.helpers.ContentHelper;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;

@ZenClass("mods.content.Fluid")
public class ContentFluid {

	@ZenMethod
	public static void registerFluid(String unlocalizedName, int density, boolean gaseous, int luminosity, int temperature, int viscosity, int color, boolean setFire, @Optional int castingMaterialID, @Optional String stillTexture, @Optional String flowingTexture) {
		if (stillTexture == null || stillTexture.isEmpty()) {
			stillTexture = "liquid_gray";
		}
		if (flowingTexture == null || flowingTexture.isEmpty()) {
			flowingTexture = "liquid_gray_flow";
		}
		MineTweakerAPI.apply(new Add(unlocalizedName, density, gaseous, luminosity, temperature, viscosity, color, castingMaterialID, setFire, stillTexture, flowingTexture));
	}

	private static class Add implements IUndoableAction {

		public String unlocalizedName;
		public int density;
		public boolean gaseous;
		public int luminosity;
		public int temperature;
		public int viscosity;
		public int color;
		public int castingMaterialID;
		public boolean setFire;
		public String stillTexture;
		public String flowingTexture;

		public Add(String unlocalizedName, int density, boolean gaseous, int luminosity, int temperature, int viscosity, int color, int castingMaterialID, boolean setFire, String stillTexture, String flowingTexture) {
			this.unlocalizedName = unlocalizedName;
			this.density = density;
			this.gaseous = gaseous;
			this.luminosity = luminosity;
			this.temperature = temperature;
			this.viscosity = viscosity;
			this.color = color;
			this.castingMaterialID = castingMaterialID;
			this.setFire = setFire;
			this.stillTexture = stillTexture;
			this.flowingTexture = flowingTexture;
		}

		@Override
		public void apply() {
			if (!FluidRegistry.isFluidRegistered(unlocalizedName)) {
				Fluid moltenFluid = new BlockFluidCustom(unlocalizedName, color).setLuminosity(luminosity).setDensity(density).setViscosity(viscosity).setTemperature(temperature).setGaseous(true);
				FluidRegistry.registerFluid(moltenFluid);
				Material mat = Material.air;
				if (setFire) {
					mat = Material.lava;
				} else
					mat = Material.water;

				FluidCustom fluidBlock = new FluidCustom(moltenFluid, mat, stillTexture, flowingTexture, unlocalizedName, density, gaseous, luminosity, temperature, viscosity, color);
				fluidBlock.setBlockName(unlocalizedName);
				GameRegistry.registerBlock(fluidBlock, unlocalizedName);

				Item bucket = new ItemFluidBucket(fluidBlock);
				bucket.setUnlocalizedName(unlocalizedName + "_bucket");
				GameRegistry.registerItem(bucket, unlocalizedName + "_bucket");

				if (castingMaterialID > 0) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setString("FluidName", unlocalizedName);
					new FluidStack(moltenFluid, 1).writeToNBT(tag);
					tag.setInteger("MaterialId", castingMaterialID);
					FMLInterModComms.sendMessage("TConstruct", "addPartCastingMaterial", tag);
				}

				ContentHelper.buckets.add((ItemFluidBucket) bucket);
				ContentHelper.fluids.add(fluidBlock);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public String describe() {
			return "Registering fluid: " + unlocalizedName + ".";
		}

		@Override
		public String describeUndo() {
			return "Cannot remove fluids during runtime.";
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public void undo() {

		}

	}


}
