package me.grace.MCBingo;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class BingoCard {
    public HashMap<Integer, Material> playerMaterials;
    public ArrayList<Material> foundMaterials;
    public BingoCard(Player player) {
        playerMaterials = new HashMap<Integer, Material>();
        foundMaterials = new ArrayList<Material>();
    }
    public HashMap<Integer, Material> getCard() {
        return this.playerMaterials;
    }

}
