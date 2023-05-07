import java.util.function.Supplier;

class Simulator {
    private final int numOfServers;
    private final int numOfSelfChecks;
    private final int qmax;
    private final ImList<Double> arrivalTimes;
    private final Supplier<Double> serviceTimes;
    private final Supplier<Double> restTimes;

    Simulator(int numOfServers, int numOfSelfChecks, 
        int qmax, ImList<Double> arrivalTimes, 
        Supplier<Double> serviceTimes,
        Supplier<Double> restTimes) {
        this.numOfServers = numOfServers;
        this.numOfSelfChecks = numOfSelfChecks;
        this.qmax = qmax;
        this.arrivalTimes = arrivalTimes;
        this.serviceTimes = serviceTimes;
        this.restTimes = restTimes;
    }

    public String simulate() {
        Counter counter = new Counter(0, 0, 0.0);
        String str = "";
        ServeManager sm = new ServeManager(createServers().first(), createServers().second(), qmax);
        PQ<Event> pq = createPQ(sm);
        
        while (!pq.isEmpty()) {
            Pair<Event, PQ<Event>> pair = pq.poll();
            Event e = pair.first();
            counter = e.updateCount(counter);
            str += e.toString();
            pq = pair.second();
            Pair<ServeManager, Event> currentPair = e.process(sm);
            Event currentEvent = currentPair.second();
            sm = currentPair.first();

            if (!e.last()) {
                pq = pq.add(currentEvent);   
            }
        }
        str += counter.toString();
        return str;
    }

    private Pair<ImList<Server>, ImList<Server>> createServers() {
        ImList<Server> servers = new ImList<Server>();
        ImList<Server> selfcheck = new ImList<Server>();

        for (int i = 1; i <= numOfServers; i++) {
            servers = servers.add(new Server(i, 0.0, qmax, restTimes));
        }
        if (numOfServers > 0) {
            for (int i = numOfServers + 1; i <= numOfSelfChecks + numOfServers; i++) {
                selfcheck = selfcheck.add(new SelfCheckOut(i, 0.0, qmax, restTimes));
            }
        } else {
            for (int i = 1; i <= numOfSelfChecks; i++) {
                selfcheck = selfcheck.add(new SelfCheckOut(i, 0.0, qmax, restTimes));
            } 
        }
        
        return new Pair<ImList<Server>, ImList<Server>>(servers, selfcheck);
    }

    private PQ<Event> createPQ(ServeManager sm) {
        PQ<Event> pq = new PQ<Event>(new EventComparator());
        for (int i = 0; i < this.arrivalTimes.size(); i++) {
            Customer c = new Customer(this.arrivalTimes.get(i), i + 1, serviceTimes);
            pq = pq.add(new ArrivalEvent(c, sm));
        }
        return pq;
    }
}
