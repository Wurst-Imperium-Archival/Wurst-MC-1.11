/*
 * Copyright � 2014 - 2017 | Wurst-Imperium | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.wurstclient.features.mods;

import net.wurstclient.events.listeners.UpdateListener;
import net.wurstclient.features.Mod;

@Mod.Info(tags = "NightVision, full bright, brightness, night vision",
	help = "Mods/Fullbright")
@Mod.Bypasses
public final class FullbrightMod extends Mod implements UpdateListener
{
	public FullbrightMod()
	{
		super("Fullbright", "Allows you to see in the dark.");
		wurst.events.add(UpdateListener.class, this);
	}
	
	@Override
	public void onDisable()
	{
		if(wurst.mods.panicMod.isActive())
			mc.gameSettings.gammaSetting = 0.5F;
	}
	
	@Override
	public void onUpdate()
	{
		if(isEnabled() || wurst.mods.xRayMod.isActive())
		{
			if(mc.gameSettings.gammaSetting < 16F)
				mc.gameSettings.gammaSetting += 0.5F;
		}else if(mc.gameSettings.gammaSetting > 0.5F)
			if(mc.gameSettings.gammaSetting < 1F)
				mc.gameSettings.gammaSetting = 0.5F;
			else
				mc.gameSettings.gammaSetting -= 0.5F;
	}
}
