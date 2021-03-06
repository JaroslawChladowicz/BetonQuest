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
package pl.betoncraft.betonquest.conditions;

import pl.betoncraft.betonquest.core.Condition;
import pl.betoncraft.betonquest.inout.PlayerConverter;

/**
 * 
 * @author Co0sh
 */
public class PermissionCondition extends Condition {
	
	private String permission;

	/**
	 * Constructor method
	 * @param playerID
	 * @param instructions
	 */
	public PermissionCondition(String playerID, String instructions) {
		super(playerID, instructions);
		String[] parts = instructions.split(" ");
		for (String part : parts) {
			if (part.contains("perm:")) {
				permission = part.substring(5);
				break;
			}
		}
	}

	@Override
	public boolean isMet() {
		if (PlayerConverter.getPlayer(playerID).hasPermission(permission)) {
			return true;
		}
		return false;
	}

}
