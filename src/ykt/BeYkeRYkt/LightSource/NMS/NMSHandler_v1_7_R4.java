package ykt.BeYkeRYkt.LightSource.NMS;

import net.minecraft.server.v1_7_R4.Chunk;
import net.minecraft.server.v1_7_R4.EnumSkyBlock;
import net.minecraft.server.v1_7_R4.PacketPlayOutMapChunk;
import net.minecraft.server.v1_7_R4.WorldServer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_7_R4.CraftChunk;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;

import ykt.BeYkeRYkt.LightSource.LightSource;



public class NMSHandler_v1_7_R4 implements NMSInterface {
	
	private static BlockFace[] SIDES = { BlockFace.UP, BlockFace.DOWN,
		BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };

	
	@Override
	public void recalculateBlockLighting(World world, int x, int y, int z) {
		WorldServer nmsWorld = ((CraftWorld) world).getHandle();
		nmsWorld.t(x, y, z);
	}

	
	@Override
	public void createLightSource(Location loc, int level) {
		WorldServer nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
		
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		
		nmsWorld.b(EnumSkyBlock.BLOCK, x, y, z, level);
		
		Block adjacent = getAdjacentAirBlock(loc.getBlock());
		recalculateBlockLighting(loc.getWorld(), adjacent.getX(), adjacent.getY(),adjacent.getZ());
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

	@Override
	public void updateChunk(Location loc, org.bukkit.Chunk chunk){
		Chunk nmsChunk = ((CraftChunk) chunk).getHandle();
		PacketPlayOutMapChunk packet = new PacketPlayOutMapChunk(nmsChunk, false, '\uffff');

		((CraftServer) Bukkit.getServer()).getServer().getPlayerList().sendPacketNearby(loc.getX(), loc.getY(), loc.getZ(), LightSource.getInstance().getConfig().getInt("RadiusSendPackets"), ((CraftWorld) loc.getWorld()).getHandle().dimension, packet);
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