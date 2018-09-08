package com.setrion.koratio.world.biomes;

import net.minecraft.world.biome.Biome;

public class BiomeFantasiaRiver extends Biome {

	public BiomeFantasiaRiver() {
		super(new BiomeProperties("fantasia_river").setWaterColor(-16711936).setBaseHeight(-0.5F).setHeightVariation(0.0F));
		this.spawnableCreatureList.clear();
	}

}
