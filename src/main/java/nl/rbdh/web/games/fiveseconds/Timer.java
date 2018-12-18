package nl.rbdh.web.games.fiveseconds;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.TimerTask;

public class Timer extends HorizontalLayout {
    public static int interval;

    public HorizontalLayout Timer(int timer) {


        HorizontalLayout timerLayout = new HorizontalLayout();
        Label timerCount = new Label("");
        interval = timer;
        int delay = 1000;
        int period = 1000;
        final java.util.Timer time = new java.util.Timer();
        System.out.println(interval);
        time.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                if (interval == 0) {
                    System.out.println("work finished");
                    time.cancel();
                    time.purge();
                } else {
                    Notification.show(String.valueOf(setInterval()), 1, Notification.Position.MIDDLE);
                    System.out.println(setInterval());
                }
            }
        }, delay, period);

        timerLayout.add(timerCount);

        return timerLayout;
    }

    private static int setInterval() {

        return --interval;
    }
}