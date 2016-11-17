package io.muic.dcom.p2;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
//




public class DataModel {
    public static class ParcelObserved {
        private String parcelId;
        private String stationId;
        private long timeStamp;


        ParcelObserved(String parcelId_, String stationId_, long ts_) {
            this.parcelId = parcelId_;
            this.stationId = stationId_;
            this.timeStamp = ts_;
        }

        public String getParcelId() { return parcelId; }
        public String getStationId() { return stationId; }
        public long getTimeStamp() { return timeStamp; }
        public ParcelObserved parcelObserved;
    }

    private List<ParcelObserved> transactions;
    private ConcurrentHashMap<String,List<ParcelObserved>> ps = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String,Long> sp = new ConcurrentHashMap<>();
    private long count;

    DataModel() {
        transactions = new ArrayList<>();
        count = 0L;


    }


    public void postObserve(String parcelId, String stationId, long timestamp) {


        ParcelObserved parcelObserved = new ParcelObserved(parcelId, stationId, timestamp);

        if (!ps.containsKey(parcelId)){
            List<ParcelObserved> a = new ArrayList<>();
            a.add(parcelObserved);
            ps.put(parcelId, a);
//
        }
        else {
            ps.get(parcelId).add(parcelObserved);

        }


        if (sp.containsKey(stationId)){
            long count = sp.get(stationId);
            count += 1L;

            sp.put(stationId, count);
        } else {
            sp.put(stationId, 1L);
        }


    }

    public List<ParcelObserved> getParcelTrail(String parcelId) {
        if (ps.containsKey(parcelId)) {
            return new ArrayList<>(ps.get(parcelId));

        } else{
            return new ArrayList<>();

        }

    }

    public long getStopCount(String stationId) {



        if (!sp.containsKey(stationId)){
            return 0L;
        }
        return sp.get(stationId);

    }
}





///////////////////////////////////////////////////////////////////



//package io.muic.dcom.p2;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class DataModel {
//    public static class ParcelObserved {
//        private String parcelId;
//        private String stationId;
//        private long timeStamp;
//
//        ParcelObserved(String parcelId_, String stationId_, long ts_) {
//            this.parcelId = parcelId_;
//            this.stationId = stationId_;
//            this.timeStamp = ts_;
//        }
//
//        public String getParcelId() { return parcelId; }
//        public String getStationId() { return stationId; }
//        public long getTimeStamp() { return timeStamp; }
//    }
//
//    private List<ParcelObserved> transactions;
//
//    DataModel() {
//        transactions = new ArrayList<>();
//    }
//
//    public synchronized void postObserve(String parcelId, String stationId, long timestamp) {
//        ParcelObserved parcelObserved = new ParcelObserved(parcelId, stationId, timestamp);
//        transactions.add(parcelObserved);
//    }
//
//    public synchronized List<ParcelObserved> getParcelTrail(String parcelId) {
//        return transactions.stream()
//                .filter(observeEvent -> observeEvent.parcelId.equals(parcelId))
//                .collect(Collectors.toList());
//    }
//
//    public synchronized long getStopCount(String stationId) {
//        return transactions.stream()
//                .filter(observeEvent -> observeEvent.stationId.equals(stationId))
//                .count();
//    }
//}
//
