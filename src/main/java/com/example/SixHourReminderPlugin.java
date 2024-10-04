package com.SixHourReminder;

import com.google.inject.Provides;
import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.time.Instant;

@Slf4j
@PluginDescriptor(
		name = "Six Hour Reminder",
		description = "Reminds you when you are close to six hours force logout.",
		tags = {"richardant", "6h", "logout", "six", "reminder", "log", "hour"}
)
public class SixHourReminderPlugin extends Plugin {

    @Inject
    private SixHourReminderConfig config;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private Client client;

    @Inject
    private SixHourReminderPanel sixHourReminderPanel;

    private Instant loginTime;

    private boolean ready;

    private Instant sixhourTime;

    private boolean sixhourReady;

    public Instant getSixhourTime() {
        return this.sixhourTime;
    }
  
    @Provides
    SixHourReminderConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(SixHourReminderConfig.class);
    }

    public void shutDown() {
        this.loginTime = null;
        this.sixhourTime = null;
        this.overlayManager.remove(this.sixHourReminderPanel);
    }

    public void startUp() {
        this.overlayManager.add(this.sixHourReminderPanel);
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event) {
        GameState state = event.getGameState();
        switch (state) {
            case LOGIN_SCREEN:
            case LOGIN_SCREEN_AUTHENTICATOR:
            case LOGGING_IN:
                this.ready = true;
                this.loginTime = null;
                this.sixhourReady = true;
                this.sixhourTime = null;
                break;
            case LOGGED_IN:
                if (this.ready) {
                    this.loginTime = Instant.now();
                    this.ready = false;
                }
                if (this.sixhourReady) {
                    this.sixhourTime = Instant.now();
                    this.sixhourReady = false;
                }
                break;
            case HOPPING:
                this.sixhourReady = true;
                break;
        }
    }
}
