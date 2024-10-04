package com.SixHourReminder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.LineComponent;

public class SixHourReminderPanel extends OverlayPanel {
    private final SixHourReminderPlugin plugin;

    private final SixHourReminderConfig config;

    private final Font font;

    @Inject
    private SixHourReminderPanel(SixHourReminderPlugin plugin, SixHourReminderConfig config) {
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        setPriority(OverlayPriority.HIGH);
        this.plugin = plugin;
        this.config = config;
        this.font = new Font("SansSerif", 0, 11);
    }

    public Dimension render(Graphics2D graphics) {
        if (this.plugin.getSixhourTime() != null) {
            int time = (int)Math.floor(Duration.between(this.plugin.getSixhourTime(), Instant.now()).getSeconds());
            int minutes = time / 60;
            if (360 - minutes <= this.config.sixHourReminderTime()) {
                this.panelComponent.getChildren().clear();
                String seconds = formatTime(21600 - time);
                LineComponent lineComponent = LineComponent.builder().left("6HR LOG").leftColor(Color.red).right(seconds).build();
                this.panelComponent.getChildren().add(lineComponent);
                return this.panelComponent.render(graphics);
            }
        }
        return null;
    }

    private String formatTime(int millis) {
        return String.format("%d:%02d", new Object[] { Long.valueOf(TimeUnit.SECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1L)),
                Long.valueOf(TimeUnit.SECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1L)) });
    }
}
