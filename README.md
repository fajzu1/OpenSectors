<div align="center">
  <img src="/assets/banner.png" alt="Banner" style="max-width: 100%; height: auto;" />
</div>

---

## ✨ Features

- 🚪 Smooth teleportation between sectors on border crossing  
- 🔄 Real-time player data synchronization  
- 🧭 Shared and synced sector information  

## ⏯ Video

![](assets/video-example.gif)<br/>

---

## 🧰 Requirements

- 🌐 1x [Velocity](https://velocitypowered.com/) proxy server  
- 🧱 Minimum 2x Spigot servers  
- ⚡ 1x Redis instance (data distribution & sync)

---

## ⚙️ Configuration

Setting up sectors is simple and straightforward. Define the area for each sector and configure your Redis instance in the config files.

### 🗺️ Sectors Configuration

```json
{
  "sectors": {
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

### 🧠 Redis Configuration

```json
{
  "redisHost": "localhost",
  "redisPassword": "root",
  "redisPort": 6379
}
```

### 📍 Spigot Configuration

```json
{
  "currentSector": "s1"
}
```

### 💬 Messages Configuration

```json
{
  "cannotPlaceBlockNearSectorMessage": "&cNie możesz stawiać bloków przy granicy sektora!",
  "cannotBreakBlockNearSectorMessage": "&cNie możesz niszczyć bloków przy granicy sektora!",
  "actionbarBorderMessage": "&7Jesteś blisko sektora &2{DISTANCE}&7m",
  "sectorIsOfflineMessage": "&cSektor z którym chcesz się połączyć jest aktualnie wyłączony!",
  "noSectorsAvailableMessage": "&cBrak dostepnych sektorów",
  "playerDataNotFoundMessage": "&cWystąpił problem podczas ładowania danych",
  "playerDataLoadedMessage": "&aPomyślnie połączono i załadowano dane w ciągu &2{TIME}ms",
  "playerAlreadyConnectedMessage": "&cJesteś aktualnie połączony z tym kanałem",
  "playerSectorTransferCooldownMessage": "&cPoczekaj chwile zanim znowu przejdziesz przez sektor",
  "spawnSectorNotFoundMessage": "&cNie odnaleziono dostepnego sektora spawna",
  "scoreboardTitle": "&a&lOpenSectors 3.0",
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
  "connectedInfoTitle": "&a&lOpenSectors 3.0",
  "connectedInfoSubTitle": "&7Pomyslnie &a&npolaczono&r &7z sektorem &a&n{SECTOR}"
}

```

---

## 🐛 Found a Bug?

Spotted an issue? Report it via Discord: **fizuxd** 💬

---

## 📌 TODO

- 🧩 Create an easy-to-use **API** for developers  
- ⛏️ Add support for **newer Minecraft versions**

---
