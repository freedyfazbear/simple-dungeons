package ru.rusekh.simpledungeons;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import ru.rusekh.simpledungeons.commands.LeaveCommand;
import ru.rusekh.simpledungeons.commands.StartCommand;
import ru.rusekh.simpledungeons.commands.StatsCommand;
import ru.rusekh.simpledungeons.handler.PlayerHandler;
import ru.rusekh.simpledungeons.task.ArenaTask;
import ru.rusekh.simpledungeons.user.UserImpl;

public class DungeonPlugin extends JavaPlugin
{
    private static HikariDataSource dataSource;

    private UserImpl userManager;

    @Override
    public void onEnable() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/sperma");
        config.setUsername("phpmyadmin");
        config.setPassword("ambitnehaslo123");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.addDataSourceProperty("useServerPrepStmts", "true");
        config.addDataSourceProperty("useLocalSessionState", "true");
        config.addDataSourceProperty("rewriteBatchedStatements", "true");
        config.addDataSourceProperty("cacheResultSetMetadata", "true");
        config.addDataSourceProperty("cacheServerConfiguration", "true");
        config.addDataSourceProperty("elideSetAutoCommits", "true");
        config.addDataSourceProperty("maintainTimeStats", "false");
        config.addDataSourceProperty("autoReconnect", "true");
        config.addDataSourceProperty("characterEncoding", "utf8");
        dataSource = new HikariDataSource(config);

        userManager = new UserImpl();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerHandler(this), this);

        getCommand("leave").setExecutor(new LeaveCommand(this));
        getCommand("stats").setExecutor(new StatsCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));

        new ArenaTask(this).runTaskTimer(this, 0L, 20L);
    }

    @Override
    public void onDisable() {
        if (!dataSource.isClosed()) dataSource.close();
    }

    public UserImpl getUserManager() {
        return userManager;
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
