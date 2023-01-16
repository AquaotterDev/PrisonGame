package prisongame.prisongame;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.type.Door;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import prisongame.prisongame.lib.Role;

import java.text.DecimalFormat;

public class MyTask extends BukkitRunnable {

    public static Integer jobm = 1;
    static Boolean hasAlerted = true;

    static Integer timer1;
    static Integer timer2;

    public static BossBar bossbar = Bukkit.createBossBar(
            ChatColor.WHITE + "Morning",
            BarColor.WHITE,
            BarStyle.SOLID);

    @Override
    public void run() {
        PrisonGame.solitcooldown = PrisonGame.solitcooldown - 1;
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getPersistentDataContainer().getOrDefault(PrisonGame.trust, PersistentDataType.DOUBLE, 0.0) > 0) {
                p.getPersistentDataContainer().set(PrisonGame.trust, PersistentDataType.DOUBLE, 0.0);
            }
            DisguiseAPI.setActionBarShown(p, false);
            if (p.isSleeping()) {
                p.setNoDamageTicks(10);
            }
            if (!PrisonGame.wealthcycle.containsKey(p)) {
                PrisonGame.wealthcycle.put(p, p.getPersistentDataContainer().get(PrisonGame.mny, PersistentDataType.DOUBLE));
            }
            if (!PrisonGame.wardentime.containsKey(p)) {
                PrisonGame.wardentime.put(p, 0);
            }
            if (!PrisonGame.worryachieve.containsKey(p)) {
                PrisonGame.worryachieve.put(p, -1);
            }
            if (!PrisonGame.axekills.containsKey(p)) {
                PrisonGame.axekills.put(p, 0);
            }
            if (!PrisonGame.prisonerlevel.containsKey(p)) {
                PrisonGame.prisonerlevel.put(p, 0);
            }
            if (p.getPersistentDataContainer().has(PrisonGame.hg))
                p.getPersistentDataContainer().remove(PrisonGame.hg);
            PrisonGame.handcuff.put(p, null);
            if (PrisonGame.worryachieve.get(p) >= 0) {
                PrisonGame.worryachieve.put(p, PrisonGame.worryachieve.get(p) + 1);
                if (PrisonGame.worryachieve.get(p) / 20 / 60 >= 15) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:whatuworried");
                    PrisonGame.worryachieve.put(p, -1);
                }
            }
            if (PrisonGame.warden == p) {
                PrisonGame.wardentime.put(p, PrisonGame.wardentime.get(p) + 1);
                if (PrisonGame.wardentime.get(p) / 20 / 60 >= 120) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:dictatorship");
                    PrisonGame.wardentime.put(p, Integer.MIN_VALUE);
                }
            }
            if (!PrisonGame.saidcycle.containsKey(p)) {
                PrisonGame.saidcycle.put(p, 0);
            }
            if (!PrisonGame.roles.containsKey(p)) {
                PrisonGame.roles.put(p, Role.PRISONER);
                MyListener.playerJoin(p, false);
            }
            if (!PrisonGame.st.containsKey(p)) {
                PrisonGame.st.put(p, 0.0);
            }
            if (!PrisonGame.hardmode.containsKey(p)) {
                PrisonGame.hardmode.put(p, false);
            }
            if (!PrisonGame.sp.containsKey(p)) {
                PrisonGame.sp.put(p, 0.0);
            }
            if (!PrisonGame.lastward.containsKey(p)) {
                PrisonGame.lastward.put(p, 20 * 10);
            }
            PrisonGame.lastward.put(p, PrisonGame.lastward.get(p) + 1);
            if (!PrisonGame.lastward2.containsKey(p)) {
                PrisonGame.lastward2.put(p, 0);
            }
            if (!PrisonGame.wardenban.containsKey(p)) {
                PrisonGame.wardenban.put(p, 0);
            }
            if (!PrisonGame.trustlevel.containsKey(p)) {
                PrisonGame.trustlevel.put(p, 0);
            }
            if (!PrisonGame.solittime.containsKey(p)) {
                PrisonGame.solittime.put(p, 0);
            }
            PrisonGame.solittime.put(p, PrisonGame.solittime.get(p) - 1);
            if (PrisonGame.solittime.get(p) <= 0 && p.getDisplayName().contains("SOLITARY")) {
                PrisonGame.tptoBed(p);
                MyListener.playerJoin(p, false);
                p.sendMessage( "You were released from solitary.");
            }
            if (!PrisonGame.word.containsKey(p)) {
                PrisonGame.word.put(p, "Amoger Susser");
            }
            if (PrisonGame.lastward.get(p) > 20 * 5) {
                PrisonGame.lastward2.put(p, 0);
            }

            if (!PrisonGame.escaped.containsKey(p)) {
                PrisonGame.escaped.put(p, false);
            }
            if (!PrisonGame.killior.containsKey(p) || p.hasPotionEffect(PotionEffectType.DOLPHINS_GRACE) || PrisonGame.roles.get(p) != Role.PRISONER) {
                PrisonGame.killior.put(p, null);
            }
            if (!PrisonGame.prisonnumber.containsKey(p)) {
                PrisonGame.prisonnumber.put(p, "690");
            }
            if (!PrisonGame.respect.containsKey(p)) {
                PrisonGame.respect.put(p, 0);
            }
            if (PrisonGame.givepig && PrisonGame.warden != null) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + PrisonGame.warden.getName() + " only prison:piggo");
                PrisonGame.givepig = false;
            }
            if (p.getPersistentDataContainer().has(PrisonGame.muted, PersistentDataType.INTEGER)) {
                if (p.getPersistentDataContainer().get(PrisonGame.muted, PersistentDataType.INTEGER) <= 0) {
                    p.getPersistentDataContainer().remove(PrisonGame.muted);
                } else {
                    p.getPersistentDataContainer().set(PrisonGame.muted, PersistentDataType.INTEGER, p.getPersistentDataContainer().get(PrisonGame.muted, PersistentDataType.INTEGER) - 1);
                }

            }
            if (PrisonGame.isInside(p, PrisonGame.nl("world", -50D, -53D, -109D, 0f, 0f), PrisonGame.nl("world", -48D, -59D, -107D, 0f, 0f))) {
                p.teleport(PrisonGame.nl("world", 70D, -59D, -69D, 180f, 0f));
            }
            if (p.getGameMode().equals(GameMode.ADVENTURE)) {
                p.getInventory().remove(Material.NETHERITE_SWORD);
                p.getInventory().remove(Material.SUGAR);
                if (PrisonGame.hardmode.get(p)) {
                    p.getInventory().remove(Material.GOLDEN_APPLE);
                    p.getInventory().remove(Material.NETHERITE_HELMET);
                }
                p.getInventory().remove(Material.IRON_BARS);
            }
            if (PrisonGame.roles.get(p) != Role.PRISONER && PrisonGame.roles.get(p) != Role.WARDEN) {
                if (p.hasPotionEffect(PotionEffectType.UNLUCK)) {
                    p.sendMessage(ChatColor.RED + "You can't be in here!");
                    p.playSound(p, Sound.ENTITY_PILLAGER_AMBIENT, 1.5f, 0.75f);
                    p.damage(6);
                    p.removePotionEffect(PotionEffectType.UNLUCK);
                    p.teleport(PrisonGame.active.getBmout());
                }
            }
        }
        PrisonGame.swapcool -= 1;
        PrisonGame.lockdowncool -= 1;
        PrisonGame.wardenCooldown -= 1;
        if (Bukkit.getWorld("world").getTime() > 0 && Bukkit.getWorld("world").getTime() < 2000) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.removePotionEffect(PotionEffectType.BLINDNESS);
            }
            timer1 = 0;
            timer2 = 2500;
            bossbar.setTitle("ROLL CALL");
            hasAlerted = false;
        }
        if (MyTask.bossbar.getTitle().equals("ROLL CALL") || MyTask.bossbar.getTitle().equals("EVENING ROLL CALL")) {
            Boolean allat = true;
            for (Player p : Bukkit.getOnlinePlayers()) {

                p.removePotionEffect(PotionEffectType.BAD_OMEN);
                p.getWorld().getWorldBorder().setWarningDistance(5);
                if (PrisonGame.roles.get(p) != Role.PRISONER && PrisonGame.hardmode.get(p)) {
                    if (!new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ()).getBlock().getType().equals(Material.LIGHT_BLUE_CONCRETE_POWDER)) {
                        p.sendTitle("", ChatColor.BLUE + "GET ONTO LIGHT BLUE POWDER OR GET FIRED!", 0, 5, 0);
                        p.addPotionEffect(PotionEffectType.HUNGER.createEffect(200, 0));
                        p.setCollidable(true);
                        p.addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 30, 0));
                        p.removePotionEffect(PotionEffectType.JUMP);
                    } else {
                        p.setFoodLevel(6);
                        if (p.hasPotionEffect(PotionEffectType.GLOWING)) {
                            p.sendMessage(ChatColor.GREEN + "You came to roll call!");
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
                        }
                        p.addPotionEffect(PotionEffectType.WEAKNESS.createEffect(20, 255));
                        p.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(20, 255));
                        p.removePotionEffect(PotionEffectType.HUNGER);
                        p.removePotionEffect(PotionEffectType.GLOWING);

                    }
                }
                if (PrisonGame.roles.get(p) == Role.PRISONER && !PrisonGame.escaped.get(p)) {
                    if (!new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ()).getBlock().getType().equals(Material.RED_SAND)) {
                        p.sendTitle("", ChatColor.RED + "GET ONTO THE RED SAND OR YOU'LL BE KILLED!", 0, 5, 0);
                        p.addPotionEffect(PotionEffectType.HUNGER.createEffect(200, 0));
                        p.setCollidable(true);
                        allat = false;
                        p.addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 30, 0));
                        p.removePotionEffect(PotionEffectType.JUMP);
                        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                    } else {
                        if (PrisonGame.roles.get(p) == Role.PRISONER && Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p) == Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals")) {
                            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners").addPlayer(p);
                        }
                        p.setHealth(p.getMaxHealth());
                        p.setFoodLevel(6);
                        if (p.hasPotionEffect(PotionEffectType.GLOWING)) {
                            p.sendMessage(ChatColor.GREEN + "You came to roll call!");
                            p.playSound(p, Sound.BLOCK_NOTE_BLOCK_BIT, 1, 1);
                        }
                        p.addPotionEffect(PotionEffectType.SLOW.createEffect(10, 255));
                        p.addPotionEffect(PotionEffectType.JUMP.createEffect(20, -25));
                        p.addPotionEffect(PotionEffectType.WEAKNESS.createEffect(20, 255));
                        p.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(20, 255));
                        p.setCollidable(false);
                        p.removePotionEffect(PotionEffectType.HUNGER);
                        p.removePotionEffect(PotionEffectType.GLOWING);

                    }
                }
            }
            if (allat && PrisonGame.warden != null) {
                PrisonGame.warden.sendTitle("", ChatColor.GREEN + "All prisoners at roll call! +1k$!", 0, 40, 0);
                PrisonGame.warden.getPersistentDataContainer().set(PrisonGame.mny, PersistentDataType.DOUBLE, PrisonGame.warden.getPersistentDataContainer().get(PrisonGame.mny, PersistentDataType.DOUBLE) + 1000.0);
                Bukkit.getWorld("world").setTime(timer2 - 1);
            }
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.setCollidable(true);
                p.removePotionEffect(PotionEffectType.JUMP);
            }
        }
        if (Bukkit.getWorld("world").getTime() == 2000) {
            if (!hasAlerted) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.getPersistentDataContainer().has(PrisonGame.mny, PersistentDataType.DOUBLE)) {
                        try {
                            if (p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0) - PrisonGame.wealthcycle.get(p) >= 3000) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:wealthy");
                            }
                            PrisonGame.wealthcycle.put(p, p.getPersistentDataContainer().get(PrisonGame.mny, PersistentDataType.DOUBLE));
                        } catch (NullPointerException ignored) {
                            Bukkit.getLogger().info(p.getName() + " seems to not have a money container?");
                        }
                    }
                }
                hasAlerted = true;
                Double taxcount = 0.0;
                DecimalFormat numberFormat = new DecimalFormat("#0.0");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPotionEffect(PotionEffectType.GLOWING) && !PrisonGame.escaped.get(p)) {
                        Bukkit.broadcastMessage(ChatColor.RED + p.getName() + ChatColor.GOLD + " didn't come to roll call! " + ChatColor.RED + "Kill them for 100 dollars!");
                        p.sendTitle("", ChatColor.RED + "COME TO ROLL CALL NEXT TIME!", 0, 60, 0);
                        if (PrisonGame.hardmode.get(p)) {
                            p.damage(99999);
                            if (PrisonGame.roles.get(p) != Role.PRISONER) {
                                if (PrisonGame.warden != null) {
                                    if (PrisonGame.warden.equals(p)) {
                                        PrisonGame.warden = null;
                                    }
                                }
                                PrisonGame.roles.put((Player) p, Role.PRISONER);
                                MyListener.playerJoin((Player) p, false);
                            }
                        }
                        p.playSound(p, Sound.ENTITY_SILVERFISH_AMBIENT, 1, 0.25f);
                    }
                    if (!p.hasPotionEffect(PotionEffectType.GLOWING) && !PrisonGame.escaped.get(p) && PrisonGame.respect.get(p) == 1) {
                        PrisonGame.respect.put(p, 0);
                        PrisonGame.calls.put(p, PrisonGame.calls.getOrDefault(p, 0) + 1);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:respect");
                    }
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() > 2000 && Bukkit.getWorld("world").getTime() < 4000) {
            timer1 = 2000;
            timer2 = 4000;
            bossbar.setTitle("Breakfast");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!PrisonGame.hardmode.get(p) && !PrisonGame.escaped.get(p) && PrisonGame.roles.get(p) == Role.PRISONER)
                    p.addPotionEffect(PotionEffectType.SATURATION.createEffect(120, 0));
            }
        }
        if (Bukkit.getWorld("world").getTime() > 4000 && Bukkit.getWorld("world").getTime() < 7000) {
            timer1 = 4000;
            timer2 = 7000;
            bossbar.setTitle("Free Time");
            jobm = 1;
        }
        if (Bukkit.getWorld("world").getTime() > 7000 && Bukkit.getWorld("world").getTime() < 10000) {
            timer1 = 7000;
            timer2 = 10000;
            bossbar.setTitle("Job Time");
            jobm = 2;
        }
        if (Bukkit.getWorld("world").getTime() > 10000 && Bukkit.getWorld("world").getTime() < 13000) {
            timer1 = 10000;
            timer2 = 13000;
            bossbar.setTitle("Lunch");
            jobm = 1;
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!PrisonGame.hardmode.get(p) && !PrisonGame.escaped.get(p) && PrisonGame.roles.get(p) == Role.PRISONER)
                    p.addPotionEffect(PotionEffectType.SATURATION.createEffect(120, 0));
            }
        }
        if (Bukkit.getWorld("world").getTime() > 13000 && Bukkit.getWorld("world").getTime() < 15000) {
            hasAlerted = false;
            timer1 = 13000;
            timer2 = 15000;
            bossbar.setTitle("EVENING ROLL CALL");
            jobm = 1;
        }
        if (Bukkit.getWorld("world").getTime() == 15000) {
            if (!hasAlerted) {
                hasAlerted = true;
                Double taxcount = 0.0;
                DecimalFormat numberFormat = new DecimalFormat("#0.0");
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (p.hasPotionEffect(PotionEffectType.GLOWING) && !PrisonGame.escaped.get(p)) {
                        Bukkit.broadcastMessage(ChatColor.RED + p.getName() + ChatColor.GOLD + " didn't come to roll call! " + ChatColor.RED + "Kill them for 100 dollars!");
                        p.sendTitle("", ChatColor.RED + "COME TO ROLL CALL NEXT TIME!", 0, 60, 0);
                        if (PrisonGame.hardmode.get(p)) {
                            p.damage(99999);
                            if (PrisonGame.roles.get(p) != Role.PRISONER) {
                                if (PrisonGame.warden != null) {
                                    if (PrisonGame.warden.equals(p)) {
                                        PrisonGame.warden = null;
                                    }
                                }
                                PrisonGame.roles.put((Player) p, Role.PRISONER);
                                MyListener.playerJoin((Player) p, false);
                            }
                        }
                        p.playSound(p, Sound.ENTITY_SILVERFISH_AMBIENT, 1, 0.25f);
                    }
                    if (!p.hasPotionEffect(PotionEffectType.GLOWING) && !PrisonGame.escaped.get(p) && PrisonGame.respect.get(p) == 1) {
                        PrisonGame.respect.put(p, 0);
                        PrisonGame.calls.put(p, PrisonGame.calls.getOrDefault(p, 0) + 1);
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:respect");
                    }
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() > 15000 && Bukkit.getWorld("world").getTime() < 16000) {
            timer1 = 15000;
            timer2 = 16000;
            bossbar.setTitle("Cell Time");
            jobm = 1;
        }
        if (Bukkit.getWorld("world").getTime() > 23300) {
            Bukkit.getWorld("world").setTime(0);
        }
        if (Bukkit.getWorld("world").getTime() == 16000) {
            Bukkit.broadcastMessage(ChatColor.RED + "All cells have been automatticaly closed! " + ChatColor.DARK_RED + "GET TO SLEEP!");
            LivingEntity le = (LivingEntity) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -40, -58, -973), EntityType.ZOMBIE);
            le.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(999999, 255));
            le.addPotionEffect(PotionEffectType.SPEED.createEffect(999999, 2));
            le.setPersistent(true);
            le.setInvulnerable(true);
            le.setSilent(true);
            PlayerDisguise playerDisguise = new PlayerDisguise("FreddyFazbearXXX");
            le.setCustomName("fredy");
            playerDisguise.setName("fredy");
            DisguiseAPI.disguiseEntity(le, playerDisguise);

            LivingEntity le2 = (LivingEntity) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -40, -58, -973), EntityType.ZOMBIE);
            le2.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(999999, 255));
            le2.addPotionEffect(PotionEffectType.SPEED.createEffect(999999, 2));
            le2.setPersistent(true);
            le2.setInvulnerable(true);
            le2.setSilent(true);
            PlayerDisguise playerDisguise2 = new PlayerDisguise("Freakedoutt");
            le2.setCustomName("boner");
            playerDisguise2.setName("boner");
            DisguiseAPI.disguiseEntity(le2, playerDisguise2);

            LivingEntity le3 = (LivingEntity) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -40, -58, -973), EntityType.ZOMBIE);
            le3.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(999999, 255));
            le3.addPotionEffect(PotionEffectType.SPEED.createEffect(999999, 2));
            le3.setPersistent(true);
            le3.setInvulnerable(true);
            le3.setSilent(true);
            PlayerDisguise playerDisguise3 = new PlayerDisguise("minekaufcraft");
            le3.setCustomName("chicker");
            playerDisguise3.setName("chicker");
            DisguiseAPI.disguiseEntity(le3, playerDisguise3);

            LivingEntity le4 = (LivingEntity) Bukkit.getWorld("world").spawnEntity(new Location(Bukkit.getWorld("world"), -40, -58, -973), EntityType.ZOMBIE);
            le4.addPotionEffect(PotionEffectType.INCREASE_DAMAGE.createEffect(999999, 255));
            le4.addPotionEffect(PotionEffectType.SPEED.createEffect(999999, 2));
            le4.setPersistent(true);
            le4.setInvulnerable(true);
            le4.setSilent(true);
            PlayerDisguise playerDisguise4 = new PlayerDisguise("Paignton");
            le4.setCustomName("foxer");
            playerDisguise4.setName("foxer");
            DisguiseAPI.disguiseEntity(le4, playerDisguise4);

            switch (PrisonGame.active.getName()) {
                case "Gaeae Fort":
                    Location[] doors = {
                            new Location(Bukkit.getWorld("world"), 48, -59, -135),
                            new Location(Bukkit.getWorld("world"), 47, -59, -134),
                            new Location(Bukkit.getWorld("world"), 29, -59, -107),
                            new Location(Bukkit.getWorld("world"),  21, -60, -102),
                            new Location(Bukkit.getWorld("world"), 14, -60, -108),
                            new Location(Bukkit.getWorld("world"),  12, -60, -117),
                            new Location(Bukkit.getWorld("world"),23, -59, -120)
                    };
                    for (Location l : doors) {
                        BlockState state = l.getBlock().getState();
                        Door openable = (Door) state.getBlockData();
                        openable.setOpen(false);
                        state.setBlockData(openable);
                        state.update();
                        l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_CLOSE, 0.75f, 0.75f);
                    }
                    break;
                case "Hypertech":
                    Location[] doors2 = {
                            new Location(Bukkit.getWorld("world"), -18, -59, -996),
                            new Location(Bukkit.getWorld("world"), -14, -59, -996),
                            new Location(Bukkit.getWorld("world"), -14, -59, -991),
                            new Location(Bukkit.getWorld("world"), -14, -59, -1006),
                            new Location(Bukkit.getWorld("world"), -18, -59, -1006),
                            new Location(Bukkit.getWorld("world"), -14, -59, -1011),
                            new Location(Bukkit.getWorld("world"), -18, -59, -1011),
                            new Location(Bukkit.getWorld("world"), -18, -52, -996),
                            new Location(Bukkit.getWorld("world"), -14, -52, -996),
                            new Location(Bukkit.getWorld("world"), -14, -52, -991)
                    };
                    for (Location l : doors2) {
                        BlockState state = l.getBlock().getState();
                        Door openable = (Door) state.getBlockData();
                        openable.setOpen(false);
                        state.setBlockData(openable);
                        state.update();
                        l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_CLOSE, 0.75f, 0.75f);
                    }
                    break;
                case "Train":
                    Location[] doors3 = {
                            new Location(Bukkit.getWorld("world"), 92, -58, 961),
                            new Location(Bukkit.getWorld("world"), 91, -58, 961),
                            new Location(Bukkit.getWorld("world"), 92, -58, 964),
                            new Location(Bukkit.getWorld("world"), 91, -58, 964),
                            new Location(Bukkit.getWorld("world"), 92, -58, 969),
                            new Location(Bukkit.getWorld("world"), 91, -58, 969),
                            new Location(Bukkit.getWorld("world"), 92, -58, 972),
                            new Location(Bukkit.getWorld("world"), 91, -58, 972),
                            new Location(Bukkit.getWorld("world"), 92, -58, 980),
                            new Location(Bukkit.getWorld("world"), 91, -58, 980)
                    };
                    for (Location l : doors3) {
                        BlockState state = l.getBlock().getState();
                        Door openable = (Door) state.getBlockData();
                        openable.setOpen(false);
                        state.setBlockData(openable);
                        state.update();
                        l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_CLOSE, 0.75f, 0.75f);
                    }
                    break;
                case "Gladiator":
                    Location[] doors4 = {
                            new Location(Bukkit.getWorld("world"), -2049, -60, 1950),
                            new Location(Bukkit.getWorld("world"), -2043, -60, 1950),
                            new Location(Bukkit.getWorld("world"), -2037, -60, 1950),
                            new Location(Bukkit.getWorld("world"), -2021, -60, 1950),
                            new Location(Bukkit.getWorld("world"), -2015, -60, 1950),
                            new Location(Bukkit.getWorld("world"), -2009, -60, 1950),
                            new Location(Bukkit.getWorld("world"), -2049, -60, 2002),
                            new Location(Bukkit.getWorld("world"), -2043, -60, 2002),
                            new Location(Bukkit.getWorld("world"), -2037, -60, 2002),
                            new Location(Bukkit.getWorld("world"), -2021, -60, 2002),
                            new Location(Bukkit.getWorld("world"), -2015, -60, 2002),
                            new Location(Bukkit.getWorld("world"), -2009, -60, 2002)
                    };
                    for (Location l : doors4) {
                        BlockState state = l.getBlock().getState();
                        Door openable = (Door) state.getBlockData();
                        openable.setOpen(false);
                        state.setBlockData(openable);
                        state.update();
                        l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_CLOSE, 0.75f, 0.75f);
                    }
                    break;
                case "Island":
                    Location[] doors5 = {
                            new Location(Bukkit.getWorld("world"), 1955, -56, -1987),
                            new Location(Bukkit.getWorld("world"), 1962, -56, -1987),
                            new Location(Bukkit.getWorld("world"), 1955, -56, -1983),
                            new Location(Bukkit.getWorld("world"), 1962, -56, -1983),
                            new Location(Bukkit.getWorld("world"), 1955, -56, -1979),
                            new Location(Bukkit.getWorld("world"), 1962, -56, -1979),

                            new Location(Bukkit.getWorld("world"), 1955, -60, -1987),
                            new Location(Bukkit.getWorld("world"), 1962, -60, -1987),
                            new Location(Bukkit.getWorld("world"), 1955, -60, -1983),
                            new Location(Bukkit.getWorld("world"), 1962, -60, -1983),
                            new Location(Bukkit.getWorld("world"), 1955, -60, -1979),
                            new Location(Bukkit.getWorld("world"), 1962, -60, -1979)
                    };
                    for (Location l : doors5) {
                        BlockState state = l.getBlock().getState();
                        Door openable = (Door) state.getBlockData();
                        openable.setOpen(false);
                        state.setBlockData(openable);
                        state.update();
                        l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_CLOSE, 0.75f, 0.75f);
                    }
                    break;
                case "Santa's Workshop":
                    Location[] doors6 = {
                            new Location(Bukkit.getWorld("world"), 1964, -60, 1931),
                            new Location(Bukkit.getWorld("world"), 1958, -60, 1931),
                            new Location(Bukkit.getWorld("world"), 1964, -60, 1926),
                            new Location(Bukkit.getWorld("world"), 1958, -60, 1926),
                            new Location(Bukkit.getWorld("world"), 1964, -60, 1921),
                            new Location(Bukkit.getWorld("world"), 1958, -60, 1921),
                            new Location(Bukkit.getWorld("world"), 1964, -60, 1916),
                            new Location(Bukkit.getWorld("world"), 1958, -60, 1916),
                            new Location(Bukkit.getWorld("world"), 1964, -60, 1911),
                            new Location(Bukkit.getWorld("world"), 1958, -60, 1911),
                            new Location(Bukkit.getWorld("world"), 1964, -60, 1906),
                            new Location(Bukkit.getWorld("world"), 1958, -60, 1906),
                            new Location(Bukkit.getWorld("world"), 1964, -60, 1901),
                            new Location(Bukkit.getWorld("world"), 1958, -60, 1901)

                    };
                    for (Location l : doors6) {
                        BlockState state = l.getBlock().getState();
                        Door openable = (Door) state.getBlockData();
                        openable.setOpen(false);
                        state.setBlockData(openable);
                        state.update();
                        l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_CLOSE, 0.75f, 0.75f);
                    }
                    break;
                case "Volcano":
                    Location[] doors7 = {
                            new Location(Bukkit.getWorld("world"), -2019, -60, -1976),
                            new Location(Bukkit.getWorld("world"), -2020, -60, -1976)

                    };
                    for (Location l : doors7) {
                        BlockState state = l.getBlock().getState();
                        Door openable = (Door) state.getBlockData();
                        openable.setOpen(false);
                        state.setBlockData(openable);
                        state.update();
                        l.getWorld().playSound(l, Sound.BLOCK_IRON_DOOR_CLOSE, 0.75f, 0.75f);
                    }
                    break;
            }
        }
        if (Bukkit.getWorld("world").getTime() < 16000) {
            for (Entity e : Bukkit.getWorld("world").getEntities()) {
                if (e.getCustomName() != null) {
                    if (e.getCustomName().equals("fredy") || e.getCustomName().equals("boner") || e.getCustomName().equals("foxer") || e.getCustomName().equals("chicker")) {
                        e.remove();
                    }
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() > 0 && Bukkit.getWorld("world").getTime() < 20) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (PrisonGame.isInside(p, new Location(Bukkit.getWorld("world"), -35, -55, -991), new Location(Bukkit.getWorld("world"), -59, -61, -968))) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:freder");
                }
            }
        }
        if (Bukkit.getWorld("world").getTime() > 16000 && Bukkit.getWorld("world").getTime() < 24000) {
            timer1 = 16000;
            timer2 = 24000;
            bossbar.setTitle("LIGHTS OUT");
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (PrisonGame.hardmode.get(p)) {
                    if (p.hasPotionEffect(PotionEffectType.LUCK)) {
                        p.addPotionEffect(PotionEffectType.LUCK.createEffect(20 * 15, 0));
                        p.addPotionEffect(PotionEffectType.BAD_OMEN.createEffect(999999, 0));
                        p.sendTitle("", ChatColor.RED + "You will be respawned at roll call!", 0, 20 * 3, 0);
                    }
                }
                if (PrisonGame.roles.get(p) == Role.PRISONER && !PrisonGame.escaped.get(p)) {
                    p.getWorld().getWorldBorder().setWarningDistance(Integer.MAX_VALUE);
                    if (p.getWorld().getName().equals("endprison")) {
                        if (!new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 1, p.getLocation().getZ()).getBlock().getType().equals(Material.JIGSAW)) {
                            if (!p.hasPotionEffect(PotionEffectType.LUCK)) {
                                p.sendTitle("", ChatColor.RED + "GET TO A CELL OR YOU'LL BE KILLED!", 0, 20 * 3, 0);
                                p.addPotionEffect(PotionEffectType.HUNGER.createEffect(200, 0));
                                p.addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 30, 0));
                                if (PrisonGame.hardmode.get(p)) {
                                    if (p.getHealth() > 1) {
                                        p.setHealth(1);
                                    }
                                }
                                Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                            } else {
                                Bukkit.getWorld("world").setTime(Bukkit.getWorld("world").getTime() + 2);
                                if (PrisonGame.roles.get(p) == Role.PRISONER && Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p) == Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals")) {
                                    Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners").addPlayer(p);
                                }
                                p.removePotionEffect(PotionEffectType.HUNGER);
                                p.removePotionEffect(PotionEffectType.GLOWING);
                            }
                        }
                    } else {
                        if (!p.isSleeping()) {
                            if (!p.hasPotionEffect(PotionEffectType.LUCK)) {
                                p.sendTitle("", ChatColor.RED + "GET TO SLEEP IN A BED OR YOU'LL BE KILLED!", 0, 20 * 3, 0);
                                p.addPotionEffect(PotionEffectType.HUNGER.createEffect(200, 0));
                                p.addPotionEffect(PotionEffectType.GLOWING.createEffect(20 * 30, 0));
                                if (PrisonGame.hardmode.get(p)) {
                                    if (p.getHealth() > 1) {
                                        p.setHealth(1);
                                    }
                                }
                                Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                                PrisonGame.respect.put(p, 0);
                            } else {
                                PrisonGame.respect.put(p, 1);
                                Bukkit.getWorld("world").setTime(Bukkit.getWorld("world").getTime() + 2);
                                if (PrisonGame.roles.get(p) == Role.PRISONER && Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p) == Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals")) {
                                    Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners").addPlayer(p);
                                }
                                p.removePotionEffect(PotionEffectType.HUNGER);
                                p.removePotionEffect(PotionEffectType.GLOWING);

                            }
                        }
                    }
                }
            }
            Bukkit.getWorld("world").setTime(Bukkit.getWorld("world").getTime() + 2);
            bossbar.setColor(BarColor.RED);
            bossbar.addFlag(BarFlag.DARKEN_SKY);
            bossbar.addFlag(BarFlag.CREATE_FOG);
        } else {
            bossbar.setColor(BarColor.WHITE);
            bossbar.removeFlag(BarFlag.DARKEN_SKY);
            if (!PrisonGame.active.getName().equals("Island"))
                bossbar.removeFlag(BarFlag.CREATE_FOG);
        }
        if (PrisonGame.active.getName().equals("Island")) {
            bossbar.addFlag(BarFlag.CREATE_FOG);
        }
        bossbar.setProgress(((float) Bukkit.getWorld("world").getTime() - (float) timer1) / ((float) timer2 - (float) timer1));
        if (Bukkit.getWorld("world").getTime() == timer2) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                    p.sendTitle("", ChatColor.BOLD + bossbar.getTitle(), 20, 40, 20);
                }, 4);
                p.playSound(p, Sound.BLOCK_BELL_USE, 1, 1);
                Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                    p.playSound(p, Sound.BLOCK_BELL_USE, 1, 1);
                }, 4);
                Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                    p.playSound(p, Sound.BLOCK_BELL_USE, 1, 1);
                }, 8);
            }
        }
        DecimalFormat numberFormat3 = new DecimalFormat("#0.0");
        String n = "(No Warden!)";
        if (PrisonGame.warden != null) {
            n = PrisonGame.wardentime.get(PrisonGame.warden) / (20 * 60) + "m";
        }
        String tab = ChatColor.translateAlternateColorCodes('&', "&7---\n&ePrisonButBad\n&fMade by agmass!\n&8Like the old tab? Use /pbsettings!\n&aPlayers: ") + Bukkit.getOnlinePlayers().size() + ChatColor.translateAlternateColorCodes('&',"\n&cWarden Time: {wardentime}\n&7---\n\n&bGuards:\n&r").replace("{wardentime}", n);
        String prisoners = "";
        String guards = "";
        String civs = "";

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (PrisonGame.isInside(p, new Location(Bukkit.getWorld("world"), -2011, -53, -1929), new Location(Bukkit.getWorld("world"), -2009, -57, -1931))) {
                if (PrisonGame.warden.equals(p)) {
                        for (Player pe : Bukkit.getOnlinePlayers()) {
                            pe.teleport(new Location(Bukkit.getWorld("world"), -2062, -50, 1945));
                        }
                        PrisonGame.active = PrisonGame.nether;
                        PrisonGame.swapcool = (20 * 60) * 5;
                        MyListener.reloadBert();
                        for (Player pe : Bukkit.getOnlinePlayers()) {
                            if (PrisonGame.roles.get(pe) != Role.WARDEN) {
                                MyListener.playerJoin(p, true);
                                pe.sendTitle("New prison!", "NETHER");
                            } else {
                                pe.teleport(PrisonGame.active.getWardenspawn());
                                Bukkit.getScheduler().runTaskLater(PrisonGame.getPlugin(PrisonGame.class), () -> {
                                    pe.teleport(PrisonGame.active.getWardenspawn());
                                }, 5);
                                if (!pe.getDisplayName().contains("ASCENDING"))
                                    pe.sendTitle("New prison!", "NETHER");
                            }
                        }
                } else {
                    p.sendTitle("", ChatColor.RED + "YOU MUST BE WARDEN!", 0, 50, 0);
                }
            }
            if (PrisonGame.roles.get(p) != Role.PRISONER) {
                ChatColor pingColor = ChatColor.GREEN;
                if (p.getPing() >= 150) {
                    pingColor = ChatColor.YELLOW;
                }
                if (p.getPing() >= 300) {
                    pingColor = ChatColor.GOLD;
                }
                if (p.getPing() >= 450) {
                    pingColor = ChatColor.RED;
                }
                if (p.getPing() >= 600) {
                    pingColor = ChatColor.DARK_RED;
                }
                if (!p.hasPotionEffect(PotionEffectType.LUCK) && !p.isDead()) {
                    guards = guards + "\n" + p.getPlayerListName() + ChatColor.GRAY  + " [" + pingColor + p.getPing() + ChatColor.GRAY + "ms]";
                }else {
                    guards = guards + ChatColor.translateAlternateColorCodes('&', "\n &4☠&7 " + p.getName());
                }
            }
            if (PrisonGame.roles.get(p) == Role.PRISONER) {
                ChatColor pingColor = ChatColor.GREEN;
                if (p.getPing() > 200) {
                    pingColor = ChatColor.YELLOW;
                }
                if (p.getPing() > 400) {
                    pingColor = ChatColor.RED;
                }
                if (!p.hasPotionEffect(PotionEffectType.LUCK) && !p.isDead()) {
                    prisoners = prisoners + "\n" + p.getDisplayName() + ChatColor.GRAY  + " [" + pingColor + p.getPing() + ChatColor.GRAY + "ms]";
                } else {
                    prisoners = prisoners + ChatColor.translateAlternateColorCodes('&', "\n &4☠&7 " + p.getName());
                }
            }
        }

        tab = tab + ChatColor.translateAlternateColorCodes('&', guards + "\n&7---\n\n&6Prisoners:\n&r" + prisoners + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (PrisonGame.calls.getOrDefault(p, 0) >= 10) {
                PrisonGame.calls.put(p, Integer.MIN_VALUE);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:npc");
            }
            if (p.getName().contains("RennArpent")) {
                p.setDisplayName(p.getDisplayName().replace("FUNDER", "fund deez nuts"));
                p.setDisplayName(p.getDisplayName().replace("WARDEN", "nah fuck this dude"));
                p.setDisplayName(p.getDisplayName().replace("GUARD", "worst guard"));
                p.setDisplayName(p.getDisplayName().replace("NURSE", "L nurse"));
                p.setDisplayName(p.getDisplayName().replace("SWAT", "who gave him swat"));
                p.setDisplayName(p.getDisplayName().replace("PRISONER", "idiot -->"));
                p.setDisplayName(p.getDisplayName().replace("SOLITARY", "deserved"));
                p.setDisplayName(p.getDisplayName().replace("VISITOR", "i wish he was a prisoner"));
                p.setDisplayName(p.getDisplayName().replace("CRIMINAL", "*snore* mimimimimi"));
            }
            //if (!p.getPersistentDataContainer().has(PrisonGame.rank, PersistentDataType.INTEGER)) {
            //    p.getPersistentDataContainer().set(PrisonGame.rank, PersistentDataType.INTEGER, 0);
            //}
            if (p.getLocation().getBlockY() == -60 && PrisonGame.active.getName().equals("Train") && PrisonGame.isInside(p, PrisonGame.nl("world", 27D, -61D, 920D, 0f, 0f), PrisonGame.nl("world", 129D, 8D, 1041D, 0f, 0f))) {
                p.damage(999);
            }
            if (p.getLocation().getBlockY() == -61 && PrisonGame.active.getName().equals("Island") && p.isInWater()) {
                p.damage(4);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:dewater");
            }
            if (p.getLocation().getBlockY() <= -53 && PrisonGame.active.getName().equals("Boat") && p.isInWater()) {
                p.damage(1);
                p.setNoDamageTicks(1);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "advancement grant " + p.getName() + " only prison:dewater");
            }
            if (p.getLocation().getY() < 118 && p.getWorld().getName().equals("endprison")) {
                p.damage(999);
            }
            if (p.getPersistentDataContainer().has(PrisonGame.nightvis, PersistentDataType.INTEGER)) {
                p.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(99999, 255));
            } else {
                p.removePotionEffect(PotionEffectType.NIGHT_VISION);
            }
            if (p.getGameMode().equals(GameMode.SURVIVAL))
                p.setGameMode(GameMode.ADVENTURE);
            if (p.hasPotionEffect(PotionEffectType.LUCK) || p.hasPotionEffect(PotionEffectType.BAD_OMEN)) {
                p.setGameMode(GameMode.SPECTATOR);
                if (PrisonGame.killior.get(p) == null) {
                    PrisonGame.tptoBed(p);
                } else {
                    if (PrisonGame.killior.get(p).isOnline() && !PrisonGame.killior.get(p).getGameMode().equals(GameMode.SPECTATOR)) {
                        p.setSpectatorTarget(PrisonGame.killior.get(p));
                    } else {
                        PrisonGame.tptoBed(p);
                    }
                }
            } else {
                if (p.getGameMode().equals(GameMode.SPECTATOR)) {
                    p.setGameMode(GameMode.ADVENTURE);
                }
            }
            /*if (p.getPersistentDataContainer().getOrDefault(PrisonGame.rank, PersistentDataType.INTEGER, 0) == 1) {
                p.setCustomName(ChatColor.GRAY + "[" + ChatColor.GOLD + "SUPPORTER" + ChatColor.GRAY + "] " + p.getDisplayName());
                p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.GOLD + "SUPPORTER" + ChatColor.GRAY + "] " + p.getDisplayName());
                p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.GOLD + "SUPPORTER" + ChatColor.GRAY + "] " + p.getDisplayName());
            }*/
            if (PrisonGame.hardmode.get(p)) {
                if (p.isSprinting() && !PrisonGame.escaped.get(p) && PrisonGame.roles.get(p) == Role.PRISONER) {
                    p.setFoodLevel(p.getFoodLevel() - 1);
                    p.sendTitle("", ChatColor.RED + "You can only sprint when you've escaped! [HARD MODE]", 0, 5, 0);
                }
                p.setMaxHealth(14);
            } else {
                p.setMaxHealth(20);
            }


            if (!p.getInventory().getItemInMainHand().getType().equals(Material.IRON_SHOVEL)) {
            if (!PrisonGame.escaped.get(p) && PrisonGame.roles.get(p) == Role.PRISONER) {
                p.setWalkSpeed(0.2f);
                p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
            }
            if (PrisonGame.escaped.get(p)) {
                p.setWalkSpeed(0.2f);
                p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4.4);
            }
            if (PrisonGame.roles.get(p) != Role.PRISONER) {
                p.setWalkSpeed(0.2f);
                p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4.4);
            }
            } else {
                p.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(9999);
            }

            if (PrisonGame.prisonerlevel.getOrDefault(p, 0) == 1) {
                if (p.isSprinting() && !PrisonGame.escaped.get(p) && PrisonGame.roles.get(p) == Role.PRISONER) {
                    p.setFoodLevel(p.getFoodLevel() - 1);
                    p.sendTitle("", ChatColor.RED + "You can only sprint when you've escaped! [F-CLASS]", 0, 5, 0);
                }
            }

            if (PrisonGame.prisonerlevel.get(p) == 1) {
                if (!p.getPlayerListName().contains("F-CLASS")) {
                    p.setCustomName(ChatColor.GRAY + "[" + ChatColor.RED + "F-CLASS" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "F-CLASS" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.RED + "F-CLASS" + ChatColor.GRAY + "] " + p.getDisplayName());
                }
            }

            if (!p.hasPotionEffect(PotionEffectType.DOLPHINS_GRACE)) {
                if (p.getVehicle() instanceof Player) {
                    p.addPotionEffect(PotionEffectType.JUMP.createEffect(20, -25));
                    p.leaveVehicle();
                }
            }

            for (Player pe : Bukkit.getOnlinePlayers()) {
                if (!p.canSee(pe)) {
                    if (!pe.isInsideVehicle()) {
                        p.showPlayer(PrisonGame.getPlugin(PrisonGame.class), pe);
                    }
                }
            }

            if (p.hasPotionEffect(PotionEffectType.DOLPHINS_GRACE)) {
                if (p.isInsideVehicle()) {
                    if (p.getVehicle() instanceof Player) {
                        ((Player) p.getVehicle()).setCooldown(Material.IRON_SHOVEL, 20 * 10);
                        ((Player) p.getVehicle()).hidePlayer(PrisonGame.getPlugin(PrisonGame.class), p);
                    }
                }
                p.setNoDamageTicks(20);
                p.addPotionEffect(PotionEffectType.WEAKNESS.createEffect(20, 255));
            }
            if (p.hasPotionEffect(PotionEffectType.DOLPHINS_GRACE)) {
                p.removePotionEffect(PotionEffectType.GLOWING);
            }

            if (!PrisonGame.hardmode.get(p)) {
                if (p.getName().equals("Jacco100") && !p.getPlayerListName().contains("REPORTER") || p.getName().equals("Goodgamer121") && !p.getPlayerListName().contains("REPORTER") || p.getName().equals("Evanbeer") && !p.getPlayerListName().contains("REPORTER") || p.getName().equals("teuli") && !p.getPlayerListName().contains("REPORTER")) {
                    p.setCustomName(ChatColor.GRAY + "[" + ChatColor.GREEN + "REPORTER" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.GREEN + "REPORTER" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.GREEN + "REPORTER" + ChatColor.GRAY + "] " + p.getDisplayName());
                }
                if (p.getName().equals("agmass") && !p.getPlayerListName().contains("OWNER")) {
                    p.setCustomName(ChatColor.GRAY + "[" + ChatColor.DARK_RED + "OWNER" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.DARK_RED + "OWNER" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.DARK_RED + "OWNER" + ChatColor.GRAY + "] " + p.getDisplayName());
                }
                if (p.getName().equals("4950") && !p.getPlayerListName().contains("BUILDER") || p.getName().equals("ClownCaked") && !p.getPlayerListName().contains("BUILDER") || p.getName().equals("Sanan1010") && !p.getPlayerListName().contains("BUILDER") || p.getName().equals("noahbt787") && !p.getPlayerListName().contains("BUILDER") || p.getName().equals("Evanbeer") && !p.getPlayerListName().contains("BUILDER")) {
                    p.setCustomName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILDER" + ChatColor.GRAY + "] " + p.getDisplayName());
                }
                if (p.getName().equals("vwdrYT") && !p.getPlayerListName().contains("BOOSTER") || p.getName().equals("TinyWiFi") && !p.getPlayerListName().contains("BOOSTER") || p.getName().equals("MonsterClaws_") && !p.getPlayerListName().contains("BOOSTER") || p.getName().equals("Hazardd_") && !p.getPlayerListName().contains("BOOSTER")) {
                    p.setCustomName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "BOOSTER" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "BOOSTER" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.LIGHT_PURPLE + "BOOSTER" + ChatColor.GRAY + "] " + p.getDisplayName());
                }
                if (p.getName().equals("Maglcite_") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals(".Rileybt18") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("Moleman1231") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("Goodgamer121") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("foxboy99") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("ATee_") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("Kyrris") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("adam214") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("JuleczkaOwO") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("RennArpent") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("Kingdarksword") && !p.getPlayerListName().contains("BUILD HELP") || p.getName().equals("Susanne_h") && !p.getPlayerListName().contains("BUILD HELP")) {
                    p.setCustomName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILD HELP" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILD HELP" + ChatColor.GRAY + "] " + p.getDisplayName());
                    p.setDisplayName(ChatColor.GRAY + "[" + ChatColor.YELLOW + "BUILD HELP" + ChatColor.GRAY + "] " + p.getDisplayName());
                }
            } else {
                p.setPlayerListName(ChatColor.GRAY + "[" + ChatColor.RED + "HARD MODE" + ChatColor.GRAY + "] " + ChatColor.GRAY + p.getName());
            }
            if (!p.getPersistentDataContainer().has(PrisonGame.tab)) {
                p.setPlayerListHeaderFooter(tab, "hi");
            } else {
                p.setPlayerListHeader("");
            }
            p.setPlayerListFooter("Imagine using old tab. Actual pussy move ngl");
            /*if (p.hasPotionEffect(PotionEffectType.GLOWING)) {
                if (PrisonGame.type.get(p) == 0 && Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p) == Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Prisoners")) {
                    Bukkit.getScoreboardManager().getMainScoreboard().getTeam("Criminals").addPlayer(p);
                }
            }*/
            bossbar.addPlayer(p);
            if (p.getInventory().getItemInMainHand().getType().equals(Material.COOKED_CHICKEN)) {
                p.kickPlayer("");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " 5m Abuse of Illegals [AUTO]");
            }
            if (p.getInventory().getItemInMainHand().hasItemMeta()) {
                if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "Handcuffs " + ChatColor.RED + "[CONTRABAND]")) {
                    if (PrisonGame.roles.get(p) == Role.PRISONER) {
                        p.getInventory().remove(p.getInventory().getItemInMainHand());
                    }
                }
            }
            if (p.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.DAMAGE_ALL)) {
                if (p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND_SWORD)) {
                    if (PrisonGame.roles.get(p) != Role.WARDEN) {
                        p.sendMessage("no illegal 4 u!");
                        p.getInventory().getItemInMainHand().removeEnchantment(Enchantment.DAMAGE_ALL);
                        p.kickPlayer("");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " 5m Abuse of Illegals [AUTO]");
                    }
                }
            }
            if (p.getLocation().getBlock().getType().equals(Material.VOID_AIR)) {
                if (PrisonGame.roles.get(p).equals(0)) {
                    if (!p.hasPotionEffect(PotionEffectType.GLOWING)) {
                        Boolean yesdothat = true;
                        if (p.getInventory().getChestplate() != null) {
                            if (p.getInventory().getChestplate().getItemMeta() != null) {
                                if (p.getInventory().getChestplate().getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "Cloak")) {
                                    yesdothat = false;
                                }
                            }
                        }
                        if (yesdothat) {
                            for (ItemStack i : p.getInventory()) {
                                if (i != null) {
                                    if (i.getItemMeta().getDisplayName().contains("[CONTRABAND]") || i.getType().equals(Material.STONE_SWORD) || i.getType().equals(Material.IRON_SWORD) || i.getType().equals(Material.IRON_HELMET) || i.getType().equals(Material.IRON_CHESTPLATE) || i.getType().equals(Material.IRON_LEGGINGS) || i.getType().equals(Material.IRON_BOOTS)) {
                                        if (!p.isInsideVehicle()) {
                                            p.addPotionEffect(PotionEffectType.GLOWING.createEffect(1200, 0));
                                            p.sendMessage(ChatColor.RED + "You were caught with contraband!");
                                            if (PrisonGame.prisonerlevel.getOrDefault(p, 0) == 1) {
                                                p.addPotionEffect(PotionEffectType.CONFUSION.createEffect(20 * 6, 0));
                                                p.addPotionEffect(PotionEffectType.SLOW.createEffect(20 * 6, 0));
                                                p.addPotionEffect(PotionEffectType.DARKNESS.createEffect(20 * 6, 0));
                                            }
                                            for (Player g : Bukkit.getOnlinePlayers()) {
                                                if (PrisonGame.roles.get(g) != Role.PRISONER) {
                                                    g.playSound(g, Sound.ENTITY_SILVERFISH_DEATH, 1, 0.5f);
                                                    g.sendMessage(ChatColor.RED + p.getName() + ChatColor.DARK_RED + " was caught with contraband!");
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (PrisonGame.roles.get(p) == Role.NURSE) {
                if (!p.getInventory().contains(Material.SPLASH_POTION)) {
                    ItemStack pot = new ItemStack(Material.SPLASH_POTION);
                    PotionMeta potionMeta = (PotionMeta) pot.getItemMeta();
                    potionMeta.addCustomEffect(PotionEffectType.HEAL.createEffect(10, 0), true);
                    pot.setItemMeta(potionMeta);

                    p.getInventory().addItem(pot);
                    p.setCooldown(Material.SPLASH_POTION, 40);
                }
            }
        }
        DecimalFormat numberFormat = new DecimalFormat("#0.0");
        DecimalFormat numberFormat2 = new DecimalFormat("#0.000");
        DecimalFormat whole = new DecimalFormat("#");
        if (PrisonGame.warden == null) {
            String wardentime = ChatColor.RED + "None! Use '/warden' to become the prison warden!";

            if (PrisonGame.wardenCooldown > 0) {
                wardentime = ChatColor.RED + "None! Warden command is on cooldown! " + net.md_5.bungee.api.ChatColor.GRAY + "[" + Math.round((float) PrisonGame.wardenCooldown / 20f) + "]";
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.getDisplayName().contains("ASCENDING")) {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + numberFormat.format(p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)) + "$" + ChatColor.GRAY + " || " + ChatColor.GRAY + "Current Warden: " + wardentime));
                } else {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent((ChatColor.AQUA + whole.format(p.getPersistentDataContainer().getOrDefault(PrisonGame.ascendcoins, PersistentDataType.DOUBLE, 0.0)) + " ascension coins.")));
                }
            }
        } else {
            if (!PrisonGame.warden.isOnline()) {
                PrisonGame.warden = null;
            }
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (!p.getDisplayName().contains("ASCENDING")) {
                    if (p != PrisonGame.warden) {
                        Role role = PrisonGame.roles.get(p);
                        String acbar = role.color + role.name();
                        if (p.getDisplayName().contains("SOLITARY"))
                            acbar = ChatColor.DARK_GRAY + "SOLITARY [ " + numberFormat.format(PrisonGame.solittime.get(p) / 20) + " seconds left. ]";
                        String mode = "Normal Mode (/hard)";
                        if (PrisonGame.hardmode.get(p)) {
                            mode = ChatColor.RED + "HARD MODE (/normal)";
                        }
                        String finisher = ")";
                        if (PrisonGame.warden.getNoDamageTicks() > 0) {
                            finisher = ChatColor.AQUA + " [" + PrisonGame.warden.getNoDamageTicks() / 20 + "s of No-Damage]" + ChatColor.RED + ")";
                        }
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + numberFormat.format(p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)) + "$" + ChatColor.GRAY + " || " + acbar + ChatColor.GRAY + " || Current Warden: " + ChatColor.DARK_RED + PrisonGame.warden.getName() + ChatColor.RED + " (" + Math.round(PrisonGame.warden.getHealth()) + " HP" + finisher));
                    } else {
                        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GREEN + numberFormat.format(p.getPersistentDataContainer().getOrDefault(PrisonGame.mny, PersistentDataType.DOUBLE, 0.0)) + "$" + ChatColor.GRAY + " || " + ChatColor.GRAY + "Current Warden: " + ChatColor.GREEN + "You! Use '/warden help' to see warden commands!"));
                    }
                }
                if (p.getDisplayName().contains("ASCENDING")) {
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent((ChatColor.AQUA + whole.format(p.getPersistentDataContainer().getOrDefault(PrisonGame.ascendcoins, PersistentDataType.DOUBLE, 0.0)) + " ascension coins.")));
                }
            }
        }
    }
}