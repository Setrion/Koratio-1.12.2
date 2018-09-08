package com.setrion.koratio.world.biomes;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.setrion.koratio.blocks.ModBlocks;
import com.setrion.koratio.entities.passive.EntityAlienunicorncat;
import com.setrion.koratio.world.feature.WorldGenFantasiaGrass;
import com.setrion.koratio.world.feature.WorldGenJubanBigTree;
import com.setrion.koratio.world.feature.WorldGenJubanTree;

public class BiomeFantasiaPlains extends Biome {

	protected static final WorldGenAbstractTree TREE = new WorldGenJubanTree(true);
	protected static final WorldGenAbstractTree BIG_TREE = new WorldGenJubanBigTree(true);
	
	public BiomeFantasiaPlains() {
		super(new BiomeProperties("fantasia_plains").setWaterColor(-16711936).setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F));
		
		this.topBlock = ModBlocks.blockFairyGrass.getDefaultState();
		this.fillerBlock = Blocks.DIRT.getDefaultState();
		
		this.flowers.clear();
		
		this.decorator.treesPerChunk = 0;
        this.decorator.extraTreeChance = 0.05F;
        this.decorator.flowersPerChunk = 0;
		this.decorator.reedsPerChunk = 0;
		this.decorator.bigMushroomsPerChunk = 1;

		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		
		this.spawnableCreatureList.add(new SpawnListEntry(EntityAlienunicorncat.class, 10, 3, 5));
	}
	
	
	
	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
		int i = rand.nextInt(5);
		if (i == 1) {
			return BIG_TREE;
		} else {
			return TREE;
		}
	}
	
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random rand)
    {
        return new WorldGenFantasiaGrass();
    }

}
