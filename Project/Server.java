import java.util.function.Supplier;

class Server {
    private final int serverID;
    private final Double serveTime;
    private final int queue;
    private final Supplier<Double> restTimes;

    Server(int id, Double time, int queue, Supplier<Double> restTimes) {
        this.serverID = id;
        this.serveTime = time;
        this.queue = queue;
        this.restTimes = restTimes;
    }

    // null server
    Server() {
        this.serverID = 0;
        this.serveTime = 0.0;
        this.queue = 0;
        Supplier<Double> serviceTimes = () -> 1.0;
        this.restTimes = serviceTimes;
    }

    Server(Server s, Double time) {
        this.serverID = s.serverID;
        this.serveTime = time;
        this.queue = s.queue;
        this.restTimes = s.restTimes;
    }

    public boolean availableAt(Double n) {
        return n >= serveTime;
    }

    public Server addQueue() {
        int qsize = queue;
        qsize--;
        return new Server(serverID, this.serveTime, qsize, restTimes);
    }

    public Server serveQueue() {
        int q = queue;
        q++;
        return new Server(serverID, this.serveTime, q, restTimes);
    }

    public Double getRestTime() {
        double resttime = this.restTimes.get();
        return resttime;
    }

    public int qsize() {
        return this.queue;
    }

    public int getID() {
        return this.serverID;
    }

    public Server makeBusy(Double t1) {
        return new Server(this.serverID, t1, this.queue, restTimes);
    }
    
    public Double serveTime() {
        return this.serveTime;
    }

    public boolean isSCO() {
        return false;
    }

    @Override
    public String toString() {
        return "" + this.serverID;
    }
}
