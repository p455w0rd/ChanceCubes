package chanceCubes.util;

import java.util.ArrayList;
import java.util.List;

import chanceCubes.rewards.rewardparts.CommandPart;
import chanceCubes.rewards.rewardparts.EntityPart;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import chanceCubes.rewards.rewardparts.ParticlePart;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class RewardsUtil
{
	/**
	 * 
	 * @param xSize
	 * @param ySize
	 * @param zSize
	 * @param block
	 * @param xOff
	 * @param yOff
	 * @param zOff
	 * @param falling
	 * @param delay
	 * @param causeUpdate
	 * @param relativeToPlayer
	 * @return
	 */
	public static OffsetBlock[] fillArea(int xSize, int ySize, int zSize, Block block, int xOff, int yOff, int zOff, boolean falling, int delay, boolean causesUpdate, boolean relativeToPlayer)
	{
		List<OffsetBlock> toReturn = new ArrayList<OffsetBlock>();

		for(int y = 0; y < ySize; y++)
			for(int z = 0; z < zSize; z++)
				for(int x = 0; x < xSize; x++)
					toReturn.add(new OffsetBlock(x + xOff, y + yOff, z + zOff, block, falling, delay).setCausesBlockUpdate(causesUpdate).setRelativeToPlayer(relativeToPlayer));

		return toReturn.toArray(new OffsetBlock[toReturn.size()]);
	}

	public static OffsetBlock[] addBlocksLists(OffsetBlock[]... lists)
	{
		int size = 0;
		for(OffsetBlock[] list : lists)
			size += list.length;

		OffsetBlock[] toReturn = new OffsetBlock[size];

		int i = 0;
		for(OffsetBlock[] list : lists)
		{
			for(OffsetBlock osb : list)
			{
				toReturn[i] = osb;
				i++;
			}
		}

		return toReturn;
	}

	public static EntityPart[] spawnXEntities(NBTTagCompound entityNbt, int amount)
	{
		EntityPart[] toReturn = new EntityPart[amount];
		for(int i = 0; i < amount; i++)
			toReturn[i] = new EntityPart(entityNbt);
		return toReturn;
	}

	public static CommandPart[] executeXCommands(String command, int amount)
	{
		CommandPart[] toReturn = new CommandPart[amount];
		for(int i = 0; i < amount; i++)
			toReturn[i] = new CommandPart(command);
		return toReturn;
	}

	public static CommandPart[] executeXCommands(String command, int amount, int delay)
	{
		CommandPart[] toReturn = new CommandPart[amount];
		for(int i = 0; i < amount; i++)
		{
			CommandPart part = new CommandPart(command);
			part.setDelay(delay);
			toReturn[i] = part;
		}
		return toReturn;
	}

	public static ParticlePart[] spawnXParticles(int particle, int amount)
	{
		ParticlePart[] toReturn = new ParticlePart[amount];
		for(int i = 0; i < amount; i++)
			toReturn[i] = new ParticlePart(particle);
		return toReturn;
	}

	public static void sendMessageToNearPlayers(World world, BlockPos pos, int distance, String message)
	{
		for(int i = 0; i < world.playerEntities.size(); ++i)
		{
			EntityPlayer entityplayer = (EntityPlayer) world.playerEntities.get(i);
			double dist = Math.sqrt(Math.pow(pos.getX() - entityplayer.posX, 2) + Math.pow(pos.getY() - entityplayer.posY, 2) + Math.pow(pos.getZ() - entityplayer.posZ, 2));
			if(dist <= distance)
				entityplayer.addChatMessage(new TextComponentString(message));
		}
	}

	public static void sendMessageToAllPlayers(World world, String message)
	{
		for(int i = 0; i < world.playerEntities.size(); ++i)
		{
			EntityPlayer entityplayer = (EntityPlayer) world.playerEntities.get(i);
			entityplayer.addChatMessage(new TextComponentString(message));
		}
	}

	public static ItemStack getItemStack(String mod, String itemName, int size)
	{
		return getItemStack(mod, itemName, size, 0);
	}

	public static ItemStack getItemStack(String mod, String itemName, int size, int meta)
	{
		Item item = Item.REGISTRY.getObject(new ResourceLocation(mod, itemName));
		return item == null ? null : new ItemStack(item, size, meta);
	}

	public static Block getBlock(String mod, String blockName)
	{
		return Block.REGISTRY.getObject(new ResourceLocation(mod, blockName));
	}

	public static boolean placeBlock(IBlockState b, World world, BlockPos pos)
	{
		return RewardsUtil.placeBlock(b, world, pos, 3);
	}

	public static boolean placeBlock(IBlockState b, World world, BlockPos pos, int update)
	{
		if(!RewardsUtil.isBlockUnbreakable(world, pos))
		{
			world.setBlockState(pos, b, update);
			return true;
		}
		return false;
	}

	public static boolean isBlockUnbreakable(World world, BlockPos pos)
	{
		return world.getBlockState(pos).getBlockHardness(world, pos) == -1;
	}

}