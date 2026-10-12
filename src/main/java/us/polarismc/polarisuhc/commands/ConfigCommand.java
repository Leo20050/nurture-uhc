package us.polarismc.polarisuhc.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import us.polarismc.polarisuhc.Main;

import java.util.ArrayList;
import java.util.List;

public class ConfigCommand implements CommandExecutor {
    private final Main plugin;

    public ConfigCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Inventory gui = Bukkit.createInventory(null, 54, ChatColor.DARK_GRAY + "UHC Config");

        ItemStack pane = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta paneMeta = pane.getItemMeta();
        paneMeta.setDisplayName(" ");
        pane.setItemMeta(paneMeta);

        for (int i = 0; i < gui.getSize(); i++) {
            gui.setItem(i, pane);
        }

        addInfo(gui, 10, Material.PAPER, "Worlds", formatSection(plugin.getConfig().getConfigurationSection("worlds"), 6));
        addInfo(gui, 12, Material.DIAMOND_SWORD, "PvP", formatSection(plugin.getConfig().getConfigurationSection("duration"), 5));
        addInfo(gui, 14, Material.BARRIER, "Border", formatSection(plugin.getConfig().getConfigurationSection("border"), 6));
        addInfo(gui, 16, Material.REDSTONE, "Rates", formatSection(plugin.getConfig().getConfigurationSection("rates"), 5));
        addInfo(gui, 31, Material.BOOK, "Toggle", formatSection(plugin.getConfig().getConfigurationSection("toggle"), 8));
        addInfo(gui, 33, Material.COMPASS, "Settings", formatSection(plugin.getConfig().getConfigurationSection("settings"), 6));

        player.openInventory(gui);
        return true;
    }

    private String formatSection(ConfigurationSection section, int maxEntries) {
        if (section == null) {
            return "N/A";
        }

        StringBuilder builder = new StringBuilder();
        int count = 0;
        for (String key : section.getKeys(false)) {
            if (count >= maxEntries) {
                builder.append("...");
                break;
            }

            Object value = section.get(key);
            if (value == null) {
                continue;
            }

            if (builder.length() > 0) {
                builder.append("\n");
            }
            builder.append(key).append(": ").append(value);
            count++;
        }

        return builder.length() == 0 ? "No values configured" : builder.toString();
    }

    private void addInfo(Inventory gui, int slot, Material material, String name, String value) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + name);

        List<String> lore = new ArrayList<>();
        String[] lines = value.split("\\n");
        for (String line : lines) {
            if (lore.size() >= 5) {
                lore.add(ChatColor.DARK_GRAY + "...");
                break;
            }
            lore.add(ChatColor.GRAY + line);
        }
        lore.add(ChatColor.DARK_GRAY + "Read-only");

        meta.setLore(lore);
        item.setItemMeta(meta);
        gui.setItem(slot, item);
    }
}
