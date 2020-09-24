package com.gihub.mcokeydokey.realtime

import org.bukkit.plugin.java.JavaPlugin

class RealTime : JavaPlugin() {

    override fun onEnable() {
        plugin = this
        Config.load()
        Scheduler.start()
    }

    override fun onDisable() {
        Scheduler.stop()
    }
}