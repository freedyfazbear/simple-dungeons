package ru.rusekh.simpledungeons.user;

import org.bukkit.Location;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import ru.rusekh.simpledungeons.DungeonPlugin;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User
{
    private UUID uuid;
    private int mobKills;
    private int highestMobKills;
    private int sessions;
    private int deaths;
    private boolean inArena;
    private ItemStack[] inventory;
    private ItemStack[] armor;
    private int lastKills;
    private Location previousLocation;

    public User(UUID uuid, int mobKills, int highestMobKills, int sessions, int deaths) {
        this.uuid = uuid;
        this.mobKills = mobKills;
        this.highestMobKills = highestMobKills;
        this.sessions = sessions;
        this.deaths = deaths;
        this.inArena = false;
        this.inventory = new ItemStack[999];
        this.armor = new ItemStack[4];
        this.lastKills = 0;
    }

    public User(ResultSet resultSet) {
        try {
             this.uuid = UUID.fromString(resultSet.getString("uuid"));
             this.mobKills = resultSet.getInt("mobKills");
             this.highestMobKills = resultSet.getInt("highestMobKills");
             this.sessions = resultSet.getInt("sessions");
             this.deaths = resultSet.getInt("deaths");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
        try {
            PreparedStatement statement = DungeonPlugin.getDataSource().getConnection().prepareStatement("UPDATE dungeonUsers SET deaths = ? WHERE uuid = ?");
            statement.setInt(1, deaths);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setHighestMobKills(int highestMobKills) {
        this.highestMobKills = highestMobKills;
        try {
            PreparedStatement statement = DungeonPlugin.getDataSource().getConnection().prepareStatement("UPDATE dungeonUsers SET highestMobKills = ? WHERE uuid = ?");
            statement.setInt(1, highestMobKills);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setMobKills(int mobKills) {
        this.mobKills = mobKills;
        try {
            PreparedStatement statement = DungeonPlugin.getDataSource().getConnection().prepareStatement("UPDATE dungeonUsers SET mobKills = ? WHERE uuid = ?");
            statement.setInt(1, mobKills);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setSessions(int sessions) {
        this.sessions = sessions;
        try {
            PreparedStatement statement = DungeonPlugin.getDataSource().getConnection().prepareStatement("UPDATE dungeonUsers SET sessions = ? WHERE uuid = ?");
            statement.setInt(1, sessions);
            statement.setString(2, uuid.toString());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setLastKills(int lastKills) {
        this.lastKills = lastKills;
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }

    public void setPreviousLocation(Location previousLocation) {
        this.previousLocation = previousLocation;
    }

    public void setInArena(boolean inArena) {
        this.inArena = inArena;
    }

    public boolean isInArena() {
        return inArena;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public Location getPreviousLocation() {
        return previousLocation;
    }

    public int getLastKills() {
        return lastKills;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getHighestMobKills() {
        return highestMobKills;
    }

    public int getMobKills() {
        return mobKills;
    }

    public int getSessions() {
        return sessions;
    }
}
