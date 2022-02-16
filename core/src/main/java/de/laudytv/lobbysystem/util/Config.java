package de.laudytv.lobbysystem.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Config {
    private final FileConfiguration fileConfiguration;
    private final File file;

    public Config(String name, File path) {
        file = new File(path, name);
        if (!path.exists())
            if (path.mkdirs())
                try {
                    if (!file.createNewFile()) {
                        throw new IOException("Unable to create file at specified path. It already exists");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
