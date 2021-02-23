package net.mcreator.allahmod.procedures;

import net.mcreator.allahmod.AllahModModElements;
import net.mcreator.allahmod.AllahModMod;

import java.util.Map;

@AllahModModElements.ModElement.Tag
public class NwordpassRightClickedInAirProcedure extends AllahModModElements.ModElement {
	public NwordpassRightClickedInAirProcedure(AllahModModElements instance) {
		super(instance, 3);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		System.out.println("Nigga");
		AllahModMod.LOGGER.info("Nigga");
	}
}
