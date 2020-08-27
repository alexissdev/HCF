package dev.notcacha.hcf.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Singleton;

@Singleton
public class GsonManager {

    private final Gson gson;

    public GsonManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public Gson getGson() {
        return this.gson;
    }
}
