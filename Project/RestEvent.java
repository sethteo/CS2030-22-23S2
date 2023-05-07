class RestEvent implements Event {
    private final Customer customer;
    private final Server server;
    private final ServeManager servers;
    private final double time;

    RestEvent(Customer c, Server s, ServeManager sm, double t) {
        customer = c;
        server = s;
        servers = sm;
        time = t;
    }
    
    @Override
    public Pair<ServeManager, Event> process(ServeManager sm) {
        Server current = sm.getServer(this.server.getID() - 1);
        Double resttime = current.getRestTime();
        current = current.makeBusy(resttime + this.time);
        sm = sm.updateServer(current);
        return new Pair<ServeManager,Event>(sm, new DoneResting(customer, server,
        sm, this.time + resttime));
    }
    
    @Override
    public boolean last() {
        return false;
    }
    
    @Override
    public Counter updateCount(Counter counter) {
        return counter;
    }

    @Override
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
