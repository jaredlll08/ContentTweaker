package contentTweaker.events;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import tconstruct.library.crafting.ModifyBuilder;
import tconstruct.library.event.ToolCraftEvent;
import tconstruct.library.tools.ToolCore;
import contentTweaker.api.EnchantHelper;
import contentTweaker.api.EnchantHelper.EnchantmentWithLevel;
import contentTweaker.helpers.ContentHelper;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ToolEventHandler {

	@SubscribeEvent
	public void lore(ItemTooltipEvent event) {
		if (event.itemStack.getItem() instanceof ToolCore) {
			if (event.itemStack.stackTagCompound != null) {
				if (event.itemStack.stackTagCompound.getCompoundTag("InfiTool") != null) {
					NBTTagCompound toolTag = event.itemStack.stackTagCompound.getCompoundTag("InfiTool");
					ArrayList<String> list = new ArrayList<String>();

					if (!list.contains(ContentHelper.getLore(toolTag.getInteger("Head"))))
						list.add(ContentHelper.getLore(toolTag.getInteger("Head")));
					if (!list.contains(ContentHelper.getLore(toolTag.getInteger("Handle"))))
						list.add(ContentHelper.getLore(toolTag.getInteger("Handle")));
					if (!list.contains(ContentHelper.getLore(toolTag.getInteger("Accessory"))))
						list.add(ContentHelper.getLore(toolTag.getInteger("Accessory")));
					if (!list.contains(ContentHelper.getLore(toolTag.getInteger("Extra"))))
						list.add(ContentHelper.getLore(toolTag.getInteger("Extra")));
					for (String str : list) {
						if (str != null)
							event.toolTip.add(str);
					}

					boolean toolTip = false;
					if (!toolTip) {
						toolTip = ContentHelper.isTweaker(toolTag.getInteger("Head"));
					}
					if (!toolTip) {
						toolTip = ContentHelper.isTweaker(toolTag.getInteger("Handle"));
					}
					if (!toolTip) {
						toolTip = ContentHelper.isTweaker(toolTag.getInteger("Accessory"));
					}
					if (!toolTip) {
						toolTip = ContentHelper.isTweaker(toolTag.getInteger("Extra"));
					}

					if (toolTip) {
						event.toolTip.add(EnumChatFormatting.DARK_RED + "This tool contains a part added by ContentTweaker.");
					}
				}
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	// Low priority, so that Tickers' WeaponryHandler can do its thing first
	public void craftTool(ToolCraftEvent.NormalTool event) {
		NBTTagCompound toolTag = event.toolTag.getCompoundTag("InfiTool");

		ItemStack result = new ItemStack(event.tool); // Used to apply native
														// modifiers
		result.stackTagCompound = event.toolTag;

		boolean override = false;
		int modifiers = toolTag.getInteger("Modifiers"); // Saved for later
		List<EnchantmentWithLevel> list = new ArrayList<EnchantmentWithLevel>();

		toolTag.setInteger("Modifiers", Integer.MAX_VALUE / 2); // Allows native
																// modifiers to
																// be applied
																// without using
																// up real
																// modifiers
		for (int materialID : new int[] { toolTag.getInteger("Head"), toolTag.getInteger("Handle"), toolTag.getInteger("Accessory"), toolTag.getInteger("Extra") }) {
			if (materialID == 0)
				continue; // No part

			// Extra modifier counter
			modifiers += ContentHelper.getModifiers(materialID); // Extra
																	// modifiers,
																	// added to
																	// the
																	// amount
																	// saved
																	// before.

			// Native TC modifiers
			ItemStack[][] nativeModifiers = ContentHelper.getNativeModifiers(materialID);
			if (nativeModifiers != null && nativeModifiers.length != 0) {
				override = true; // We have at least 1 native modifier so we
									// override the result
				for (ItemStack[] items : nativeModifiers) {
					if (items == null || items.length == 0)
						continue; // null items
					ItemStack tmp = ModifyBuilder.instance.modifyItem(result, items); // Do
																						// the
																						// actual
																						// modification
					if (tmp == null)
						continue; // Unable to comply
					result = tmp;
				}
			}

			// Add native vanilla enchants to list so that there levels add up,
			// instead of the enchantment being applied multiple times.
			EnchantmentWithLevel[] nativeEnchantments = ContentHelper.getNativeEnchantments(materialID);
			if (nativeEnchantments != null && nativeEnchantments.length != 0) {
				for (EnchantmentWithLevel enchantment : nativeEnchantments) {
					if (enchantment != null) {
						EnchantHelper.addToList(enchantment, list);
					}
				}
			}
		}

		// The enchant applies to the itemstack, so we override
		if (!list.isEmpty()) {
			override = true;
			for (EnchantmentWithLevel enchantment : list) {
				enchantment.applyTo(result);
			}
		}

		if (override) {
			result.stackTagCompound.getCompoundTag("InfiTool").setInteger("Modifiers", modifiers); // set
																									// back
																									// the
																									// proper
																									// amount
																									// of
																									// modifiers
			event.overrideResult(result);
		}
		else {
			toolTag.setInteger("Modifiers", modifiers);
		}
	}
}
