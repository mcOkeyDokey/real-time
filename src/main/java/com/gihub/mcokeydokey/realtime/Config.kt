package com.gihub.mcokeydokey.realtime

import java.util.*

object Config {

    internal lateinit var worlds: Map<String, TimeZone>
        private set

    init {
        plugin.saveDefaultConfig()
    }

    internal fun load() {
        plugin.reloadConfig()
        val config = plugin.config

        worlds = config.getConfigurationSection("worlds")?.let { section ->
            section.getKeys(false)
                    .map { world -> world to TimeZone.getTimeZone(section.getString(world)) }
                    .toMap()
        } ?: emptyMap()
    }


}