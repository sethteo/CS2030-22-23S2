import java.util.function.Supplier;

class SelfCheckOut extends Server {

    SelfCheckOut(int id, Double time, int queue, Supplier<Double> restTimes) {
        super(id, time, queue, restTimes);
    }

    SelfCheckOut(Server s, Double time) {
        super(s, time);
    }

    @Override
    public Double getRestTime() {
        return 0.0;
    }

    @Override
    public String toString() {
        return "self-check " + super.getID();
    }

    @Override
    public Server serveQueue() {
        return this;
    }

    public boolean isSCO() {
        return true;
    }

    @Override
    public Server makeBusy(Double t1) {
        return new SelfCheckOut(this, t1);
    }
}
