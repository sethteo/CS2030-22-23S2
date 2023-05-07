class ContinueWaiting implements Event {
    private final Customer customer;
    private final Server server;
    private final ServeManager servers;
    private final double nextServeTime;

    ContinueWaiting(Customer c, Server s, ServeManager ss, Double t) {
        customer = c;
        server = s;
        servers = ss;
        //nextServeTime is the result of the Server's serveTime() function which 
        //returns the Customer ArrivalTime + Customer ServiceTime as that is 
        //when the Server would be busy until
        nextServeTime = t;
    }

    @Override
    public String toString() {
        return "";
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
    public Pair<ServeManager, Event> process(ServeManager sm) {
        int serverID = server.getID();
        Server mostUpdated = sm.getServer(serverID - 1);
        //checking if current Server can serve
        //else return continuewait
        if (!mostUpdated.isSCO()) {
            if (mostUpdated.availableAt(nextServeTime)) {
                return new Pair<ServeManager, Event>(sm, 
                        new ServeEvent(customer, mostUpdated, sm, 
                            nextServeTime, true));
            } else {
                return new Pair<ServeManager, Event>(sm, 
                    new ContinueWaiting(customer, server, sm, 
                        mostUpdated.serveTime()));
            }
        } else {
            //if current server is an SCO server, checks if any SCO 
            //in list can server
            if (sm.scoServe(nextServeTime).first()) {
                mostUpdated = sm.scoServe(nextServeTime).second();
                return new Pair<ServeManager, Event>(sm, 
                        new ServeEvent(customer, mostUpdated, sm, 
                            nextServeTime, true));
            }
            //if unable to serve
            //returns a continuewait with the earliest serve time
            //amongst all SCO
            Double newtime = sm.getMostUpdatedServeTime();
            return new Pair<ServeManager, Event>(sm, 
                new ContinueWaiting(customer, mostUpdated, sm, newtime));
        }
    }

    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public double getTime() {
        return nextServeTime;
    }
}
