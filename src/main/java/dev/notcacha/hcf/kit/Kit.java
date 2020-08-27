package dev.notcacha.hcf.kit;

import dev.notcacha.core.model.Model;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Optional;

public interface Kit extends Model {

    /**
     * @return items from this kti
     */

    Optional<ItemStack[]> getItems();

    /**
     * Set items
     *
     * @param items has been set from this kit
     */

    Kit setItems(ItemStack[] items);

    /**
     * @return armor from this koth
     */

    Optional<ItemStack[]> getArmor();

    /**
     * Set armor
     *
     * @param armor has been set from kit
     */

    Kit setArmor(ItemStack[] armor);
}
