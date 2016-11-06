package org.devathon.contest2016.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.ai.AnnaStand;

public class MaskinrListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();

		if(e.getMessage().startsWith("!bot ")) {
			String match = e.getMessage().replaceFirst("!bot ", "");
			match = match.replaceAll("[^\\w\\s]", "");

			Bukkit.broadcastMessage("§a§l" + p.getName() + " §rasked §6§lAnna: §r\"" + e.getMessage().replaceFirst("!bot ", "") + "\"");
			Bukkit.broadcastMessage("§6§lAnna: §r\"" + DevathonPlugin.getLearner().getAI().getMatch(match.split(" ")) + "\"");

			e.setCancelled(true);
			return;
		} else if (e.getMessage().startsWith("!create")) {
			Bukkit.getScheduler().runTask(DevathonPlugin.getInstance(), new Runnable() {

				@Override
				public void run() {
					if(DevathonPlugin.getLearner().annaStand != null)
						DevathonPlugin.getLearner().annaStand.remove();
					
					DevathonPlugin.getLearner().annaStand = AnnaStand.createAnnaStand(p.getLocation());
				}

			});

			e.setCancelled(true);
			return;
		}

		DevathonPlugin.getLearner().getAI().sentences.add(e.getMessage());
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if(DevathonPlugin.getLearner().annaStand == null) {
			p.sendMessage(ChatColor.RED + "Hey! You should spawn a Anna. Do it by typing \"!create\" in chat.");
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		if(DevathonPlugin.getLearner().annaStand != null) {
			DevathonPlugin.getLearner().annaStand.updateFaceDirection(p.getLocation());
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e) {
		Player p = e.getPlayer();
		if(e.getRightClicked() instanceof ArmorStand) {
			p.sendMessage(ChatColor.GOLD + "Hello! My name is Anna. I am a computing machine, and definetly your only possible future girlfriend.");
			p.sendMessage(ChatColor.GOLD + "You can ask me any question, and I'll try to answer it!");
			p.sendMessage(ChatColor.GRAY + "Type a message into the chat starting with \"!bot\" to ask me stuff..");
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void ArmorStandDestroy(EntityDamageByEntityEvent e){
		if (!(e.getEntity() instanceof LivingEntity)) {
			return;
		}

		final LivingEntity livingEntity = (LivingEntity)e.getEntity();
		if(!livingEntity.getType().equals(EntityType.ARMOR_STAND)){
			return;
		}

		if(e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			p.sendMessage(ChatColor.GOLD + "Hello! My name is Anna. I am a computing machine, and definetly your only possible future girlfriend.");
			p.sendMessage(ChatColor.GOLD + "You can ask me any question, and I'll try to answer it!");
			p.sendMessage(ChatColor.GRAY + "Type a message into the chat starting with \"!bot\" to ask me stuff..");
		}

		e.setCancelled(true);
	}

}
