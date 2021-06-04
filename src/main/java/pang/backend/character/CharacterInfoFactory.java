package pang.backend.character;

import pang.backend.character.player.Player;
import pang.backend.properties.config.ConfigLoader;
import pang.backend.properties.config.GameConfig;
import pang.backend.properties.info.GameInfo;
import pang.backend.properties.info.GameInfoFactory;
import pang.backend.properties.info.Info;
import pang.backend.util.ClassNameConverter;

public class CharacterInfoFactory extends GameInfoFactory {
    @Override
    public GameInfo create(Info character) throws IllegalArgumentException {
        String className = ClassNameConverter.getSimpleClassNameOf(character);
        return switch (className) {
            case "Player" -> getPlayerInfo((Player) character);
            default -> throw new IllegalArgumentException();
        };
    }

    private GameInfo getPlayerInfo(Player player) {
        info = new GameInfo("Player");
        addScore(player);
        return info;
    }

    private void addScore(Player player) {
        Double score = player.getStat("score");
        GameConfig gameConfig = ConfigLoader.CONFIG_LOADER.getConfig("Pang");
        Double k = gameConfig.getAttribute("difficulty");
        Double p = player.getStat("health");
        Double s = player.getStat("ammunition");

        double scoreWithBonuses = score + Math.ceil( 10 * ( 0.028*Math.pow(k,6) - 0.4*Math.pow(k,2) + 2.3*k - 2.55 + 0.4*(p-2.5) + 0.8*(s-3) ) + 29);
        info.addAttribute("scoreWithBonus", Double.toString(scoreWithBonuses));
    }


}
