package contentTweaker.content.materials;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import com.google.common.base.Strings;

import contentTweaker.api.EnchantHelper;
import contentTweaker.api.EnchantHelper.EnchantmentWithLevel;

public class MaterialCustom {
	public String key;
	public String name;
	public String style;
	public String lore;
	public ItemStack resource;
	public int materialID;
	public int harvestLevel;
	public int durability;
	public int miningSpeed;
	public int attack;
	public int reinforced;
	public int primaryColor;
	public int value;
	public float handleModifier;
	public float stonebound;
	public boolean buildParts;
	public int modifiers;
	public float arrowMass;
	public float arrowBreakChance;
	public float bowSpeedMax;
	public int bowDrawSpeed;

	public ItemStack[][] nativeModifiers;
	public EnchantmentWithLevel[] nativeEnchantments;

	public MaterialCustom(String key, String name, String style, String lore, ItemStack resource, int materialID, int harvestLevel, int durability, int miningSpeed, int attack, int reinforced, int primaryColor, int value, float handleModifier, float stonebound, boolean buildParts, int modifiers, float arrowMass, float arrowBreakChance, float bowSpeedMax, int bowDrawSpeed, ItemStack[][] nativeModifers, EnchantmentWithLevel[] nativeEnchantments) {
		this.key = key;
		this.name = name;
		this.style = style;
		this.lore = lore;
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
		this.arrowMass = arrowMass;
		this.arrowBreakChance = arrowBreakChance;
		this.bowSpeedMax = bowSpeedMax;
		this.bowDrawSpeed = bowDrawSpeed;
		this.nativeModifiers = nativeModifers;
		this.nativeEnchantments = nativeEnchantments;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getLore() {
		return lore;
	}

	public void setLore(String lore) {
		this.lore = lore;
	}

	public ItemStack getResource() {
		return resource;
	}

	public void setResource(ItemStack resource) {
		this.resource = resource;
	}

	public int getMaterialID() {
		return materialID;
	}

	public void setMaterialID(int materialID) {
		this.materialID = materialID;
	}

	public int getHarvestLevel() {
		return harvestLevel;
	}

	public void setHarvestLevel(int harvestLevel) {
		this.harvestLevel = harvestLevel;
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	public int getMiningSpeed() {
		return miningSpeed;
	}

	public void setMiningSpeed(int miningSpeed) {
		this.miningSpeed = miningSpeed;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getReinforced() {
		return reinforced;
	}

	public void setReinforced(int reinforced) {
		this.reinforced = reinforced;
	}

	public int getPrimaryColor() {
		return primaryColor;
	}

	public void setPrimaryColor(int primaryColor) {
		this.primaryColor = primaryColor;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public float getHandleModifier() {
		return handleModifier;
	}

	public void setHandleModifier(float handleModifier) {
		this.handleModifier = handleModifier;
	}

	public float getStonebound() {
		return stonebound;
	}

	public void setStonebound(float stonebound) {
		this.stonebound = stonebound;
	}

	public boolean isBuildParts() {
		return buildParts;
	}

	public void setBuildParts(boolean buildParts) {
		this.buildParts = buildParts;
	}

	public int getModifiers() {
		return modifiers;
	}

	public void setModifiers(int modifiers) {
		this.modifiers = modifiers;
	}

	public float getArrowMass() {
		return arrowMass;
	}

	public void setArrowMass(float arrowMass) {
		this.arrowMass = arrowMass;
	}

	public float getArrowBreakChance() {
		return arrowBreakChance;
	}

	public void setArrowBreakChance(float arrowBreakChance) {
		this.arrowBreakChance = arrowBreakChance;
	}

	public float getBowSpeedMax() {
		return bowSpeedMax;
	}

	public void setBowSpeedMax(float bowSpeedMax) {
		this.bowSpeedMax = bowSpeedMax;
	}

	public int getBowDrawSpeed() {
		return bowDrawSpeed;
	}

	public void setBowDrawSpeed(int bowDrawSpeed) {
		this.bowDrawSpeed = bowDrawSpeed;
	}

	public ItemStack[][] getNativeModifiers() {
		return nativeModifiers;
	}

	public void setNativeModifiers(ItemStack[][] nativeModifiers) {
		this.nativeModifiers = nativeModifiers;
	}

	public EnchantmentWithLevel[] getNativeEnchantments() {
		return nativeEnchantments;
	}

	public void setNativeEnchantments(EnchantmentWithLevel[] nativeEnchantments) {
		this.nativeEnchantments = nativeEnchantments;
	}
	
	
}
