# Sleep Anywhere

The Sleep Anywhere mod is a convenient addition to Minecraft that allows players to sleep anywhere they want without the need for a bed. Whether you're exploring distant lands, delving into deep caves, or simply caught out in the wilderness at night, this mod lets you rest on the go.

## Features

- **Singleplayer and Multiplayer compatible**: Both the server and client need to have the mod installed. The config of the server takes priority.

- **Counts towards the vanilla sleep percentage**: Sleeping on the ground is registered exactly like sleeping in a bed, so the `playersSleepingPercentage` gamerule and the "x/y players are sleeping" message work as they normally would.

- **Configurable Key Binding**: You can configure the key binding for sleeping according to your preference. By default, press 'M' to sleep.

- **Debuffs for Balance**: To maintain game balance and prevent abuse, the mod introduces configurable debuffs when sleeping without a bed. These debuffs include:
    - Hunger
    - Nausea
    - Blindness
    - Darkness
    - Mining Fatigue
    - Weakness
    - Slowness

## Configuration

The config file lives at `config/sleepanywhere.json` and is created with defaults the first time the mod runs.

| Option | Default | Meaning |
| --- | --- | --- |
| `hungerEffect` | `5` | Hunger duration in seconds, `0` disables it |
| `nauseaEffect` | `5` | Nausea duration in seconds, `0` disables it |
| `blindnessEffect` | `5` | Blindness duration in seconds, `0` disables it |
| `darknessEffect` | `0` | Darkness duration in seconds, `0` disables it |
| `fatigueEffect` | `10` | Mining Fatigue duration in seconds, `0` disables it |
| `weaknessEffect` | `10` | Weakness duration in seconds, `0` disables it |
| `slownessEffect` | `10` | Slowness duration in seconds, `0` disables it |
| `setSpawnPoint` | `false` | Set your respawn point where you sleep, like a bed would |
| `sleepThroughThunderstorms` | `true` | Allow sleeping during a thunderstorm, like a bed would |

## Versions

| Minecraft | Loader | Branch |
| --- | --- | --- |
| 1.20 - 1.20.4 | Fabric | `1.20` - `1.20.4` |
| 1.21.1 | Fabric | `1.21.1` |
| 1.21.1 | NeoForge | `1.21.1-neoforge` |
| 1.21.11 | Fabric | `1.21.11` |
| 1.21.11 | NeoForge | `1.21.11-neoforge` |
| 26.3 | Fabric | `26.3` |
| 26.3 | NeoForge | `26.3-neoforge` |

## Feedback and Support

If you encounter any issues or have suggestions for improving the Sleep Anywhere mod, feel free to reach out via [GitHub Issues](https://github.com/BogdanValentin/SleepAnywhere/issues) or leave a message in the comments.

## License

This mod is distributed under the [CC0-1.0 License](LICENSE).
