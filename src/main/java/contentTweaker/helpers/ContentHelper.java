package contentTweaker.helpers;

import java.util.ArrayList;
import java.util.List;

import minetweaker.api.item.IItemStack;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import contentTweaker.api.EnchantHelper.EnchantmentWithLevel;
import contentTweaker.content.fluids.FluidCustom;
import contentTweaker.content.items.ItemFluidBucket;
import contentTweaker.content.materials.MaterialCustom;

public class ContentHelper {

	public static BiMap<String, Material> materials = HashBiMap.create();
	public static BiMap<String, CreativeTabs> tabs = HashBiMap.create();

	public static List<MaterialCustom> ticMaterials = new ArrayList<MaterialCustom>();

	public static List<FluidCustom> fluids = new ArrayList<FluidCustom>();
	public static List<ItemFluidBucket> buckets = new ArrayList<ItemFluidBucket>();

	public static void preInit() {
		materials.put("air", Material.air);
		materials.put("anvil", Material.anvil);
		materials.put("cactus", Material.cactus);
		materials.put("cake", Material.cake);
		materials.put("carpet", Material.carpet);
		materials.put("circuits", Material.circuits);
		materials.put("clay", Material.clay);
		materials.put("cloth", Material.cloth);
		materials.put("coral", Material.coral);
		materials.put("craftedSnow", Material.craftedSnow);
		materials.put("dragonEgg", Material.dragonEgg);
		materials.put("fire", Material.fire);
		materials.put("glass", Material.glass);
		materials.put("gourd", Material.gourd);
		materials.put("grounds", Material.ground);
		materials.put("ice", Material.ice);
		materials.put("iron", Material.iron);
		materials.put("lava", Material.lava);
		materials.put("leaves", Material.leaves);
		materials.put("packedIce", Material.packedIce);
		materials.put("piston", Material.piston);
		materials.put("plants", Material.plants);
		materials.put("portal", Material.portal);
		materials.put("redstoneLight", Material.redstoneLight);
		materials.put("rock", Material.rock);
		materials.put("sand", Material.sand);
		materials.put("snow", Material.snow);
		materials.put("sponge", Material.sponge);
		materials.put("tnt", Material.tnt);
		materials.put("vine", Material.vine);
		materials.put("water", Material.water);
		materials.put("web", Material.web);
		materials.put("wood", Material.wood);

	}

	public static ItemStack toStack(IItemStack iStack) {
		if (iStack == null) {
			return null;
		} else {
			Object internal = iStack.getInternal();
			if (!(internal instanceof ItemStack)) {
				System.out.println("Not a valid item stack: " + iStack);
			}

			return (ItemStack) internal;
		}
	}

	public static ItemStack[] toStacks(IItemStack[] iStack) {
		if (iStack == null) {
			return null;
		} else {
			ItemStack[] output = new ItemStack[iStack.length];
			for (int i = 0; i < iStack.length; i++) {
				output[i] = toStack(iStack[i]);
			}

			return output;
		}
	}

	public static String getLore(int id) {
		for (MaterialCustom mat : ticMaterials) {
			if (mat.getMaterialID() == id) {
				return mat.lore;
			}
		}
		return "";
	}

	public static boolean isTweaker(int id) {
		for (MaterialCustom mat : ticMaterials) {
			if (mat.getMaterialID() == id) {
				return true;
			}
		}
		return false;
	}

	public static int getModifiers(int id) {

		for (MaterialCustom mat : ticMaterials) {
			if (mat.getMaterialID() == id) {
				return mat.getModifiers();
			}
		}
		return 0;
	}

	public static ItemStack[][] getNativeModifiers(int materialID) {
		for (MaterialCustom set : ticMaterials) {
			if (set.materialID == materialID) {
				return set.nativeModifiers;
			}
		}
		return null;
	}

	public static EnchantmentWithLevel[] getNativeEnchantments(int materialID) {
		for (MaterialCustom set : ticMaterials) {
			if (set.materialID == materialID) {
				return set.nativeEnchantments;
			}
		}
		return null;
	}

}
