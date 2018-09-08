package com.setrion.koratio.world.dimension.fantasia.layer;

import com.setrion.koratio.world.biomes.ModBiomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;

public class GenLayerFantasiaStream extends GenLayer {

	public GenLayerFantasiaStream(long l, GenLayer genlayer) {
		super(l);
		super.parent = genlayer;
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth) {

		int nx = x - 1;
		int nz = z - 1;
		int nwidth = width + 2;
		int ndepth = depth + 2;
		int input[] = parent.getInts(nx, nz, nwidth, ndepth);
		int output[] = IntCache.getIntCache(width * depth);

		int stream = Biome.getIdForBiome(ModBiomes.FANTASIA_RIVER);

		for (int dz = 0; dz < depth; dz++) {
			for (int dx = 0; dx < width; dx++) {

				int left  = input[dx + 0 + (dz + 1) * nwidth];
				int right = input[dx + 2 + (dz + 1) * nwidth];
				int down  = input[dx + 1 + (dz + 0) * nwidth];
				int up    = input[dx + 1 + (dz + 2) * nwidth];
				int mid   = input[dx + 1 + (dz + 1) * nwidth];
//                if(mid == 0 || left == 0 || right == 0 || down == 0 || up == 0)
//                {
//                    output[dx + dz * width] = Biome.getIdForBiome(BiomeLibrary.stream);
//                    continue;
//                }
				if (shouldStream(mid, left, down, right, up)) {
					output[dx + dz * width] = stream;
				} else {
					output[dx + dz * width] = -1;
				}
			}
		}

		return output;
	}

	boolean shouldStream(int mid, int left, int down, int right, int up) {
		if (shouldStream(mid, left)) {
			return true;
		} else if (shouldStream(mid, right)) {
			return true;
		} else if (shouldStream(mid, down)) {
			return true;
		} else if (shouldStream(mid, up)) {
			return true;
		} else {
			return false;
		}
	}

	boolean shouldStream(int id1, int id2) {

		if (id1 == id2) {
			return false;
		}
		if (id1 == -id2) {
			return false;
		}

		Biome biome1 = Biome.getBiomeForId(id1);
		Biome biome2 = Biome.getBiomeForId(id2);

		// glacier and snow have no border
		if (biome1 == ModBiomes.FANTASIA_FOREST && biome2 == ModBiomes.FANTASIA_HILLS) {
			return false;
		}
		if (biome1 == ModBiomes.FANTASIA_HILLS && biome2 == ModBiomes.FANTASIA_FOREST) {
			return false;
		}

		return true;
	}
}