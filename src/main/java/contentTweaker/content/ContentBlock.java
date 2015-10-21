package contentTweaker.content;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import contentTweaker.content.blocks.BlockCustom;
import contentTweaker.helpers.ContentHelper;
import cpw.mods.fml.common.registry.GameRegistry;

@ZenClass("mods.content.Block")
public class ContentBlock {

	@ZenMethod
	public static void registerBlock(String name, String unlocalizedName, String material, String textureName, @Optional String creativeTab, @Optional int renderType, @Optional IItemStack[] drops, @Optional boolean unbreakable, @Optional double hardness, @Optional float lightLevel, @Optional int lightOpacity) {
		if (creativeTab == null) {
			creativeTab = CreativeTabs.tabAllSearch.getTabLabel();
		}
		if (unbreakable) {
			hardness = -1;
		}
		if (drops == null) {
			drops = new IItemStack[] {};
		}
		if(!textureName.contains(":")){
			textureName = "contenttweaker:" + textureName;
		}
		ItemStack[] iDrops = ContentHelper.toStacks(drops);
		MineTweakerAPI.apply(new Add(name, unlocalizedName, ContentHelper.materials.get(material), textureName, ContentHelper.tabs.get(creativeTab), renderType, iDrops, unbreakable, (float) hardness, lightLevel, lightOpacity));
	}

	private static class Add implements IUndoableAction {
		public String name;
		public String unlocalizedName;
		public Material material;
		public String textureName;
		public CreativeTabs tab;
		public boolean unbreakable;
		public float hardness;
		public float lightLevel;
		public int lightOpacity;
		public int renderType;
		public ItemStack[] drops;

		public Add(String name, String unlocalizedName, Material material, String textureName, CreativeTabs tab, int renderType, ItemStack[] drops, boolean unbreakable, float hardness, float lightLevel, int lightOpacity) {
			this.name = name;
			this.unlocalizedName = unlocalizedName;
			this.material = material;
			this.textureName = textureName;
			this.tab = tab;
			this.unbreakable = unbreakable;
			this.hardness = hardness;
			this.lightLevel = lightLevel;
			this.lightOpacity = lightOpacity;
			this.renderType = renderType;
			this.drops = drops;
		}

		@Override
		public void apply() {
			if (GameRegistry.findBlock("contenttweaker", unlocalizedName) == null) {
				GameRegistry.registerBlock(new BlockCustom(material, unbreakable, renderType, drops).setBlockTextureName(textureName).setCreativeTab(tab).setBlockName(name).setHardness(hardness).setLightLevel(lightLevel).setLightOpacity(lightOpacity), unlocalizedName);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public String describe() {
			return "Registering block: " + name + ".";
		}

		@Override
		public String describeUndo() {
			return "Cannot remove blocks during runtime.";
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
