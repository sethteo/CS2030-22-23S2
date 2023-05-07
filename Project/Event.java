interface Event {
    public Customer getCustomer();

    public double getTime();

    public Pair<ServeManager, Event> process(ServeManager sm);

    public boolean last();
    
    public Counter updateCount(Counter counter);
    
}

