package io.github.shotoh.katclient.core;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

public class KatClientConfig extends Config {
    public KatClientConfig() {
        super(new Mod("Kat Client", ModType.SKYBLOCK), "katclient.json");
        initialize();
    }

    @Switch(
            name = "Fishing Notifications",
            description = "Alert your party whenever you fish up a sea creature"
    )
    public static boolean fishingNotifications = false;

    @Switch(
            name = "Inquisitor Waypoints",
            description = "Alert your party whenever you find an inquisitor"
    )
    public static boolean inquisitorWaypoints = false;
}