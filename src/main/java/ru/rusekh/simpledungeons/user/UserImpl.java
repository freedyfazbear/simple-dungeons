package ru.rusekh.simpledungeons.user;

import ru.rusekh.simpledungeons.DungeonPlugin;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserImpl
{
    private final Map<UUID, User> userMap = new HashMap<>();

    public UserImpl() {
        try {
            PreparedStatement statement = DungeonPlugin.getDataSource().getConnection().prepareStatement("SELECT * from dungeonUsers");
            statement.executeQuery();
            while (statement.getResultSet().next()) {
                UUID uuid = UUID.fromString(statement.getResultSet().getString("uuid"));
                userMap.put(uuid, new User(statement.getResultSet()));
            }
            statement.getResultSet().close();
            statement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void insertUser(UUID uuid, User user) {
        userMap.putIfAbsent(uuid, user);
        try {
            PreparedStatement statement = DungeonPlugin.getDataSource().getConnection().prepareStatement("INSERT INTO dungeonUsers (uuid, mobKills, highestMobKills, sessions, deaths) VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, uuid.toString());
            statement.setInt(2, user.getMobKills());
            statement.setInt(3, user.getHighestMobKills());
            statement.setInt(4, user.getSessions());
            statement.setInt(5, user.getDeaths());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(UUID uuid) {
        return userMap.get(uuid);
    }

    public Map<UUID, User> getUserMap() {
        return userMap;
    }
}
