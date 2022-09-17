package ru.rusekh.simpledungeons.handler;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.rusekh.simpledungeons.DungeonPlugin;
import ru.rusekh.simpledungeons.user.User;

public class PlayerHandler implements Listener
{
    private final DungeonPlugin dungeonPlugin;

    public PlayerHandler(DungeonPlugin dungeonPlugin) {
        this.dungeonPlugin = dungeonPlugin;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = dungeonPlugin.getUserManager().getUser(player.getUniqueId());
        if (user == null) dungeonPlugin.getUserManager().insertUser(player.getUniqueId(), new User(player.getUniqueId(), 0, 0, 0, 0));
    }

    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        User user = dungeonPlugin.getUserManager().getUser(player.getUniqueId());
        Bukkit.getScheduler().runTask(dungeonPlugin, () -> player.spigot().respawn());
        if (user == null) return;
        if (!user.isInArena()) return;

        player.getInventory().setContents(user.getInventory());
        player.getInventory().setArmorContents(user.getArmor());
        user.setDeaths(user.getDeaths() + 1);
        if (user.getHighestMobKills() < user.getLastKills())
            user.setHighestMobKills(user.getLastKills());
        player.teleport(user.getPreviousLocation());
    }

    @EventHandler
    private void onPlayerKill(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        if (player == null) return;
        EntityType entityType = event.getEntityType();
        if (entityType != EntityType.ZOMBIE && entityType != EntityType.SKELETON) return;

        User user = dungeonPlugin.getUserManager().getUser(player.getUniqueId());
        if (user == null) return;

        user.setLastKills(user.getLastKills() + 1);
    }
}
