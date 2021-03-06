package contentTweaker;

import java.io.File;

import javafx.scene.ParentBuilder;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.PatternBuilder.ItemKey;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.smeltery.TinkerSmelteryEvents;
import tconstruct.tools.TinkerTools;
import minetweaker.MineTweakerAPI;
import minetweaker.MineTweakerImplementationAPI;
import minetweaker.mc1710.brackets.ItemBracketHandler;
import minetweaker.runtime.providers.ScriptProviderDirectory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import contentTweaker.content.ContentBlock;
import contentTweaker.content.ContentFluid;
import contentTweaker.content.ContentItem;
import contentTweaker.content.ContentMaterials;
import contentTweaker.content.materials.MaterialCustom;
import contentTweaker.events.ToolEventHandler;
import contentTweaker.helpers.ContentHelper;
import contentTweaker.proxy.IProxy;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import fluxedCore.util.ResourcePackAssembler;

@Mod(modid = "contenttweaker", name = "Content Tweaker", version = "1.0.4", dependencies = "required-after:MineTweaker3; after:TConstruct;required-after:fluxedcore;")
public class Tweaker {
	public static File configDir = null;
	public static int liquidID;

	@SidedProxy(clientSide = "contentTweaker.proxy.ClientProxy", serverSide = "contentTweaker.proxy.CommonProxy")
	public static IProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		MinecraftForge.EVENT_BUS.register(new ToolEventHandler());
		FMLCommonHandler.instance().bus().register(new ToolEventHandler());
		proxy.registerRenderers();
		MineTweakerAPI.registerBracketHandler(new ItemBracketHandler());
		ItemBracketHandler.rebuildItemRegistry();
		configDir = new File(e.getSuggestedConfigurationFile().getParentFile().getAbsolutePath() + "/" + "contenttweaker");
		fluxedCore.util.ResourcePackAssembler assembler = new ResourcePackAssembler(new File(configDir.getAbsolutePath() + "/ContentTweaker-Resourcepack"), "ContentTweaker Resource Pack", "contenttweaker");
		addItems(assembler);
		addBlocks(assembler);
		addLangs(assembler);
		assembler.assemble().inject();
		ContentHelper.preInit();
		MineTweakerAPI.registerClass(ContentBlock.class);
		MineTweakerAPI.registerClass(ContentItem.class);
		MineTweakerAPI.registerClass(ContentMaterials.class);
		MineTweakerAPI.registerClass(ContentFluid.class);

		File global = new File("contentScripts");
		if (!global.exists()) {
			global.mkdirs();
		}
		MineTweakerImplementationAPI.setScriptProvider(new ScriptProviderDirectory(global));
		MineTweakerImplementationAPI.reload();
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		for (MaterialCustom set : ContentHelper.ticMaterials) {
			NBTTagCompound tag = new NBTTagCompound();
			NBTTagCompound item = new NBTTagCompound();
			tag.setInteger("Id", set.materialID);
			tag.setString("Name", set.key);
			tag.setInteger("HarvestLevel", set.harvestLevel);
			tag.setInteger("Durability", set.durability);
			tag.setInteger("MiningSpeed", set.miningSpeed);
			tag.setInteger("Attack", set.attack);
			tag.setFloat("HandleModifier", set.handleModifier);
			tag.setInteger("Reinforced", set.reinforced);
			tag.setFloat("Stonebound", set.stonebound);
			tag.setString("Style", set.style);
			tag.setInteger("Color", set.primaryColor);
			tag.setInteger("Bow_DrawSpeed", set.bowDrawSpeed);
			tag.setFloat("Bow_ProjectileSpeed", set.bowSpeedMax);
			tag.setFloat("Projectile_Mass", set.arrowMass);
			tag.setFloat("Projectile_Fragility", set.arrowBreakChance);
			FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);

			tag = new NBTTagCompound();
			tag.setInteger("MaterialId", set.materialID);
			tag.setInteger("Value", set.value);
			item = new NBTTagCompound();
			set.resource.writeToNBT(item);
			tag.setTag("Item", item);

			FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);

			if (set.buildParts) {
				tag = new NBTTagCompound();
				tag.setInteger("MaterialId", set.materialID);
				item = new NBTTagCompound();
				set.resource.writeToNBT(item);
				tag.setTag("Item", item);
				item = new NBTTagCompound();
				(new ItemStack(GameRegistry.findItem("TConstruct", "toolShard"), 1, set.materialID)).writeToNBT(item);
				tag.setTag("Shard", item);
				tag.setInteger("Value", set.value);
				FMLInterModComms.sendMessage("TConstruct", "addPartBuilderMaterial", tag);
			}
		}
	}

	private static void initialize(String dir) {
		File temp = new File(configDir.getAbsolutePath() + "/" + dir);
		temp.mkdirs();
	}

	private static void addItems(ResourcePackAssembler assembler) {
		initialize("icons/items");
		for (File f : new File(configDir.getAbsolutePath() + "/icons/items").listFiles()) {
			assembler.addCustomFile("/assets/" + "contenttweaker" + "/textures/items", f);
		}
	}

	private static void addBlocks(ResourcePackAssembler assembler) {
		initialize("icons/blocks");
		for (File f : new File(configDir.getAbsolutePath() + "/icons/blocks").listFiles()) {
			assembler.addCustomFile("/assets/" + "contenttweaker" + "/textures/blocks", f);
		}
	}

	private static void addLangs(ResourcePackAssembler assembler) {
		initialize("lang");
		for (File f : new File(configDir.getAbsolutePath() + "/lang").listFiles()) {
			assembler.addLang(f);
		}
	}
}
