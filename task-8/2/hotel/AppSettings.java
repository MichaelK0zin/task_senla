package hotel;

import config.ConfigProperty;

public class AppSettings {

    @ConfigProperty(propertyName = "HotelAdmin.lastBookingsCount", type = Integer.class)
    public int lastBookingsCount;

    @ConfigProperty(propertyName = "HotelAdmin.allowStatusChange", type = Boolean.class)
    public boolean allowStatusChange;

}
