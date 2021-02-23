
package net.mcreator.allahmod.item;

@AllahModModElements.ModElement.Tag
public class GangstasParadiseItem extends AllahModModElements.ModElement {

	@ObjectHolder("allah_mod:gangstas_paradise")
	public static final Item block = null;

	public GangstasParadiseItem(AllahModModElements instance) {
		super(instance, 7);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new MusicDiscItemCustom());
	}

	public static class MusicDiscItemCustom extends MusicDiscItem {

		public MusicDiscItemCustom() {
			super(0, AllahModModElements.sounds.get(new ResourceLocation("allah_mod:music_gangstasparadise")),
					new Item.Properties().group(ItemGroup.MISC).maxStackSize(1).rarity(Rarity.RARE));
			setRegistryName("gangstas_paradise");
		}

	}

}
