package dev.lazurite.dropz;

import dev.lazurite.rayon.api.event.EntityBodyCollisionEvent;
import dev.lazurite.rayon.api.registry.DynamicEntityRegistry;
import dev.lazurite.rayon.api.shape.provider.BoundingBoxShapeProvider;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The main entrypoint for Dropz. All it really does is
 * register {@link ItemEntity} with Rayon. It also sets up
 * a collision event for {@link ItemEntity}s which allows
 * them to merge when they collide with one another.
 */
public class Dropz implements ModInitializer {
	public static final String MODID = "dropz";
	public static final Logger LOGGER = LogManager.getLogger("Dropz");

	@Override
	public void onInitialize() {
		LOGGER.info("Yeet.");

		DynamicEntityRegistry.INSTANCE.register(ItemEntity.class, BoundingBoxShapeProvider::get, 2.5f);
		EntityBodyCollisionEvent.ENTITY_COLLISION.register((body1, body2) -> {
			if (!body1.getEntity().getEntityWorld().isClient()) {
				if (body1.getEntity() instanceof ItemEntity && body2.getEntity() instanceof ItemEntity) {
					((ItemEntity) body1.getEntity()).tryMerge(((ItemEntity) body2.getEntity()));
				}
			}
		});
	}
}
