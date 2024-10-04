package com.SixHourReminder;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class SixHourReminderTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(SixHourReminderPlugin.class);
		RuneLite.main(args);
	}
}
