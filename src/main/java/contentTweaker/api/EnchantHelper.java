package contentTweaker.api;

import com.google.common.base.Strings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.lwjgl.Sys;

import java.util.List;

/**
 * @author Dries007
 */
public class EnchantHelper {

    public static class EnchantmentWithLevel implements Cloneable {
        public Enchantment enchantment;
        public int level = 1;

        public EnchantmentWithLevel(Enchantment enchantment, int level)
        {
            this.enchantment = enchantment;
            this.level = level;
        }

        public EnchantmentWithLevel(Enchantment enchantment)
        {
            this.enchantment = enchantment;
        }

        /**
         * No checking preformed!
         */
        public void applyTo(ItemStack stack) {
            stack.addEnchantment(enchantment, Math.min(enchantment.getMaxLevel(), level));
        }

        @Override
        public EnchantmentWithLevel clone()
        {
            return new EnchantmentWithLevel(enchantment, level);
        }

        @Override
        public String toString()
        {
            return enchantment.getTranslatedName(level);
        }
    }

    public static void addToList(EnchantmentWithLevel enchantment, List<EnchantmentWithLevel> list) {
        if (enchantment == null || enchantment.enchantment == null) return;
        for (EnchantmentWithLevel item : list) {
            if (item.enchantment == enchantment.enchantment) {
                item.level += enchantment.level;
                return;
            }
        }
        list.add(enchantment.clone());
    }

    public static EnchantmentWithLevel getEnchantmentWithLevelFromString(String input) {
        if (Strings.isNullOrEmpty(input)) return null;
        return getEnchantmentWithLevelFromArray(input.trim().split(" "));
    }

    private static EnchantmentWithLevel getEnchantmentWithLevelFromArray(String[] split) {
        int id = Integer.parseInt(split[0]);
        if (id >= Enchantment.enchantmentsList.length) throw new IllegalArgumentException("Enchantment id > max size" + Enchantment.enchantmentsList.length);
        Enchantment enchantment = Enchantment.enchantmentsList[id];
        if (enchantment == null) return null;
        EnchantmentWithLevel out = new EnchantmentWithLevel(enchantment);
        if (split.length > 1) out.level = Integer.parseInt(split[1]);
        return out;
    }
}