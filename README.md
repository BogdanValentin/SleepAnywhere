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

The config file lives at `config/sleepanywhere.json` and is created with defaults the first time the mod runs. Each debuff takes a duration in seconds and a level, where level 1 is the normal strength you would get from a potion. Setting `seconds` to `0` disables that debuff.

| Option | Default | Notes |
| --- | --- | --- |
| `hunger` | 120s, level 1 | Drains roughly one food point every 40 seconds at level 1 |
| `nausea` | 20s, level 1 | Screen warp only |
| `blindness` | 10s, level 1 | Severe, keep it short |
| `darkness` | 30s, level 1 | The pulsing Warden darkness |
| `miningFatigue` | 90s, level 1 | Level 1 is already a 70% cut to block breaking speed |
| `weakness` | 90s, level 1 | Level 1 is a flat -4 attack damage |
| `slowness` | 60s, level 1 | -15% movement speed per level |
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
