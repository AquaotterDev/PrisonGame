package prisongame.prisongame;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import prisongame.prisongame.lib.Role;

import java.util.Random;

public class MyListener implements Listener {

    public static void reloadBert() {

        for (Entity e : Bukkit.getWorld("world").getEntities()) {
            if (e.getType().equals(EntityType.VILLAGER))
                e.remove();
        }

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

    public static void playerJoinignoreAsc(Player p, Boolean dontresetshit) {
            if (!dontresetshit) {
                p.getOpenInventory().getTopInventory().clear();
                p.getOpenInventory().getBottomInventory().clear();
                p.getOpenInventory().close();
                p.getInventory().clear();
            }
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
                if (p.getName().equals("Kingdarksword") || p.getName().equals("Wemmbu__")) {
                    chestmeta.setColor(Color.fromRGB(255, 0, 255));
                }
                chestmeta.setDisplayName("Prisoner Uniform");
                orangechest.setItemMeta(chestmeta);

                ItemStack orangeleg = new ItemStack(Material.LEATHER_LEGGINGS);
                LeatherArmorMeta orangelegItemMeta = (LeatherArmorMeta) orangeleg.getItemMeta();
                orangelegItemMeta.setColor(Color.fromRGB(208, 133, 22));
                if (p.getName().equals("Kingdarksword") || p.getName().equals("Wemmbu__")) {
                    orangelegItemMeta.setColor(Color.fromRGB(255, 0, 255));
                }
                orangelegItemMeta.setDisplayName("Prisoner Uniform");
                orangeleg.setItemMeta(orangelegItemMeta);

                ItemStack orangeboot = new ItemStack(Material.LEATHER_BOOTS);
                LeatherArmorMeta orangebootItemMeta = (LeatherArmorMeta) orangeboot.getItemMeta();
                orangebootItemMeta.setColor(Color.fromRGB(40, 20, 2));
                //if (p.getPersistentDataContainer().getOrDefault(PrisonGame.rank, PersistentDataType.INTEGER, 0) == 1) {
                //    orangebootItemMeta.setColor(Color.YELLOW);
                //}
                if (p.getName().equals("Kingdarksword") || p.getName().equals("Wemmbu__")) {
                    orangebootItemMeta.setColor(Color.fromRGB(255, 100, 255));
                }
                orangebootItemMeta.setDisplayName("Prisoner Uniform");
                orangeboot.setItemMeta(orangebootItemMeta);

                p.getInventory().setChestplate(orangechest);
                p.getInventory().setLeggings(orangeleg);
                p.getInventory().setBoots(orangeboot);
                if (p.getName().equals("Kingdarksword") || p.getName().equals("Wemmbu__")) {
                    ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
                    ItemMeta cardm = card.getItemMeta();
                    cardm.setDisplayName(ChatColor.BLUE + "Keycard " + ChatColor.RED + "[CONTRABAND]");
                    card.setItemMeta(cardm);
                    p.getInventory().addItem(card);
                }
            }
            if (!dontresetshit) {
                PrisonGame.roles.put(p, Role.PRISONER);
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

    public static void playerJoin(Player p, Boolean dontresetshit) {
        if (!p.getDisplayName().contains("ASCENDING")) {
            playerJoinignoreAsc(p, dontresetshit);
        }
    }


}