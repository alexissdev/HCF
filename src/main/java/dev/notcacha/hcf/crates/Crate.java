package dev.notcacha.hcf.crates;

import dev.notcacha.core.model.Model;
import dev.notcacha.hcf.crates.key.Key;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Crate extends Model {

    /**
     * @return key from this crate
     */

    Key getKey();

    /**
     * @return items from this create
     */

    List<ItemStack> getItems();
}