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
package pl.betoncraft.betonquest.core;

import pl.betoncraft.betonquest.BetonQuest;
import pl.betoncraft.betonquest.inout.ObjectiveSaving;


/**
 * This class represents an objective. You must extend it and register using registerObjectives() method from this plugin instance.
 * @author Co0sh
 */
public abstract class Objective {
	
	protected String playerID;
	protected String instructions;
	private ObjectiveSaving listener;
	protected String conditions;
	protected String events;
	protected String tag;
	
	public Objective(String playerID, String instructions) {
		this.playerID = playerID;
		this.instructions = instructions;
		for (String part : instructions.split(" ")) {
			if (part.contains("tag:")) {
				tag = part.substring(4);
			}
			if (part.contains("events:")) {
				events = part;
			}
			if (part.contains("conditions:")) {
				conditions = part;
			}
		}
		listener = new ObjectiveSaving(playerID, this);
	}
	
/**
 * Use this method to when your objective is completed. Unregister all Listeners before!
 */
	protected void completeObjective() {
		// split instructions
		String[] parts = instructions.split(" ");
		String rawEvents = null;
		// find part with events
		for (String part : parts) {
			if (part.contains("events:")) {
				// extrapolate events
				rawEvents = part.substring(7);
				break;
			}
		}
		// if there are any events, do something with them
		if (rawEvents != null && !rawEvents.equalsIgnoreCase("")) {
			// split them to separate ids
			String[] events = rawEvents.split(",");
			// fire all events
			for (String eventID : events) {
				BetonQuest.event(playerID, eventID);
			}
		}
		listener.deleteThis();
	}
	
/**
 * Use this method to check if all conditions have been met before accepting objective completion.
 */
	protected boolean checkConditions() {
		// split instructions
		String[] parts = instructions.split(" ");
		String rawConditions = null;
		// find part with conditions
		for (String part : parts) {
			if (part.contains("conditions:")) {
				// extrapolate conditions
				rawConditions = part.substring(11);
				break;
			}
		}
		// if there are any conditions, do something with them
		if (rawConditions != null && !rawConditions.equalsIgnoreCase("")) {
			// split them to separate ids
			String[] conditions = rawConditions.split(",");
			// if some condition is not met, return false
			for (String conditionID : conditions) {
				if (!BetonQuest.condition(playerID, conditionID)) {
					return false;
				}
			}
		}
		// if there are no conditions or all of them are met return true
		return true;
	}
	
	/**
	 * This method has to return instructions string for current state of objective and end this object's work (eg. unregister all Listeners etc.)
	 * @return
	 */
	abstract public String getInstructions();

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

}