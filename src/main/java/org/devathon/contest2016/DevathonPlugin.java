package org.devathon.contest2016;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.ai.AnnaStand;
import org.devathon.contest2016.ai.Learner;
import org.devathon.contest2016.listener.MaskinrListener;

public class DevathonPlugin extends JavaPlugin {
	private Logger _l = this.getLogger();
	private static Learner learner = null;

	@Override
	public void onEnable() {
		_l.info("Anna - the computing machine | Loading....");
		saveDefaultConfig();

		learner = new Learner();
		List<String> sentences = getConfig().getStringList("learned");
		learner.getAI().sentences.addAll(sentences);

		ConfigurationSection annas = getConfig().getConfigurationSection("anna");
		if(annas != null) {
			Location l = (Location) annas.get("location");
			learner.annaStand = AnnaStand.createAnnaStand(l);
		}

		_l.info("Anna - the computing machine | Initializing listener....");
		getServer().getPluginManager().registerEvents(new MaskinrListener(), this);
	}

	@Override
	public void onDisable() {
		_l.info("Anna - the computing machine | Shutting down...");

		getConfig().set("learned", learner.getAI().sentences.toArray(new String[] {}));
		getConfig().set("anna.location", learner.annaStand.getStand().getLocation());
		saveConfig();
		
		learner.annaStand.remove();
	}

	public static Learner getLearner() {
		return learner;
	}

	public static DevathonPlugin getInstance() {
		return getPlugin(DevathonPlugin.class);
	}
}

