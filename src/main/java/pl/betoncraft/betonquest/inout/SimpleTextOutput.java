/**
 * BetonQuest - advanced quests for Bukkit
 * Copyright (C) 2015  Jakub "Co0sh" Sapalski
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pl.betoncraft.betonquest.inout;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;


/**
 * 
 * @author Co0sh
 */
public class SimpleTextOutput {
	
	/**
	 * Sends player a message that is supposed to look like a plugin notification 
	 * @param playerID
	 * @param message
	 */
	public static void sendSystemMessage(String playerID, String message, String soundName) {
		Player player = PlayerConverter.getPlayer(playerID);
		String finalString = (ConfigInput.getString("messages.global.plugin_prefix") + message).replaceAll("&", "§");
		player.sendMessage(finalString);
		if (!soundName.equalsIgnoreCase("false")) {
			player.playSound(player.getLocation(), Sound.valueOf(soundName), 1F, 1F);
		}
	}
	
	/**
	 * Sends player a message that looks like quester said it. All "%player%" in message are replaced with player's name, all "%quester%" are replaced by quester's name
	 * @param playerID
	 * @param quester
	 * @param message
	 */
	public static void sendQuesterMessage(String playerID, String quester, String message) {
		String finalString = (ConfigInput.getString("messages.global.quester_line_format") + message).replaceAll("%player%", PlayerConverter.getPlayer(playerID).getName()).replaceAll("%quester%", quester).replaceAll("&", "§");
		PlayerConverter.getPlayer(playerID).sendMessage(finalString);
	}
	
	/**
	 * Sends player a message that looks like an option to reply to npc
	 * @param playerID
	 * @param number
	 * @param quester
	 * @param message
	 */
	public static void sendQuesterReply(String playerID, int number, String quester, String message) {
		String finalString = (ConfigInput.getString("messages.global.quester_reply_format") + message).replaceAll("%quester%", quester).replaceAll("%number%", String.valueOf(number)).replaceAll("%player%", PlayerConverter.getPlayer(playerID).getName()).replaceAll("&", "§");
		if (ConfigInput.getString("config.tellraw").equalsIgnoreCase("true")) {
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + PlayerConverter.getPlayer(playerID).getName() + " {\"text\":\"\",\"extra\":[{\"text\":\"" + finalString + "\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"" + number + "\"}}]}");
		} else {
			PlayerConverter.getPlayer(playerID).sendMessage(finalString);
		}
	}
	
	/**
	 * Sends player a message that looks like his answer to npc
	 * @param playerID
	 * @param quester
	 * @param message
	 */
	public static void sendPlayerReply(String playerID, String quester, String message) {
		String finalString = (ConfigInput.getString("messages.global.player_reply_format") + message).replaceAll("%player%", PlayerConverter.getPlayer(playerID).getName()).replaceAll("%quester%", quester).replaceAll("&", "§");
		PlayerConverter.getPlayer(playerID).sendMessage(finalString);
	}
}
