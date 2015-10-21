package contentTweaker.content;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.impl.client.ContentEncodingHttpClient;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import com.google.common.base.Strings;

import contentTweaker.api.EnchantHelper;
import contentTweaker.api.EnchantHelper.EnchantmentWithLevel;
import contentTweaker.content.materials.MaterialCustom;
import contentTweaker.helpers.ContentHelper;

@ZenClass("mods.content.Material")
public class ContentMaterials {

	@ZenMethod
	public static void registerMaterial(String key, String name, String style, IItemStack resource, int materialID, int harvestLevel, int durability, int miningSpeed, int attack, int reinforced, int primaryColor, int value, double handleModifier, double stonebound, boolean buildParts, int modifiers, String lore, double arrowMass, double arrowBreakChance, int bowDrawSpeed, double bowSpeedMax, @Optional IItemStack[][] nativeModifiers, @Optional String[] nativeEnchantments) {
		if (nativeModifiers == null || nativeModifiers.length == 0) {
			nativeModifiers = new IItemStack[][] {};
		}

		EnchantmentWithLevel[] enchants = null;
		if (nativeEnchantments == null)
			enchants = new EnchantmentWithLevel[0];
		if (nativeEnchantments.length == 0)
			enchants = new EnchantmentWithLevel[0];
		if (nativeEnchantments != null && nativeEnchantments.length > 0) {
			List<EnchantmentWithLevel> out = new ArrayList<EnchantmentWithLevel>();
			for (String string : nativeEnchantments) {
				if (Strings.isNullOrEmpty(string))
					continue;
				EnchantHelper.addToList(EnchantHelper.getEnchantmentWithLevelFromString(string), out);
			}
			enchants = out.toArray(new EnchantmentWithLevel[out.size()]);
		}

		ItemStack[][] modi = new ItemStack[0][];
		if (nativeModifiers.length == 0)
			modi = new ItemStack[0][];
		List<ItemStack[]> output = new ArrayList<ItemStack[]>();
		for (IItemStack[] stackGroups : nativeModifiers) {
			if (stackGroups == null || stackGroups.length == 0)
				continue;
			List<ItemStack> group = new ArrayList<ItemStack>();
			for (IItemStack stackString : stackGroups) {
				if (stackString == null)
					continue;
				ItemStack stack = ContentHelper.toStack(stackString);
				if (stack.stackSize <= 0)
					continue;
				while (stack.stackSize > 1) {
					group.add(stack.splitStack(1));
				}
				group.add(stack);
			}
			output.add(group.toArray(new ItemStack[group.size()]));
		}
		modi = output.toArray(new ItemStack[output.size()][]);

		MineTweakerAPI.apply(new Add(key, name, style, ContentHelper.toStack(resource).copy(), materialID, harvestLevel, durability, miningSpeed, attack, reinforced, primaryColor, value, (float) handleModifier, (float) stonebound, buildParts, modifiers, lore, (float) arrowMass, (float) arrowBreakChance, bowDrawSpeed, (float) bowSpeedMax, modi, enchants));
	}

	private static class Add implements IUndoableAction {
		public String key = "key";
		public String name = "name";
		public String style = "style";
		public ItemStack resource = new ItemStack(Blocks.bedrock);
		public int materialID = 0;
		public int harvestLevel = 0;
		public int durability = 0;
		public int miningSpeed = 0;
		public int attack = 0;
		public int reinforced = 0;
		public int primaryColor = 0;
		public int value = 0;
		public float handleModifier = 0;
		public float stonebound = 0;
		public boolean buildParts = true;
		public int modifiers = 0;
		public String lore = "";
		public float arrowMass = 0;
		public float arrowBreakChance = 0;
		public int bowDrawSpeed = 0;
		public float bowSpeedMax = 0;
		public ItemStack[][] nativeModifiers;
		public EnchantmentWithLevel[] nativeEnchantments;

		public Add(String key, String name, String style, ItemStack resource, int materialID, int harvestLevel, int durability, int miningSpeed, int attack, int reinforced, int primaryColor, int value, float handleModifier, float stonebound, boolean buildParts, int modifiers, String lore, float arrowMass, float arrowBreakChance, int bowDrawSpeed, float bowSpeedMax, ItemStack[][] nativeModifers, EnchantmentWithLevel[] nativeEnchantments) {
			this.key = key;
			this.name = name;
			this.style = style;
			this.resource = resource;
			this.materialID = materialID;
			this.harvestLevel = harvestLevel;
			this.durability = durability;
			this.miningSpeed = miningSpeed;
			this.attack = attack;
			this.reinforced = reinforced;
			this.primaryColor = primaryColor;
			this.value = value;
			this.handleModifier = handleModifier;
			this.stonebound = stonebound;
			this.buildParts = buildParts;
			this.modifiers = modifiers;
			this.lore = lore;
			this.arrowMass = arrowMass;
			this.arrowBreakChance = arrowBreakChance;
			this.bowDrawSpeed = bowDrawSpeed;
			this.bowSpeedMax = bowSpeedMax;
			this.nativeModifiers = nativeModifers;
			this.nativeEnchantments = nativeEnchantments;
		}

		@Override
		public void apply() {
			if (!ContentHelper.ticMaterials.contains(new MaterialCustom(key, name, style, lore, resource, materialID, harvestLevel, durability, miningSpeed, attack, reinforced, primaryColor, value, handleModifier, stonebound, buildParts, modifiers, arrowMass, arrowBreakChance, bowSpeedMax, bowDrawSpeed, nativeModifiers, nativeEnchantments))) {
				ContentHelper.ticMaterials.add(new MaterialCustom(key, name, style, lore, resource, materialID, harvestLevel, durability, miningSpeed, attack, reinforced, primaryColor, value, handleModifier, stonebound, buildParts, modifiers, arrowMass, arrowBreakChance, bowSpeedMax, bowDrawSpeed, nativeModifiers, nativeEnchantments));
			}
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public String describe() {
			return "Registering TiC material: " + name + ".";
		}

		@Override
		public String describeUndo() {
			return "Cannot remove materials during runtime.";
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
