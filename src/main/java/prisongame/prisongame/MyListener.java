package prisongame.prisongame;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Random;

public class MyListener implements Listener {

    public static void reloadBert() {
        if (PrisonGame.bertrude != null) {
            PrisonGame.bertrude.remove();
            PrisonGame.bertrude = null;
        }
        PrisonGame.bertrude = (LivingEntity) Bukkit.getWorld("world").spawnEntity(PrisonGame.active.bert, EntityType.VILLAGER);
        PrisonGame.bertrude.setAI(false);
        PrisonGame.bertrude.setGravity(false);
        PrisonGame.bertrude.setCustomName("bertrude (real settings)");
        PrisonGame.bertrude.setInvulnerable(true);
    }

    public static void playerJoin(Player p, Boolean dontresetshit) {
        if (!p.getDisplayName().contains("ASCENDING")) {
            if (!dontresetshit)
                p.getInventory().clear();
            if (!dontresetshit)
                PrisonGame.escaped.put(p, false);
            p.playSound(p, Sound.ENTITY_ZOMBIE_BREAK_WOODEN_DOOR, 1, 0.75f);

            if (!dontresetshit) {
                p.setCustomName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + p.getName());
                p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + p.getName());
                p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + p.getName());

            }

            if (!dontresetshit) {
                ItemStack orangechest = new ItemStack(Material.LEATHER_CHESTPLATE);
                LeatherArmorMeta chestmeta = (LeatherArmorMeta) orangechest.getItemMeta();
                chestmeta.setColor(Color.fromRGB(208, 133, 22));
                chestmeta.setDisplayName("Prisoner Uniform");
                orangechest.setItemMeta(chestmeta);

                ItemStack orangeleg = new ItemStack(Material.LEATHER_LEGGINGS);
                LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeleg.getItemMeta();
                orangelegItemMeta.setColor(Color.fromRGB(208, 133, 22));
                orangelegItemMeta.setDisplayName("Prisoner Uniform");
                orangeleg.setItemMeta(orangelegItemMeta);

                ItemStack orangeboot = new ItemStack(Material.LEATHER_BOOTS);
                LeatherArmorMeta orangebootItemMeta = (LeatherArmorMeta) orangeboot.getItemMeta();
                orangebootItemMeta.setColor(Color.fromRGB(40, 20, 2));
                //if (p.getPersistentDataContainer().getOrDefault(PrisonGame.rank, PersistentDataType.INTEGER, 0) == 1) {
                //    orangebootItemMeta.setColor(Color.YELLOW);
                //}
                orangebootItemMeta.setDisplayName("Prisoner Uniform");
                orangeboot.setItemMeta(orangebootItemMeta);

                p.getInventory().setChestplate(orangechest);
                p.getInventory().setLeggings(orangeleg);
                p.getInventory().setBoots(orangeboot);
            }
            if (!dontresetshit) {
                PrisonGame.type.put(p, 0);
            }
            Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                p.teleport(PrisonGame.active.getSpwn());
            }, 5L);
            Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                p.teleport(PrisonGame.active.getSpwn());
            }, 5L);
            if (!PrisonGame.hardmode.containsKey(p))
                PrisonGame.hardmode.put(p, false);
            if (PrisonGame.hardmode.get(p)) {
                String prisonerNumber = "" + new Random().nextInt(100, 999);
                PrisonGame.prisonnumber.put(p, prisonerNumber);
                PlayerDisguise playerDisguise = new PlayerDisguise("pdlCAMERA");
                playerDisguise.setName("Prisoner " + prisonerNumber);
                playerDisguise.setKeepDisguiseOnPlayerDeath(true);
                DisguiseAPI.disguiseToAll(p, playerDisguise);
                p.setCustomName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + "Prisoner " + prisonerNumber);
                p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.GOLD + "PRISONER" + ChatColor.GRAY + "] " + ChatColor.DARK_GRAY + "Prisoner " + prisonerNumber);
                p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "HARD MODE" + ChatColor.DARK_GRAY + "] " + p.getName());
            }
            if (!dontresetshit)
                Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners").addPlayer(p);
        }
    }


}