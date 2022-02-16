package de.laudytv.lobbysystem.util;

import com.google.gson.*;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import com.mojang.util.UUIDTypeAdapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GameProfileBuilder {

    public static Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(UUID.class, new UUIDTypeAdapter())
            .registerTypeAdapter(GameProfile.class, new GameProfileSerializer())
            .registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer())
            .create();
    private static String URL = "https://sessionserver.mojang.com/session/minecraft/profile/%s?unsigned=false";
    private static HashMap<UUID, GameProfile> cache = new HashMap<>();

    public static GameProfile fetch(UUID uuid) {
        if (cache.containsKey(uuid))
            return cache.get(uuid);

        try {
            HttpURLConnection connection =
                    (HttpURLConnection) new URL(String.format(URL, uuid)).openConnection();
            connection.setUseCaches(false);
            connection.setDefaultUseCaches(false);
            connection.addRequestProperty("User-Agent", "Mozilla/5.0");
            connection.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            connection.addRequestProperty("Pragma", "no-cache");
            connection.setReadTimeout(5000);

            try (BufferedReader bufferedReader =
                         new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) response.append(line);

                String json = response.toString();

                final JsonElement parsed = new JsonParser().parse(json);

                if (!parsed.isJsonObject())
                    return null;

                JsonObject data = parsed.getAsJsonObject();

                GameProfile fetchedProfile = gson.fromJson(data, GameProfile.class);
                cache.put(uuid, fetchedProfile);

                return fetchedProfile;
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private HashMap<UUID, GameProfile> getCache() {
        return cache;
    }


    private static class GameProfileSerializer implements JsonSerializer<GameProfile>, JsonDeserializer<GameProfile> {

        public GameProfile deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            JsonObject object = (JsonObject) json;
            UUID id = object.has("id") ? (UUID) context.deserialize(object.get("id"), UUID.class) : null;
            String name = object.has("name") ? object.getAsJsonPrimitive("name").getAsString() : null;
            GameProfile profile = new GameProfile(id, name);
            if (object.has("properties")) {
                for (Map.Entry<String, Property> prop : ((PropertyMap) context.deserialize(object.get("properties"), PropertyMap.class)).entries()) {
                    profile.getProperties().put(prop.getKey(), prop.getValue());
                }
            }
            return profile;
        }

        public JsonElement serialize(GameProfile profile, Type type, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            if (profile.getId() != null)
                result.add("id", context.serialize(profile.getId()));
            if (profile.getName() != null)
                result.addProperty("name", profile.getName());
            if (!profile.getProperties().isEmpty())
                result.add("properties", context.serialize(profile.getProperties()));
            return result;
        }
    }


}

