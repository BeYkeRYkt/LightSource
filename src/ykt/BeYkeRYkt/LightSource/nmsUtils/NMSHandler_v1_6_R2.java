package ykt.BeYkeRYkt.LightSource.nmsUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import net.minecraft.server.v1_6_R3.ChunkCoordIntPair;
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
import org.bukkit.craftbukkit.v1_6_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_6_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import ykt.BeYkeRYkt.LightSource.LightSource;



public class NMSHandler_v1_6_R2 implements NMSInterface {

	/**
	 * 
	 * BETA STAGE №1 !!
	 * 
	 * @author BeYkeRYkt
	 */
	
	
	private static BlockFace[] SIDES = { BlockFace.UP, BlockFace.DOWN,
			BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
	private static Method cachedPlayerChunk;
	private static Field cachedDirtyField;
	
	private static Method getPlayerCountMethod() throws NoSuchMethodException, SecurityException {
		if (cachedPlayerChunk == null) {
		cachedPlayerChunk = PlayerChunkMap.class.getDeclaredMethod("a", int.class, int.class, boolean.class);
		cachedPlayerChunk.setAccessible(true);
		}
		return cachedPlayerChunk;
		}
		 
		private static Field getDirtyField(Object playerChunk) throws NoSuchFieldException, SecurityException {
		if (cachedDirtyField == null) {
		cachedDirtyField = playerChunk.getClass().getDeclaredField("dirtyCount");
		cachedDirtyField.setAccessible(true);
		}
		return cachedDirtyField;
		}
	
	@Override
	public void recalculateBlockLighting(World world, int x, int y, int z) {
		WorldServer nmsWorld = ((CraftWorld) world).getHandle();
		nmsWorld.A(x, y, z);
	}

	//LOCATION
	@Override
	public void createLightSource(Location loc, int level, UpdateLocationType type) {
		WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();

		nmsWorld.b(EnumSkyBlock.BLOCK, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), level);

		updateChunk(loc.getWorld(), loc, type);
	}

	@Override
	public void deleteLightSource(Location loc) {
		WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
		nmsWorld.c(EnumSkyBlock.BLOCK, loc.getBlockX(), loc.getBlockY(),loc.getBlockZ());
	}

	@Override
	public void deleteLightSourceAndUpdate(Location loc,  UpdateLocationType type) {
		deleteLightSource(loc);

		updateChunk(loc.getWorld(), loc, type);
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
	public void updateChunk(World world, Location loc, UpdateLocationType type) {
		try {
			WorldServer nmsWorld = ((CraftWorld) world).getHandle();
			PlayerChunkMap map = nmsWorld.getPlayerChunkMap();
			
			IWorldAccess access = countLightUpdates(loc.getWorld(), map);
			nmsWorld.addIWorldAccess(access);
				
			Block adjacent = getAdjacentAirBlock(loc.getBlock());

			recalculateBlockLighting(world, adjacent.getX(), adjacent.getY(),adjacent.getZ());

			Field field = net.minecraft.server.v1_6_R3.World.class.getDeclaredField("u");
			field.setAccessible(true);
			((List) field.get(nmsWorld)).remove(access);
			
			//List<Chunk> cs = new ArrayList<Chunk>();

			//for(int x=-1; x<=1; x++) {
			//for(int z=-1; z<=1; z++) {
			//Chunk chunk = ((CraftChunk) loc.clone().add(16 * x, 0, 16 * z).getChunk()).getHandle();
			//if (!cs.contains(chunk)) {
				//cs.add(chunk);
			//}
			//}
			//}
			
			
			int chunkX = loc.getBlockX() >> 4;
			int chunkZ = loc.getBlockZ() >> 4;
			map.flagDirty(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
							
			for (int dX = -1; dX <= 1; dX++) {
				for (int dZ =-1; dZ <=1; dZ++) {
				// That class is package private unfortunately				 					
				Object playerChunk = getPlayerCountMethod().invoke(map, chunkX + dX, chunkZ + dZ, false);
				
				if (playerChunk != null) {
				Field dirtyField = getDirtyField(playerChunk);
				int dirtyCount = (Integer) dirtyField.get(playerChunk);
				 
				// Minecraft will automatically send out a Packet51MapChunk for us,
				// with only those segments (16x16x16) that are needed.
				if (dirtyCount > 0) {
				dirtyField.set(playerChunk, 64);
				}
				
				if(type == UpdateLocationType.PLAYER_LOCATION || type == UpdateLocationType.ITEM_LOCATION){
				ChunkCoordIntPair chunkCoord = new ChunkCoordIntPair(nmsWorld.getChunkAt(chunkX +dX, chunkZ + dZ).x, nmsWorld.getChunkAt(chunkX +dX, chunkZ + dZ).z);
				for(Player players: loc.getWorld().getPlayers()){
				if(players.getLocation().distance(loc) <= Bukkit.getServer().getViewDistance()*16){
				   EntityPlayer nmsplayers = ((CraftPlayer) players).getHandle();
		           if (!nmsplayers.chunkCoordIntPairQueue.contains(chunkCoord)) {
		                nmsplayers.playerConnection.sendPacket(new Packet51MapChunk(nmsWorld.getChunkAt(chunkX +dX, chunkZ + dZ), false, 65535));
		                if(LightSource.getInstance().getConfig().getBoolean("Debug")){
		                    LightSource.getInstance().getLogger().info("SendPacket to chunk: X=" + nmsWorld.getChunkAt(chunkX +dX, chunkZ + dZ).x + " , Z="+nmsWorld.getChunkAt(chunkX +dX, chunkZ + dZ).z);
		                  }
		                nmsWorld.getChunkAt(chunkX +dX, chunkZ + dZ).initLighting();
		            }
				   }
				}
				}
				//end
				
				
				}
				}
				}
				
			
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