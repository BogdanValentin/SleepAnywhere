# Sleep Anywhere

The Sleep Anywhere mod is a convenient addition to Minecraft that allows players to sleep anywhere they want without the need for a bed. Whether you're exploring distant lands, delving into deep caves, or simply caught out in the wilderness at night, this mod lets you rest on the go.

## Features

- **Singleplayer and Multiplayer compatible**: Both the server and client need to have the mod installed. The config of the server takes priority. 

- **Counts towards sleeping players**: Sleeping on the ground is counted by the game just like sleeping in a bed, so the `playersSleepingPercentage` gamerule and the "x/y players are sleeping" message include you.

- **Configurable Key Binding**: You can configure the key binding for sleeping according to your preference. By default, press 'M' to sleep.

- **Debuffs for Balance**: To maintain game balance and prevent abuse, the mod introduces configurable debuffs when sleeping. These debuffs include:
    - Hunger
    - Nausea
    - Blindness
    - Darkness
    - Mining Fatigue
    - Weakness
    - Slowness

## Configuration

You can customize the effects of the Sleep Anywhere mod through its configuration file (located in .minecraft/config/sleepanywhere-common.toml), or in game from the Config button on the mod's entry in the mod list:
- **Debuffs**: Adjust the duration in seconds and the level of the debuffs applied when sleeping, or disable them entirely if desired (by setting the duration to 0). Level 1 is the normal strength you would get from a potion.
- **Spawn point**: Set `setSpawnPoint` to true to have sleeping set your respawn point, like a bed would.
- **Thunderstorms**: Set `sleepThroughThunderstorms` to false to only allow sleeping at night.

## Feedback and Support

If you encounter any issues or have suggestions for improving the Sleep Anywhere mod, feel free to reach out via [GitHub Issues](https://github.com/BogdanValentin/SleepAnywhere/issues) or leave a message in the comments.

## License

This mod is distributed under the [CC0-1.0 License](LICENSE).
