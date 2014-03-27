package ykt.BeYkeRYkt.LightSource.nmsUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_5_R3.Chunk;
import net.minecraft.server.v1_5_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityHuman;
import net.minecraft.server.v1_5_R3.EntityPlayer;
import net.minecraft.server.v1_5_R3.EnumSkyBlock;
import net.minecraft.server.v1_5_R3.IWorldAccess;
import net.minecraft.server.v1_5_R3.Packet51MapChunk;
import net.minecraft.server.v1_5_R3.PlayerChunkMap;
import net.minecraft.server.v1_5_R3.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_5_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_5_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMSHandler_v1_5_R3 implements NMSInterface {

	/**
	 * 
	 * BETA STAGE №2 !!
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
		// Find the first adjacent air block
		for (BlockFace face : SIDES) {
			// Don't use these sides
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

	
	//Данная версия состоит из рукожопного кода из моего рукожопного мозга. Если у вас способ получше, я его с радостью приму :3
	//Баги:
	//1. Иногда зависает свет
	//2. Два источника света = говножоп
	//3. Свет изчезает в узких помещениях
	//4. ГОВНОКОД
	@Override
	public void updateChunk(World world, Location loc) {
		try {
			WorldServer nmsWorld = ((CraftWorld) world).getHandle();
			PlayerChunkMap map = nmsWorld.getPlayerChunkMap();
			
			IWorldAccess access = countLightUpdates(loc.getWorld(), map);
			nmsWorld.addIWorldAccess(access);
				
			Block adjacent = getAdjacentAirBlock(loc.getBlock());

			recalculateBlockLighting(world, adjacent.getX(), adjacent.getY(),adjacent.getZ());

			Field field = net.minecraft.server.v1_5_R3.World.class.getDeclaredField("u");
			field.setAccessible(true);
			((List) field.get(nmsWorld)).remove(access);
			
			
			List<Chunk> cs = new ArrayList<Chunk>();
			 
			for(int x=-1; x<=1; x++) {
			for(int z=-1; z<=1; z++) {
			cs.add(((CraftChunk) loc.clone().add(16 * x, 0, 16 * z).getChunk()).getHandle());
			}
			}
			
			for(Chunk c: cs){
			ChunkCoordIntPair chunkCoord = new ChunkCoordIntPair(c.x, c.z);
			for(Player players: loc.getWorld().getPlayers()){
			if(players.getLocation().distance(loc) <= Bukkit.getServer().getViewDistance()*16 ){
			EntityPlayer nmsplayers = ((CraftPlayer) players).getHandle();
	           if (!nmsplayers.chunkCoordIntPairQueue.contains(chunkCoord)) {
	                nmsplayers.playerConnection.sendPacket(new Packet51MapChunk(nmsWorld.getChunkAt(chunkCoord.x,chunkCoord.z), false, 0));
	                c.initLighting();
	            }
			   }
			}
			}
			
			map.flagDirty(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
			
			map.flush();
			
			
		} catch (SecurityException e) {
			throw new RuntimeException("Access denied", e);
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException("Reflection problem.", e);
		}
	}
	
	//Альтер.
	//public void updateChunk2(World world, Location loc) {
	//	try {
	//		WorldServer nmsWorld = ((CraftWorld) world).getHandle();
	//		PlayerChunkMap map = nmsWorld.getPlayerChunkMap();
	//		
	//		if(loc.getChunk().isLoaded()){
    //
	//		IWorldAccess access = countLightUpdates(loc.getWorld(), map);
	//		nmsWorld.addIWorldAccess(access);
	//			
	//		Block adjacent = getAdjacentAirBlock(loc.getBlock());
    //
	//		recalculateBlockLighting(world, adjacent.getX(), adjacent.getY(),adjacent.getZ());
    //
	//   	Field field = net.minecraft.server.v1_5_R3.World.class.getDeclaredField("u");
	//		field.setAccessible(true);
	//   	((List) field.get(nmsWorld)).remove(access);
    //
	//	map.flagDirty(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    //						    
	//		ChunkCoordIntPair location = new ChunkCoordIntPair(loc.getChunk().getX() * 16, loc.getChunk().getZ() * 16);
	//					
	//		for(Player players: loc.getWorld().getPlayers()){
	//		if(players.getLocation().distance(loc) <= Bukkit.getServer().getViewDistance()*16 ){
	//		EntityPlayer nmsplayers = ((CraftPlayer) players).getHandle();
	//          if (!nmsplayers.chunkCoordIntPairQueue.contains(location)) {
	//                nmsplayers.chunkCoordIntPairQueue.add(location); // Не знаю что написал ._.
	//           }
	//		   }
	//		}
	//		}
	//		
	//		map.flush();
    //
	//	} catch (SecurityException e) {
	//		throw new RuntimeException("Access denied", e);
	//	} catch (ReflectiveOperationException e) {
	//		throw new RuntimeException("Reflection problem.", e);
	//	}
	//}

	private static IWorldAccess countLightUpdates(final org.bukkit.World world,
			final PlayerChunkMap map) {
		return new IWorldAccess() {
			@Override
			// markBlockForUpdate
			public void a(int x, int y, int z) {
				map.flagDirty(x, y, z);
			}

			@Override
			// markBlockForRenderUpdate
			public void b(int x, int y, int z) {
				map.flagDirty(x, y, z);
			}

			@Override
			// destroyBlockPartially
			public void b(int arg0, int arg1, int arg2, int arg3, int arg4) {
			}

			@Override
			// playAuxSFX
			public void a(EntityHuman arg0, int arg1, int arg2, int arg3,
					int arg4, int arg5) {
			}

			@Override
			// markBlockRangeForRenderUpdate
			public void a(int minX, int minY, int minZ, int maxX, int maxY,
					int maxZ) {
				// Ignore
			}

			@Override
			// broadcastSound
			public void a(int arg0, int arg1, int arg2, int arg3, int arg4) {
			}

			@Override
			// playSound
			public void a(String arg0, double arg1, double arg2, double arg3,
					float arg4, float arg5) {
			}

			@Override
			// playSoundToNearExcept
			public void a(EntityHuman arg0, String arg1, double arg2,
					double arg3, double arg4, float arg5, float arg6) {
			}

			@Override
			// spawnParticle
			public void a(String arg0, double arg1, double arg2, double arg3,
					double arg4, double arg5, double arg6) {
			}

			@Override
			// playRecord
			public void a(String arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			// onEntityCreate
			public void a(Entity arg0) {
			}

			@Override
			// onEntityDestroy (probably)
			public void b(Entity arg0) {
			}

		};
	}
}