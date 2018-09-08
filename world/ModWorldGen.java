package com.setrion.koratio.world;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import com.setrion.koratio.blocks.ModBlocks;
import com.setrion.koratio.utils.ConfigUtil;

public class ModWorldGen implements IWorldGenerator {

	private final Random rand = new Random();
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if (world.provider.getDimension() == 0) {
			generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		} else if (world.provider.getDimension() == -1) {
			generateNether(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		} else if (world.provider.getDimension() == 1) {
			generateEnd(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		} else if (world.provider.getDimension() == ConfigUtil.fantasia_id) {
			generateFantasia(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
		}
	}
	
	private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		generateOre(ModBlocks.oreCopper.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, 6 + random.nextInt(3), 6);
		generateOre(ModBlocks.oreSteel.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, 6 + random.nextInt(3), 6);
		generateOre(ModBlocks.oreTin.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, 6 + random.nextInt(3), 6);
		generateOre(ModBlocks.oreSilver.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, 6 + random.nextInt(3), 6);
	}
	
	private void generateNether(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		generateNetherOre(ModBlocks.oreFlarus.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, 3 + random.nextInt(3), 2);
	}
	
	private void generateEnd(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		generateEndOre(ModBlocks.oreEnderium.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, 3 + random.nextInt(3), 2);
	}

	private void generateFantasia(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		generateOre(ModBlocks.oreCopper.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, 6 + random.nextInt(3), 6);
		generateOre(ModBlocks.oreSteel.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, 6 + random.nextInt(3), 6);
		generateOre(ModBlocks.oreTin.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, 6 + random.nextInt(3), 6);
		generateOre(ModBlocks.oreSilver.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, 6 + random.nextInt(3), 6);
	}
	
	private void generateOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances) {
		int deltaY = maxY - minY;
	
		for (int i = 0; i < chances; i++) {
			BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));
			
			WorldGenMinable generator = new WorldGenMinable(ore, size, BlockMatcher.forBlock(Blocks.STONE));
			generator.generate(world, random, pos);
		}
	}
	
	private void generateNetherOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances) {
		int deltaY = maxY - minY;
	
		for (int i = 0; i < chances; i++) {
			BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));
			
			WorldGenMinable generator = new WorldGenMinable(ore, size, BlockMatcher.forBlock(Blocks.NETHERRACK));
			generator.generate(world, random, pos);
		}
	}
	
	private void generateEndOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances) {
		int deltaY = maxY - minY;
	
		for (int i = 0; i < chances; i++) {
			BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));
			
			WorldGenMinable generator = new WorldGenMinable(ore, size, BlockMatcher.forBlock(Blocks.END_STONE));
			generator.generate(world, random, pos);
		}
	}
	
	private void generateFantasiaOre(IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances) {
		int deltaY = maxY - minY;
	
		for (int i = 0; i < chances; i++) {
			BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));
			
			WorldGenMinable generator = new WorldGenMinable(ore, size, BlockMatcher.forBlock(Blocks.STONE));
			generator.generate(world, random, pos);
		}
	}
	
}
