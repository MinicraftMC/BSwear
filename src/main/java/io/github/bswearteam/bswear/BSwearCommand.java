package io.github.bswearteam.bswear;

import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BSwearCommand implements CommandExecutor {

    public BSwear m;
    public BSwearCommand(BSwear b) {
        this.m = b;
    }
    
    /**
     * The /bswear command for BSwear
     * 
     * @author BSwear Team
     * @since v2.0
     */
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("bswear")) {
            if (sender.hasPermission(m.COMMAND_PERM) || sender.isOp() || sender.hasPermission(m.allPerm) || sender.hasPermission("bukkit.antiswear")) {
                if (args.length == 0) {
                    sendMessage(sender, m.prefix);
                    sendMessage(sender, ChatColor.AQUA + "BSwear v" + m.version);
                    sendMessage(sender, ChatColor.AQUA + "Cmd Help: /bswear help");
                } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {

                    sendMessage(sender, m.prefix + " Command Usage: /bswear <OPTION>");
                    sendMessage(sender, m.prefix + " Options:");
                    m.showCommandUsage(sender);

                } else if (args.length == 2 && args[0].equalsIgnoreCase("add")) {

                    List<String> words = m.getSwearConfig().getStringList("warnList");
                    String word = args[1].toLowerCase();
                    if (!words.contains(word)) {
                        words.add(word);
                        m.getSwearConfig().set("warnList", words);
                        m.saveSwearConfig();
                        sender.sendMessage(m.prefix + m.getConfig().getString("messages.addword"));
                    } else {
                        sender.sendMessage(m.prefix + ChatColor.RED + ChatColor.BOLD + "Error! This word is already blocked!");
                    }

                } else if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {

                    List<String> words = m.getSwearConfig().getStringList("warnList");
                    String word = args[1].toLowerCase();
                    if (words.contains(word)) {
                        words.remove(word);
                        m.getSwearConfig().set("warnList", words);
                        m.saveSwearConfig();
                        sender.sendMessage(m.prefix + m.getConfig().getString("messages.delword"));
                    } else {
                        sender.sendMessage(m.prefix + ChatColor.RED + ChatColor.BOLD + "Error! This word is not blocked!");
                    }

                } else if (args.length == 1 && args[0].equalsIgnoreCase("version")) {

                    sender.sendMessage(ChatColor.GOLD + "Version:" + ChatColor.GREEN + "BSwear v" + m.version);

                } else if (args.length == 3 && args[0].equalsIgnoreCase("mute")) {

                    ((Player) sender).performCommand("mute "+args[1]+" "+args[2]);

                } else if (args.length == 1 && args[0].equalsIgnoreCase("wordlist")) {

                    List<String> words = m.getSwearConfig().getStringList("warnList");
                    String message = "Blocked Words: ";
                    for (String w : words) {
                        message = message + w;
                        
                        if (!(w == words.get(words.size() - 1))) {
                            message = message + ", ";
                        }
                    }
                    sender.sendMessage(message);

                } else if (args.length == 1 && args[0].equalsIgnoreCase("swearers")) {

                    Set<String> keys = m.swearers.getConfigurationSection("swearers").getKeys(false);
                    sender.sendMessage(ChatColor.BLUE + "Swearers:");
                    String swearerList = "";
                    for (String s : keys) {
                        swearerList = swearerList + s + ", ";
                    }
                    sender.sendMessage(swearerList);

                } else if (args.length == 1 && args[0].equalsIgnoreCase("clear")) {
                    List<String> words = m.getSwearConfig().getStringList("warnList");
                    for (String word : words) {
                        words.remove(word);
                        m.getSwearConfig().set("warnList", words);
                        m.saveSwearConfig();
                        sender.sendMessage(m.prefix + "All blocked words have been unblocked!");
                    }

                } else if (args.length == 2 && args[0].equalsIgnoreCase("prefix")) {

                    m.getConfig().set("messages.prefix", args[1]);
                    m.saveConfig();
                    m.prefix = ChatColor.translateAlternateColorCodes('&', args[1] + " ");

                } else if (args.length > 0 && args[0].equalsIgnoreCase("useTitles")) {

                    if (args.length == 2) {
                        if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("y") || args[1].equalsIgnoreCase("enable")) {
                            m.getConfig().set("sendTitle", true);
                            sendMessage(sender, m.prefix + "Title on swear is now ENABLED");
                        } else {
                            m.getConfig().set("sendTitle", false);
                            sendMessage(sender, m.prefix + "Title on swear is now DISABLED");
                        }
                        m.saveConfig();
                    } else {
                        if (m.getConfig().getBoolean("sendTitle")) {
                            sendMessage(sender, m.prefix + "Title on swear is ENABLED");
                        } else {
                            sendMessage(sender, m.prefix + "Title on swear is DISABLED");
                        }
                    }

                } else {
                    sender.sendMessage(m.prefix + "Error! please check your args OR do \"/bswear help\" for command help");
                }

            } else {
                sender.sendMessage(m.prefix + "BSwear v" + m.version);
                sender.sendMessage(m.prefix + m.getConfig().getString("messages.noperm"));
            }
            return false;
        }
        return false;
    }

    public void sendMessage(CommandSender s, String msg) {
        m.sendMessage(s,msg);
    }
}
