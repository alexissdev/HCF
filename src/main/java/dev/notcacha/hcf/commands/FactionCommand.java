package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.api.events.faction.FactionCreateEvent;
import dev.notcacha.hcf.api.events.faction.FactionDisbandEvent;
import dev.notcacha.hcf.api.events.faction.FactionRenameEvent;
import dev.notcacha.hcf.api.events.faction.UserLeftFactionEvent;
import dev.notcacha.hcf.ebcm.parameter.provider.annotation.Language;
import dev.notcacha.hcf.faction.Faction;
import dev.notcacha.hcf.faction.SimpleFaction;
import dev.notcacha.hcf.faction.role.Role;
import dev.notcacha.hcf.user.User;
import dev.notcacha.hcf.utils.FactionUtils;
import dev.notcacha.hcf.utils.LanguageUtils;
import dev.notcacha.hcf.utils.item.ItemBuilder;
import dev.notcacha.hcf.utils.item.LoreBuilder;
import dev.notcacha.languagelib.LanguageLib;
import dev.notcacha.languagelib.message.TranslatableMessage;
import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import me.fixeddev.ebcm.parametric.annotation.Usage;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@ACommand(names = {"faction", "f"})
public class FactionCommand implements CommandClass {

    @Inject
    private LanguageLib languageLib;

    @Inject
    private LanguageUtils languageUtils;

    @Inject
    private CacheProvider<UUID, User> userCache;

    @Inject
    private HCF plugin;

    @Inject
    private CacheProvider<String, Faction> factionCache;

    @ACommand(names = "", permission = "hcf.faction")
    public boolean mainCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language) {
        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.usage.1");
        message.colorize();

        message.getMessages(language).forEach(player::sendMessage);
        return true;
    }

    @ACommand(names = {"help", "h"}, permission = "hcf.faction.help")
    @Usage(usage = "§cCorrect usage /faction help <page>")
    public boolean helpCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, Integer page) {
        if (page > 2) {
            page = 1;
        }

        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.usage." + page);
        message.colorize();

        message.getMessages(language).forEach(player::sendMessage);
        return true;
    }

    @ACommand(names = {"create", "add", "c"}, permission = "hcf.faction.create")
    @Usage(usage = "§cCorrect usage is /faction create <name from faction>")
    public boolean createCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, String factionName) {
        if (factionCache.exists(factionName) || FactionUtils.isNotPermittedFactionName(factionName)) {
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.error.exists");
            message.setVariable("%faction_name%", factionName).colorize();

            player.sendMessage(message.getMessage(language));
            return true;
        }

        factionCache.add(factionName, new SimpleFaction(factionName, player.getName()));

        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.create");
        message.setVariable("%faction_name%", factionName).colorize();

        plugin.getServer().getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.sendMessage(message.getMessage(languageUtils.getLanguage(onlinePlayer))));

        plugin.getServer().getPluginManager().callEvent(new FactionCreateEvent(player, factionName));
        return true;
    }

    @ACommand(names = {"disband", "d"}, permission = "hcf.faction.disband")
    public boolean disbandCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<String> factionName = user.get().getFaction().getFactionName();
            if (!factionName.isPresent()) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.no-contains-faction");

                player.sendMessage(message.colorize().getMessage(language));
                return true;
            }

            Optional<Faction> faction = factionCache.find(factionName.get());
            if (faction.isPresent()) {
                Optional<String> leader = faction.get().getLeader();
                if (leader.isPresent()) {
                    if (!leader.get().equals(player.getName())) {
                        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.is-not-leader");
                        message.setVariable("%faction_name%", factionName.get()).colorize();

                        player.sendMessage(message.getMessage(language));
                        return true;
                    }
                }

                faction.get().getMembers().ifPresent(member ->
                        member.forEach(memberName -> {
                            OfflinePlayer memberPlayer = plugin.getServer().getOfflinePlayer(memberName);

                            userCache.find(memberPlayer.getUniqueId()).ifPresent(memberUser -> memberUser.getFaction().setFactionName(null));
                        }));

                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.disband");

                message.setVariable("%faction_name%", factionName.get()).colorize();

                player.sendMessage(message.getMessage(language));

                plugin.getServer().getPluginManager().callEvent(new FactionDisbandEvent(player, factionName.get()));
                factionCache.remove(factionName.get());
            }

        }
        return true;
    }

    @ACommand(names = "invite", permission = "hcf.faction.invite")
    @Usage(usage = "§cCorrect usage is /faction invite <player has been invited from your faction>")
    public boolean inviteCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, OfflinePlayer target) {

        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<String> userFaction = user.get().getFaction().getFactionName();
            if (!userFaction.isPresent()) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.no-contains-faction");

                player.sendMessage(message.colorize().getMessage(language));
                return true;
            }
            Role userFactionRole = user.get().getFaction().getRole();
            if (userFactionRole == Role.MEMBER) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.is-not-leader");
                message.setVariable("%faction_name%", userFaction.get()).colorize();

                player.sendMessage(message.getMessage(language));
                return true;
            }

            Optional<User> targetUser = userCache.find(target.getUniqueId());
            if (!targetUser.isPresent()) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("general.target-offline");
                message.setVariable("%target_name%", target.getName()).colorize();

                player.sendMessage(message.getMessage(language));
                return true;
            }
            targetUser.get().getFaction().getInvites().add(userFaction.get());
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.invite.player");
            message.setVariable("%target_name%", target.getName()).colorize();

            player.sendMessage(message.getMessage(language));

            if (target.getPlayer() != null) {
                TranslatableMessage targetMessage = languageLib.getTranslationManager().getTranslation("faction.invite.target");
                targetMessage.setVariable("%player_name%", player.getName())
                        .setVariable("%faction_name%", userFaction.get())
                        .colorize();

                target.getPlayer().sendMessage(targetMessage.getMessage(languageUtils.getLanguage(target.getPlayer())));
            }
        }
        return true;
    }

    @ACommand(names = "leave", permission = "hcf.faction.leave")
    public boolean leaveCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<String> userFaction = user.get().getFaction().getFactionName();
            if (!userFaction.isPresent()) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.no-contains-faction");

                player.sendMessage(message.getMessage(language));
                return true;
            }
            Role userFactionRole = user.get().getFaction().getRole();
            if (userFactionRole == Role.LEADER) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.leave.leader-leave-error");

                player.sendMessage(message.getMessage(language));
                return true;
            }
            TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.leave.player");
            message.setVariable("%faction_name%", userFaction.get()).colorize();

            player.sendMessage(message.getMessage(language));

            Optional<Faction> faction = factionCache.find(userFaction.get());
            if (faction.isPresent()) {
                faction.get().getMembers().ifPresent(members -> {
                    members.forEach(member -> {
                        OfflinePlayer memberPlayer = plugin.getServer().getOfflinePlayer(member);
                        if (memberPlayer.getPlayer() != null) {
                            TranslatableMessage memberMessage = languageLib.getTranslationManager().getTranslation("faction.leave.faction");
                            memberMessage.setVariable("%player_name%", player.getName()).colorize();

                            memberPlayer.getPlayer().sendMessage(memberMessage.getMessage(languageUtils.getLanguage(memberPlayer.getPlayer())));
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
    public boolean renameCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language, String name) {
        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<String> userFaction = user.get().getFaction().getFactionName();
            if (!userFaction.isPresent()) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.no-contains-faction");

                player.sendMessage(message.getMessage(language));
                return true;
            }
            if (factionCache.exists(name) || FactionUtils.isNotPermittedFactionName(name)) {
                TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.error.exists");
                message.setVariable("%faction_name%", name).colorize();

                player.sendMessage(message.getMessage(language));
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
                plugin.getServer().getPluginManager().callEvent(new FactionRenameEvent(faction.get(), player.getName()));
            }

        }
        return true;
    }


    @ACommand(names = "claim", permission = "hcf.faction.claim")
    public boolean claimCommand(@Injected(true) @Sender Player player, @Injected(true) @Language String language) {
        Configuration languageFile = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "language_en.yml"));

        ItemStack item = new ItemBuilder(Material.GOLD_SPADE)
                .setName(languageFile.getString("faction.claim.item.name", "&6Claim Item"), true)
                .setLore(new LoreBuilder(languageFile.getStringList("faction.claim.item.lore")).setColor().build())
                .build();
        player.getInventory().addItem(item);
        TranslatableMessage message = languageLib.getTranslationManager().getTranslation("faction.claim.get");

        player.sendMessage(message.getMessage(language));
        return true;
    }

}
