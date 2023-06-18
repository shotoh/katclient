package io.github.shotoh.katclient.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.shotoh.katclient.KatClient;

import java.io.*;

public class DataHandler {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String path = "./config/katclient.json";

    public static void save() {
        KatClientData data = KatClient.data;
        try {
            FileWriter writer = new FileWriter(path);
            gson.toJson(data, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static KatClientData load() {
        try {
            FileReader reader = new FileReader(path);
            KatClientData data = gson.fromJson(reader, KatClientData.class);
            if (data == null) {
                data = new KatClientData();
            }
            return data;
        } catch (FileNotFoundException e) {
            new File(path);
            return new KatClientData();
        }
    }
}
