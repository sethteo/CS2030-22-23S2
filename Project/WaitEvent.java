class WaitEvent implements Event {
    private final Customer customer;
    private final Server server;
    private final ServeManager servers;
    
    WaitEvent(Customer customer, Server server, ServeManager servers) {
        this.customer = customer;
        this.servers = servers;
        this.server = server;
    }

    @Override
    public Counter updateCount(Counter counter) {
        return counter;
    }

    @Override
    public boolean last() {
        return false;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s waits at %s\n", getTime(), customer, server.toString());
    }

    @Override
    public Pair<ServeManager, Event> process(ServeManager sm) {
        //checking if current server is SCO
        //if is a SCO, ensures that the continuewait time
        //is the shortest among all SCO
        if (this.server.isSCO()) {
            double shortest = sm.getMostUpdatedServeTime();
            return new Pair<ServeManager, Event>(sm, 
                new ContinueWaiting(customer, server, sm, shortest));
        }
        return new Pair<ServeManager, Event>(sm, 
                new ContinueWaiting(customer, server, sm, server.serveTime()));
    }

    /*-----------------------------------for comparator--------------------------------------- */
    @Override
    public double getTime() {
        return this.customer.getArrivalTime();
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }
    /*---------------------------------------------------------------------------------------- */

}
