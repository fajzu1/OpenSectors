<div align="center">
  <img src="/assets/banner.png" alt="Banner" style="max-width: 100%; height: auto;" />
</div>

---

> [!WARNING]
> **Project is currently under active development**
> **on branch** [Click](https://github.com/fajzu1/OpenSectors/tree/4.x)
>
> ⚠️ Features may be incomplete or unstable.  
> ❌ This branch is **unstable** and should not be used on the main/production server.  
> ✅ Use it only for testing or development purposes.

---

## ✨ Features

- 🚪 Smooth teleportation between sectors on border crossing
- 🔄 Real-time player data synchronization
- 🧭 Shared and synced sector information
- 💬 Global player chat synchronized across all sectors

---

- 🔗 Wiki [Click](https://github.com/fajzu1/OpenSectors/wiki)

---

## ⏯ Video

![](assets/video-example.gif)<br/>

---

## 🧰 Requirements

- 🌐 1x [Velocity](https://velocitypowered.com/) proxy server
- 🧱 Minimum 2x [Paper](https://papermc.io/downloads/paper) servers
- ⚡ 1x [Nats](https://nats.io/) instance (data distribution & sync)

---

## ⚙️ Configuration

Setting up sectors is simple and straightforward. Define the area for each sector and configure your Nats instance in
the config files.

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
  "scoreboardTitle": "&a&lOpenSectors",
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
  "connectedInfoTitle": "&a&lOpenSectors",
  "connectedInfoSubTitle": "&7Pomyslnie &a&npolaczono&r &7z sektorem &a&n{SECTOR}"
}

```

---

## TODO

- 📡 Add support for NATS
- 📝 Change configuration system from JSON to YAML
- 🐛 Fix 1.21.1+ NMS support

---

## 💖 Thanks for Your Support!

If you enjoy using OpenSectors, leave a ⭐ on the GitHub repo and share it with your friends!
