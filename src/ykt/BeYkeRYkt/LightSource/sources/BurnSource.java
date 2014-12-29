package ykt.BeYkeRYkt.LightSource.sources;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import ykt.BeYkeRYkt.LightSource.LightSource;
import ykt.BeYkeRYkt.LightSource.api.LightAPI;
import ykt.BeYkeRYkt.LightSource.api.sources.Source;

public class BurnSource extends Source {

    public BurnSource(Entity entity) {
        super(entity, entity.getLocation(), -1, 14);
    }

    @Override
    public void doTick() {
        Location newLocation = getOwner().getLocation();

        if (LightSource.getInstance().getDB().isBurnLight()) {
            if (LightSource.getInstance().getDB().getWorld(this.getOwner().getWorld().getName())) {
                if (!getOwner().isDead()) {
                    if (getOwner().getFireTicks() > 0) {
                        updateLight(newLocation);
                        return;
                    }
                }
            }
        }
        LightAPI.deleteLight(this.getLocation(), true);
        LightAPI.getSourceManager().removeSource(this);
        return;
    }

    @Override
    protected void removeItem() {
    }
}