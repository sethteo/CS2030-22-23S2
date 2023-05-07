import java.util.Comparator;

class EventComparator implements Comparator<Event> {
    public int compare(Event x, Event y) {
        if (x.getTime() == y.getTime()) {
            return x.getCustomer().getID() - y.getCustomer().getID();
        }
        if (x.getTime() > y.getTime()) {
            return 1;
        } else {
            return -1;
        }
    }
}

