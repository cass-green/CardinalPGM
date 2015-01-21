package in.twizmwaz.cardinal.chat;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandException;
import in.twizmwaz.cardinal.module.modules.team.TeamModule;
import in.twizmwaz.cardinal.util.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdminChat {

    public static void sendAdminMessage(String msg, Player sender){
        for (Player player : Bukkit.getOnlinePlayers()) {
            TeamModule team = TeamUtils.getTeamByPlayer(sender); //Gets the team of the online player
            if (player.isOp()) {
                player.sendMessage(ChatColor.WHITE + "[" + ChatColor.GOLD + "A" + ChatColor.WHITE + "] " + team.getColor() + sender.getDisplayName() + ChatColor.WHITE + ": " + ChatColor.WHITE + msg);
            }
        }
    }

    @Command(aliases = {"admin", "a"}, desc = "Send a message to all ops")
    public static void admin(final CommandContext cmd, CommandSender sender) throws CommandException {
        Player player = (Player) sender;
        if (player.isOp()) {
            if (cmd.argsLength() == 0) {
                //Implement this later once toggle permissions are introduced
            } else {
                String msg = "";
                for (int i = 0; i < cmd.argsLength(); i++) {
                    msg += cmd.getString(i) + " ";
                }
                msg = msg.trim();
                sendAdminMessage(msg, player);
            }
        }
    }
}