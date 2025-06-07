package dev.ctrlneo.playerutils.client.modules.content.listeners.event;

import dev.ctrlneo.playerutils.client.event.Event;
import dev.ctrlneo.playerutils.client.event.EventFactory;

public interface PlayerHealedEvent {
    void onCallback(double newHealth, double oldHealth);

    Event<PlayerHealedEvent> EVENT = EventFactory.createArrayBacked(PlayerHealedEvent.class, (listeners) -> (newHealth, oldHealth) -> {
        for (PlayerHealedEvent event : listeners) {
            event.onCallback(newHealth, oldHealth);
        }
    });
}
