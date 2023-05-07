import java.util.function.Supplier;

class Customer {
    private final Double arrivalTime;
    private final int customerID;
    private final Supplier<Double> serviceTime;

    Customer(Double time, int id, Supplier<Double> serviceTime) {
        this.arrivalTime = time;
        this.customerID = id;
        this.serviceTime = serviceTime;
    }
    
    public Double getArrivalTime() {
        return this.arrivalTime;
    }

    public Double getServiceTime() {
        return this.serviceTime.get();
    }

    public int getID() {
        return this.customerID;
    }

    public Customer changeTime(Double a) {
        return new Customer(a, customerID, serviceTime);
    }

    @Override
    public String toString() {
        return "" + this.customerID;
    }
}
