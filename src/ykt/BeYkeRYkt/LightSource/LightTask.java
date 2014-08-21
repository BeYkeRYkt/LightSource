package ykt.BeYkeRYkt.LightSource;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import ykt.BeYkeRYkt.LightSource.Light.ItemManager;
import ykt.BeYkeRYkt.LightSource.Light.Light;

public class LightTask extends BukkitRunnable{


	@Override
	public void run() {
		
		if(!LightAPI.getSources().isEmpty()){
			//fix
			int index;
			for(index = LightAPI.getSources().size() - 1; index >= 0; --index){
			Light lights = LightAPI.getSources().get(index);
			//end
			
			
			if (LightSource.getInstance().getDB().isPlayerLight()){
				if (LightSource.getInstance().getDB().getWorld(lights.getOwner().getWorld().getName())){
			if(lights.getOwner() instanceof Player){
				Player entity = (Player) lights.getOwner();
				if(!entity.isDead()){
				if(entity.getEquipment().getItemInHand() != null && ItemManager.isLightSource(entity.getEquipment().getItemInHand())){
						lights.updateLight(entity.getLocation(), ItemManager.getLightLevel(entity.getEquipment().getItemInHand()));
				}else if(entity.getEquipment().getHelmet() != null && ItemManager.isLightSource(entity.getEquipment().getHelmet())){
					lights.updateLight(entity.getLocation(), ItemManager.getLightLevel(entity.getEquipment().getHelmet()));
				}else{
					LightAPI.deleteLightSource(lights.getLocation());
				    lights.updateChunks();
					LightAPI.getSources().remove(lights);
				}
				
				}else{
					LightAPI.deleteLightSource(lights.getLocation());
				    lights.updateChunks();
					LightAPI.getSources().remove(lights);
				}
			}
			}else{
				if(lights.getOwner() instanceof Player){
					LightAPI.deleteLightSource(lights.getLocation());
				    lights.updateChunks();
					LightAPI.getSources().remove(lights);
				}
			}
			}else{
				if(lights.getOwner() instanceof Player){
					LightAPI.deleteLightSource(lights.getLocation());
				    lights.updateChunks();
					LightAPI.getSources().remove(lights);
				}
			}
			
			if (LightSource.getInstance().getDB().isEntityLight()){
				if (LightSource.getInstance().getDB().getWorld(lights.getOwner().getWorld().getName())){
					if(lights.getOwner() instanceof LivingEntity && !(lights.getOwner() instanceof Player)){
						LivingEntity entity = (LivingEntity) lights.getOwner();
						if(!entity.isDead()){
						if(entity.getEquipment().getItemInHand() != null && ItemManager.isLightSource(entity.getEquipment().getItemInHand())){
							lights.updateLight(entity.getLocation(), ItemManager.getLightLevel(entity.getEquipment().getItemInHand()));
						}else if(entity.getEquipment().getHelmet() != null && ItemManager.isLightSource(entity.getEquipment().getHelmet())){
							lights.updateLight(entity.getLocation(), ItemManager.getLightLevel(entity.getEquipment().getHelmet()));
						}else{
							LightAPI.deleteLightSource(lights.getLocation());
						    lights.updateChunks();
							LightAPI.getSources().remove(lights);
						}
						
						}else{
							LightAPI.deleteLightSource(lights.getLocation());
						    lights.updateChunks();
							LightAPI.getSources().remove(lights);
						}
					}
				}else{
					LightAPI.deleteLightSource(lights.getLocation());
				    lights.updateChunks();
					LightAPI.getSources().remove(lights);	
				}
			}else{
				if(lights.getOwner() instanceof LivingEntity && !(lights.getOwner() instanceof Player)){
					LightAPI.deleteLightSource(lights.getLocation());
				    lights.updateChunks();
					LightAPI.getSources().remove(lights);
				}
			}
			
			
			
			if (LightSource.getInstance().getDB().isItemLight()){
				if (LightSource.getInstance().getDB().getWorld(lights.getOwner().getWorld().getName())){
		             if(lights.getOwner() instanceof Item){
		 				Item entity = (Item) lights.getOwner();
		 				ItemStack item = entity.getItemStack();
		 				
		 				if(!entity.isDead()){
		 				if(ItemManager.isLightSource(item)){
		 					lights.updateLight(entity.getLocation(), ItemManager.getLightLevel(item));
		 				}
		 				}else{
		 					LightAPI.deleteLightSource(lights.getLocation());
						    lights.updateChunks();
		 					LightAPI.getSources().remove(lights);
		 				}
		 			}
				}else{
					LightAPI.deleteLightSource(lights.getLocation());
				    lights.updateChunks();
					LightAPI.getSources().remove(lights);	
				}
			}else{
				if(lights.getOwner() instanceof Item){
					LightAPI.deleteLightSource(lights.getLocation());
				    lights.updateChunks();
					LightAPI.getSources().remove(lights);
				}
			}
		}
			
	}
		
				//Others enities
				Player[] onlinePlayers = Bukkit.getOnlinePlayers();
				for (int i = 0; i < onlinePlayers.length; i++) {
					Player player = onlinePlayers[i];
										
					
					int radius = LightSource.getInstance().getDB().getRadius();
					List<Entity> nearbyEntities = player.getNearbyEntities(radius, radius, radius);
					for (int j = 0; j < nearbyEntities.size(); j++) {
						Entity ent = nearbyEntities.get(j);
						
						if (LightSource.getInstance().getDB().isPlayerLight()){
							if (LightSource.getInstance().getDB().getWorld(ent.getWorld().getName())){
						if(ent instanceof Player){
							Player entity = (Player) ent;
							if(LightAPI.checkEntityID(entity) == null){
							if(!entity.isDead()){
							if(entity.getEquipment().getItemInHand() != null && ItemManager.isLightSource(entity.getEquipment().getItemInHand())){
									Light light = new Light(entity, player.getLocation());
									LightAPI.addSource(light);
							}else if(entity.getEquipment().getHelmet() != null && ItemManager.isLightSource(entity.getEquipment().getHelmet())){
								Light light = new Light(entity, player.getLocation());
								LightAPI.addSource(light);
							}
							}
							}
						}
						}
						}	
						
						if (LightSource.getInstance().getDB().isEntityLight()){
							if (LightSource.getInstance().getDB().getWorld(ent.getWorld().getName())){
								if(ent instanceof LivingEntity  && !(ent instanceof Player)){
									LivingEntity le = (LivingEntity) ent;	

									if(LightAPI.checkEntityID(le) == null){
										if(!le.isDead()){
											if(le.getEquipment().getItemInHand() != null && ItemManager.isLightSource(le.getEquipment().getItemInHand())){
													Light light = new Light(le, le.getLocation());
													LightAPI.addSource(light);
												
											}else if(le.getEquipment().getHelmet() != null && ItemManager.isLightSource(le.getEquipment().getHelmet())){
													Light light = new Light(le, le.getLocation());
													LightAPI.addSource(light);
												
											}
										}
									}
								}
							}
						}
						
						if (LightSource.getInstance().getDB().isItemLight()){
							if (LightSource.getInstance().getDB().getWorld(ent.getWorld().getName())){
								if(ent instanceof Item){
									Item item = (Item) ent;
									ItemStack stack = item.getItemStack();
									
									if(LightAPI.checkEntityID(item) == null){
										if(ItemManager.isLightSource(stack)){
											Light light = new Light(item, item.getLocation());
											LightAPI.addSource(light);
									  }
							   }
						}
					}
				}
			}
		}
	}		
}