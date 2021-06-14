package pang.backend.character.player;

public class PlayerReaction {
    private char[] horizontalMovementKey = {'a', 'A', 'd', 'D'};
    private char[] verticalMovementKey = {'w', 'W'};

    public String fromKeyName(char keyName) {
        return switch (keyName) {
            case 'a', 'A', 'd', 'D' -> "motionVectorX";
            case 'w', 'W' -> "motionVectorY";
            case 'k', 'K' -> "ammunition";
            default -> "none";
        };
    }
/*
    public boolean isMotionKey(char keyName) {
        }
        return keyName

    }
    /
 */

}
