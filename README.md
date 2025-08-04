<h1 align="center">🌍 OpenSectors [1.8]</h1>
<p align="center">A powerful Minecraft plugin that splits your world into multiple servers and syncs player data seamlessly.</p>

<img src="/assets/banner.png" alt="Banner" style="display: block; margin: 0 auto; max-width: 100%; height: auto;" />

---

## ✨ Features

- 🚪 Smooth teleportation between sectors on border crossing  
- 🔄 Real-time player data synchronization  
- 🧭 Shared and synced sector information  

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
  "sectorIsOfflineMessage": "&cSektor, z którym chcesz się połączyć, jest aktualnie wyłączony!",
  "noSectorsAvailableMessage": "&cBrak dostępnych sektorów!",
  "playerDataNotFoundMessage": "&cWystąpił problem podczas ładowania danych.",
  "playerDataLoadedMessage": "&aPomyślnie załadowano twoje dane.",
  "playerAlreadyConnectedMessage": "&cJesteś już połączony z tym kanałem!",
  "spawnSectorNotFoundMessage": "&cNie odnaleziono dostępnego sektora spawna!",
  "scoreboardTitle": "&a&lOpenSectors 2.0",
  "scoreboardLines": [
    "",
    "&7Połączono z &a{SECTOR}",
    "&7Użyj: &a/ch &7aby ",
    "&7zmienić kanał",
    "",
    "&7Online: &a{ONLINE}",
    "&7TPS: &a{TPS}",
    ""
  ],
  "connectedInfoTitle": "&a&lOpenSectors 2.0",
  "connectedInfoSubTitle": "&7Pomyślnie &a&npołączono &7z sektorem &a&n{SECTOR}"
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

## 💖 Thanks for Your Support!

If you enjoy using OpenSectors, leave a ⭐ on the GitHub repo and share it with your friends!
