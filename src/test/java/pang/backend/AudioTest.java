package pang.backend;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AudioTest {
    Audio music = new Audio();

    @Test
    void testMusicIsLoaded(){
        assertTrue(music.getPath() != null);
        assertTrue(music.load() != -1 );
    }
    @Test
    void testMusicInterfaceIsWorking(){
        music.play();
        music.stop();
        assertTrue(music.play());
    }
}
