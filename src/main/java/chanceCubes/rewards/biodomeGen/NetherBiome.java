package chanceCubes.rewards.biodomeGen;

import java.util.List;
import java.util.Random;

import chanceCubes.rewards.giantRewards.BioDomeReward;
import chanceCubes.rewards.rewardparts.OffsetBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NetherBiome implements IBioDomeBiome
{
	private Random rand = new Random();

	@Override
	public void spawnEntities(BlockPos center, World world)
	{
		for(int i = 0; i < rand.nextInt(10) + 5; i++)
		{
			int ri = rand.nextInt(5);

			if(ri == 0)
			{
				EntityGhast ghast = new EntityGhast(world);
				ghast.setLocationAndAngles(center.getX() + (rand.nextInt(31) - 15), center.getY() + 5, center.getZ() + (rand.nextInt(31) - 15), 0, 0);
				world.spawnEntityInWorld(ghast);
			}
			else
			{
				EntityPigZombie pigman = new EntityPigZombie(world);
				pigman.setLocationAndAngles(center.getX() + (rand.nextInt(31) - 15), center.getY() + 1, center.getZ() + (rand.nextInt(31) - 15), 0, 0);
				world.spawnEntityInWorld(pigman);
			}
		}
	}

	@Override
	public Block getFloorBlock()
	{
		return Blocks.NETHERRACK;
	}

	@Override
	public void getRandomGenBlock(float dist, Random rand, int x, int y, int z, List<OffsetBlock> blocks, int delay)
	{
		if(y != 0)
			return;

		if(dist < 0 && rand.nextInt(50) == 0)
		{
			OffsetBlock osb = new OffsetBlock(x, y - 1, z, Blocks.NETHERRACK, false, (delay / BioDomeReward.delayShorten));
			blocks.add(osb);
			delay++;
			osb = new OffsetBlock(x, y, z, Blocks.LAVA, false, (delay / BioDomeReward.delayShorten) + 1);
			blocks.add(osb);
			delay++;
		}
		else if(dist < 0 && rand.nextInt(20) == 0)
		{
			OffsetBlock osb = new OffsetBlock(x, y, z, Blocks.SOUL_SAND, false, (delay / BioDomeReward.delayShorten) + 1);
			blocks.add(osb);
			delay++;
		}
	}
}