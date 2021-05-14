package pang.backend.character.player;

public class PlayerReaction {
    public String fromKeyName(char keyName) {
        return switch (keyName) {
            case 'a', 'A', 'd', 'D' -> "posX";
            case 'w', 'W' -> "posY";
            case 'k', 'K' -> "ammunition";
            default -> "none";
        };
    }
}
