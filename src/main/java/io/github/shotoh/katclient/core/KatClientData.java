package io.github.shotoh.katclient.core;

import java.util.HashMap;

public class KatClientData {
    private HashMap<String, String> nickHiderMap;

    public KatClientData() {
        this.nickHiderMap = new HashMap<>();
    }

    public KatClientData(HashMap<String, String> nickHiderMap) {
        this.nickHiderMap = nickHiderMap;
    }

    public HashMap<String, String> getNickHiderMap() {
        return nickHiderMap;
    }

    public void setNickHiderMap(HashMap<String, String> nickHiderMap) {
        this.nickHiderMap = nickHiderMap;
    }
}
