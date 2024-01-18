package io.github.shotoh.katclient.core;

import io.github.moulberry.moulconfig.Config;
import io.github.moulberry.moulconfig.annotations.Category;
import io.github.moulberry.moulconfig.annotations.ConfigEditorBoolean;
import io.github.moulberry.moulconfig.annotations.ConfigOption;

public class KatClientConfig extends Config {
    @Override
    public String getTitle() {
        return "§dKat Client Config";
    }

    @Category(name = "General", desc = "General features")
    public GeneralCategory generalCategory = new GeneralCategory();


    public static class GeneralCategory {
        @ConfigOption(name = "Fishing Notifications", desc = "Alert your party whenever you fish up a sea creature")
        @ConfigEditorBoolean
        public boolean fishingNotifications = false;

        @ConfigOption(name = "Inquisitor Waypoints", desc = "Alert your party whenever you find an inquisitor")
        @ConfigEditorBoolean
        public boolean inquisitorWaypoints = false;
    }
}