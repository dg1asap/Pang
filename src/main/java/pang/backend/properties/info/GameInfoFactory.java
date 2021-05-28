package pang.backend.properties.info;

public abstract class GameInfoFactory {
    protected GameInfo info;

    public abstract GameInfo create(Info owner);

    public void update(GameInfo info) {
        this.info.merge(info);
    }


}
