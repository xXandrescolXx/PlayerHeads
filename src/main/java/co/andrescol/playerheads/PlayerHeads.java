package co.andrescol.playerheads;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import co.andrescol.playerheads.mobs.MobHeadsFactory;
import co.andrescol.playerheads.player.PlayerHeadsFactory;

/**
 * Main plugin class that start all features
 * @author xX_andrescol_Xx
 */
public final class PlayerHeads extends JavaPlugin{

    private PlayerHeadsCommandExecutor commandExecutor;
    private PlayerHeadsListener listener;

    /*
     * (non-Javadoc)
     * @see org.bukkit.plugin.java.JavaPlugin#onEnable()
     */
    @Override
    public void onEnable() {
        saveReourceFiles();
        Config.init(this);
        Lang.init(this);
        MobHeadsFactory.init(this);
        PlayerHeadsFactory.init(this);
        listener = new PlayerHeadsListener(this);
        commandExecutor = new PlayerHeadsCommandExecutor(this);
        getServer().getPluginManager().registerEvents(listener, this);
        getCommand("PlayerHeads").setExecutor(commandExecutor);
    }

    /*
     * (non-Javadoc)
     * @see org.bukkit.plugin.java.JavaPlugin#onDisable()
     */
    @Override
    public void onDisable() {
        EntityDeathEvent.getHandlerList().unregister(listener);
        PlayerInteractEvent.getHandlerList().unregister(listener);
        BlockBreakEvent.getHandlerList().unregister(listener);
        Lang.disable();
        Config.disable();
        MobHeadsFactory.disable();
    }
    
    /**
     * Reload all plugin data
     */
    public void reload(){
    	// Disables
        Lang.disable();
        Config.disable();
        MobHeadsFactory.disable();
        
        // Enables
        saveReourceFiles();
        reloadConfig();
        Config.init(this);
        Lang.init(this);
        MobHeadsFactory.init(this);
    }
    
    /**
     * Save files on plugin server folder
     */
    private void saveReourceFiles(){
        getConfig().options().copyDefaults(true);
        File config = new File(getDataFolder(), "config.yml");
        File heads = new File(getDataFolder(), "heads.yml");
        try{
            if(!config.exists()){
                saveDefaultConfig();
            }
            if(!heads.exists())
            	saveResource("heads.yml", true);
        }catch(Exception e){
            this.getLogger().log(Level.SEVERE, "Could not be saved plugin files", e);
        }
    }
    
    /**
     * Save files on plugin server folder
     * @param fileName the file name 
     */
    public void saveReourceFile(String fileName){
        getConfig().options().copyDefaults(true);
        File file = new File(getDataFolder(), fileName);
        try{
            if(!file.exists())
            	saveResource(fileName, true);
        }catch(Exception e){
            this.getLogger().log(Level.SEVERE, "Could not be saved plugin files", e);
        }
    }
}