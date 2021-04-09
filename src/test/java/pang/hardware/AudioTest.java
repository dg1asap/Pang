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

        long timeWhenPaused,timeAfterPause;
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
        if(isPauseTimeEqual(timeWhenPaused,timeAfterPause) && isTimeNonZeroValue(timeWhenPaused)) {
            return true;
        }
        else return false;
   }

   private boolean isPauseTimeEqual(long timeWhenPaused, long timeAfterPause){
       if(timeWhenPaused == timeAfterPause) {
           return true;
       }
       else return false;
   }

   private boolean isTimeNonZeroValue(long time){
       if(time != 0) {
           return true;
       }
       else return false;
   }
}
