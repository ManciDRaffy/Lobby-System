package de.laudytv.lobbysystem.util;

public class Utils {

    public static String generateUUID() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++)
            builder.append(chars.charAt((int) (Math.random() * chars.length())));
        return builder.toString();
    }
}
