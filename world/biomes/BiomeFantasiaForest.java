package com.setrion.koratio.world.biomes;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.setrion.koratio.blocks.ModBlocks;
import com.setrion.koratio.entities.passive.EntityAlienunicorncat;
import com.setrion.koratio.entities.passive.EntityFantasiaChicken;
import com.setrion.koratio.world.feature.WorldGenFantasiaGrass;
import com.setrion.koratio.world.feature.WorldGenJubanTree;

public class BiomeFantasiaForest extends Biome {

	protected static final WorldGenAbstractTree TREE = new WorldGenJubanTree(true);
	
	public BiomeFantasiaForest() {
		super(new BiomeProperties("fantasia_forest").setWaterColor(-16711936).setTemperature(0.7F));
		
		this.topBlock = ModBlocks.blockFairyGrass.getDefaultState();
		this.fillerBlock = Blocks.DIRT.getDefaultState();
		
		this.decorator.treesPerChunk = 10;
        this.decorator.grassPerChunk = 2;
        this.decorator.reedsPerChunk = 0;
        this.flowers.clear();
        this.addFlower(ModBlocks.blockFantasiaFlower.getDefaultState(), 10);
        
        this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		
		this.spawnableCreatureList.add(new SpawnListEntry(EntityAlienunicorncat.class, 10, 3, 5));
		this.spawnableCreatureList.add(new SpawnListEntry(EntityFantasiaChicken.class, 10, 3, 5));
	}
	
	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
		return TREE;
	}
	
	@Override
	public WorldGenerator getRandomWorldGenForGrass(Random rand)
    {
        return new WorldGenFantasiaGrass();
    }

}
