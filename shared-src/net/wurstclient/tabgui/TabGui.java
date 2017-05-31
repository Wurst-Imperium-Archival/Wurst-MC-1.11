/*
 * Copyright � 2014 - 2017 | Wurst-Imperium | All rights reserved.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package net.wurstclient.tabgui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.wurstclient.WurstClient;
import net.wurstclient.events.listeners.UpdateListener;
import net.wurstclient.font.Fonts;

public final class TabGui implements UpdateListener
{
	private final ArrayList<Tab> tabs = new ArrayList<>();
	
	public TabGui()
	{
		WurstClient.INSTANCE.events.add(UpdateListener.class, this);
		
		WurstClient wurst = WurstClient.INSTANCE;
		Tab blocksTab = new Tab("Blocks");
		blocksTab.add(wurst.mods.antiCactusMod);
		blocksTab.add(wurst.mods.autoBuildMod);
		blocksTab.add(wurst.mods.autoMineMod);
		tabs.add(blocksTab);
		
		Tab chatTab = new Tab("Chat");
		chatTab.add(wurst.mods.antiSpamMod);
		chatTab.add(wurst.mods.fancyChatMod);
		chatTab.add(wurst.mods.forceOpMod);
		tabs.add(chatTab);
	}
	
	@Override
	public void onUpdate()
	{
		
	}
	
	public void render(float partialTicks)
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glShadeModel(GL11.GL_SMOOTH);
		
		GL11.glPushMatrix();
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		GL11.glTranslatef(2, 22, 0);
		
		drawBox(0, 0, 92, 160);
		int factor = sr.getScaleFactor();
		GL11.glScissor(2 * factor, (sr.getScaledHeight() - 182) * factor,
			92 * factor, 160 * factor);
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		
		int posY = -2;
		for(Tab tab : tabs)
		{
			Fonts.segoe18.drawString(tab.getName(), 2, posY, 0xffffffff);
			posY += 10;
		}
		
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glPopMatrix();
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
	
	private void drawBox(int x1, int y1, int x2, int y2)
	{
		// color
		GL11.glColor4f(0.25F, 0.25F, 0.25F, 0.5F);
		
		// box
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glVertex2i(x1, y1);
			GL11.glVertex2i(x2, y1);
			GL11.glVertex2i(x2, y2);
			GL11.glVertex2i(x1, y2);
		}
		GL11.glEnd();
		
		// outline positions
		double xi1 = x1 - 0.1;
		double xi2 = x2 + 0.1;
		double yi1 = y1 - 0.1;
		double yi2 = y2 + 0.1;
		
		// outline
		GL11.glLineWidth(1);
		GL11.glColor4f(0, 0, 0, 0.5F);
		GL11.glBegin(GL11.GL_LINE_LOOP);
		{
			GL11.glVertex2d(xi1, yi1);
			GL11.glVertex2d(xi2, yi1);
			GL11.glVertex2d(xi2, yi2);
			GL11.glVertex2d(xi1, yi2);
		}
		GL11.glEnd();
		
		// shadow positions
		xi1 -= 0.9;
		xi2 += 0.9;
		yi1 -= 0.9;
		yi2 += 0.9;
		
		// top left
		GL11.glBegin(GL11.GL_POLYGON);
		{
			GL11.glColor4f(0.125F, 0.125F, 0.125F, 0.75F);
			GL11.glVertex2d(x1, y1);
			GL11.glVertex2d(x2, y1);
			GL11.glColor4f(0, 0, 0, 0);
			GL11.glVertex2d(xi2, yi1);
			GL11.glVertex2d(xi1, yi1);
			GL11.glVertex2d(xi1, yi2);
			GL11.glColor4f(0.125F, 0.125F, 0.125F, 0.75F);
			GL11.glVertex2d(x1, y2);
		}
		GL11.glEnd();
		
		// bottom right
		GL11.glBegin(GL11.GL_POLYGON);
		{
			GL11.glVertex2d(x2, y2);
			GL11.glVertex2d(x2, y1);
			GL11.glColor4f(0, 0, 0, 0);
			GL11.glVertex2d(xi2, yi1);
			GL11.glVertex2d(xi2, yi2);
			GL11.glVertex2d(xi1, yi2);
			GL11.glColor4f(0.125F, 0.125F, 0.125F, 0.75F);
			GL11.glVertex2d(x1, y2);
		}
		GL11.glEnd();
	}
}
