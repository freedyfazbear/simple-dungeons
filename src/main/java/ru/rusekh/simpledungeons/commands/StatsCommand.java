package ru.rusekh.simpledungeons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.rusekh.simpledungeons.DungeonPlugin;
import ru.rusekh.simpledungeons.user.User;

public class StatsCommand implements CommandExecutor
{
    private final DungeonPlugin dungeonPlugin;

    public StatsCommand(DungeonPlugin dungeonPlugin) {
        this.dungeonPlugin = dungeonPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        User user = dungeonPlugin.getUserManager().getUser(player.getUniqueId());
        if (user == null) return false;

        player.sendMessage("Deaths: " + user.getDeaths());
        player.sendMessage("Sessions: " + user.getSessions());
        player.sendMessage("Kills: " + user.getMobKills());
        player.sendMessage("Highest Score: " + user.getHighestMobKills());
        return false;
    }
}
