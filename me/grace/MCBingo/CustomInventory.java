package me.grace.MCBingo;

import com.mysql.fabric.xmlrpc.base.Array;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class CustomInventory implements Listener {
    Plugin plugin = Main.getPlugin(Main.class);
    private static final Material[] matlist = Material.values();
    private static final int[] slotlist = {0,1,7,8,9,10,16,17,18,19,25,26,27,28,34,35,36,37,43,44};
    private static final ArrayList<Integer> slotlist2 = new ArrayList<>();

    public void bingoInventory(Player player, HashMap<Integer, Material> playerMaterials, ArrayList<Material> foundMaterials) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Inventory inventory = plugin.getServer().createInventory(null, 45, ChatColor.BLUE + "" + ChatColor.BOLD + p.getName() + "'s Bingo Card");
            int amount = ThreadLocalRandom.current().nextInt(1, 5);
            for(int slot : slotlist) {
                slotlist2.add(slot);
            }
            if(playerMaterials.size() == 25) {
                for (int i = 0; i <= 44; i++) {
                    if (slotlist2.contains(i)) continue;
                    if(foundMaterials.contains(playerMaterials.get(i))) {
                        inventory.setItem(i, CustomItems.completedItem(playerMaterials.get(i).name().replaceAll("_", " ")));
                    } else {
                        inventory.setItem(i, new ItemStack(playerMaterials.get(i)));
                    }
                }
            }
            if(playerMaterials.size() == 0) {
                for (int i = 0; i <= 44; i++) {
                    if (slotlist2.contains(i)) continue;
                    int random = new Random().nextInt(matlist.length);
                    Material mat = matlist[random];
                    if (mat != null && mat.isItem() && !mat.isRecord() && !String.valueOf(mat).contains("SPAWN_EGG") && !playerMaterials.containsValue(mat) && !String.valueOf(mat).contains("PAPER") && !String.valueOf(mat).contains("COMMAND_BLOCK") && !String.valueOf(mat).endsWith("HEAD") && !String.valueOf(mat).contains("NETHER_STAR") && !String.valueOf(mat).contains("DEBUG_STICK") && !String.valueOf(mat).startsWith("INFESTED")  && !String.valueOf(mat).startsWith("PETRIFIED") && !String.valueOf(mat).contains("KNOWLEDGE_BOOK") && !String.valueOf(mat).startsWith("UNCRAFTABLE")) {
                        ItemStack materialStack = new ItemStack(mat);
                        ItemMeta meta = materialStack.getItemMeta();
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add("Bingo Item");
                        meta.setLore(lore);
                        materialStack.setItemMeta(meta);
                        inventory.setItem(i, materialStack);
                        playerMaterials.put(i, mat);
                    } else {
                        i = i - 1;
                    }
                }
            }
            if(p == player) player.openInventory(inventory);
        }
    }
}
