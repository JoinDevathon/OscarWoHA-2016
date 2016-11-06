package org.devathon.contest2016.ai;

import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.Vector;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class AnnaStand {
	private ArmorStand instance = null;

	public AnnaStand(ArmorStand instance) {
		this.instance = instance;

		//Do you even annaStand?
		this.setupAnnaStand();
	}

	//Just a mashup of shit, and yeah, I used this § to color stuff - just cause I'm lazy
	private void setupAnnaStand() {
		this.instance.setGravity(false);
		this.instance.setBasePlate(false);
		this.instance.setCollidable(false);
		this.instance.setArms(true);
		this.instance.setCustomName("§6§lAnna");
		this.instance.setCustomNameVisible(true);

		this.instance.setHelmet(this.getSkull("eyJ1cmwiOiJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2QzYTdkMTAzYjljNGQ0YWI2ODAzNjlhMTQyYTEwYzllZWE4MDdlOGU3Mjc2ZDhlMmRlNzczM2E2ZDk1ZWY3In0="));
		
		ItemStack book = new ItemStack(Material.BOOK, 1);
		this.instance.setItemInHand(book);	
		
		/*
		 * All this shitty code for some shitty bot that I wrote just for the lulz <3
		 * Creds to the French dude on Discord for suggesting that Anna should have a pink chestplate on her ;)
		 */
		ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		LeatherArmorMeta meta = (LeatherArmorMeta) chest.getItemMeta();
		meta.setColor(Color.PURPLE);
		chest.setItemMeta(meta);
		
		this.instance.setChestplate(chest);
	}
	
	//This code is awesome
	public void updateFaceDirection(Location pl) {
		Vector from = new Vector(this.instance.getLocation().getX(), this.instance.getLocation().getY(), this.instance.getLocation().getZ());
		Vector to  = new Vector(pl.getX(), pl.getY(), pl.getZ());
		 
		Vector vector = to.subtract(from);
		Location newloc = this.instance.getLocation();
		newloc.setDirection(vector);
		
		this.instance.teleport(newloc);
	}

	//Should always be able to get a private field through a function
	public ArmorStand getStand() {
		return this.instance;
	}

	//Static because I'm to lazy to infer an Location argument in constructor xD
	public static AnnaStand createAnnaStand(Location l) {
		return new AnnaStand((ArmorStand) l.getWorld().spawnEntity(l, EntityType.ARMOR_STAND));
	}

	
	//What am I even doing here
	public void remove() {
		this.instance.remove();
	}

	//This...this fucking code...mojang and their systems man
	public ItemStack getSkull(String skinURL) {
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		
		if (skinURL.isEmpty()) return head;
		
		ItemMeta headMeta = head.getItemMeta();
		
		Field profileField = null;
		try {
			profileField = headMeta.getClass().getDeclaredField("profile");

			profileField.setAccessible(true);
			profileField.set(headMeta, this.getNonPlayerProfile("http://textures.minecraft.net/texture/d3a7d103b9c4d4ab680369a142a10c9eea807e8e7276d8e2de7733a6d95ef7"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		head.setItemMeta(headMeta);
		return head;
	}
	
	public GameProfile getNonPlayerProfile(String skinURL) {
	    GameProfile newSkinProfile = new GameProfile(UUID.randomUUID(), null);
	    newSkinProfile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{url:\"" + skinURL + "\"}}}")));
	    return newSkinProfile;
	}

}