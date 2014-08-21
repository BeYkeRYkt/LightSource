package ykt.BeYkeRYkt.LightSource;

import java.util.ArrayList;
import java.util.Map;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

/**
 * 
 * http://forums.bukkit.org/threads/howto-staggered-tasks.129487/
 * 
 * Author: BeYkeRYkt
 * 
 */

public class StaggeredRunnable implements Runnable
{
    private final Plugin myPlugin;
    private ArrayList<Chunk> chunks;
    private ArrayList<Location> locs;
    private boolean running = false;
 
    @SuppressWarnings("unused")
	private int taskId;
 
    private int iteratorCount = 0;
    private final int maxIterationsPerTick = 300;
 
    public StaggeredRunnable(Plugin myPlugin, Map<Chunk, Location> hugeList)
    {
        this.myPlugin = myPlugin;
        chunks = new ArrayList<Chunk>(hugeList.keySet());
        locs = new ArrayList<Location>(hugeList.values());
    }
    
    public void setList(Map<Chunk, Location> list){
    	  if(chunks.isEmpty()){
          chunks = new ArrayList<Chunk>(list.keySet());
    	  }
    	  
    	  if(locs.isEmpty()){
          locs = new ArrayList<Location>(list.values());
    	  }
    	}
 
    public void start()
    {
        iteratorCount = 0;
 
        long delay_before_starting = LightSource.getInstance().getDB().getDelayStart();
        long delay_between_restarting = LightSource.getInstance().getDB().getDelayRestart();
        this.taskId = this.myPlugin.getServer().getScheduler().runTaskTimer(this.myPlugin, this, delay_before_starting, delay_between_restarting).getTaskId();
    }

    @Override
    public void run()
    {
    	if(!running){
    	running = true;
        iteratorCount = 0;
        long startTime = System.currentTimeMillis();
  
        while (!this.chunks.isEmpty() && !this.locs.isEmpty() && iteratorCount < maxIterationsPerTick)
        {
    		LightAPI.updateChunk(locs.get(0), chunks.get(0));
            chunks.remove(0);
            locs.remove(0);
 
            iteratorCount++;
        }
 
        if(this.chunks.isEmpty()||this.locs.isEmpty()){
        	
            if(LightSource.getInstance().getDB().isDebug()){
                long endTime = System.currentTimeMillis();
                long time = endTime - startTime;
        		LightSource.getInstance().getLogger().info("Sending time : " + time + " ms!");
            }
        	setList(LightAPI.getChunksForUpdate());
        	running = false;
        }
    }
    	
    }
 
}