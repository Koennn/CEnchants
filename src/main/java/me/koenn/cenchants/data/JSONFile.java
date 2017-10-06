package me.koenn.cenchants.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import me.koenn.cenchants.CEnchants;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

/**
 * Class representing a .json file. Used for easy interaction with the file.
 */
public class JSONFile {

    private JSONObject json;
    private File file;

    /**
     * Creates a <code>JSONFile</code> from a file path.
     *
     * @param fileName path to the file to create the <code>JSONFile</code> from.
     */
    public JSONFile(String fileName) {
        this(new File(fileName));
    }

    /**
     * Creates a <code>JSONFile</code> from a <code>File</code>.
     *
     * @param file <code>File</code> to create the <code>JSONFile</code> from.
     */
    public JSONFile(File file) {
        this.file = file;
        this.load();
    }

    private static JSONObject parseJSON(File file) {
        try {
            return (JSONObject) new JSONParser().parse(new FileReader(file));
        } catch (ParseException e) {
            CEnchants.LOGGER.severe(String.format("Error while parsing JSON file \'%s\': %s", file.getName(), e));
        } catch (FileNotFoundException e) {
            CEnchants.LOGGER.severe(String.format("JSON file \'%s\' could not be found: %s", file.getName(), e));
        } catch (IOException e) {
            CEnchants.LOGGER.severe(String.format("Unable to access JSON file \'%s\': %s", file.getName(), e));
        }
        return null;
    }

    private static void writeJSON(File file, String json) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            CEnchants.LOGGER.severe(String.format("Unable to access JSON file \'%s\': %s", file.getName(), e));
        }
    }

    private void load() {
        if (this.file.exists()) {
            this.json = new JSONObject();
        } else {
            this.json = parseJSON(this.file);
        }
    }

    private void save() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        writeJSON(this.file, gson.toJson(new JsonParser().parse(this.json.toJSONString())));
    }

    /**
     * Set an entry in the <code>JSONFile</code>. Does not auto-save the file.
     *
     * @param key   the key of the entry.
     * @param value the value of the entry.
     */
    public void setInJSON(String key, Object value) {
        this.json.put(key, value);
    }

    /**
     * Get a value from the <code>JSONFile</code> using it's key.
     *
     * @param key key to get the value for.
     * @return value associated with the key.
     */
    public Object getFromJSON(String key) {
        return this.json.get(key);
    }

    /**
     * Get the full <code>JSONObject</code> from the file.
     *
     * @return <code>JSONObject</code> of the file.
     */
    public JSONObject getJSON() {
        return json;
    }
}
