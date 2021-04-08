package pang.hardware;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class AudioTest {

    Audio music = new Audio();

    @Test
    void testMusicIsLoaded(){
        assertNotNull(music.getPath());
    }

    @Test
    void testMusicInterfaceIsWorking(){

        long a,b;
        music.load();
        music.loop();
        JOptionPane.showMessageDialog(null, "Test music");
        music.pause();
        a = music.getTime();
        JOptionPane.showMessageDialog(null, "Music paused");
        music.resumeLoop();
        b = music.getTime();
        JOptionPane.showMessageDialog(null, "Music resumed");

        assertTrue( (a == b) && a != 0);

   }
   
}
