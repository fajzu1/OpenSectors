package net.lightcode.configuration.impl;

import net.lightcode.configuration.Configuration;

import java.util.ArrayList;
import java.util.List;

public class MessagesConfiguration implements Configuration {

    private final String cannotPlaceBlockNearSectorMessage, cannotBreakBlockNearSectorMessage;

    private final String actionbarBorderMessage;

    private final String sectorIsOfflineMessage, noSectorsAvailableMessage;

    private final String playerDataNotFoundMessage, playerDataLoadedMessage, playerAlreadyConnectedMessage,playerSectorTransferCooldownMessage;

    private final String spawnSectorNotFoundMessage;

    private final String scoreboardTitle;

    private final List<String> scoreboardLines;

    private final String connectedInfoTitle;
    private final String connectedInfoSubTitle;

    public MessagesConfiguration() {
        this.cannotBreakBlockNearSectorMessage = "&cNie możesz niszczyć bloków przy granicy sektora!";
        this.cannotPlaceBlockNearSectorMessage = "&cNie możesz stawiać bloków przy granicy sektora!";

        this.actionbarBorderMessage = "&7Jesteś blisko sektora &2{DISTANCE}&7m";

        this.sectorIsOfflineMessage = "&cSektor z którym chcesz się połączyć jest aktualnie wyłączony!";
        this.noSectorsAvailableMessage = "&cBrak dostepnych sektorów";

        this.playerDataNotFoundMessage = "&cWystąpił problem podczas ładowania danych";
        this.playerDataLoadedMessage = "&aPomyślnie połączono i załadowano dane w ciągu &2{TIME}ms";
        this.playerAlreadyConnectedMessage = "&cJesteś aktualnie połączony z tym kanałem";
        this.playerSectorTransferCooldownMessage = "&cPoczekaj chwile zanim znowu przejdziesz przez sektor";

        this.spawnSectorNotFoundMessage = "&cNie odnaleziono dostepnego sektora spawna";

        this.connectedInfoTitle = "&a&lOpenSectors 3.0";
        this.connectedInfoSubTitle = "&7Pomyslnie &a&npolaczono&r &7z sektorem &a&n{SECTOR}";

        this.scoreboardTitle = "&a&lOpenSectors 3.0";
        this.scoreboardLines = new ArrayList<>();

        this.scoreboardLines.add("");
        this.scoreboardLines.add("&7Polaczono z &a{SECTOR}");
        this.scoreboardLines.add("&7Uzyj: &a/ch &7aby ");
        this.scoreboardLines.add("&7zmienic kanal");
        this.scoreboardLines.add("");
        this.scoreboardLines.add("&7Online: &a{ONLINE}");
        this.scoreboardLines.add("&7TPS: &a{TPS}");
        this.scoreboardLines.add("");

    }

    public String playerSectorTransferCooldownMessage() {
        return this.playerSectorTransferCooldownMessage;
    }

    public String connectedInfoSubTitle() {
        return this.connectedInfoSubTitle;
    }

    public String connectedInfoTitle() {
        return this.connectedInfoTitle;
    }

    public List<String> scoreboardLines() {
        return this.scoreboardLines;
    }

    public String scoreboardTitle() {
        return this.scoreboardTitle;
    }

    public String playerAlreadyConnectedMessage() {
        return this.playerAlreadyConnectedMessage;
    }

    public String playerDataLoadedMessage() {
        return this.playerDataLoadedMessage;
    }

    public String noSectorsAvailableMessage() {
        return this.noSectorsAvailableMessage;
    }

    public String spawnSectorNotFoundMessage() {
        return this.spawnSectorNotFoundMessage;
    }

    public String cannotPlaceBlockNearSectorMessage() {
        return this.cannotPlaceBlockNearSectorMessage;
    }

    public String cannotBreakBlockNearSectorMessage() {
        return this.cannotBreakBlockNearSectorMessage;
    }

    public String actionbarBorderMessage() {
        return this.actionbarBorderMessage;
    }

    public String sectorIsOfflineMessage() {
        return this.sectorIsOfflineMessage;
    }

    public String playerDataNotFoundMessage() {
        return this.playerDataNotFoundMessage;
    }

    @Override
    public String fileName() {
        return "messages.json";
    }
}
