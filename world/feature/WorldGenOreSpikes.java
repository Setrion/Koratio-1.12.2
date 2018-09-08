package com.setrion.koratio.world.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.setrion.koratio.blocks.ModBlocks;

public class WorldGenOreSpikes extends WorldGenerator {
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        while (worldIn.isAirBlock(position) && position.getY() > 2) {
            position = position.down();
        }

        if (worldIn.getBlockState(position).getBlock() != ModBlocks.blockFairyGrass) {
            return false;
        } else {
            position = position.up(rand.nextInt(4));
            int i = rand.nextInt(4) + 7;
            int j = i / 4 + rand.nextInt(2);

            if (j > 1 && rand.nextInt(60) == 0)
            {
                position = position.up(10 + rand.nextInt(30));
            }

            for (int k = 0; k < i; ++k)
            {
                float f = (1.0F - (float)k / (float)i) * (float)j;
                int l = MathHelper.ceil(f);

                for (int i1 = -l; i1 <= l; ++i1)
                {
                    float f1 = (float)MathHelper.abs(i1) - 0.25F;

                    for (int j1 = -l; j1 <= l; ++j1)
                    {
                        float f2 = (float)MathHelper.abs(j1) - 0.25F;

                        if ((i1 == 0 && j1 == 0 || f1 * f1 + f2 * f2 <= f * f) && (i1 != -l && i1 != l && j1 != -l && j1 != l || rand.nextFloat() <= 0.75F))
                        {
                            IBlockState iblockstate = worldIn.getBlockState(position.add(i1, k, j1));
                            Block block = iblockstate.getBlock();

                            if (iblockstate.getBlock().isAir(iblockstate, worldIn, position.add(i1, k, j1)) || block == Blocks.DIRT || block == ModBlocks.blockFairyGrass)
                            {
                                this.setBlockAndNotifyAdequately(worldIn, position.add(i1, k, j1), getRandomOre());
                            }

                            if (k != 0 && l > 1)
                            {
                                iblockstate = worldIn.getBlockState(position.add(i1, -k, j1));
                                block = iblockstate.getBlock();

                                if (iblockstate.getBlock().isAir(iblockstate, worldIn, position.add(i1, -k, j1)) || block == Blocks.DIRT || block == ModBlocks.blockFairyGrass)
                                {
                                    this.setBlockAndNotifyAdequately(worldIn, position.add(i1, -k, j1), getRandomOre());
                                }
                            }
                        }
                    }
                }
            }

            int k1 = j - 1;

            if (k1 < 0)
            {
                k1 = 0;
            }
            else if (k1 > 1)
            {
                k1 = 1;
            }

            for (int l1 = -k1; l1 <= k1; ++l1)
            {
                for (int i2 = -k1; i2 <= k1; ++i2)
                {
                    BlockPos blockpos = position.add(l1, -1, i2);
                    int j2 = 50;

                    if (Math.abs(l1) == 1 && Math.abs(i2) == 1)
                    {
                        j2 = rand.nextInt(5);
                    }

                    while (blockpos.getY() > 50)
                    {
                        IBlockState iblockstate1 = worldIn.getBlockState(blockpos);
                        Block block1 = iblockstate1.getBlock();

                        if (!iblockstate1.getBlock().isAir(iblockstate1, worldIn, blockpos) && block1 != Blocks.DIRT && block1 != ModBlocks.blockFairyGrass)
                        {
                            break;
                        }

                        this.setBlockAndNotifyAdequately(worldIn, blockpos, getRandomOre());
                        blockpos = blockpos.down();
                        --j2;

                        if (j2 <= 0)
                        {
                            blockpos = blockpos.down(rand.nextInt(5) + 1);
                            j2 = rand.nextInt(5);
                        }
                    }
                }
            }

            return true;
        }
    }

    public static IBlockState getRandomOre() {
    	Random rand = new Random();
    	int i = rand.nextInt(200);
    	if (i == 1) {
    		return Blocks.EMERALD_ORE.getDefaultState();
    	} else if (i > 1 && i < 5) {
    		return Blocks.DIAMOND_ORE.getDefaultState();
    	} else if (i > 5 && i < 15) {
    		return Blocks.GOLD_ORE.getDefaultState();
    	} else if (i > 15 && i < 25) {
    		return Blocks.REDSTONE_ORE.getDefaultState();
    	} else if (i > 25 && i < 35) {
    		return Blocks.LAPIS_ORE.getDefaultState();
    	} else if (i > 35 && i < 50) {
    		return Blocks.IRON_ORE.getDefaultState();
    	} else if (i > 50 && i < 65) {
    		return ModBlocks.oreCopper.getDefaultState();
    	} else if (i > 65 && i < 80) {
    		return ModBlocks.oreTin.getDefaultState();
    	} else if (i > 80 && i < 95) {
    		return ModBlocks.oreSteel.getDefaultState();
    	} else if (i > 95 && i < 110) {
    		return ModBlocks.oreSilver.getDefaultState();
    	} else if (i > 110 && i < 150) {
    		return Blocks.COAL_ORE.getDefaultState();
    	} else {
    		int i2 = rand.nextInt(12);
    		if (i2 == 1) {
    			return Blocks.STONE.getStateFromMeta(1);
    		} else if (i2 > 1 && i2 < 4) {
    			return Blocks.STONE.getStateFromMeta(3);
    		} else if (i2 > 4 && i2 < 8) {
    			return Blocks.STONE.getStateFromMeta(5);
    		} else {
    			return Blocks.STONE.getDefaultState();
    		}
    	}
    }
}