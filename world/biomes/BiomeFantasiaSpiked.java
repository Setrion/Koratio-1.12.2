package com.setrion.koratio.world.biomes;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import com.setrion.koratio.blocks.ModBlocks;
import com.setrion.koratio.entities.monster.EntityElementalGolem;
import com.setrion.koratio.entities.passive.EntityFantasiaChicken;
import com.setrion.koratio.world.feature.WorldGenOreSpikes;

public class BiomeFantasiaSpiked extends Biome {

	private final WorldGenOreSpikes oreSpike = new WorldGenOreSpikes();
	
	public BiomeFantasiaSpiked() {
		super(new BiomeProperties("fantasia_spiked").setWaterColor(-16711936).setBaseHeight(0.325F).setHeightVariation(0.1F).setTemperature(0.3F));
		
		this.topBlock = ModBlocks.blockFairyGrass.getDefaultState();
		this.fillerBlock = Blocks.DIRT.getDefaultState();
		
		this.decorator.treesPerChunk = -999;
        this.decorator.flowersPerChunk = 0;
        this.decorator.grassPerChunk = 0;
		this.decorator.reedsPerChunk = 0;
		
		this.spawnableCaveCreatureList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.spawnableWaterCreatureList.clear();
		
		this.spawnableCreatureList.add(new SpawnListEntry(EntityFantasiaChicken.class, 10, 3, 5));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityElementalGolem.class, 5, 1, 2));
		
	}
	
	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
		return null;
	}
	
	public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        for (int i = 0; i < 3; ++i)
        {
            int j = rand.nextInt(16) + 8;
            int k = rand.nextInt(16) + 8;
            this.oreSpike.generate(worldIn, rand, worldIn.getHeight(pos.add(j, 0, k)));
        }

        super.decorate(worldIn, rand, pos);
    }

}
