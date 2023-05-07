class LeavesEvent implements Event { 
    private final Customer customer;
    private final ServeManager servers;

    LeavesEvent(Customer customer, ServeManager sm) {
        this.customer = customer;
        this.servers = sm;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s leaves\n", this.customer.getArrivalTime(),
                this.customer.getID());
    }

    @Override
    public Counter updateCount(Counter counter) {
        return counter.leave();
    }


    @Override
    public Pair<ServeManager, Event> process(ServeManager sm) {
        return new Pair<ServeManager,Event>(sm, this);
    }

    @Override
    public boolean last() {
        return true;
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

