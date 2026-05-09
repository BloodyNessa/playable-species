package nessa.plsp.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import nessa.plsp.client.gui.SpeciesSelectionScreen;

/**
 * Client entrypoint. Registers a tick listener to detect when the local player entity
 * changes (join/respawn) and opens the species selection GUI.
 */
public class PlayableSpeciesClient implements ClientModInitializer {
    // Track last seen player entity id to detect spawn/respawn (new client-side player instance)
    private int lastPlayerEntityId = -1;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client == null) return;

            if (client.player == null) {
                // no player in world
                lastPlayerEntityId = -1;
                return;
            }

            int id = client.player.getId();
            if (id != lastPlayerEntityId) {
                // A new client player instance appeared (first join or respawn) -> open GUI
                lastPlayerEntityId = id;
                client.setScreenAndShow(new SpeciesSelectionScreen());
            }
        });
    }
}
