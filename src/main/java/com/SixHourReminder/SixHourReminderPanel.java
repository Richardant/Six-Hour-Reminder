package com.SixHourReminder;

import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;

import javax.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class SixHourReminderPanel extends OverlayPanel {
    private final SixHourReminderPlugin plugin;

    private final SixHourReminderConfig config;

    @Inject
    private SixHourReminderPanel(SixHourReminderPlugin plugin, SixHourReminderConfig config) {
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        this.plugin = plugin;
        this.config = config;
    }

    public Dimension render(Graphics2D graphics) {
        if (this.plugin.getSixhourTime() != null) {
            int time = (int)Math.floor(Duration.between(this.plugin.getSixhourTime(), Instant.now()).getSeconds());
            int minutes = time / 60;
            if (360 - minutes <= this.config.sixHourReminderTime()) {
                this.panelComponent.getChildren().clear();
                String seconds = formatTime(21600 - time);
                String overtime = formatTime(time - 21600);
                LineComponent lineComponent;
                if(21600 > time) {
                    lineComponent = LineComponent.builder().left("6H LOGOUT").leftColor(Color.red).right(seconds).build();
                }
                else
                {
                    lineComponent = LineComponent.builder().left("OVERTIME").leftColor(Color.red).right(overtime).build();
                }
                this.panelComponent.getChildren().add(lineComponent);
                return this.panelComponent.render(graphics);
            }
            else if (config.previewMode()) {
                int previewtime = (360 - config.sixHourReminderTime()) * 60;
                int timep = (int)Math.floor(Duration.between(this.plugin.getSixhourTimePreview(), Instant.now()).getSeconds()) + previewtime;
                this.panelComponent.getChildren().clear();
                String seconds = formatTime(21600 - timep);
                String overtime = formatTime(timep - 21600);
                LineComponent lineComponent;
                if(21600 > timep) {
                    lineComponent = LineComponent.builder().left("6H LOGOUT").leftColor(Color.red).right(seconds).build();
                }
                else
                {
                    lineComponent = LineComponent.builder().left("OVERTIME").leftColor(Color.red).right(overtime).build();
                }
                this.panelComponent.getChildren().add(lineComponent);
                return this.panelComponent.render(graphics);
            }
        }
        return null;
    }

    private String formatTime(int millis) {
        return String.format("%d:%02d", TimeUnit.SECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1L),
                TimeUnit.SECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1L));
    }
}
