package io.github.shotoh.katclient.core;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;

import java.io.File;

public class KatClientConfig extends Vigilant {
    // features
    @Property(
            type = PropertyType.SWITCH,
            name = "Time To Scan",
            category = "Features",
            subcategory = "Crystal Hollows",
            description = "Whether you want to show how long it takes to scan in chat"
    )
    public static boolean timeToScan = false;
    @Property(
            type = PropertyType.SWITCH,
            name = "Party Fishing Notifications",
            category = "Features",
            subcategory = "Fishing",
            description = "Whether you want to alert your party whenever you fish up a sea creature"
    )
    public static boolean partyFishingNotifications = false;
    @Property(
            type = PropertyType.SWITCH,
            name = "Nick Hider",
            category = "Features",
            subcategory = "General",
            description = "Whether you want to replace certain usernames with nicknames"
    )
    public static boolean nickHider = false;
    // qol
    @Property(
            type = PropertyType.SWITCH,
            name = "Auto Fish",
            category = "QOL",
            subcategory = "General",
            description = "Whether you want to enable auto fish"
    )
    public static boolean autoFish = false;
    @Property(
            type = PropertyType.SWITCH,
            name = "Auto Reconnect",
            category = "QOL",
            subcategory = "General",
            description = "Whether you want to enable auto reconnect"
    )
    public static boolean autoReconnect = false;
    @Property(
            type = PropertyType.SWITCH,
            name = "Free Cam",
            category = "QOL",
            subcategory = "General",
            description = "Whether you want to enable free cam"
    )
    public static boolean freeCam = false;
    @Property(
            type = PropertyType.SWITCH,
            name = "Ghost Blocks",
            category = "QOL",
            subcategory = "General",
            description = "Whether you want to enable ghost blocks"
    )
    public static boolean ghostBlocks = false;

    public static KatClientConfig config = new KatClientConfig();

    public KatClientConfig() {
        super(new File("./config/katclient.toml"), "Kat Client");
        initialize();
    }
}