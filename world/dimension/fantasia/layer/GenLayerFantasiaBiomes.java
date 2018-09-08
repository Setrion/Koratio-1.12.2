package com.setrion.koratio.world.dimension.fantasia.layer;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.setrion.koratio.world.biomes.ModBiomes;

/**
 * Applies the twilight forest biomes to the map
 *
 * @author Ben
 */
public class GenLayerFantasiaBiomes extends GenLayer {

	private static final int RARE_BIOME_CHANCE = 15;

	protected static final List<Supplier<Biome>> commonBiomes = Arrays.asList(
			() -> ModBiomes.FANTASIA_PLAINS,
			() -> ModBiomes.FANTASIA_FOREST,
			() -> ModBiomes.FANTASIA_HILLS
	);
	protected static final List<Supplier<Biome>> rareBiomes = Arrays.asList(
			() -> ModBiomes.FANTASIA_SPIKED
	);

	public GenLayerFantasiaBiomes(long l, GenLayer genlayer) {
		super(l);
		parent = genlayer;
	}

	public GenLayerFantasiaBiomes(long l) {
		super(l);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth) {

		int dest[] = IntCache.getIntCache(width * depth);

		for (int dz = 0; dz < depth; dz++) {
			for (int dx = 0; dx < width; dx++) {
				initChunkSeed(dx + x, dz + z);
				if (nextInt(RARE_BIOME_CHANCE) == 0) {
					// make rare biome
					dest[dx + dz * width] = Biome.getIdForBiome(ModBiomes.FANTASIA_SPIKED);
				} else {
					// make common biome
					dest[dx + dz * width] = Biome.getIdForBiome(ModBiomes.FANTASIA_PLAINS);
				}
			}
		}
		return dest;
	}

	private Biome getRandomBiome(List<Supplier<Biome>> biomes) {
		return biomes.get(nextInt(biomes.size())).get();
	}
}