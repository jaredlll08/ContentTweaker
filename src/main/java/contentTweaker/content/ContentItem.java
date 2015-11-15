package contentTweaker.content;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import net.minecraft.creativetab.CreativeTabs;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import contentTweaker.content.items.ItemCustom;
import contentTweaker.helpers.ContentHelper;
import cpw.mods.fml.common.registry.GameRegistry;

@ZenClass("mods.content.Item")
public class ContentItem {

	@ZenMethod
	public static void registerItem(String name, String unlocalizedName, String textureName, @Optional String tab, @Optional int maxDamage, @Optional int maxStackSize, @Optional String toolType, @Optional int toolLevel, @Optional boolean full3d, @Optional String[] lore) {
		if (toolType != null && toolType.isEmpty()) {
			toolType = "null";
		}
		if (maxStackSize == 0) {
			maxStackSize = 64;
		}
		if (lore == null) {
			lore = new String[0];
		}
		if(!textureName.contains(":")){
			textureName = "contenttweaker:" + textureName;
		}
		MineTweakerAPI.apply(new Add(name, unlocalizedName, textureName, maxDamage, maxStackSize, toolType, toolLevel, full3d, lore));
	}

	private static class Add implements IUndoableAction {

		public String name;
		public String unlocalizedName;
		public String textureName;
		public int maxDamage;
		public int maxStackSize;
		public String toolType;
		public int toolLevel;
		public boolean full3D;
		public String[] lore;

		public Add(String name, String unlocalizedName, String textureName, int maxDamage, int maxStackSize, String toolType, int toolLevel, boolean full3d, String[] lore) {
			this.name = name;
			this.unlocalizedName = unlocalizedName;
			this.textureName = textureName;
			this.maxDamage = maxDamage;
			this.maxStackSize = maxStackSize;
			this.toolType = toolType;
			this.toolLevel = toolLevel;
			full3D = full3d;
			this.lore = lore;
		}

		@Override
		public void apply() {
			if (GameRegistry.findItem("contenttweaker", unlocalizedName) == null) {
				GameRegistry.registerItem(new ItemCustom(toolType, toolLevel, full3D, lore).setMaxDamage(maxDamage).setMaxStackSize(maxStackSize).setTextureName(textureName).setUnlocalizedName(unlocalizedName), unlocalizedName);
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public String describe() {
			return "Registering item: " + name + ".";
		}

		@Override
		public String describeUndo() {
			return "Cannot remove items during runtime.";
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
