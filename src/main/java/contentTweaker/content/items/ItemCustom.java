package contentTweaker.content.items;

import java.util.List;

import fluxedCore.util.StringUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemCustom extends Item {
	String[] lore;

	public ItemCustom(String toolType, int toolLevel, boolean full3D, String[] lore) {
		if (toolType !=null && !toolType.equals("null"))
			setHarvestLevel(toolType, toolLevel);
		if (full3D) {
			setFull3D();
		}
		this.lore = lore;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
		for (String s : lore) {
			list.add(StringUtils.format(stack, player, s));
		}
	}

}
