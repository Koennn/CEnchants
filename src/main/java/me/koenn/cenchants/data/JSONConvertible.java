package me.koenn.cenchants.data;

import org.json.simple.JSONObject;

/**
 * Interface for converting an <code>Object</code> to and from a <code>JSONObject</code>.
 */
public interface JSONConvertible {

    /**
     * Convert the <code>Object</code> to a <code>JSONObject</code>.
     *
     * @return <code>JSONObject</code> representing the <code>Object</code>.
     */
    JSONObject toJSON();

    /**
     * Load the <code>Object</code>'s data from a <code>JSONObject</code>.
     *
     * @param json <code>JSONObject</code> to load the data from.
     */
    void fromJSON(JSONObject json);
}
