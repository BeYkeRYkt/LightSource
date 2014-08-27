package ykt.BeYkeRYkt.LightSource.NMS;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.EntityHuman;
import net.minecraft.server.v1_7_R1.EnumSkyBlock;
import net.minecraft.server.v1_7_R1.IWorldAccess;
import net.minecraft.server.v1_7_R1.PlayerChunkMap;
import net.minecraft.server.v1_7_R1.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R1.CraftWorld;


public class NMSHandler_v1_7_R1 implements NMSInterface {
	
	private static BlockFace[] SIDES = { BlockFace.UP, BlockFace.DOWN,
		BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
    private List<IWorldAccess> worlds = new ArrayList<IWorldAccess>();
    private static Method cachedPlayerChunk;
    private static Field cachedDirtyField;
	
	@Override
	public void recalculateBlockLighting(World world, int x, int y, int z) {
		WorldServer nmsWorld = ((CraftWorld) world).getHandle();
		nmsWorld.t(x, y, z);
	}
	

	@Override
	public void initWorlds() {
		for(World worlds: Bukkit.getWorlds()){
			WorldServer nmsWorld = ((CraftWorld) worlds).getHandle();
            IWorldAccess access = getLightIWorldAccess(worlds);
            
            nmsWorld.addIWorldAccess(access);
            this.worlds.add(access);
		}
	}
	
	@Override
	public void unloadWorlds(){
		try {
		for(World worlds: Bukkit.getWorlds()){
			WorldServer nmsWorld = ((CraftWorld) worlds).getHandle();
			
		for(IWorldAccess access: this.worlds){
        Field field = net.minecraft.server.v1_7_R1.World.class.getDeclaredField("u");
        field.setAccessible(true);
        ((List<?>) field.get(nmsWorld)).remove(access);
		}
		
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void createLightSource(Location loc, int level,boolean flag) {
		WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
		int oldlevel = loc.getBlock().getLightLevel();
		
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		nmsWorld.b(EnumSkyBlock.BLOCK, x, y, z, level);
		
	    Block adjacent = getAdjacentAirBlock(loc.getBlock());
	    recalculateBlockLighting(loc.getWorld(), adjacent.getX(), adjacent.getY(),adjacent.getZ());
	    
	    if(flag){
		    nmsWorld.b(EnumSkyBlock.BLOCK, x, y, z, oldlevel);
	    }
	    update(nmsWorld, loc);
	}

	public void update(WorldServer nmsWorld, Location loc){
		try{ 
		    //from: https://gist.github.com/aadnk/5841942
		    //Thanks Comphenix!
		    PlayerChunkMap map = nmsWorld.getPlayerChunkMap();
		    
	        int chunkX = loc.getBlockX() >> 4;
	        int chunkZ = loc.getBlockZ() >> 4;
	        
	        map.flagDirty(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	        
	        for (int dX = -1; dX <= 1; dX++) {
	            for (int dZ =-1; dZ <=1; dZ++) {
	                Object playerChunk = getPlayerCountMethod().invoke(map, chunkX + dX, chunkZ + dZ, false);

	                if (playerChunk != null) {
	                    Field dirtyField = getDirtyField(playerChunk);
	                    int dirtyCount = (Integer) dirtyField.get(playerChunk);     
	                    if (dirtyCount > 0) {
	                        dirtyField.set(playerChunk, 64);
	                    }
	                }
	            }
	        }
	        map.flush();
	        
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	@Override
	public void deleteLightSource(Location loc) {
		
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();

		Location delete = new Location(loc.getWorld(), x, y, z);
		Material blockMaterial = delete.getBlock().getType();
		delete.getBlock().setType(blockMaterial);
	}
 
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
    public IWorldAccess getLightIWorldAccess(final org.bukkit.World world) {
    		final PlayerChunkMap map = ((CraftWorld) world).getHandle().getPlayerChunkMap();
        return new IWorldAccess() {         
            @Override
            //markBlockForUpdate
            public void a(int x, int y, int z) {
                map.flagDirty(x, y, z);
            }
            
            @Override
            //markBlockForRenderUpdate
            public void b(int x, int y, int z) {
                map.flagDirty(x, y, z);
            }
            
            @Override
            //destroyBlockPartially
            public void b(int arg0, int arg1, int arg2, int arg3, int arg4) { }
                        
            @Override
            //playAuxSFX
            public void a(EntityHuman arg0, int arg1, int arg2, int arg3, int arg4, int arg5) { }
                        
            @Override
            //markBlockRangeForRenderUpdate
            public void a(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
                // Ignore
            }
            
            @Override
            //broadcastSound
            public void a(int arg0, int arg1, int arg2, int arg3, int arg4) { }
            
            @Override
            //playSound
            public void a(String arg0, double arg1, double arg2, double arg3, float arg4, float arg5) {
            }
            
            @Override
            //playSoundToNearExcept
            public void a(EntityHuman arg0, String arg1, double arg2, double arg3, double arg4, float arg5,float arg6) {
            }
            
            @Override
            //spawnParticle
            public void a(String arg0, double arg1, double arg2, double arg3, double arg4, double arg5, double arg6) { }
            
            @Override
            //playRecord
            public void a(String arg0, int arg1, int arg2, int arg3) { }
            
            @Override
            //onEntityCreate
            public void a(Entity arg0) { }
            
            @Override
            //onEntityDestroy (probably)
            public void b(Entity arg0) { }

			@Override
			public void b() {
				// TODO Auto-generated method stub
				
			}
        };
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
}