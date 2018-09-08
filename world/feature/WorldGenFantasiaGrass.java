package com.setrion.koratio.world.feature;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.setrion.koratio.blocks.ModBlocks;

public class WorldGenFantasiaGrass extends WorldGenerator
{
    private final IBlockState fantasiaGrassState;
    private final IBlockState fantasiaFlowerState;

    public WorldGenFantasiaGrass()
    {
        this.fantasiaGrassState = ModBlocks.blockFantasiaGrass.getDefaultState();
        this.fantasiaFlowerState = ModBlocks.blockFantasiaFlower.getDefaultState();
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (IBlockState iblockstate = worldIn.getBlockState(position); (iblockstate.getBlock().isAir(iblockstate, worldIn, position) || iblockstate.getBlock().isLeaves(iblockstate, worldIn, position)) && position.getY() > 0; iblockstate = worldIn.getBlockState(position))
        {
            position = position.down();
        }

        for (int i = 0; i < 128; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && ModBlocks.blockFantasiaGrass.canBlockStay(worldIn, blockpos, this.fantasiaGrassState))
            {
            	int t = rand.nextInt(5);
            	if (t == 1) {
            		worldIn.setBlockState(blockpos, this.fantasiaFlowerState, 2);
            	} else {
            		worldIn.setBlockState(blockpos, this.fantasiaGrassState, 2);
            	}
            }
        }

        return true;
    }
}