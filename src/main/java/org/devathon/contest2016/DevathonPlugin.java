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
		_l.info("DevathonPlugin loading....");
		saveDefaultConfig();

		learner = new Learner();
		List<String> sentences = getConfig().getStringList("learned");
		learner.getAI().sentences.addAll(sentences);

		ConfigurationSection annas = getConfig().getConfigurationSection("stands");
		if(annas != null) {
			for(String ankey : annas.getKeys(false)) {
				Location l = (Location) annas.get(ankey + ".location");
				learner.annaStand = AnnaStand.createAnnaStand(l);
			}
		}

		_l.info("Listener loading....");
		getServer().getPluginManager().registerEvents(new MaskinrListener(), this);
	}

	@Override
	public void onDisable() {
		_l.info("Shutting down");

		getConfig().set("learned", learner.getAI().sentences.toArray(new String[] {}));
		int id = 0;
		getConfig().set("stands." + id + ".location", learner.annaStand.getStand().getLocation());
		learner.annaStand.remove();

		saveConfig();
	}

	public static Learner getLearner() {
		return learner;
	}

	public static DevathonPlugin getInstance() {
		return getPlugin(DevathonPlugin.class);
	}
}

