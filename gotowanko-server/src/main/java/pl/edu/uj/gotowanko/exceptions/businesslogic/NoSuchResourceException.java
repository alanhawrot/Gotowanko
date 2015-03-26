package pl.edu.uj.gotowanko.exceptions.businesslogic;

import org.springframework.http.HttpStatus;

/**
 * Created by michal on 23.03.15.
 */
public class NoSuchResourceException extends BusinessLogicException {
    public NoSuchResourceException(String message) {
        super(message, HttpStatus.NOT_FOUND, new CargoDTO());
    }

    private static class CargoDTO { //to tylko przykład użycia dodatkowych elementów błędu, do wyjebania.
        String item = "additional error data";

        public String getItem() {
            return item;
        }

        public void setItem(String item) {
            this.item = item;
        }
    }
}
