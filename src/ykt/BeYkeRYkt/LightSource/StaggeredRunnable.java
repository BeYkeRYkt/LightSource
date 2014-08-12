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
    //private Map<Chunk, Location> hugeList;
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
        //this.hugeList = hugeList;
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
        // reset whenever we call this method
        iteratorCount = 0;
 
        long delay_before_starting = 5;
        long delay_between_restarting = 5;
 
        // synchronous - thread safe
        this.taskId = this.myPlugin.getServer().getScheduler().runTaskTimer(this.myPlugin, this, delay_before_starting, delay_between_restarting).getTaskId();
 
        // asynchronous - NOT thread safe
        //this.taskId = this.myPlugin.getServer().getScheduler().runTaskTimerAsynchronously(this.myPlugin, this, delay_before_starting, delay_between_restarting).getTaskId();
 
        // Choose one or the other, not both.
        // They are both here simply for the sake of completion.
    }
 
    // this example will stagger parsing a huge list
 
    @Override
    public void run()
    {
    	if(!running){
    	running = true;
        iteratorCount = 0;
        long startTime = System.currentTimeMillis();
        
        // while the list isnt empty, and we havent exceeded matIteraternsPerTick....
        // the loop will stop when it reaches 300 iterations OR the list becomes empty
        // this ensures that the server will be happy clappy, not doing too much per tick.
  
        while (!this.chunks.isEmpty() && !this.locs.isEmpty() && iteratorCount < maxIterationsPerTick)
        {
            // do something with this huge list...    
    		LightAPI.updateChunk(locs.get(0), chunks.get(0));
            // remove the first element (will always be present if !isEmpty)
            chunks.remove(0);
            locs.remove(0);
 
            iteratorCount++;
        }
 
        if(this.chunks.isEmpty()||this.locs.isEmpty()){
        	
            if(LightSource.getInstance().getDB().isDebug()){
                long endTime = System.currentTimeMillis();
                
            	//LightSource.getInstance().getLogger().info("Chunks amount: " + chunks.size());
            	//LightSource.getInstance().getLogger().info("Packets(One player): " + 1 * chunks.size());
            	//LightSource.getInstance().getLogger().info("Packets(all): " + 1 * chunks.size() * LightAPI.getSources().size());
                long time = endTime - startTime;
        		LightSource.getInstance().getLogger().info("Sending time : " + time + " ms!");
            }
        	setList(LightAPI.getChunksForUpdate());
        	running = false;
        }
        
        // if our condition/result is met, cancel this task.
        // this can be anything, it is just cancelling this repeating task when we have met a condition we are looking for.
        
        
        //if (hugeList.isEmpty())
        //{
        //    this.myPlugin.getServer().getScheduler().cancelTask(this.taskId);
        //}
    }
    	
    }
 
}