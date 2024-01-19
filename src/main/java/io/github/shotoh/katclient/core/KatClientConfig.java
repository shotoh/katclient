package io.github.shotoh.katclient.core;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Number;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.annotations.Text;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

public class KatClientConfig extends Config {
    public KatClientConfig() {
        super(new Mod("Kat Client", ModType.SKYBLOCK), "katclient.json");
        initialize();
    }

    @Switch(
            name = "Dungeon Blacklist",
            description = "Auto kick blacklisted players"
    )
    public static boolean dungeonBlacklist = false;

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

    @Text(
            name = "Server IP",
            description = "IP address for the socket and database",
            placeholder = "localhost",
            category = "Auth"
    )
    public static String serverIP = "localhost";

    @Number(
            name = "Socket Port",
            min = 0, max = 65535,
            category = "Auth"
    )
    public static int socketPort = 55555;

    @Text(
            name = "Database Username",
            placeholder = "default",
            category = "Auth"
    )
    public static String databaseUsername;

    @Text(
            name = "Database Password",
            placeholder = "default",
            secure = true,
            category = "Auth"
    )
    public static String databasePassword;
}