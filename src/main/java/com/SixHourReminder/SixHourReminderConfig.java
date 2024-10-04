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
            description = "Amount of time needed until 6h logout for the infobox to appear"
    )
    @Units(Units.MINUTES)
    @Range(min = 1, max = 60)
    default int sixHourReminderTime()
    {
        return 30;
    }
}
