package contentTweaker.proxy;

import contentTweaker.Tweaker;
import contentTweaker.client.render.LiquidRenderer;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy {

	@Override
	public void registerRenderers() {
		Tweaker.liquidID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new LiquidRenderer());
	}

}
