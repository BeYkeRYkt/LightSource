package ykt.BeYkeRYkt.LightSource.api.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;

import ykt.BeYkeRYkt.LightSource.LSConfig.TaskMode;
import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.tasks.BurnTask;
import ykt.BeYkeRYkt.LightSource.tasks.EntityTask;
import ykt.BeYkeRYkt.LightSource.tasks.FoundEntityTask;
import ykt.BeYkeRYkt.LightSource.tasks.ItemTask;
import ykt.BeYkeRYkt.LightSource.tasks.PlayerTask;
import ykt.BeYkeRYkt.LightSource.tasks.UpdateTask;

public class TaskManager {

    private List<Task> tasks;
    private int taskId;

    public void init() {
        this.tasks = new ArrayList<Task>();
        this.taskId = Bukkit.getScheduler().runTaskTimer(LightSource.getInstance(), new MainRunnable(this), 0L, LightSource.getInstance().getDB().getTaskTicks()).getTaskId();

        addTask(new FoundEntityTask(), true);
        if(LightSource.getInstance().getDB().getTaskMode() == TaskMode.ONE_FOR_ALL){
        // Add main tasks
        addTask(new UpdateTask(), true);
        }else if(LightSource.getInstance().getDB().getTaskMode() == TaskMode.ONE_FOR_ONE){
            addTask(new BurnTask(), true);
            addTask(new EntityTask(), true);
            addTask(new ItemTask(), true);
            addTask(new PlayerTask(), true);
        }
    }

    /**
     * @return the tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task, boolean autoStart) {
        tasks.add(task);
        if (autoStart) {
            task.start();
        }
    }

    public void removeTask(Task task) {
        Iterator<Task> it = getTasks().iterator();
        while (it.hasNext()) {
            Task i = it.next();
            if (i.getId().equals(task.getId())) {
                i.stop();
                it.remove();
            }
        }
    }

    public Task getTask(String id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    public int getRunnableId() {
        return taskId;
    }

    // DEV
    public void reloadRunnable(int taskDelay) {
        for (Task task : tasks) {
            task.stop();
        }
        Bukkit.getScheduler().cancelTask(getRunnableId());
        this.taskId = Bukkit.getScheduler().runTaskTimer(LightSource.getInstance(), new MainRunnable(this), 0L, taskDelay).getTaskId();
        for (Task task : tasks) {
            task.start();
        }
    }
}