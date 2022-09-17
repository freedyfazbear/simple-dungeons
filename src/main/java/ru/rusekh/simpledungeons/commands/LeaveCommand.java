package ru.rusekh.simpledungeons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.rusekh.simpledungeons.DungeonPlugin;
import ru.rusekh.simpledungeons.user.User;

public class LeaveCommand implements CommandExecutor
{
    private final DungeonPlugin dungeonPlugin;

    public LeaveCommand(DungeonPlugin dungeonPlugin) {
        this.dungeonPlugin = dungeonPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        User user = dungeonPlugin.getUserManager().getUser(player.getUniqueId());
        if (user == null) return false;
        if (!user.isInArena()) {
            player.sendMessage("You're not in arena!");
            return false;
        }
        user.setInArena(false);
        player.getInventory().clear();
        player.getInventory().setContents(user.getInventory());
        player.getInventory().setArmorContents(user.getArmor());
        player.teleport(user.getPreviousLocation());
        user.setPreviousLocation(null);
        return false;
    }
}
