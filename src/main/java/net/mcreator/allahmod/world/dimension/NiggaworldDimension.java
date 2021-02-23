
package net.mcreator.allahmod.world.dimension;

import net.minecraft.block.material.Material;

@AllahModModElements.ModElement.Tag
public class NiggaworldDimension extends AllahModModElements.ModElement {

	@ObjectHolder("allah_mod:niggaworld")
	public static final ModDimension dimension = null;

	@ObjectHolder("allah_mod:niggaworld_portal")
	public static final CustomPortalBlock portal = null;

	public static DimensionType type = null;

	private static Biome[] dimensionBiomes;

	public NiggaworldDimension(AllahModModElements instance) {
		super(instance, 6);

		MinecraftForge.EVENT_BUS.register(this);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@SubscribeEvent
	public void registerDimension(RegistryEvent.Register<ModDimension> event) {
		event.getRegistry().register(new CustomModDimension().setRegistryName("niggaworld"));
	}

	@SubscribeEvent
	public void onRegisterDimensionsEvent(RegisterDimensionsEvent event) {
		if (DimensionType.byName(new ResourceLocation("allah_mod:niggaworld")) == null) {
			DimensionManager.registerDimension(new ResourceLocation("allah_mod:niggaworld"), dimension, null, false);
		}

		type = DimensionType.byName(new ResourceLocation("allah_mod:niggaworld"));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		dimensionBiomes = new Biome[]{ForgeRegistries.BIOMES.getValue(new ResourceLocation("nether")),
				ForgeRegistries.BIOMES.getValue(new ResourceLocation("shattered_savanna_plateau")),
				ForgeRegistries.BIOMES.getValue(new ResourceLocation("modified_wooded_badlands_plateau")),};
	}

	@Override
	public void initElements() {
		elements.blocks.add(() -> new CustomPortalBlock());
		elements.items.add(() -> new NiggaworldItem().setRegistryName("niggaworld"));
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void clientLoad(FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(portal, RenderType.getTranslucent());
	}

	public static class CustomPortalBlock extends NetherPortalBlock {

		public CustomPortalBlock() {
			super(Block.Properties.create(Material.PORTAL).doesNotBlockMovement().tickRandomly().hardnessAndResistance(-1.0F).sound(SoundType.GLASS)
					.lightValue(2).noDrops());
			setRegistryName("niggaworld_portal");
		}

		@Override
		public void tick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		}

		public void portalSpawn(World world, BlockPos pos) {
			CustomPortalBlock.Size portalsize = this.isValid(world, pos);
			if (portalsize != null)
				portalsize.placePortalBlocks();
		}

		/* failed to load code for net.minecraft.block.NetherPortalBlock */

		/* failed to load code for net.minecraft.block.NetherPortalBlock */

		@Override /* failed to load code for net.minecraft.block.NetherPortalBlock */

		@OnlyIn(Dist.CLIENT)
		@Override
		public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
			for (int i = 0; i < 4; i++) {
				double px = pos.getX() + random.nextFloat();
				double py = pos.getY() + random.nextFloat();
				double pz = pos.getZ() + random.nextFloat();
				double vx = (random.nextFloat() - 0.5) / 2f;
				double vy = (random.nextFloat() - 0.5) / 2f;
				double vz = (random.nextFloat() - 0.5) / 2f;
				int j = random.nextInt(4) - 1;
				if (world.getBlockState(pos.west()).getBlock() != this && world.getBlockState(pos.east()).getBlock() != this) {
					px = pos.getX() + 0.5 + 0.25 * j;
					vx = random.nextFloat() * 2 * j;
				} else {
					pz = pos.getZ() + 0.5 + 0.25 * j;
					vz = random.nextFloat() * 2 * j;
				}
				world.addParticle(ParticleTypes.EXPLOSION, px, py, pz, vx, vy, vz);
			}

			if (random.nextInt(110) == 0)
				world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(("block.chest.open"))),
						SoundCategory.BLOCKS, 0.5f, random.nextFloat() * 0.4F + 0.8F, false);
		}

		@Override
		public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
			if (!entity.isPassenger() && !entity.isBeingRidden() && entity.isNonBoss() && !entity.world.isRemote && true) {
				if (entity.timeUntilPortal > 0) {
					entity.timeUntilPortal = entity.getPortalCooldown();
				} else if (entity.dimension != type) {
					entity.timeUntilPortal = entity.getPortalCooldown();
					teleportToDimension(entity, pos, type);
				} else {
					entity.timeUntilPortal = entity.getPortalCooldown();
					teleportToDimension(entity, pos, DimensionType.OVERWORLD);
				}
			}
		}

		private void teleportToDimension(Entity entity, BlockPos pos, DimensionType destinationType) {
			entity.changeDimension(destinationType, getTeleporterForDimension(entity, pos, entity.getServer().getWorld(destinationType)));
		}

		private TeleporterDimensionMod getTeleporterForDimension(Entity entity, BlockPos pos, ServerWorld nextWorld) {
			BlockPattern.PatternHelper bph = NiggaworldDimension.CustomPortalBlock.createPatternHelper(entity.world, pos);
			double d0 = bph.getForwards().getAxis() == Direction.Axis.X
					? (double) bph.getFrontTopLeft().getZ()
					: (double) bph.getFrontTopLeft().getX();
			double d1 = bph.getForwards().getAxis() == Direction.Axis.X ? entity.getPosZ() : entity.getPosX();
			d1 = Math.abs(MathHelper.pct(d1 - (double) (bph.getForwards().rotateY().getAxisDirection() == Direction.AxisDirection.NEGATIVE ? 1 : 0),
					d0, d0 - (double) bph.getWidth()));
			double d2 = MathHelper.pct(entity.getPosY() - 1, (double) bph.getFrontTopLeft().getY(),
					(double) (bph.getFrontTopLeft().getY() - bph.getHeight()));
			return new TeleporterDimensionMod(nextWorld, new Vec3d(d1, d2, 0), bph.getForwards());
		}

	public static class Size /* failed to load code for net.minecraft.block.NetherPortalBlock */

}
		private static PointOfInterestType poi = null;

		public static final TicketType<BlockPos> CUSTOM_PORTAL = TicketType.create("niggaworld_portal", Vec3i::compareTo, 300);

		@SubscribeEvent
		public void registerPointOfInterest(RegistryEvent.Register<PointOfInterestType> event) {
			try {
				Method method = ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "func_226359_a_", String.class, Set.class,
						int.class, int.class);
				method.setAccessible(true);
				poi = (PointOfInterestType) method.invoke(null, "niggaworld_portal",
						Sets.newHashSet(ImmutableSet.copyOf(portal.getStateContainer().getValidStates())), 0, 1);
				event.getRegistry().register(poi);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public static class TeleporterDimensionMod implements ITeleporter {

			private Vec3d lastPortalVec;
			private Direction teleportDirection;

			protected final ServerWorld world;
			protected final Random random;

			public TeleporterDimensionMod(ServerWorld worldServer, Vec3d lastPortalVec, Direction teleportDirection) {
				this.world = worldServer;
				this.random = new Random(worldServer.getSeed());

				this.lastPortalVec = lastPortalVec;
				this.teleportDirection = teleportDirection;
			}

			/* failed to load code for net.minecraft.world.Teleporter */

			/* failed to load code for net.minecraft.world.Teleporter */

			/* failed to load code for net.minecraft.world.Teleporter */

			@Override
			public Entity placeEntity(Entity entity, ServerWorld serverworld, ServerWorld serverworld1, float yaw,
					Function<Boolean, Entity> repositionEntity) {
				double d0 = entity.getPosX();
				double d1 = entity.getPosY();
				double d2 = entity.getPosZ();

				if (entity instanceof ServerPlayerEntity) {
					entity.setLocationAndAngles(d0, d1, d2, yaw, entity.rotationPitch);

					if (!this.placeInPortal(entity, yaw)) {
						this.makePortal(entity);
						this.placeInPortal(entity, yaw);
					}

					entity.setWorld(serverworld1);
					serverworld1.addDuringPortalTeleport((ServerPlayerEntity) entity);
					((ServerPlayerEntity) entity).connection.setPlayerLocation(entity.getPosX(), entity.getPosY(), entity.getPosZ(), yaw,
							entity.rotationPitch);

					return entity;
				} else {
					Vec3d vec3d = entity.getMotion();
					BlockPos blockpos = new BlockPos(d0, d1, d2);

					BlockPattern.PortalInfo blockpattern$portalinfo = this.placeInExistingPortal(blockpos, vec3d, teleportDirection, lastPortalVec.x,
							lastPortalVec.y, entity instanceof PlayerEntity);
					if (blockpattern$portalinfo == null)
						return null;

					blockpos = new BlockPos(blockpattern$portalinfo.pos);
					vec3d = blockpattern$portalinfo.motion;
					float f = (float) blockpattern$portalinfo.rotation;

					Entity entityNew = entity.getType().create(serverworld1);
					if (entityNew != null) {
						entityNew.copyDataFromOld(entity);
						entityNew.moveToBlockPosAndAngles(blockpos, entityNew.rotationYaw + f, entityNew.rotationPitch);
						entityNew.setMotion(vec3d);
						serverworld1.addFromAnotherDimension(entityNew);
					}

					return entityNew;
				}
			}

		}

		public static class CustomModDimension extends ModDimension {

			@Override
			public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
				return CustomDimension::new;
			}

		}

	public static class CustomDimension extends Dimension {

		private BiomeProviderCustom biomeProviderCustom = null;

		public CustomDimension(World world, DimensionType type) {
			super(world, type, 0.5f);
			this.nether = true;
		}


		@Override @OnlyIn(Dist.CLIENT) public Vec3d getFogColor(float cangle, float ticks) {
			return new Vec3d(0.752941176471,0.847058823529,1);
		}

		@Override public ChunkGenerator<?> createChunkGenerator() {
			if(this.biomeProviderCustom == null) {
				this.biomeProviderCustom = new BiomeProviderCustom(this.world);
			}
			return new ChunkProviderModded(this.world, this.biomeProviderCustom);
		}

		@Override public boolean isSurfaceWorld() {
			return false;
		}

		@Override public boolean canRespawnHere() {
			return false;
		}

		@OnlyIn(Dist.CLIENT) @Override public boolean doesXZShowFog(int x, int z) {
			return false;
		}

		@Override public SleepResult canSleepAt(PlayerEntity player, BlockPos pos){
        	return SleepResult.ALLOW;
		}

		@Nullable public BlockPos findSpawn(ChunkPos chunkPos, boolean checkValid) {
   		   return null;
   		}

   		@Nullable public BlockPos findSpawn(int x, int z, boolean checkValid) {
   		   return null;
   		}

		@Override public boolean doesWaterVaporize() {
      		return false;
   		}

		@Override /* failed to load code for net.minecraft.world.dimension.OverworldDimension */

	}

		public static class ChunkProviderModded extends NetherChunkGenerator {

			public ChunkProviderModded(World world, BiomeProvider provider) {
				super(world, provider, new NetherGenSettings() {
					public BlockState getDefaultBlock() {
						return ObamiumBlock.block.getDefaultState();
					}

					public BlockState getDefaultFluid() {
						return Blocks.LAVA.getDefaultState();
					}
				});
				this.randomSeed.skip(9716);
			}

			@Override
			public List<Biome.SpawnListEntry> getPossibleCreatures(EntityClassification creatureType, BlockPos pos) {
				return this.world.getBiome(pos).getSpawns(creatureType);
			}

		}

		public static class BiomeLayerCustom implements IC0Transformer {

			@Override
			public int apply(INoiseRandom context, int value) {
				return Registry.BIOME.getId(dimensionBiomes[context.random(dimensionBiomes.length)]);
			}

		}

		public static class BiomeProviderCustom extends BiomeProvider {

			private Layer genBiomes;

			public BiomeProviderCustom(World world) {
				super(new HashSet<Biome>(Arrays.asList(dimensionBiomes)));

				this.genBiomes = getBiomeLayer(world.getSeed());

			}

			public Biome getNoiseBiome(int x, int y, int z) {
				return this.genBiomes.func_215738_a(x, z);
			}

			private Layer getBiomeLayer(long seed) {
				LongFunction<IExtendedNoiseRandom<LazyArea>> contextFactory = l -> new LazyAreaLayerContext(25, seed, l);

				IAreaFactory<LazyArea> parentLayer = IslandLayer.INSTANCE.apply(contextFactory.apply(1));
				IAreaFactory<LazyArea> biomeLayer = (new BiomeLayerCustom()).apply(contextFactory.apply(200), parentLayer);

				biomeLayer = ZoomLayer.NORMAL.apply(contextFactory.apply(1000), biomeLayer);
				biomeLayer = ZoomLayer.NORMAL.apply(contextFactory.apply(1001), biomeLayer);
				biomeLayer = ZoomLayer.NORMAL.apply(contextFactory.apply(1002), biomeLayer);
				biomeLayer = ZoomLayer.NORMAL.apply(contextFactory.apply(1003), biomeLayer);
				biomeLayer = ZoomLayer.NORMAL.apply(contextFactory.apply(1004), biomeLayer);
				biomeLayer = ZoomLayer.NORMAL.apply(contextFactory.apply(1005), biomeLayer);

				return new Layer(biomeLayer);
			}

		}

}
