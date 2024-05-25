import jxbrowser.engine.Engine;
import jxbrowser.view.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import jxbrowser.engine.RenderingMode.HARDWARE_ACCELERATED;
import static javax.swing.SwingConstants.CENTER;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class Installer {

    public static void showBrowser(){
        var engine=Engine.newInstance(HARDWARE_ACCELERATED);
        var browser=engine.newBrowser();
        var frame=new JFrame();
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });

        var view=BrowserView.newInstance(browser);
        frame.add(view, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setSize(1280, 900);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static JWindow showSplashScreen(){
        var splash=new JWindow();
        splash.getContentPane().add(new JLabel("LOADING...", CENTER));
        splash.setBounds(400, 120, 200, 60);
        splash.setVisible(true);
        return splash;
    }
}
