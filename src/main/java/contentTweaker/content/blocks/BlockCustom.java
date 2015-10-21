package contentTweaker.content.blocks;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockCustom extends Block {

	public int renderType;
	public ItemStack[] drops;

	public BlockCustom(Material material, Boolean unbreakable, int renderType, ItemStack[] drops) {
		super(material);
		if (unbreakable) {
			setBlockUnbreakable();
		}
		this.renderType = renderType;
		this.drops = drops;
	}

	@Override
	public int getRenderType() {
		return this.renderType;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return renderType == 0;
	}

	@Override
	public boolean isOpaqueCube() {
		return renderType == 0;
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player) {
		for (ItemStack d : getDrops(world, x, y, z, meta, 0)) {
			EntityItem item = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, d);
			world.spawnEntityInWorld(item);
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		if (drops.length > 0) {
			for (ItemStack d : drops) {
				if (d != null)
					list.add(d.copy());
			}
		} else {
			return super.getDrops(world, x, y, z, metadata, fortune);
		}
		return list;
	}
}
