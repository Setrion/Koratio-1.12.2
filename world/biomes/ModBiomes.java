package com.setrion.koratio.world.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import com.setrion.koratio.utils.ConfigUtil;

public class ModBiomes {
	public static final Biome FANTASIA_PLAINS = new BiomeFantasiaPlains();
	public static final Biome FANTASIA_FOREST = new BiomeFantasiaForest();
	public static final Biome FANTASIA_HILLS = new BiomeFantasiaHills();
	public static final Biome FANTASIA_SPIKED = new BiomeFantasiaSpiked();
	public static final Biome FANTASIA_RIVER = new BiomeFantasiaRiver();
	public static final Biome FANTASIA_STREAM = new BiomeFantasiaStream();
	
	public final static String dimFantasia = "Fantasia";
	
	public static void registerBiomes() {
		registerBiome(FANTASIA_PLAINS, "Fantasia Plains", BiomeType.WARM, dimFantasia, Type.PLAINS);
		registerBiome(FANTASIA_FOREST, "Fantasia Forest", BiomeType.WARM, dimFantasia, Type.FOREST);
		registerBiome(FANTASIA_HILLS, "Fantasia Hills", BiomeType.COOL, dimFantasia, Type.HILLS);
		registerBiome(FANTASIA_SPIKED, "Fantasia Spiked", BiomeType.COOL, dimFantasia, Type.HILLS);
		registerBiome(FANTASIA_RIVER, "Fantasia River", BiomeType.WARM, dimFantasia, Type.RIVER);
		registerBiome(FANTASIA_STREAM, "Fantasia Stream", BiomeType.COOL, dimFantasia, Type.COLD);
	}
	
	private static Biome registerBiome(Biome biome, String name, BiomeType biomeType, String dim, Type... type) {
		biome.setRegistryName(name);
		ForgeRegistries.BIOMES.register(biome);
		BiomeDictionary.addTypes(biome, type);
		if (dim == dimFantasia && ConfigUtil.generateFantasiaBiomesOverworld == true) {
			BiomeManager.addSpawnBiome(biome);
			BiomeManager.addBiome(biomeType, new BiomeEntry(biome, 10));
		}
		return biome;
	}

}
