package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.api.events.faction.FactionCreateEvent;
import dev.notcacha.hcf.api.events.faction.FactionDisbandEvent;
import dev.notcacha.hcf.api.events.faction.UserLeftFactionEvent;
import dev.notcacha.hcf.faction.Faction;
import dev.notcacha.hcf.faction.SimpleFaction;
import dev.notcacha.hcf.faction.role.Role;
import dev.notcacha.hcf.guice.anotations.cache.FactionCache;
import dev.notcacha.hcf.guice.anotations.cache.UserCache;
import dev.notcacha.hcf.user.User;
import dev.notcacha.hcf.utils.FactionUtils;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.languagelib.LanguageLib;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@ACommand(names = {"faction", "f"})
public class FactionCommand implements CommandClass {

    @Inject
    private LanguageLib<Configuration> languageLib;

    @Inject
    private LanguageUtils languageUtils;

    @Inject
    @UserCache
    private CacheProvider<UUID, User> userCache;

    @Inject
    private HCF plugin;

    @Inject
    @FactionCache
    private CacheProvider<String, Faction> factionCache;

    @ACommand(names = "", permission = "hcf.faction")
    public boolean mainCommand(@Injected(true) @Sender Player player) {
        String language = languageUtils.getLanguage(player);

        languageLib.getTranslationManager().getTranslation("faction.usage.1").ifPresent(message -> {
            message.setColor(true);

            message.getMessages(language).forEach(player::sendMessage);
        });
        return true;
    }

    @ACommand(names = {"help", "h"}, permission = "hcf.faction.help")
    @Usage(usage = "§cCorrect usage /faction help <page>")
    public boolean helpCommand(@Injected(true) @Sender Player player, Integer page) {
        String language = languageUtils.getLanguage(player);

        if (page > 2) {
            languageLib.getTranslationManager().getTranslation("faction.usage.1").ifPresent(message -> {
                message.setColor(true);

                message.getMessages(language).forEach(player::sendMessage);
            });
            return true;
        }

        languageLib.getTranslationManager().getTranslation("faction.usage." + page).ifPresent(message -> {
            message.setColor(true);

            message.getMessages(language).forEach(player::sendMessage);
        });
        return true;
    }

    @ACommand(names = {"create", "add", "c"}, permission = "hcf.faction.create")
    @Usage(usage = "§cCorrect usage is /faction create <name from faction>")
    public boolean createCommand(@Injected(true) @Sender Player player, String factionName) {
        String language = languageUtils.getLanguage(player);

        if (factionCache.exists(factionName) || FactionUtils.isNotPermittedFactionName(factionName)) {
            languageLib.getTranslationManager().getTranslation("faction.error.exists").ifPresent(message -> {
                message.setVariable("%faction_name%", factionName).setColor(true);

                player.sendMessage(message.getMessage(language));
            });
            return true;
        }

        factionCache.add(factionName, new SimpleFaction(factionName, player.getName()));
        languageLib.getTranslationManager().getTranslation("faction.create").ifPresent(message -> {
            message.setVariable("%faction_name%", factionName).setColor(true);

            plugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(message.getMessage(languageUtils.getLanguage(onlinePlayer))));
        });
        plugin.getServer().getPluginManager().callEvent(new FactionCreateEvent(player, factionName));
        return true;
    }

    @ACommand(names = {"disband", "d"}, permission = "hcf.faction.disband")
    public boolean disbandCommand(@Injected(true) @Sender Player player) {
        String language = languageUtils.getLanguage(player);

        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<String> factionName = user.get().getFaction().getFactionName();
            if (!factionName.isPresent()) {
                languageLib.getTranslationManager().getTranslation("faction.no-contains-faction").ifPresent(message -> {
                    message.setColor(true);

                    player.sendMessage(message.getMessage(language));
                });
                return true;
            }

            Optional<Faction> faction = factionCache.find(factionName.get());
            if (faction.isPresent()) {
                Optional<String> leader = faction.get().getLeader();
                if (leader.isPresent()) {
                    if (!leader.get().equals(player.getName())) {
                        languageLib.getTranslationManager().getTranslation("faction.is-not-leader").ifPresent(message -> {
                            message.setVariable("%faction_name%", factionName.get()).setColor(true);

                            player.sendMessage(message.getMessage(language));
                        });
                        return true;
                    }
                }

                faction.get().getMembers().ifPresent(member ->
                        member.forEach(memberName -> {
                            OfflinePlayer memberPlayer = plugin.getServer().getOfflinePlayer(memberName);

                            userCache.find(memberPlayer.getUniqueId()).ifPresent(memberUser -> memberUser.getFaction().setFactionName(null));
                        }));

                languageLib.getTranslationManager().getTranslation("faction.disband").ifPresent(message -> {
                    message.setVariable("%faction_name%", factionName.get()).setColor(true);

                    player.sendMessage(message.getMessage(language));
                });
                plugin.getServer().getPluginManager().callEvent(new FactionDisbandEvent(player, factionName.get()));
                factionCache.remove(factionName.get());
            }

        }
        return true;
    }

    @ACommand(names = "invite", permission = "hcf.faction.invite")
    @Usage(usage = "§cCorrect usage is /faction invite <player has been invited from your faction>")
    public boolean inviteCommand(@Injected(true) @Sender Player player, OfflinePlayer target) {
        String language = languageUtils.getLanguage(player);

        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<String> userFaction = user.get().getFaction().getFactionName();
            if (!userFaction.isPresent()) {
                languageLib.getTranslationManager().getTranslation("faction.no-contains-faction").ifPresent(message -> {
                    message.setColor(true);

                    player.sendMessage(message.getMessage(language));
                });
                return true;
            }
            Role userFactionRole = user.get().getFaction().getRole();
            if (userFactionRole == Role.MEMBER) {
                languageLib.getTranslationManager().getTranslation("faction.is-not-leader").ifPresent(message -> {
                    message.setVariable("%faction_name%", userFaction.get()).setColor(true);

                    player.sendMessage(message.getMessage(language));
                });
                return true;
            }

            Optional<User> targetUser = userCache.find(target.getUniqueId());
            if (!targetUser.isPresent()) {
                languageLib.getTranslationManager().getTranslation("general.target-offline").ifPresent(message -> {
                    message.setVariable("%target_name%", target.getName()).setColor(true);

                    player.sendMessage(message.getMessage(language));
                });
                return true;
            }
            targetUser.get().getFaction().getInvites().add(userFaction.get());
            languageLib.getTranslationManager().getTranslation("faction.invite.player").ifPresent(message -> {
                message.setVariable("%target_name%", target.getName()).setColor(true);

                player.sendMessage(message.getMessage(language));
            });
            if (target.getPlayer() != null) {
                languageLib.getTranslationManager().getTranslation("faction.invite.target").ifPresent(message -> {
                    message.setVariable("%player_name%", player.getName())
                            .setVariable("%faction_name%", userFaction.get())
                            .setColor(true);

                    target.getPlayer().sendMessage(message.getMessage(languageUtils.getLanguage(target.getPlayer())));
                });
            }
        }
        return true;
    }

    @ACommand(names = "leave", permission = "hcf.faction.leave")
    public boolean leaveCommand(@Injected(true) @Sender Player player) {
        String language = languageUtils.getLanguage(player);

        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<String> userFaction = user.get().getFaction().getFactionName();
            if (!userFaction.isPresent()) {
                languageLib.getTranslationManager().getTranslation("faction.no-contains-faction").ifPresent(message -> {
                    message.setColor(true);

                    player.sendMessage(message.getMessage(language));
                });
                return true;
            }
            Role userFactionRole = user.get().getFaction().getRole();
            if (userFactionRole == Role.LEADER) {
                languageLib.getTranslationManager().getTranslation("faction.leave.leader-leave-error").ifPresent(message -> {
                    message.setColor(true);

                    player.sendMessage(message.getMessage(language));
                });
                return true;
            }
            languageLib.getTranslationManager().getTranslation("faction.leave.player").ifPresent(message -> {
                message.setVariable("%faction_name%", userFaction.get()).setColor(true);

                player.sendMessage(message.getMessage(language));
            });
            Optional<Faction> faction = factionCache.find(userFaction.get());
            if (faction.isPresent()) {
                faction.get().getMembers().ifPresent(members -> {
                    members.forEach(member -> {
                        OfflinePlayer memberPlayer = plugin.getServer().getOfflinePlayer(member);
                        if (memberPlayer.getPlayer() != null) {
                            languageLib.getTranslationManager().getTranslation("faction.leave.faction").ifPresent(message -> {
                                message.setVariable("%player_name%", player.getName()).setColor(true);

                                memberPlayer.getPlayer().sendMessage(message.getMessage(languageUtils.getLanguage(memberPlayer.getPlayer())));
                            });
                        }
                    });
                });

                plugin.getServer().getPluginManager().callEvent(new UserLeftFactionEvent(player, faction.get()));
            }
            user.get().getFaction().setFactionName(null);
            user.get().getFaction().setRole(null);
        }
        return true;
    }

    @ACommand(names = "rename", permission = "hcf.faction.rename")
    @Usage(usage = "§cCorrect usage is /faction rename <name has been set from faction>")
    public boolean renameCommand(@Injected(true) @Sender Player player, String name) {
        String language = languageUtils.getLanguage(player);

        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<String> userFaction = user.get().getFaction().getFactionName();
            if (!userFaction.isPresent()) {
                languageLib.getTranslationManager().getTranslation("faction.no-contains-faction").ifPresent(message -> {
                    message.setColor(true);

                    player.sendMessage(message.getMessage(language));
                });
                return true;
            }
            if (factionCache.exists(name) || FactionUtils.isNotPermittedFactionName(name)) {
                languageLib.getTranslationManager().getTranslation("faction.error.exists").ifPresent(message -> {
                    message.setVariable("%faction_name%", name).setColor(true);

                    player.sendMessage(message.getMessage(language));
                });
                return true;
            }
            Optional<Faction> faction = factionCache.find(userFaction.get());
            if (faction.isPresent()) {
                faction.get().setName(name);

                faction.get().getMembers().ifPresent(members -> {
                    members.forEach(member -> {
                        OfflinePlayer memberPlayer = plugin.getServer().getOfflinePlayer(member);

                        userCache.find(memberPlayer.getUniqueId()).ifPresent(memberUser -> memberUser.getFaction().setFactionName(name));
                    });
                });
            }

        }
        return true;
    }

}
