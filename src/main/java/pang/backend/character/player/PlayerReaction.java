package pang.backend.character.player;

public class PlayerReaction {
    public String fromKeyName(char keyName) {
        return switch (keyName) {
            case 'a', 'd' -> "posX";
            case 'w' -> "posY";
            case 's' -> "posY"; //chwilowe
            default -> "none";
        };
    }
}
