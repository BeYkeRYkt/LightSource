package ykt.BeYkeRYkt.LightSource.nmsUtils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_5_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_6_R3.Chunk;
import net.minecraft.server.v1_6_R3.Entity;
import net.minecraft.server.v1_6_R3.EntityHuman;
import net.minecraft.server.v1_6_R3.EntityPlayer;
import net.minecraft.server.v1_6_R3.EnumSkyBlock;
import net.minecraft.server.v1_6_R3.IWorldAccess;
import net.minecraft.server.v1_6_R3.Packet51MapChunk;
import net.minecraft.server.v1_6_R3.PlayerChunkMap;
import net.minecraft.server.v1_6_R3.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_6_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_6_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;



public class NMSHandler_v1_6_R3 implements NMSInterface {
	/**
	 * 
	 * BETA STAGE №1 !!
	 * 
	 * @author BeYkeRYkt
	 */

	private static BlockFace[] SIDES = { BlockFace.UP, BlockFace.DOWN,
			BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

	@Override
	public void recalculateBlockLighting(World world, int x, int y, int z) {
		WorldServer nmsWorld = ((CraftWorld) world).getHandle();
		nmsWorld.A(x, y, z);
	}

	
	
	//LOCATION
	@Override
	public void createLightSource(Location loc, int level) {
		WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();

		nmsWorld.b(EnumSkyBlock.BLOCK, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), level);

		updateChunk(loc.getWorld(), loc);
	}

	@Override
	public void deleteLightSource(Location loc) {
		WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
		nmsWorld.c(EnumSkyBlock.BLOCK, loc.getBlockX(), loc.getBlockY(),loc.getBlockZ());
	}

	@Override
	public void deleteLightSourceAndUpdate(Location loc) {
		deleteLightSource(loc);

		updateChunk(loc.getWorld(), loc);
	}
	
	public Block getAdjacentAirBlock(Block block) {
		for (BlockFace face : SIDES) {
			if (block.getY() == 0x0 && face == BlockFace.DOWN)
				continue;
			if (block.getY() == 0xFF && face == BlockFace.UP)
				continue;

			Block candidate = block.getRelative(face);

			if (candidate.getType().isTransparent()) {
				return candidate;
			}
		}
		return block;
	}
	
	@Override
	public void updateChunk(World world, Location loc){
		for(Player player : world.getPlayers()){
			if(player.getLocation().distance(loc) <= Bukkit.getViewDistance() * 16){
			EntityPlayer nmsplayers = ((CraftPlayer) player).getHandle();
			 
			List<Chunk> cs = new ArrayList<Chunk>();
			 
			for(int x=-1; x<=1; x++) {
			for(int z=-1; z<=1; z++) {
			
			Chunk chunk = ((CraftChunk)loc.clone().add(16 * x, 0, 16 * z).getChunk()).getHandle();
			
			if(!cs.contains(chunk)){	
			cs.add(chunk);
			}
			
			}
			}
			Block adjacent = getAdjacentAirBlock(loc.getBlock());

			recalculateBlockLighting(world, adjacent.getX(), adjacent.getY(),adjacent.getZ());
			
			sendPackets(cs, nmsplayers);
			}
		}
	}
	
	public void sendPackets(List list, EntityPlayer nmsplayers){
		int i = list.size();
		
		for (int k = 0; k < i; ++k) {
			Chunk chunk = (Chunk) list.get(k);
			
			ChunkCoordIntPair coord = new ChunkCoordIntPair(chunk.x, chunk.z);
			if(!nmsplayers.chunkCoordIntPairQueue.contains(coord)){
			Packet51MapChunk packet = new Packet51MapChunk(chunk, false, '\uffff');
			nmsplayers.playerConnection.sendPacket(packet);
			}
		}
	}

	private static IWorldAccess countLightUpdates(final org.bukkit.World world,
			final PlayerChunkMap map) {
		return new IWorldAccess() {
			@Override
			public void a(int x, int y, int z) {
				map.flagDirty(x, y, z);
			}

			@Override
			public void b(int x, int y, int z) {
				map.flagDirty(x, y, z);
			}

			@Override
			public void b(int arg0, int arg1, int arg2, int arg3, int arg4) {
			}

			@Override
			public void a(EntityHuman arg0, int arg1, int arg2, int arg3,
					int arg4, int arg5) {
			}

			@Override
			public void a(int minX, int minY, int minZ, int maxX, int maxY,
					int maxZ) {
			}

			@Override
			public void a(int arg0, int arg1, int arg2, int arg3, int arg4) {
			}

			@Override
			public void a(String arg0, double arg1, double arg2, double arg3,
					float arg4, float arg5) {
			}

			@Override
			public void a(EntityHuman arg0, String arg1, double arg2,
					double arg3, double arg4, float arg5, float arg6) {
			}

			@Override
			public void a(String arg0, double arg1, double arg2, double arg3,
					double arg4, double arg5, double arg6) {
			}

			@Override
			public void a(String arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void a(Entity arg0) {
			}

			@Override
			public void b(Entity arg0) {
			}

		};
	}
}