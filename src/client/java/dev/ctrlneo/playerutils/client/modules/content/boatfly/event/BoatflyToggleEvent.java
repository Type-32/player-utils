package dev.ctrlneo.playerutils.client.modules.content.boatfly.event;

import dev.ctrlneo.playerutils.client.event.Event;
import dev.ctrlneo.playerutils.client.event.EventFactory;

public interface BoatflyToggleEvent {
    void onCallback(boolean toggled);

    Event<BoatflyToggleEvent> EVENT = EventFactory.createArrayBacked(BoatflyToggleEvent.class, (listeners) -> (toggled) -> {
        for (BoatflyToggleEvent event : listeners) {
            event.onCallback(toggled);
        }
    });
}
