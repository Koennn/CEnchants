package me.koenn.cenchants.gui;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Class representing a button in a <code>GUI</code>.
 * Add the Option to a gui by calling <code>gui.addOption</code> or <code>gui.setOption</code>
 */
public class Option {

    private final ItemStack option;
    private final ClickListener listener;

    /**
     * Option runnable constructor.
     *
     * @param icon   ItemStack which will display in the gui
     * @param action Runnable to run when the option is clicked
     */
    public Option(ItemStack icon, Runnable action) {
        this(icon, clickedSlot -> action.run());
    }

    /**
     * Option listener constructor.
     *
     * @param icon     ItemStack which will display in the gui
     * @param listener Listener to call when the option is clicked
     */
    public Option(ItemStack icon, ClickListener listener) {
        this.listener = listener;
        this.option = icon;
    }

    /**
     * Run the listener/runnable specified in the constructor.
     * Will automaticly be called when the option is clicked in the gui.
     *
     * @param clickedSlot Slot in which the option has been clicked
     * @return true if the listener/runnable exists and can be called
     */
    public boolean run(int clickedSlot) {
        if (this.listener != null) {
            this.listener.click(clickedSlot);
            return true;
        }
        return false;
    }

    /**
     * Set the lore (tooltip) of the ItemStack which will display in the gui.
     *
     * @param lore Lore (tooltip) to set on the ItemStack
     */
    public void setLore(List<String> lore) {
        ItemMeta meta = this.option.getItemMeta();
        meta.setLore(lore);
        this.option.setItemMeta(meta);
    }

    /**
     * Get the ItemStack to display in the gui.
     *
     * @return ItemStack to display in the gui
     */
    public ItemStack getIcon() {
        return option;
    }
}

