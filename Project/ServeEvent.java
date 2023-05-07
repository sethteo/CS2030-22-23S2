class ServeEvent implements Event {
    private final Server server;
    private final Customer customer;
    private final ServeManager servers;
    private final double time;
    private final boolean wait;

    ServeEvent(Customer customer, Server server, ServeManager servers) {
        this.server = server;
        this.customer = customer;
        this.servers = servers;
        this.time = customer.getArrivalTime();
        this.wait = false;
    }

    ServeEvent(Customer customer, Server server, ServeManager servers, double time, 
        boolean wait) {
        this.server = server;
        this.customer = customer;
        this.servers = servers;
        this.time = time;
        this.wait = wait;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s serves by %s\n",
                this.getTime(), this.customer.getID(),
                this.server.toString());
    }

    @Override
    public Pair<ServeManager, Event> process(ServeManager sm) {
        Server currServer = sm.getServer(server.getID() - 1);
        if (this.wait) {
            currServer = currServer.serveQueue();
            sm = sm.updateQueue(currServer, false);
        }
        currServer = currServer.makeBusy(customer.getServiceTime() + this.time);
        sm = sm.updateServer(currServer);     
        return new Pair<ServeManager, Event>(sm,
                new DoneEvent(customer, currServer, sm));
    }

    @Override
    public Counter updateCount(Counter counter) {
        counter = counter.wait(time - customer.getArrivalTime());
        return counter.serve();
    }

    @Override
    public boolean last() {
        return false;
    }

    /*-----------------------------------for comparator--------------------------------------- */
    @Override
    public double getTime() {
        return this.time;
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }
    /*---------------------------------------------------------------------------------------- */

}
