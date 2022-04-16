package me.grace.MCBingo.Events;

import me.grace.MCBingo.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class EventsClass implements Listener {
    public ArrayList<Integer> completedSlots = new ArrayList<>();
    public ArrayList<Player> finishedPlayers = new ArrayList<>();

    //    public int[] completedSlotsList = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    @EventHandler
    public void InvenClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        if (inventory == null) return;
        if (event.getView().getTitle().contains("Bingo Card")) {
            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.BLOCK_WOODEN_BUTTON_CLICK_OFF, 1.0F, 1.0F);
            event.setResult(Event.Result.DENY);
            if(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                for (int i =0; i<=25;i++) {
                    ItemStack item = player.getInventory().getItem(i);
                    if(item != null && item.getItemMeta() != null && item.getItemMeta().getLore() != null) {
                        if(item.getItemMeta().getLore().get(0).contains("Bingo Item")) {
                            player.getInventory().remove(item);
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Material block = player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType();
        if(Variables.enabled) {
            if(Commands.players.get(player).playerMaterials.containsValue(block)) {
                if(!Commands.players.get(player).foundMaterials.contains(block)) {
                    HashMap<Integer, Material> playerMaterials = Commands.players.get(player).playerMaterials;
                    Commands.players.get(player).foundMaterials.add(block);
                    for (Map.Entry<Integer, Material> entry : playerMaterials.entrySet()) {
                        if (block.equals(entry.getValue())) {
                            completedSlots.add(entry.getKey());
                        }
                    }
                    Collections.sort(completedSlots);
                    int[] completedSlotsList = completedSlots.stream().mapToInt(i -> i).toArray();
                    Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + player.getName() + ChatColor.RESET + "" + ChatColor.GREEN + " found " + block.name().replaceAll("_", " ") + " (" + Commands.players.get(player).foundMaterials.size() + "/25)");
                    for (int[] intArray : Variables.getWinChances()) {
                        if (Arrays.toString(intArray).equals(Arrays.toString(completedSlotsList))) {
                            Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + player.getName() + " has finished their bingo card!");
                            finishedPlayers.add(player);
                            if(finishedPlayers.size() == Bukkit.getOnlinePlayers().size()) {
                                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Everyone has finished their bingo card!");
                                Bukkit.broadcastMessage(ChatColor.RED + "Stopping bingo...");
                                Variables.setDisabled();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerClicks(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = event.getItem();
        ItemStack bingoCard = CustomItems.bingoCardItem();
        if(action.equals(Action.LEFT_CLICK_AIR) && !String.valueOf(event.getItem()).equals("null")) {
            if(Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "" + ChatColor.BOLD + "Bingo Card")) {
                if(Variables.enabled) {
                    CustomInventory customInventory = new CustomInventory();
                    for (Map.Entry mapElement : Commands.players.entrySet()) {
                        Player key = (Player)mapElement.getKey();
                        BingoCard value = ((BingoCard)mapElement.getValue());
                        if(key == player) customInventory.bingoInventory(key, value.playerMaterials, value.foundMaterials);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event) {
        if (Variables.enabled) {
            Material item = event.getItem().getItemStack().getType();
            Player player = event.getPlayer();
            if (Commands.players.get(player).playerMaterials.containsValue(item) && !Commands.players.get(player).foundMaterials.contains(item)) {
                HashMap<Integer, Material> playerMaterials = Commands.players.get(player).playerMaterials;
                Commands.players.get(player).foundMaterials.add(item);
                for (Map.Entry<Integer, Material> entry : playerMaterials.entrySet()) {
                    if (item.equals(entry.getValue())) {
                        completedSlots.add(entry.getKey());
                    }
                }
                Collections.sort(completedSlots);
                int[] completedSlotsList = completedSlots.stream().mapToInt(i -> i).toArray();
                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + player.getName() + ChatColor.RESET + "" + ChatColor.GREEN + " found " + item.name().replaceAll("_", " ") + " (" + Commands.players.get(player).foundMaterials.size() + "/25)");
                for (int[] intArray : Variables.getWinChances()) {
                    if (Arrays.toString(intArray).equals(Arrays.toString(completedSlotsList))) {
                        Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + player.getName() + " has finished their bingo card!");
                        finishedPlayers.add(player);
                        if(finishedPlayers.size() == Bukkit.getOnlinePlayers().size()) {
                            Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Everyone has finished their bingo card!");
                            Bukkit.broadcastMessage(ChatColor.RED + "Stopping bingo...");
                            Variables.setDisabled();
                            break;
                        }
                    }
                }
            }

        }
    }
}
