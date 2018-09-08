package com.setrion.koratio.world.biomes;

import java.util.Random;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenerator;

public class FantasiaBiomeDecorator extends BiomeDecorator
{
	
	@Override
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    {
    }
    
	@Override
    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre(worldIn, random, chunkPos));
        this.generateOres(worldIn, random);

        int k1 = this.treesPerChunk;

        if (random.nextFloat() < this.extraTreeChance)
        {
            ++k1;
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE))
        for (int j2 = 0; j2 < k1; ++j2)
        {
            int k6 = random.nextInt(16) + 8;
            int l = random.nextInt(16) + 8;
            WorldGenAbstractTree worldgenabstracttree = biomeIn.getRandomTreeFeature(random);
            worldgenabstracttree.setDecorationDefaults();
            BlockPos blockpos = worldIn.getHeight(this.chunkPos.add(k6, 0, l));

            if (worldgenabstracttree.generate(worldIn, random, blockpos))
            {
                worldgenabstracttree.generateSaplings(worldIn, random, blockpos);
            }
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM))
        for (int k2 = 0; k2 < this.bigMushroomsPerChunk; ++k2)
        {
            int l6 = random.nextInt(16) + 8;
            int k10 = random.nextInt(16) + 8;
            this.bigMushroomGen.generate(worldIn, random, worldIn.getHeight(this.chunkPos.add(l6, 0, k10)));
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS))
        for (int l2 = 0; l2 < this.flowersPerChunk; ++l2)
        {
            int i7 = random.nextInt(16) + 8;
            int l10 = random.nextInt(16) + 8;
            int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

            if (j14 > 0)
            {
                int k17 = random.nextInt(j14);
                BlockPos blockpos1 = this.chunkPos.add(i7, k17, l10);
                BlockFlower.EnumFlowerType blockflower$enumflowertype = biomeIn.pickRandomFlower(random, blockpos1);
                BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();

                if (blockflower.getDefaultState().getMaterial() != Material.AIR)
                {
                    this.flowerGen.setGeneratedBlock(blockflower, blockflower$enumflowertype);
                    this.flowerGen.generate(worldIn, random, blockpos1);
                }
            }
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS))
        for (int i3 = 0; i3 < this.grassPerChunk; ++i3)
        {
            int j7 = random.nextInt(16) + 8;
            int i11 = random.nextInt(16) + 8;
            int k14 = worldIn.getHeight(this.chunkPos.add(j7, 0, i11)).getY() * 2;

            if (k14 > 0)
            {
                int l17 = random.nextInt(k14);
                biomeIn.getRandomWorldGenForGrass(random).generate(worldIn, random, this.chunkPos.add(j7, l17, i11));
            }
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.DEAD_BUSH))
        for (int j3 = 0; j3 < this.deadBushPerChunk; ++j3)
        {
            int k7 = random.nextInt(16) + 8;
            int j11 = random.nextInt(16) + 8;
            int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

            if (l14 > 0)
            {
                int i18 = random.nextInt(l14);
                (new WorldGenDeadBush()).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
            }
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LILYPAD))
        for (int k3 = 0; k3 < this.waterlilyPerChunk; ++k3)
        {
            int l7 = random.nextInt(16) + 8;
            int k11 = random.nextInt(16) + 8;
            int i15 = worldIn.getHeight(this.chunkPos.add(l7, 0, k11)).getY() * 2;

            if (i15 > 0)
            {
                int j18 = random.nextInt(i15);
                BlockPos blockpos4;
                BlockPos blockpos7;

                for (blockpos4 = this.chunkPos.add(l7, j18, k11); blockpos4.getY() > 0; blockpos4 = blockpos7)
                {
                    blockpos7 = blockpos4.down();

                    if (!worldIn.isAirBlock(blockpos7))
                    {
                        break;
                    }
                }

                this.waterlilyGen.generate(worldIn, random, blockpos4);
            }
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SHROOM))
        {
        for (int l3 = 0; l3 < this.mushroomsPerChunk; ++l3)
        {
            if (random.nextInt(4) == 0)
            {
                int i8 = random.nextInt(16) + 8;
                int l11 = random.nextInt(16) + 8;
                BlockPos blockpos2 = worldIn.getHeight(this.chunkPos.add(i8, 0, l11));
                this.mushroomBrownGen.generate(worldIn, random, blockpos2);
            }

            if (random.nextInt(8) == 0)
            {
                int j8 = random.nextInt(16) + 8;
                int i12 = random.nextInt(16) + 8;
                int j15 = worldIn.getHeight(this.chunkPos.add(j8, 0, i12)).getY() * 2;

                if (j15 > 0)
                {
                    int k18 = random.nextInt(j15);
                    BlockPos blockpos5 = this.chunkPos.add(j8, k18, i12);
                    this.mushroomRedGen.generate(worldIn, random, blockpos5);
                }
            }
        }

        if (random.nextInt(4) == 0)
        {
            int i4 = random.nextInt(16) + 8;
            int k8 = random.nextInt(16) + 8;
            int j12 = worldIn.getHeight(this.chunkPos.add(i4, 0, k8)).getY() * 2;

            if (j12 > 0)
            {
                int k15 = random.nextInt(j12);
                this.mushroomBrownGen.generate(worldIn, random, this.chunkPos.add(i4, k15, k8));
            }
        }

        if (random.nextInt(8) == 0)
        {
            int j4 = random.nextInt(16) + 8;
            int l8 = random.nextInt(16) + 8;
            int k12 = worldIn.getHeight(this.chunkPos.add(j4, 0, l8)).getY() * 2;

            if (k12 > 0)
            {
                int l15 = random.nextInt(k12);
                this.mushroomRedGen.generate(worldIn, random, this.chunkPos.add(j4, l15, l8));
            }
        }
        } // End of Mushroom generation
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Post(worldIn, random, chunkPos));
    }

    /**
     * Generates ores in the current chunk
     */
    protected void generateOres(World worldIn, Random random)
    {
        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Pre(worldIn, random, chunkPos));
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, dirtGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIRT))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.dirtCount, this.dirtGen, this.chunkProviderSettings.dirtMinHeight, this.chunkProviderSettings.dirtMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, gravelOreGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRAVEL))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.gravelCount, this.gravelOreGen, this.chunkProviderSettings.gravelMinHeight, this.chunkProviderSettings.gravelMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, dioriteGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIORITE))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.dioriteCount, this.dioriteGen, this.chunkProviderSettings.dioriteMinHeight, this.chunkProviderSettings.dioriteMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, graniteGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRANITE))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.graniteCount, this.graniteGen, this.chunkProviderSettings.graniteMinHeight, this.chunkProviderSettings.graniteMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, andesiteGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.ANDESITE))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.andesiteCount, this.andesiteGen, this.chunkProviderSettings.andesiteMinHeight, this.chunkProviderSettings.andesiteMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, coalGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.COAL))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.coalCount, this.coalGen, this.chunkProviderSettings.coalMinHeight, this.chunkProviderSettings.coalMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, ironGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.IRON))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.ironCount, this.ironGen, this.chunkProviderSettings.ironMinHeight, this.chunkProviderSettings.ironMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, goldGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GOLD))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.goldCount, this.goldGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, redstoneGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.REDSTONE))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.redstoneCount, this.redstoneGen, this.chunkProviderSettings.redstoneMinHeight, this.chunkProviderSettings.redstoneMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, diamondGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIAMOND))
        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.diamondCount, this.diamondGen, this.chunkProviderSettings.diamondMinHeight, this.chunkProviderSettings.diamondMaxHeight);
        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, lapisGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.LAPIS))
        this.genStandardOre2(worldIn, random, this.chunkProviderSettings.lapisCount, this.lapisGen, this.chunkProviderSettings.lapisCenterHeight, this.chunkProviderSettings.lapisSpread);
        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Post(worldIn, random, chunkPos));
    }

    /**
     * Standard ore generation helper. Vanilla uses this to generate most ores.
     * The main difference between this and {@link #genStandardOre2} is that this takes min and max heights, while
     * genStandardOre2 takes center and spread.
     */
    protected void genStandardOre1(World worldIn, Random random, int blockCount, WorldGenerator generator, int minHeight, int maxHeight)
    {
        if (maxHeight < minHeight)
        {
            int i = minHeight;
            minHeight = maxHeight;
            maxHeight = i;
        }
        else if (maxHeight == minHeight)
        {
            if (minHeight < 255)
            {
                ++maxHeight;
            }
            else
            {
                --minHeight;
            }
        }

        for (int j = 0; j < blockCount; ++j)
        {
            BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(maxHeight - minHeight) + minHeight, random.nextInt(16));
            generator.generate(worldIn, random, blockpos);
        }
    }

    /**
     * Standard ore generation helper. Vanilla uses this to generate Lapis Lazuli.
     * The main difference between this and {@link #genStandardOre1} is that this takes takes center and spread, while
     * genStandardOre1 takes min and max heights.
     */
    protected void genStandardOre2(World worldIn, Random random, int blockCount, WorldGenerator generator, int centerHeight, int spread)
    {
        for (int i = 0; i < blockCount; ++i)
        {
            BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(spread) + random.nextInt(spread) + centerHeight - spread, random.nextInt(16));
            generator.generate(worldIn, random, blockpos);
        }
    }
    
}