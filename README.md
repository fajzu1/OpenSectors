# OpenSectors [1.8]

This is a Minecraft server plugin that divides the world into individual servers and synchronizes your data with them.

# Features

  - Going through sector border and teleporting between sectors
  - Player data synchronization between sectors
  - Synchronization of sector information

# Requirements

  - One velocity server
  - min. 2 spigot servers
  - One redis distribution

# Configuration 

The configuration of the sectors themselves is simple and clear. Just set the location of each sector and provide the redis distribution data in config

**Sectors configuration**

```JSON
{
  "sectorMap": {
    "s1": {
      "name": "s1",
      "sectorType": "NORMAL",
      "minX": -100,
      "maxX": 1000,
      "minZ": 100,
      "maxZ": 1000
    },
    "spawn_1": {
      "name": "spawn_1",
      "sectorType": "SPAWN",
      "minX": -100,
      "maxX": 100,
      "minZ": -100,
      "maxZ": 100
    }
  }
}
```

**Database Configuration**

```JSON
{
  "redisHost": "localhost",
  "redisPassword": "root",
  "redisPort": 6379
}

```

**Spigot Configuration**

```JSON
{
  "currentSector": "s1"
}

```

**Messages Configuration**

```JSON
{
  "cannotPlaceBlockNearSectorMessage": "&CNie możesz stawiać bloków przy granicy sektora!",
  "cannotBreakBlockNearSectorMessage": "&cNie możesz niszczyć bloków przy granicy sektora!",
  "actionbarBorderMessage": "&7Jesteś blisko sektora &2{DISTANCE}&7m",
  "sectorIsOfflineMessage": "&cSektor z którym się chcesz aktualnie połączyć jest aktualnie wyłączony!",
  "noSectorsAvailableMessage": "&cBrak dostepnych sektorów",
  "playerDataNotFoundMessage": "&cWystąpił problem podczas ładowania danych",
  "playerDataLoadedMessage": "&aPomyslnie zaladowano twoje dane",
  "playerAlreadyConnectedMessage": "&cJesteś aktualnie połączony z tym kanałem",
  "spawnSectorNotFoundMessage": "&cNie odnaleziono dostepnego sektora spawna",
  "scoreboardTitle": "&a&lOpenSectors 2.0",
  "scoreboardLines": [
    "",
    "&7Polaczono z &a{SECTOR}",
    "&7Uzyj: &a/ch &7aby ",
    "&7zmienic kanal",
    "",
    "&7Online: &a{ONLINE}",
    "&7TPS: &a{TPS}",
    ""
  ],
  "connectedInfoTitle": "&a&lOpenSectors 2.0",
  "connectedInfoSubTitle": "&7Pomyslnie &a&npolaczono &7z sektorem &a&n{SECTOR}"
}

```

# Bug

if you found a bug in OpenSectors write to me on discord **fizuxd**

# TODO

  - Make an easy-to-use **API** for developers
  - Support for newer MC versions

# Thanks for Support ❤️
