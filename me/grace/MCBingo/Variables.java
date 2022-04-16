package me.grace.MCBingo;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Variables {
    public static boolean enabled = false;
    public static ArrayList<int[]> winChances = new ArrayList<>();
    public static int[] chance1 = {2,3,4,5,6};
    public static int[] chance2 = {11,12,13,14,15};
    public static int[] chance3 = {20,21,22,23,24};
    public static int[] chance4 = {29,30,31,32,33};
    public static int[] chance5 = {38,39,40,41,42};
    public static int[] chance6 = {2,11,20,29,38};
    public static int[] chance7 = {3,12,21,30,39};
    public static int[] chance8 = {4,13,22,31,40};
    public static int[] chance9 = {5,14,23,32,41};
    public static int[] chance10 = {6,15,24,33,42};
    public static int[] chance11 = {6,14,22,30,38};
    public static int[] chance12 = {2,12,22,32,42};

    public static void setEnabled() {
        enabled = true;
    }
    public static void setDisabled() {
        enabled = false;
    }

    public HashMap<Integer, Material> getCard(Player player) {
        BingoCard bingoCard = new BingoCard(player);
        return bingoCard.playerMaterials;
    }
    public static ArrayList<int[]> getWinChances() {
        winChances.add(chance1);
        winChances.add(chance2);
        winChances.add(chance3);
        winChances.add(chance4);
        winChances.add(chance5);
        winChances.add(chance6);
        winChances.add(chance7);
        winChances.add(chance8);
        winChances.add(chance9);
        winChances.add(chance10);
        winChances.add(chance11);
        winChances.add(chance12);
        return winChances;
    }
}
