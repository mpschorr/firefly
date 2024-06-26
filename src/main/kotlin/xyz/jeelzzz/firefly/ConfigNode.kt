package xyz.jeelzzz.firefly

import org.bukkit.configuration.ConfigurationSection
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector

class ConfigNode(val configuration: ConfigurationSection, val key: String) {
    fun getBoolean() : Boolean {
        return this.configuration.getBoolean(key)
    }

    fun getColor() : org.bukkit.Color {
        return this.configuration.getColor(key)
    }

    fun getDouble() : Double {
        return this.configuration.getDouble(key)
    }

    fun getInt(): Int {
        return this.configuration.getInt(key)
    }

    fun getItemStack() : ItemStack {
        return this.configuration.getItemStack(key)
    }

    fun getLong() : Long {
        return this.configuration.getLong(key)
    }

    fun getVector() : Vector {
        return this.configuration.getVector(key)
    }

    fun getString(): String? {
        return this.configuration.getString(key)
    }

//    fun getConfigurationSection() : ConfigurationSection {
//        return this.getConfigurationSection()
//    }

    fun createChild(childKey: String) : ConfigNode {
        val newKey =
            if (key.isNotEmpty()) "$key.$childKey"
            else childKey
        return ConfigNode(this.configuration, newKey)
    }
}
