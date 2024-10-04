package com.SixHourReminder;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;
import net.runelite.client.config.Units;

@ConfigGroup("sixhourreminder")
public interface SixHourReminderConfig extends Config {
    @Units(" mins")
    @Range(min = 1, max = 60)
    @ConfigItem(keyName = "sixHourReminderTime", name = "Time until 6hr", description = "Show overlay of the time until 6hr log (minutes)")
    default int sixHourReminderTime() {
        return 30;
    }
}
