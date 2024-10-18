package com.SixHourReminder;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;
import net.runelite.client.config.Units;

@ConfigGroup("sixhourreminder")
public interface SixHourReminderConfig extends Config {
    @ConfigItem(
            keyName = "sixHourReminderTime",
            name = "Time until 6h",
            description = "Amount of time needed until 6h logout for the infobox to appear",
            position = 0
    )
    @Units(Units.MINUTES)
    @Range(min = 1, max = 60)
    default int sixHourReminderTime()
    {
        return 30;
    }

    @ConfigItem(
            keyName = "enablePreview",
            name = "Enable Preview",
            description = "Shows a preview of the overlay",
            position = 1
    )
    default boolean previewMode()
    {
        return false;
    }
}
