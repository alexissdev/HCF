package dev.notcacha.hcf.commands;

import me.fixeddev.ebcm.bukkit.parameter.provider.annotation.Sender;
import me.fixeddev.ebcm.parametric.CommandClass;
import me.fixeddev.ebcm.parametric.annotation.ACommand;
import me.fixeddev.ebcm.parametric.annotation.Injected;
import org.bukkit.entity.Player;

@ACommand(names = {"faction", "f"})
public class FactionCommand implements CommandClass {

    @ACommand(names = "", permission = "hcf.faction")
    public boolean mainCommand(@Injected(true) @Sender Player player) {
        return true;
    }

}
