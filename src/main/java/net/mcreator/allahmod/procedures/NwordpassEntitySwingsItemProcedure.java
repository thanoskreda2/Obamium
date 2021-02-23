package net.mcreator.allahmod.procedures;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.allahmod.AllahModModElements;

import java.util.Map;

@AllahModModElements.ModElement.Tag
public class NwordpassEntitySwingsItemProcedure extends AllahModModElements.ModElement {
	public NwordpassEntitySwingsItemProcedure(AllahModModElements instance) {
		super(instance, 4);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				System.err.println("Failed to load dependency entity for procedure NwordpassEntitySwingsItem!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof PlayerEntity)
			((PlayerEntity) entity).addExperienceLevel((int) 5);
	}
}
