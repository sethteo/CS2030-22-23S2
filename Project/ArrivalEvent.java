class ArrivalEvent implements Event {
    private final Customer customer;
    private final ServeManager sm;

    ArrivalEvent(Customer customer, ServeManager sm) {
        this.customer = customer;
        this.sm = sm;
    }

    @Override
    public String toString() {
        return String.format("%.3f %s arrives\n", 
                this.customer.getArrivalTime(), this.customer.getID());    
    }

    @Override
    public Pair<ServeManager, Event> process(ServeManager sm) {
        if (sm.canServe(customer.getArrivalTime()).first()) {
            return new Pair<ServeManager, Event>(sm, new ServeEvent(customer, 
                sm.canServe(customer.getArrivalTime()).second(), sm)); 
        }
        if (sm.canWait().first()) {
            Server current = sm.canWait().second();
            sm = sm.updateServer(current);
            sm = sm.updateQueue(current, true);
            return new Pair<ServeManager,Event>(sm, 
                     new WaitEvent(customer, current, sm));
        } 
        return new Pair<ServeManager, Event>(sm, new LeavesEvent(customer, sm));
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
        return this.customer.getArrivalTime();
    }

    @Override
    public Customer getCustomer() {
        return this.customer;
    }
    /*---------------------------------------------------------------------------------------- */

}
