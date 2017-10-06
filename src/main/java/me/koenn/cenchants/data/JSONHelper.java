package me.koenn.cenchants.data;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Helper class to convert Bukkit objects to JSON.
 */
public final class JSONHelper {

    private JSONHelper() {
    }

    /**
     * Converts a Bukkit <code>ItemStack</code> to a <code>JSONObject</code>
     *
     * @param stack Bukkit <code>ItemStack</code> to convert.
     * @return <code>JSONObject</code> representing the <code>ItemStack</code>.
     */
    public static JSONObject stackToJSON(ItemStack stack) {
        JSONObject json = new JSONObject();
        json.put("type", stack.getType());
        json.put("amount", stack.getAmount());
        json.put("durability", stack.getDurability());

        JSONObject metaJson = new JSONObject();
        ItemMeta meta = stack.getItemMeta();

        if (meta.hasDisplayName()) {
            metaJson.put("displayName", meta.getDisplayName());
        }

        if (meta.hasLore()) {
            JSONArray lore = new JSONArray();
            lore.addAll(meta.getLore());
            metaJson.put("lore", lore);
        }

        if (meta.hasEnchants()) {
            JSONArray enchants = new JSONArray();
            meta.getEnchants().keySet().forEach(enchantment -> enchants.add(enchantToJSON(enchantment, meta.getEnchantLevel(enchantment))));
            metaJson.put("enchantments", enchants);
        }

        if (meta.getItemFlags().size() > 0) {
            JSONArray flags = new JSONArray();
            meta.getItemFlags().forEach(itemFlag -> flags.add(itemFlag.name()));
            metaJson.put("flags", flags);
        }

        json.put("meta", metaJson);
        return json;
    }

    /**
     * Converts a <code>JSONObject</code> to a Bukkit <code>ItemStack</code>.
     *
     * @param json <code>JSONObject</code> to convert.
     * @return Bukkit <code>ItemStack</code> made from the <code>JSONObject</code>.
     */
    public static ItemStack JSONToStack(JSONObject json) {
        Material type = Material.valueOf((String) json.get("type"));
        int amount = Integer.parseInt(String.valueOf(json.get("amount")));
        short durability = Short.parseShort(String.valueOf(json.get("durability")));
        ItemStack stack = new ItemStack(type, amount, durability);

        ItemMeta meta = stack.getItemMeta();
        JSONObject metaJson = (JSONObject) json.get("meta");
        if (metaJson.containsKey("displayName")) {
            meta.setDisplayName((String) metaJson.get("displayName"));
        }

        if (metaJson.containsKey("lore")) {
            meta.setLore((JSONArray) metaJson.get("lore"));
        }

        if (metaJson.containsKey("enchantments")) {
            ((JSONArray) metaJson.get("enchantments")).forEach(enchantObj -> {
                JSONObject enchantment = (JSONObject) enchantObj;
                meta.addEnchant(
                        Enchantment.getByName((String) enchantment.get("name")),
                        Integer.parseInt(String.valueOf(enchantment.get("level"))),
                        false
                );
            });
        }

        if (metaJson.containsKey("flags")) {
            ((JSONArray) metaJson.get("flags")).forEach(flag -> meta.addItemFlags(ItemFlag.valueOf((String) flag)));
        }

        stack.setItemMeta(meta);
        return stack;
    }

    private static JSONObject enchantToJSON(Enchantment enchantment, int level) {
        JSONObject json = new JSONObject();
        json.put("name", enchantment.getName());
        json.put("level", level);
        return json;
    }
}
