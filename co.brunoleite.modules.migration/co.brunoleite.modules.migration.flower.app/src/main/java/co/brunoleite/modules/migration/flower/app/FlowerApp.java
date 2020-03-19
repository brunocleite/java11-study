package co.brunoleite.modules.migration.flower.app;

import co.brunoleite.modules.migration.flower.Rose;

import java.util.logging.Logger;

public class FlowerApp {

    private static Logger log = Logger.getLogger(FlowerApp.class.getName());

    public static void main(String[] args) {
        Rose rose = new Rose(9d);

        if (rose.getBeautyLevel() > 8d) {
            log.info("What a beautiful flower");
        }

    }
}
