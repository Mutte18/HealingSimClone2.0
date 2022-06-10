package com.healing;

import com.healing.gamelogic.Game;
import com.healing.gui.MainWindow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HealingSimCloneServerApplication {

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        // System.out.println(java.awt.GraphicsEnvironment.isHeadless());
        SpringApplication.run(HealingSimCloneServerApplication.class, args);
        Game game = new Game();
        new Thread(game).start();
        new MainWindow(game);
    }

}
