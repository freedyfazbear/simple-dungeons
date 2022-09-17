package ru.rusekh.simpledungeons.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.rusekh.simpledungeons.DungeonPlugin;
import ru.rusekh.simpledungeons.user.User;

public class StartCommand implements CommandExecutor
{
    private final DungeonPlugin dungeonPlugin;

    public StartCommand(DungeonPlugin dungeonPlugin) {
        this.dungeonPlugin = dungeonPlugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        User user = dungeonPlugin.getUserManager().getUser(player.getUniqueId());
        if (user == null) {
            player.sendMessage("Please relog!");
            return false;
        }

        if (user.isInArena()) {
            player.sendMessage("You're in arena! Type /leave");
            return false;
        }
        user.setPreviousLocation(player.getLocation());
        user.setInArena(true);
        user.setArmor(player.getInventory().getArmorContents());
        user.setInventory(player.getInventory().getContents());

        player.getInventory().clear();
        player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
        player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 8));

        player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));

        Bukkit.getOnlinePlayers().forEach(player::hidePlayer);
        user.setLastKills(0);
        user.setSessions(user.getSessions() + 1);
        return false;
    }
}
