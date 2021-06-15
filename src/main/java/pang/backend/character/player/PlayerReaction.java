package pang.backend.character.player;

/**
 * Klasa przypisująca konkretnemu klawiszowi konkretną statystykę na którą wpływa
 */
public class PlayerReaction {
    /**
     * tłumaczy wciśniety klawisz przez gracza na nazwę statysyki którą wciśnięcie go modyfikuje
     * @param keyName wciśnięty klawisz
     * @return modyfikowana statystyka
     */
    public String fromKeyName(char keyName) {
        return switch (keyName) {
            case 'a', 'A', 'd', 'D' -> "motionVectorX";
            case 'w', 'W' -> "motionVectorY";
            case 'k', 'K' -> "ammunition";
            default -> "none";
        };
    }

}
