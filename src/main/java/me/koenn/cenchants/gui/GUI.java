package me.koenn.cenchants.gui;

import me.koenn.cenchants.CEnchants;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * A class representing an in-game GUI for player interaction.
 */
public class GUI implements Listener {

    private final Inventory inventory;
    private final HashMap<Integer, Option> options;

    private GUI(Builder builder) {
        this.inventory = Bukkit.createInventory(null, builder.size, builder.name);
        this.options = new HashMap<>();

        for (Option option : builder.options.keySet()) {
            int index = builder.options.get(option);
            this.inventory.setItem(index, option.getIcon());
            this.options.put(index, option);
        }
    }

    /**
     * Show the <code>GUI</code> to a player.
     *
     * @param player Player to show the <code>GUI</code> to.
     */
    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    /**
     * Listener for the <code>InventoryClickEvent</code>. Gets called by Bukkit's event manager.
     */
    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getClickedInventory().equals(this.inventory) || event.getCurrentItem() == null || !(event.getWhoClicked() instanceof Player)) {
            return;
        }

        int slot = event.getSlot();
        Option option = this.options.get(slot);
        if (option == null) {
            return;
        }

        if (option.run(slot)) {
            event.setCancelled(true);
        }
    }

    /**
     * Builder class for a <code>GUI</code> object.
     */
    public static final class Builder {

        private String name;
        private int size;
        private LinkedHashMap<Option, Integer> options;

        /**
         * Initial constructor for <code>GUI</code> builder.
         *
         * @param name name of the <code>GUI</code>
         */
        public Builder(String name) {
            this.name = name;
            this.options = new LinkedHashMap<>();
        }

        /**
         * Set the size of the <code>GUI</code>. Needs to be a multiple of 9!
         *
         * @param size size of the <code>GUI</code>
         * @return <code>Builder</code> object for chaining calls.
         */
        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        /**
         * Set an <code>Option</code> in the <code>GUI</code>
         *
         * @param option <code>Option</code> to add.
         * @return <code>Builder</code> object for chaining calls.
         */
        public Builder setOption(int index, Option option) {
            this.options.put(option, index);
            return this;
        }

        /**
         * Build the new <code>GUI</code> object.
         *
         * @return <code>GUI</code> object.
         */
        public GUI build() {
            GUI gui = new GUI(this);
            Bukkit.getPluginManager().registerEvents(gui, CEnchants.INSTANCE);
            return gui;
        }
    }
}
