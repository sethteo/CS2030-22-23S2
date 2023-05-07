import java.util.Optional;

class ServeManager {
    private final ImList<Server> servers;
    private final ImList<Server> selfcheck;
    private final int scqueue;

    ServeManager(ImList<Server> s, ImList<Server> sco, int q) {
        servers = s;
        selfcheck = sco;
        scqueue = q;
    }

    //Updates the server argument with the server in ServeManager's list
    public ServeManager updateServer(Server s) {  
        int id = s.getID();
        if (id > servers.size()) {
            ImList<Server> update = selfcheck;
            update = update.set(id - servers.size() - 1, s);
            return new ServeManager(servers, update, scqueue);
        } else {
            ImList<Server> update = servers;
            update = update.set(s.getID() - 1, s);
            return new ServeManager(update, selfcheck, scqueue);
        }
    }

    //Used to update ServeManager's SCO queue
    public ServeManager updateQueue(Server s, boolean bool) {
        int id = s.getID();
        // is a self check server and is a queue event
        if (id > servers.size() && bool) {
            int result = scqueue;
            result--;
            return new ServeManager(servers, selfcheck, result);
        } else if (id > servers.size() && !bool) {
            //is a self check server is a done serve
            int result = scqueue;
            result++;
            return new ServeManager(servers, selfcheck, result);
        }
        return this;
    }
    
    public Server getServer(int index) {
        try {
            return selfcheck.get(index - servers.size());
        } catch (Exception e) {
            return servers.get(index);
        }
    }

    //Used in ArrivalEvent
    public Pair<Boolean, Server> canServe(Double time) {
        //looping through human servers
        for (int i = 0; i < servers.size(); i++) {
            Server current = servers.get(i);
            if (current.availableAt(time)) {
                return new Pair<Boolean, Server>(true, current);
            }
        }
        //looping through self check outs
        for (int i = 0; i < selfcheck.size(); i++) {
            Server current = selfcheck.get(i);
            if (current.availableAt(time)) {
                return new Pair<Boolean,Server>(true, current);
            }
        }
        return new Pair<Boolean,Server>(false, new Server());
    }

    //Used in ArrivalEvent
    public Pair<Boolean, Server> canWait() {
        //looping through human servers
        for (int i = 0; i < servers.size(); i++) {
            Server current = servers.get(i);
            if (current.qsize() > 0) {
                current = current.addQueue();
                return new Pair<Boolean,Server>(true, current);
            }
        }
        //checking if self check out queue is full
        try {
            if (this.scqueue > 0) {
                return new Pair<Boolean,Server>(true, selfcheck.get(0));
            }
        } catch (Exception e) {
            return new Pair<Boolean,Server>(false, new Server());
        }
        return new Pair<Boolean,Server>(false, new Server());
    }

    //Looping through all self check outs and serving if possible
    public Pair<Boolean, Server> scoServe(Double time) {
        for (int i = 0; i < selfcheck.size(); i++) {
            Server current = selfcheck.get(i);
            if (current.availableAt(time)) {
                return new Pair<Boolean,Server>(true, current);
            }
        }
        return new Pair<Boolean, Server>(false, new Server());
    }


    //Getting the most updated serveTime among all self check outs
    public double getMostUpdatedServeTime() {
        Double result = selfcheck.get(0).serveTime();
        for (int i = 0; i < selfcheck.size(); i++) {
            if (selfcheck.get(i).serveTime() < result) {
                result = selfcheck.get(i).serveTime();
            }
        } 
        return result;
    }
}



