package dev.ctrlneo.playerutils.client.modules.event;

import dev.ctrlneo.playerutils.client.event.Event;
import dev.ctrlneo.playerutils.client.event.EventFactory;
import dev.ctrlneo.playerutils.client.modules.content.boatfly.event.BoatflyToggleEvent;

public interface ModuleToggledEvent {
    void onCallback(boolean toggled, String moduleName, String moduleId);

    Event<ModuleToggledEvent> EVENT = EventFactory.createArrayBacked(ModuleToggledEvent.class, (listeners) -> (toggled, moduleName, moduleId) -> {
        for (ModuleToggledEvent event : listeners) {
            event.onCallback(toggled, moduleName, moduleId);
        }
    });
}
