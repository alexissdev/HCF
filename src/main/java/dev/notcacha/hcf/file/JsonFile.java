package dev.notcacha.hcf.file;

import com.google.gson.JsonElement;
import org.bukkit.plugin.Plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

public class JsonFile {

    private final Plugin plugin;
    private final File file;
    private final String fileName;

    public JsonFile(Plugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.file = new File(plugin.getDataFolder(), (fileName.endsWith(".json") ? fileName : fileName + ".json"));
    }

    public JsonFile(Plugin plugin, File folder, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        file = new File(folder, (fileName.endsWith(".json") ? fileName : fileName + ".json"));
    }

    public void create() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "An error occurred while trying to create the file", e);
            }
        }
    }

    public BufferedReader getBufferedReaderFile() {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "An error occurred while trying to find the file", e);
        }
        return new BufferedReader(fileReader);
    }

    public String getText() {
        String line = "";

        StringBuilder builder = new StringBuilder();
        BufferedReader reader = getBufferedReaderFile();

        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public void write(String jsonText) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(jsonText);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(JsonElement object) {
        write(object.getAsString());
    }

    public String getFileName() {
        return this.fileName;
    }

}
