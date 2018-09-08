package com.setrion.koratio.world.dimension.fantasia;

import net.minecraft.block.Block;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// TODO: doc out all the vanilla copying
public abstract class ChunkGeneratorFantasiaBase implements IChunkGenerator {

	protected final Random rand;

	protected final NoiseGeneratorPerlin surfaceNoise;

	protected final World world;
	protected final WorldType terrainType;

	protected double[] depthBuffer = new double[256];
	protected Biome[] biomesForGeneration;

	//protected final MapGenTFMajorFeatureOld majorFeatureGenerator = new MapGenTFMajorFeatureOld();

	private final boolean shouldGenerateBedrock;

	protected static long getSeed(int x, int z) {
		return x * 0x4f9939f508L + z * 0x1ef1565bd5L;
	}

	public ChunkGeneratorFantasiaBase(World world, long seed, boolean enableFeatures, boolean shouldGenerateBedrock) {
		this.world = world;
		this.terrainType = world.getWorldInfo().getTerrainType();
		this.rand = new Random(seed);
		this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);
		this.shouldGenerateBedrock = shouldGenerateBedrock;
	}

	protected final Chunk makeChunk(int x, int z, ChunkPrimer primer) {

		Chunk chunk = new Chunk(world, primer, x, z);

		// load in biomes, to prevent striping?!
		byte[] chunkBiomes = chunk.getBiomeArray();
		for (int i = 0; i < chunkBiomes.length; ++i) {
			chunkBiomes[i] = (byte) Biome.getIdForBiome(this.biomesForGeneration[i]);
		}

		chunk.generateSkylightMap();

		return chunk;
	}

	// [VanillaCopy] Exact, ChunkGeneratorOverworld.replaceBiomeBlocks
	public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn)
	{
		if (!ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, this.world)) return;
		double d0 = 0.03125D;
		this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, (double)(x * 16), (double)(z * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);

		for (int i = 0; i < 16; ++i)
		{
			for (int j = 0; j < 16; ++j)
			{
				Biome biome = biomesIn[j + i * 16];
				biome.genTerrainBlocks(this.world, this.rand, primer, x * 16 + i, z * 16 + j, this.depthBuffer[j + i * 16]);
			}
		}
	}

	/**
	 * Raises up and hollows out the hollow hills.
	 */

	protected final boolean allowSurfaceLakes(Biome biome) {
		return false;
	}

	public final boolean shouldGenerateBedrock() {
		return shouldGenerateBedrock;
	}

	@Override
	public boolean generateStructures(Chunk chunk, int x, int z) {
		return false;
	}

	/**
	 * Returns a list of creatures of the specified type that can spawn at the
	 * given location.
	 * <p>
	 * Twilight Forest variant! First check features, then only if we're not in
	 * a feature, check the biome.
	 */
	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		return null;
	}

	@Nullable
	@Override
	public BlockPos getNearestStructurePos(World world, String structureName, BlockPos position, boolean findUnexplored) {
		return null;
	}

	//public StructureBoundingBox getFullSBBAt(int mapX, int mapZ) {
	//	TFFeature.getFeatureAt(mapX, mapZ, world).getFeatureGenerator().getFullSBBAt(mapX, mapZ);
	//}

	@Override
	public void recreateStructures(Chunk chunk, int x, int z) {
	}

	@Override
	public boolean isInsideStructure(World world, String structureName, BlockPos pos) {
		return false;
	}
}