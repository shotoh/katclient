package io.github.shotoh.katclient.utils;

import io.github.shotoh.katclient.KatClient;

import java.io.*;
import java.net.URL;

public class MojangUtils {
    static class MinecraftAccount {
        String id;
        String name;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static String getUUID(String name) {
        String url = "https://api.mojang.com/users/profiles/minecraft/" + name;
        try {
            InputStream is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String jsonText = readAll(rd);
            MinecraftAccount acc = KatClient.GSON.fromJson(jsonText, MinecraftAccount.class);
            return acc.id;
        } catch (IOException e) {
            return null;
        }
    }
}
