package us.polarismc.polarisuhc.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ConfigCommand implements CommandExecutor {

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

        addInfo(gui, 10, Material.PAPER, "Worlds", "Overworld: uhc\nNether: uhc_nether\nEnd: uhc_the_end");
        addInfo(gui, 12, Material.DIAMOND_SWORD, "PvP", "Starts: 15 min\nEnabled: true");
        addInfo(gui, 14, Material.BARRIER, "Border", "Overworld: 2000\nNether: 1500");
        addInfo(gui, 16, Material.REDSTONE, "Rates", "XP Kill: 3\nApple: 2%");
        addInfo(gui, 31, Material.BOOK, "Toggle", "Read-only view");
        addInfo(gui, 33, Material.COMPASS, "Status", "Config values are fixed");

        player.openInventory(gui);
        return true;
    }

    private void addInfo(Inventory gui, int slot, Material material, String name, String value) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + name);
        meta.setLore(java.util.List.of(
                ChatColor.GRAY + value.replace("\n", " | "),
                ChatColor.DARK_GRAY + "Read-only"
        ));
        item.setItemMeta(meta);
        gui.setItem(slot, item);
    }
}
