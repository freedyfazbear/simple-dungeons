package ru.rusekh.simpledungeons.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;
import ru.rusekh.simpledungeons.DungeonPlugin;

public class ArenaTask extends BukkitRunnable {

    private final DungeonPlugin dungeonPlugin;

    public ArenaTask(DungeonPlugin dungeonPlugin) {
        this.dungeonPlugin = dungeonPlugin;
    }

    @Override
    public void run() {
        for (Player it : Bukkit.getOnlinePlayers()) {
            dungeonPlugin.getUserManager().getUserMap().forEach((uuid, user) -> {
                Player player = Bukkit.getPlayer(uuid);
                if (player == null) return;

                if (user.isInArena()) {
                    Entity zombie = player.getWorld().spawnEntity(player.getLocation(), EntityType.ZOMBIE);
                    Entity skeleton = player.getWorld().spawnEntity(player.getLocation(), EntityType.SKELETON);
                    it.hideEntity(dungeonPlugin, zombie);
                    it.hideEntity(dungeonPlugin, skeleton);
                }
            });
        }
    }
}
