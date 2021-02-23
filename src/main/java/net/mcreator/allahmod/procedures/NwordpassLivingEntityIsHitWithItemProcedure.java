package net.mcreator.allahmod.procedures;

import net.minecraft.entity.Entity;

import net.mcreator.allahmod.AllahModModElements;

import java.util.Map;

@AllahModModElements.ModElement.Tag
public class NwordpassLivingEntityIsHitWithItemProcedure extends AllahModModElements.ModElement {
	public NwordpassLivingEntityIsHitWithItemProcedure(AllahModModElements instance) {
		super(instance, 2);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				System.err.println("Failed to load dependency entity for procedure NwordpassLivingEntityIsHitWithItem!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		entity.setFire((int) 15);
	}
}
