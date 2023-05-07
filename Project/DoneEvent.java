class DoneEvent implements Event {
    private final Customer customer;
    private final Server server;
    private final ServeManager servers;
    
    DoneEvent(Customer c, Server s, ServeManager servers) {
        this.customer = c;
        this.server = s;
        this.servers = servers;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s done serving by %s\n", 
        server.serveTime(), customer.getID(), server.toString());
    }

    @Override
    public Pair<ServeManager, Event> process(ServeManager servers) {
        Server updated = servers.getServer(server.getID() - 1);
        return new Pair<ServeManager,Event>(servers, 
            new RestEvent(customer, updated, servers, this.getTime()));
    }

    @Override
    public Counter updateCount(Counter counter) {
        return counter;
    }

    @Override
    public boolean last() {
        return false;
    }
    
    /*-----------------------------------for comparator--------------------------------------- */
    @Override
    public double getTime() {
        return this.server.serveTime();
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }
    /*---------------------------------------------------------------------------------------- */


}
