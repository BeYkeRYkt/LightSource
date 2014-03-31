package ykt.BeYkeRYkt.LightSource.nmsUtils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_6_R3.Chunk;
import net.minecraft.server.v1_6_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_6_R3.EntityPlayer;
import net.minecraft.server.v1_6_R3.EnumSkyBlock;
import net.minecraft.server.v1_6_R3.Packet51MapChunk;
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
	 * BETA STAGE №6 !!
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
		
		int x = loc.getBlockX();
		int y = loc.getBlockY() + 1;
		int z = loc.getBlockZ();

		nmsWorld.b(EnumSkyBlock.BLOCK, x, y, z, level);
		
		Location newloc = new Location(loc.getWorld(), x, y,z);

		updateChunk(loc.getWorld(), newloc);
	}

	@Override
	public void deleteLightSource(Location loc) {
		WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
		
		int x = loc.getBlockX();
		int y = loc.getBlockY() + 1;
		int z = loc.getBlockZ();
		
		nmsWorld.c(EnumSkyBlock.BLOCK, x, y, z);
	}

	@Override
	public void deleteLightSourceAndUpdate(Location loc) {
		int x = loc.getBlockX();
		int y = loc.getBlockY() + 1;
		int z = loc.getBlockZ();
		
		Location newloc = new Location(loc.getWorld(), x, y, z);
		
		deleteLightSource(loc);
		
		updateChunk(loc.getWorld(), newloc);
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


	@Override
	public void createLightSourceStatic(Location loc, int level) {
		// TODO Auto-generated method stub
		WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
		int blocklevel = loc.getBlock().getLightLevel();

		nmsWorld.b(EnumSkyBlock.BLOCK, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), level);

		updateChunk(loc.getWorld(), loc);
		
		nmsWorld.b(EnumSkyBlock.BLOCK, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), blocklevel);
	}


	@Override
	public void deleteLightSourceStatic(Location loc) {
	       deleteLightSource(loc);
			WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
		   
		   loc.getBlock().setType(loc.getBlock().getType());
		   for(int x=-2; x <=2; x++){
			   for(int z=-2; z<=2; z++){
				   loc.getBlock().getRelative(x, 0, z).setType(loc.getBlock().getRelative(x, 0, z).getType());
			   }
		   }

		   updateChunk(loc.getWorld(), loc);      
	}
}