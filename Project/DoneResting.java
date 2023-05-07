class DoneResting implements Event {
    private final Customer customer;
    private final Server server;
    private final ServeManager servers;
    private final double time;

    DoneResting(Customer c, Server s, ServeManager sm, double t) {
        customer = c;
        server = s;
        servers = sm;
        time = t;
    }

    public Pair<ServeManager, Event> process(ServeManager sm) {
        return new Pair<ServeManager,Event>(sm, 
            new DoneResting(customer, server, sm, this.getTime()));
    }

    public boolean last() {
        return true;
    }
    
    public Counter updateCount(Counter counter) {
        return counter;
    }

    public String toString() {
        return "";
    }

    /*-----------------------------------for comparator--------------------------------------- */
    @Override
    public double getTime() {
        return time;
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }
    /*---------------------------------------------------------------------------------------- */
}
