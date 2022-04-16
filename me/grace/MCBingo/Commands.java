package me.grace.MCBingo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class Commands implements Listener, CommandExecutor {
    public String bingo = "bingo";
    public String gui = "gui";
    public static HashMap<Player, BingoCard> players = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(command.getName().equalsIgnoreCase(bingo)) {
                if(strings != null && strings.length != 0) {
                    if(strings[0].equalsIgnoreCase("start")) {
                            if(!Variables.enabled) {
                                Variables.setEnabled();
                                for(Player p : Bukkit.getOnlinePlayers()) {
                                    BingoCard bingoCard = new BingoCard(p);
                                    players.put(p, bingoCard);
                                    p.getInventory().clear();
                                    CustomItems.getBingoCard(p);
                                }
                                Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "A new bingo game has started! Get ready...");
                            } else player.sendMessage(ChatColor.RED + "Bingo is already enabled and in-game!");

                    } else if(strings[0].equals("stop")) {
                        if(Variables.enabled) {
                            Variables.setDisabled();
                            Bukkit.broadcastMessage(ChatColor.RED + "" + ChatColor.BOLD + "Bingo has been disabled!");
                        } else player.sendMessage(ChatColor.RED + "Bingo is already disabled!");
                    } else if(strings[0].equals("gui")) {
                        if(Variables.enabled) {
                            CustomInventory customInventory = new CustomInventory();
                            for (Map.Entry mapElement : players.entrySet()) {
                                Player key = (Player)mapElement.getKey();
                                BingoCard value = ((BingoCard)mapElement.getValue());
                                if(key == player) customInventory.bingoInventory(key, value.playerMaterials, value.foundMaterials);
                            }
                        } else {
                            player.sendMessage(Main.cc("&c&lOops! &r&cYou need to be in a bingo game to do that."));
                        }
                    } else if(strings[0].equals("card")) {
                        if(Variables.enabled) {
                            CustomItems.getBingoCard(player);
                        } else {
                            player.sendMessage(Main.cc("&c&lOops! &r&cYou need to be in a bingo game to do that."));
                        }
                    } else if(strings[0].equalsIgnoreCase("help")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lBingo Commands \n&r&a/bingo start &f- starts a new bingo game \n&r&a/bingo stop &f- stops the current bingo game \n&r&a/bingo gui &f- access your own bingo card \n&r&a/bingo card &f- gives a bingo card item"));
                    }
                } else {
                  player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a&lBingo Commands \n&r&a/bingo start &f- starts a new bingo game \n&r&a/bingo stop &f- stops the current bingo game \n&r&a/bingo gui &f- access your own bingo card \n&r&a/bingo card &f- gives a bingo card item"));
                }
            }
        } else {
            commandSender.sendMessage("Only players can execute this command!");
        }
        return false;
    }
}
