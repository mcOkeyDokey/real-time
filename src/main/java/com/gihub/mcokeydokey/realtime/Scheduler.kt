package com.gihub.mcokeydokey.realtime

import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.scheduler.BukkitRunnable
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor

object Scheduler {

    private const val REAL_DAY_SECONDS = 24 * 60 * 60
    private const val IN_GAME_DAY_TICKS = 24000
    private const val IN_GAME_MIDNIGHT_TICKS = 18000

    private val task: BukkitRunnable

    init {
        task = object : BukkitRunnable() {
            override fun run() {
                Config.worlds.forEach { (worldName, timeZone) ->
                    val world = Bukkit.getWorld(worldName) ?: return
                    world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false)
                    val percent = getElapsedSeconds(timeZone).toDouble() / REAL_DAY_SECONDS.toDouble() * 100.0
                    world.time = floor(IN_GAME_DAY_TICKS / 100.0 * percent).toLong() + IN_GAME_MIDNIGHT_TICKS
                }
            }
        }
    }

    internal fun start() = task.runTaskTimer(plugin, 0, 20)

    internal fun stop() {
        task.cancel()
        Config.worlds.forEach { (worldName, _) ->
            Bukkit.getWorld(worldName)?.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true)
        }
    }

    private fun getElapsedSeconds(timeZone: TimeZone): Long {
        val calendar = Calendar.getInstance(timeZone)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        dateFormat.timeZone = timeZone
        val date = dateFormat.parse(dateFormat.format(calendar.time))
        return (System.currentTimeMillis() - date.time) / 1000
    }

}