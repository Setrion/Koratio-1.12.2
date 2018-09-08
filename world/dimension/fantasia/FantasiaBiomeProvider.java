package com.setrion.koratio.world.dimension.fantasia;

import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;

import java.util.Arrays;

import com.setrion.koratio.world.biomes.ModBiomes;
import com.setrion.koratio.world.dimension.fantasia.layer.GenLayerFantasiaBiomes;
import com.setrion.koratio.world.dimension.fantasia.layer.GenLayerFantasiaCompanionBiomes;
import com.setrion.koratio.world.dimension.fantasia.layer.GenLayerFantasiaKeyBiomes;
import com.setrion.koratio.world.dimension.fantasia.layer.GenLayerFantasiaRiverMix;
import com.setrion.koratio.world.dimension.fantasia.layer.GenLayerFantasiaStabilize;
import com.setrion.koratio.world.dimension.fantasia.layer.GenLayerFantasiaStream;

public class FantasiaBiomeProvider extends BiomeProvider {

	private final FantasiaBiomeCache mapCache;
	private GenLayer genBiomes;
    /** A GenLayer containing the indices into BiomeGenBase.biomeList[] */
    private GenLayer biomeIndexLayer;

	public FantasiaBiomeProvider(World world) {
		getBiomesToSpawnIn().clear();
		getBiomesToSpawnIn().add(ModBiomes.FANTASIA_PLAINS);
		getBiomesToSpawnIn().add(ModBiomes.FANTASIA_HILLS);
		getBiomesToSpawnIn().add(ModBiomes.FANTASIA_FOREST);
		getBiomesToSpawnIn().add(ModBiomes.FANTASIA_SPIKED);

		makeLayers(world.getSeed());
		mapCache = new FantasiaBiomeCache(this, 512, true);
	}

	private void makeLayers(long seed) {
		GenLayer biomes = new GenLayerFantasiaBiomes(1L);
		biomes = new GenLayerFantasiaKeyBiomes(1000L, biomes);
		biomes = new GenLayerFantasiaCompanionBiomes(1000L, biomes);

		biomes = new GenLayerZoom(1000L, biomes);
		biomes = new GenLayerZoom(1001, biomes);

		biomes = new GenLayerFantasiaStabilize(700L, biomes);

		biomes = new GenLayerZoom(1002, biomes);
		biomes = new GenLayerZoom(1003, biomes);
		biomes = new GenLayerZoom(1004, biomes);
		biomes = new GenLayerZoom(1005, biomes);

		GenLayer riverLayer = new GenLayerFantasiaStream(1L, biomes);
		riverLayer = new GenLayerSmooth(7000L, riverLayer);
		biomes = new GenLayerFantasiaRiverMix(100L, biomes, riverLayer);

		// do "voronoi" zoom
		GenLayer genlayervoronoizoom = new GenLayerVoronoiZoom(10L, biomes);

		biomes.initWorldGenSeed(seed);
		genlayervoronoizoom.initWorldGenSeed(seed);

		this.genBiomes = biomes;
		this.biomeIndexLayer = genlayervoronoizoom;
	}

	@Override
	public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height) {
		return getBiomesForGeneration(biomes, x, z, width, height, true);
	}

	public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height, boolean useCache) {
		// for grid-centred magic maps, get from map cache
		if (useCache && mapCache.isGridAligned(x, z, width, height)) {
			Biome[] cached = mapCache.getBiomes(x, z);
			return Arrays.copyOf(cached, cached.length);
		}
		return super.getBiomesForGeneration(biomes, x, z, width, height);
	}

	@Override
	public void cleanupCache() {
		mapCache.cleanup();
		super.cleanupCache();
	}
}