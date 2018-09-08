package com.setrion.koratio.world.dimension.fantasia;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.feature.WorldGenDungeons;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nullable;

// TODO: doc out all the vanilla copying
public class ChunkGeneratorFantasia extends ChunkGeneratorFantasiaBase {

	private final NoiseGeneratorOctaves minLimitPerlinNoise;
	private final NoiseGeneratorOctaves maxLimitPerlinNoise;
	private final NoiseGeneratorOctaves mainPerlinNoise;
	private final NoiseGeneratorOctaves noiseGen4;
	//private final NoiseGeneratorOctaves scaleNoise;
	private final NoiseGeneratorOctaves depthNoise;
	//private final NoiseGeneratorOctaves forestNoise;

	private final double[] heightMap;
	private final float[] biomeWeights;

	private double[] mainNoiseRegion;
	private double[] minLimitRegion;
	private double[] maxLimitRegion;
	private double[] depthRegion;


	public ChunkGeneratorFantasia(World world, long seed, boolean enableFeatures) {
		super(world, seed, enableFeatures, true);
		this.minLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
		this.maxLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
		this.mainPerlinNoise = new NoiseGeneratorOctaves(this.rand, 8);
		this.noiseGen4 = new NoiseGeneratorOctaves(rand, 4);
		//this.scaleNoise = new NoiseGeneratorOctaves(rand, 10);
		this.depthNoise = new NoiseGeneratorOctaves(rand, 16);
		//this.forestNoise = new NoiseGeneratorOctaves(rand, 8);
		this.heightMap = new double[825];
		this.biomeWeights = new float[25];

		for (int j = -2; j <= 2; ++j) {
			for (int k = -2; k <= 2; ++k) {
				float f = 10.0F / MathHelper.sqrt((float) (j * j + k * k) + 0.2F);
				this.biomeWeights[j + 2 + (k + 2) * 5] = f;
			}
		}
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		rand.setSeed(getSeed(x, z));
		ChunkPrimer primer = new ChunkPrimer();
		setBlocksInChunk(x, z, primer);

		// now we reload the biome array so that it's scaled 1:1 with blocks on the ground
		this.biomesForGeneration = world.getBiomeProvider().getBiomes(biomesForGeneration, x * 16, z * 16, 16, 16);
		replaceBiomeBlocks(x, z, primer, biomesForGeneration);

		return makeChunk(x, z, primer);
	}

	public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
		byte seaLevel = 63;
		this.biomesForGeneration = this.world.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
		this.generateHeightmap(x * 4, 0, z * 4);

		for (int k = 0; k < 4; ++k) {
			int l = k * 5;
			int i1 = (k + 1) * 5;

			for (int j1 = 0; j1 < 4; ++j1) {
				int k1 = (l + j1) * 33;
				int l1 = (l + j1 + 1) * 33;
				int i2 = (i1 + j1) * 33;
				int j2 = (i1 + j1 + 1) * 33;

				for (int k2 = 0; k2 < 32; ++k2) {
					double d0 = 0.125D;
					double d1 = this.heightMap[k1 + k2];
					double d2 = this.heightMap[l1 + k2];
					double d3 = this.heightMap[i2 + k2];
					double d4 = this.heightMap[j2 + k2];
					double d5 = (this.heightMap[k1 + k2 + 1] - d1) * d0;
					double d6 = (this.heightMap[l1 + k2 + 1] - d2) * d0;
					double d7 = (this.heightMap[i2 + k2 + 1] - d3) * d0;
					double d8 = (this.heightMap[j2 + k2 + 1] - d4) * d0;

					for (int l2 = 0; l2 < 8; ++l2) {
						double d9 = 0.25D;
						double d10 = d1;
						double d11 = d2;
						double d12 = (d3 - d1) * d9;
						double d13 = (d4 - d2) * d9;

						for (int i3 = 0; i3 < 4; ++i3) {
							double d14 = 0.25D;
							double d16 = (d11 - d10) * d14;
							double d15 = d10 - d16;

							for (int k3 = 0; k3 < 4; ++k3) {
								if ((d15 += d16) > 0.0D) {
									primer.setBlockState(k * 4 + i3, k2 * 8 + l2, j1 * 4 + k3, Blocks.STONE.getDefaultState());
								} else if (k2 * 8 + l2 < seaLevel) {
									primer.setBlockState(k * 4 + i3, k2 * 8 + l2, j1 * 4 + k3, Blocks.WATER.getDefaultState());
								}
							}

							d10 += d12;
							d11 += d13;
						}

						d1 += d5;
						d2 += d6;
						d3 += d7;
						d4 += d8;
					}
				}
			}
		}
	}

	private void generateHeightmap(int x, int zero, int z) {

		this.depthRegion = this.depthNoise.generateNoiseOctaves(this.depthRegion, x, z, 5, 5, 200.0D, 200.0D, 0.5D);
		this.mainNoiseRegion = this.mainPerlinNoise.generateNoiseOctaves(this.mainNoiseRegion, x, zero, z, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
		this.minLimitRegion = this.minLimitPerlinNoise.generateNoiseOctaves(this.minLimitRegion, x, zero, z, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		this.maxLimitRegion = this.maxLimitPerlinNoise.generateNoiseOctaves(this.maxLimitRegion, x, zero, z, 5, 33, 5, 684.412D, 684.412D, 684.412D);
		int terrainIndex = 0;
		int noiseIndex = 0;

		for (int ax = 0; ax < 5; ++ax) {
			for (int az = 0; az < 5; ++az) {
				float totalVariation = 0.0F;
				float totalHeight = 0.0F;
				float totalFactor = 0.0F;
				byte two = 2;
				Biome biome = this.biomesForGeneration[ax + 2 + (az + 2) * 10];

				for (int ox = -two; ox <= two; ++ox) {
					for (int oz = -two; oz <= two; ++oz) {
						Biome biome1 = this.biomesForGeneration[ax + ox + 2 + (az + oz + 2) * 10];
						float rootHeight = biome1.getBaseHeight();
						float heightVariation = biome1.getHeightVariation();

						if (this.terrainType == WorldType.AMPLIFIED && rootHeight > 0.0F) {
							rootHeight = 1.0F + rootHeight * 2.0F;
							heightVariation = 1.0F + heightVariation * 4.0F;
						}

						float heightFactor = this.biomeWeights[ox + 2 + (oz + 2) * 5] / (rootHeight + 2.0F);

						if (biome1.getBaseHeight() > biome.getBaseHeight()) {
							heightFactor /= 2.0F;
						}

						totalVariation += heightVariation * heightFactor;
						totalHeight += rootHeight * heightFactor;
						totalFactor += heightFactor;
					}
				}

				totalVariation /= totalFactor;
				totalHeight /= totalFactor;
				totalVariation = totalVariation * 0.9F + 0.1F;
				totalHeight = (totalHeight * 4.0F - 1.0F) / 8.0F;
				double terrainNoise = this.depthRegion[noiseIndex] / 8000.0D;

				if (terrainNoise < 0.0D) {
					terrainNoise = -terrainNoise * 0.3D;
				}

				terrainNoise = terrainNoise * 3.0D - 2.0D;

				if (terrainNoise < 0.0D) {
					terrainNoise /= 2.0D;

					if (terrainNoise < -1.0D) {
						terrainNoise = -1.0D;
					}

					terrainNoise /= 1.4D;
					terrainNoise /= 2.0D;
				} else {
					if (terrainNoise > 1.0D) {
						terrainNoise = 1.0D;
					}

					terrainNoise /= 8.0D;
				}

				++noiseIndex;
				double heightCalc = (double) totalHeight;
				double variationCalc = (double) totalVariation;
				heightCalc += terrainNoise * 0.2D;
				heightCalc = heightCalc * 8.5D / 8.0D;
				double d5 = 8.5D + heightCalc * 4.0D;

				for (int ay = 0; ay < 33; ++ay) {
					double d6 = ((double) ay - d5) * 12.0D * 128.0D / 256.0D / variationCalc;

					if (d6 < 0.0D) {
						d6 *= 4.0D;
					}

					double d7 = this.minLimitRegion[terrainIndex] / 512.0D;
					double d8 = this.maxLimitRegion[terrainIndex] / 512.0D;
					double d9 = (this.mainNoiseRegion[terrainIndex] / 10.0D + 1.0D) / 2.0D;
					double terrainCalc = MathHelper.clampedLerp(d7, d8, d9) - d6;

					if (ay > 29) {
						double d11 = (double) ((float) (ay - 29) / 3.0F);
						terrainCalc = terrainCalc * (1.0D - d11) + -10.0D * d11;
					}

					this.heightMap[terrainIndex] = terrainCalc;
					++terrainIndex;
				}
			}
		}
	}

	@Override
	public void populate(int x, int z) {

		BlockFalling.fallInstantly = true;

		int i = x * 16;
		int j = z * 16;
		BlockPos blockpos = new BlockPos(i, 0, j);
		Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
		this.rand.setSeed(this.world.getSeed());
		long k = this.rand.nextLong() / 2L * 2L + 1L;
		long l = this.rand.nextLong() / 2L * 2L + 1L;
		this.rand.setSeed((long)x * k + (long)z * l ^ this.world.getSeed());
		boolean flag = false;
		ChunkPos chunkpos = new ChunkPos(x, z);

		ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, flag);

		boolean disableFeatures = false;

		biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));

		if (TerrainGen.populate(this, this.world, this.rand, x, z, flag, PopulateChunkEvent.Populate.EventType.ANIMALS)) {
			WorldEntitySpawner.performWorldGenSpawning(this.world, biome, i + 8, j + 8, 16, 16, this.rand);
		}

		blockpos = blockpos.add(8, 0, 8);

		if (TerrainGen.populate(this, this.world, this.rand, x, z, flag, PopulateChunkEvent.Populate.EventType.ICE)) {
			for (int k2 = 0; k2 < 16; ++k2) {
				for (int j3 = 0; j3 < 16; ++j3) {

					BlockPos blockpos1 = this.world.getPrecipitationHeight(blockpos.add(k2, 0, j3));
					BlockPos blockpos2 = blockpos1.down();

					if (this.world.canBlockFreezeWater(blockpos2)) {
						this.world.setBlockState(blockpos2, Blocks.ICE.getDefaultState(), 2);
					}

					if (this.world.canSnowAt(blockpos1, true)) {
						this.world.setBlockState(blockpos1, Blocks.SNOW_LAYER.getDefaultState(), 2);
					}
				}
			}
		}//Forge: End ICE

		ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, flag);

		BlockFalling.fallInstantly = false;
	}

	@Nullable
	@Override
	public BlockPos getNearestStructurePos(World world, String structureName, BlockPos position, boolean findUnexplored) {
		return super.getNearestStructurePos(world, structureName, position, findUnexplored);
	}

	@Override
	public void recreateStructures(Chunk chunk, int x, int z) {
		super.recreateStructures(chunk, x, z);
	}

	@Override
	public boolean isInsideStructure(World world, String structureName, BlockPos pos) {
		return super.isInsideStructure(world, structureName, pos);
	}
}