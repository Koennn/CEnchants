package me.koenn.cenchants.backpack;

import me.koenn.cenchants.data.JSONConvertible;
import me.koenn.cenchants.data.JSONHelper;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class representing an in-game backpack.
 */
public class Backpack implements JSONConvertible {

    private UUID uuid;
    private List<ItemStack> contents;
    private int size;

    /**
     * Create a new <b>EMPTY</b> backpack.
     */
    public Backpack() {
        this.uuid = UUID.randomUUID();
        this.contents = new ArrayList<>();
    }

    /**
     * Convert the <code>Backpack</code> to a <code>JSONObject</code>.
     *
     * @return <code>JSONObject</code> made from the <code>Backpack</code>.
     */
    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        JSONArray contents = new JSONArray();
        this.contents.forEach(stack -> contents.add(JSONHelper.stackToJSON(stack)));
        json.put("uuid", this.uuid.toString());
        json.put("contents", contents);
        json.put("size", size);
        return json;
    }

    /**
     * Load the data for this <code>Backpack</code> from a <code>JSONObject</code>.
     *
     * @param json <code>JSONObject</code> to load from.
     */
    @Override
    public void fromJSON(JSONObject json) {
        this.uuid = UUID.fromString((String) json.get("uuid"));
        this.size = Integer.parseInt(String.valueOf(json.get("size")));
        ((JSONArray) json.get("contents")).forEach(stack -> this.contents.add(JSONHelper.JSONToStack((JSONObject) stack)));
    }
}
