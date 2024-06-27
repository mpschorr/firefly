package xyz.jeelzzz.firefly

import org.bukkit.configuration.ConfigurationSection

class ConfigNode(val configuration: ConfigurationSection, val bukkitKey: String, decodeKey: String = bukkitKey) {

    val decodeKey = decodeKey
        get() {
            var key = field
            if (key.startsWith("_")) {
                key = key.substring(key.indexOf('.') + 1)
            }
            return key.ifEmpty { "_root_" }
        }

    fun exists() : Boolean {
        return this.configuration.contains(bukkitKey)
    }

    fun getBoolean() : Boolean {
        return this.configuration.getBoolean(bukkitKey)
    }

    fun isBoolean() : Boolean {
        return this.exists() && this.configuration.isBoolean(bukkitKey)
    }

    fun getDouble() : Double {
        return this.configuration.getDouble(bukkitKey)
    }

    fun isDouble() : Boolean {
        return this.exists() && this.configuration.isDouble(bukkitKey)
    }

    fun getInt(): Int {
        return this.configuration.getInt(bukkitKey)
    }

    fun isInt() : Boolean {
        return this.exists() && this.configuration.isInt(bukkitKey)
    }

    fun getLong() : Long {
        return this.configuration.getLong(bukkitKey)
    }

    fun isLong() : Boolean {
        return this.exists() && this.configuration.isLong(bukkitKey)
    }

    fun getString(): String? {
        return this.configuration.getString(bukkitKey)
    }

    fun isString() : Boolean {
        return this.exists() && this.configuration.isString(bukkitKey)
    }

//    fun getConfigurationSection() : ConfigurationSection {
//        return this.getConfigurationSection()
//    }

    fun createChild(childKey: String) : ConfigNode {
        val newBukkitKey =
            if (bukkitKey.isNotEmpty()) "$bukkitKey.$childKey"
            else childKey
        val newDecodeKey =
            if (decodeKey.isNotEmpty()) "$decodeKey.$childKey"
            else childKey
        return ConfigNode(this.configuration, newBukkitKey, newDecodeKey)
    }
}
