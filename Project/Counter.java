class Counter {
    private final int served;
    private final int leave;
    private final double averageWait;

    Counter(int served, int leave, double averageWait) {
        this.served = served;
        this.leave = leave;
        this.averageWait = averageWait;
    }

    public Counter serve() {
        return new Counter(this.served + 1, this.leave, averageWait);
    }

    public Counter leave() {
        return new Counter(this.served, this.leave + 1, averageWait);
    }

    public Counter wait(double time) {
        return new Counter(served, leave, time + averageWait);
    }

    @Override
    public String toString() {
        double finalWait = 0.00;
        if (served != 0) {
            finalWait = averageWait / served;
        }
        return String.format("[%.3f %s %s]",
                finalWait, served, leave);
    }
}
