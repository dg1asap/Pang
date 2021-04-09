package pang.hardware;

import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class AudioTest {

    Audio music = new Audio();
    long timeWhenPaused;
    long timeAfterPause;

    @Test
    void testMusicIsLoaded(){
        assertNotNull(music.getPath());
    }

    @Test
    void testMusicInterfaceIsWorking(){

        music.load();
        music.loop();
        JOptionPane.showMessageDialog(null, "Test music");
        music.pause();
        timeWhenPaused = music.getTime();
        JOptionPane.showMessageDialog(null, "Music paused");
        music.resumeLoop();
        timeAfterPause = music.getTime();
        JOptionPane.showMessageDialog(null, "Music resumed");

        assertTrue(checkMusicInterface(timeWhenPaused,timeAfterPause));
   }

   private boolean checkMusicInterface(long timeWhenPaused, long timeAfterPause){
       return isPauseTimeEqual(timeWhenPaused, timeAfterPause) && isTimeNonZeroValue(timeWhenPaused);
   }

   private boolean isPauseTimeEqual(long timeWhenPaused, long timeAfterPause){
       return timeWhenPaused == timeAfterPause;
   }

   private boolean isTimeNonZeroValue(long time){
       return time != 0;
   }
}
