package contentTweaker.events;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import contentTweaker.helpers.ContentHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;

public class TweakerEvents {
	@SubscribeEvent
	public void bucketFill(FillBucketEvent evt) {
		if (evt.current.getItem() == Items.bucket && evt.target.typeOfHit == MovingObjectType.BLOCK) {
			int hitX = evt.target.blockX;
			int hitY = evt.target.blockY;
			int hitZ = evt.target.blockZ;

			if (evt.entityPlayer != null && !evt.entityPlayer.canPlayerEdit(hitX, hitY, hitZ, evt.target.sideHit, evt.current)) {
				return;
			}

			Block bID = evt.world.getBlock(hitX, hitY, hitZ);
			for (int id = 0; id < ContentHelper.fluids.size(); id++) {
				if (bID == ContentHelper.fluids.get(id)) {
					if (evt.entityPlayer.capabilities.isCreativeMode) {
						evt.world.setBlockToAir(hitX, hitY, hitZ);
					} else {
						evt.world.setBlockToAir(hitX, hitY, hitZ);

						evt.setResult(Result.ALLOW);
						evt.result = new ItemStack(ContentHelper.buckets.get(id));
					}
				}
			}
		}
	}
}
