package nl.rbdh.web.games.fiveseconds;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.concurrent.CountDownLatch;

public class Timer extends HorizontalLayout {

    public Timer() {

        CountDownLatch countdown = new CountDownLatch(5000);


        countdown.getCount();



        HorizontalLayout timer = new HorizontalLayout();

    }


}
