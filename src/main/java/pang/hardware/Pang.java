package pang.hardware;

public class Pang {
//    Screen screen;
//    Audio audio;

    public static void main(String args[]) {
        loadMusic();
    }

   private static void loadMusic(){

    String musicPath ="./data/music/spaceSample.aif";
    Audio music = new Audio();
    music.play(musicPath);


   }

}
