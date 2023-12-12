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
            description = "Scan Crystal Hollows for specific structures"
    )
    public static boolean timeToScan = false;
    @Property(
            type = PropertyType.SWITCH,
            name = "Party Fishing Notifications",
            category = "Features",
            subcategory = "Fishing",
            description = "Alert your party whenever you fish up a sea creature"
    )
    public static boolean partyFishingNotifications = false;
    // qol
    @Property(
            type = PropertyType.SWITCH,
            name = "Auto Fish",
            category = "QOL",
            subcategory = "General",
            description = "Automatically fish"
    )
    public static boolean autoFish = false;
    @Property(
            type = PropertyType.SWITCH,
            name = "Free Cam",
            category = "QOL",
            subcategory = "General",
            description = "Fly around and phase through blocks"
    )
    public static boolean freeCam = false;
    @Property(
            type = PropertyType.SWITCH,
            name = "Ghost Blocks",
            category = "QOL",
            subcategory = "General",
            description = "Make blocks disappear"
    )
    public static boolean ghostBlocks = false;
    @Property(
            type = PropertyType.SWITCH,
            name = "Term Auto Clicker",
            category = "QOL",
            subcategory = "Term Auto Clicker",
            description = "Automatically clicks when holding down right click"
    )
    public static boolean termAutoClicker = false;
    @Property(
            type = PropertyType.DECIMAL_SLIDER,
            name = "Minimum CPS",
            category = "QOL",
            subcategory = "Term Auto Clicker",
            description = "Minimum value for CPS range",
            maxF = 64.0f
    )
    public static float minCPS = 8.0f;
    @Property(
            type = PropertyType.DECIMAL_SLIDER,
            name = "Maximum CPS",
            category = "QOL",
            subcategory = "Term Auto Clicker",
            description = "Maximum value for CPS range",
            maxF = 64.0f
    )
    public static float maxCPS = 12.0f;

    public static KatClientConfig config = new KatClientConfig();

    public KatClientConfig() {
        super(new File("./config/katclient.toml"), "Kat Client");
        initialize();
        addDependency("minCPS", "termAutoClicker");
        addDependency("maxCPS", "termAutoClicker");
    }
}