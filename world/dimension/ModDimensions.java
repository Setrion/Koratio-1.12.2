package com.setrion.koratio.world.dimension;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

import com.setrion.koratio.utils.ConfigUtil;
import com.setrion.koratio.world.dimension.fantasia.FantasiaWorldProvider;

public class ModDimensions {

	public static final DimensionType FANTASIA = DimensionType.register("fantasia", "_fantasia", ConfigUtil.fantasia_id, FantasiaWorldProvider.class, false);
	/* public static final DimensionType DEMONIC = DimensionType.register("Demonic", "_demonic", 8, DemonicWorldProvider.class, false); */
	
	public static void registerDimensions() {
		DimensionManager.registerDimension(ConfigUtil.fantasia_id, FANTASIA);
	}
	
}
