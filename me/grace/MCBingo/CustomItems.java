package me.grace.MCBingo;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class CustomItems implements Listener {
    public static ItemStack completedItem(String itemName) {
        ItemStack completedItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta meta = completedItem.getItemMeta();
        assert meta != null;
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + itemName);
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "Item already found!");
        meta.setLore(lore);
        completedItem.setItemMeta(meta);
        return completedItem;
    }
    public static ItemStack bingoCardItem() {
        ItemStack bingoCardItem = new ItemStack(Material.PAPER, 1);
        ItemMeta meta = bingoCardItem.getItemMeta();
        assert meta != null;
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Bingo Card");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE + "Left-click to access your bingo card");
        meta.setLore(lore);
        bingoCardItem.setItemMeta(meta);
        return bingoCardItem;
    }
    public static ItemMeta getBingoCardMeta() {
        return bingoCardItem().getItemMeta();
    }
    public static void getBingoCard(Player player) {
        player.getInventory().addItem(bingoCardItem());
    }
}
