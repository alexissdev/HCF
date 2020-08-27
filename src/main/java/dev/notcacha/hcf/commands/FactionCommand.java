package dev.notcacha.hcf.commands;

import com.google.inject.Inject;
import dev.notcacha.core.cache.CacheProvider;
import dev.notcacha.hcf.HCF;
import dev.notcacha.hcf.faction.Faction;
import dev.notcacha.hcf.faction.SimpleFaction;
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
        return true;
    }

    @ACommand(names = {"disband", "d"}, permission = "hcf.faction.disband")
    public boolean disbandCommand(@Injected(true) @Sender Player player) {
        String language = languageUtils.getLanguage(player);

        Optional<User> user = userCache.find(player.getUniqueId());
        if (user.isPresent()) {
            Optional<String> factionName = user.get().getFaction().getFactionName();
            if (!factionName.isPresent()) {
                languageLib.getTranslationManager().getTranslation("no-contains-faction").ifPresent(message -> {
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
                        languageLib.getTranslationManager().getTranslation("is-not-leader").ifPresent(message -> {
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
                factionCache.remove(factionName.get());
            }

        }
        return true;
    }

}
