package com.setrion.koratio.world.biomes;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.setrion.koratio.blocks.ModBlocks;
import com.setrion.koratio.entities.passive.EntityFantasiaChicken;
import com.setrion.koratio.world.feature.WorldGenFantasiaGrass;
import com.setrion.koratio.world.feature.WorldGenJubanBigTree;

public class BiomeFantasiaHills extends Biome {
	
	protected static final WorldGenAbstractTree BIG_TREE = new WorldGenJubanBigTree(true);

	public BiomeFantasiaHills() {
		super(new BiomeProperties("fantasia_hills").setWaterColor(-16711936).setBaseHeight(1.0F).setHeightVariation(0.5F).setTemperature(0.2F));
		
		this.topBlock = ModBlocks.blockFairyGrass.getDefaultState();
		this.fillerBlock = Blocks.DIRT.getDefaultState();
		
		this.decorator.treesPerChunk = 0;
        this.decorator.extraTreeChance = 0.0125F;
        this.decorator.flowersPerChunk = 0;
		this.decorator.reedsPerChunk = 0;
		
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		
		this.spawnableCreatureList.add(new SpawnListEntry(EntityFantasiaChicken.class, 10, 3, 5));
	}
	
	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
		return BIG_TREE;
	}
	
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random rand)
    {
        return new WorldGenFantasiaGrass();
    }

}
