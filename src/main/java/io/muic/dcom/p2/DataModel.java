package io.muic.dcom.p2;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.stream.Collectors;



public class DataModel {
    public static class ParcelObserved {
        private String parcelId;
        private String stationId;
        private long timeStamp;
//        public long count ;



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


//        if(!ps.containsKey(parcelId)){
//            ConcurrentSkipListSet<ParcelObserved> a = new ConcurrentSkipListSet<>();
//            a.add(parcelObserved);
////            System.out.println(a);
//            ps.put(parcelId, a);
//        }
//        else {
//            ps.get(parcelId).add(parcelObserved);
//        }
//
//
//
//        if (sp.containsKey(stationId) == false){
//            sp.put(stationId,1L);
//        }
//        else {
//            long count = sp.get(stationId); //value
//            count += 1;
//            sp.put(stationId, count);
//        }

        if (!ps.containsKey(parcelId)){
            List<ParcelObserved> a = new ArrayList<>();
            a.add(parcelObserved);
            ps.put(parcelId, a);
//
        }
        else {
            ps.get(parcelId).add(parcelObserved);

        }

//        sp.put(stationId, count += 1);

        if (sp.containsKey(stationId)){
            long count = sp.get(stationId);
            count += 1L;

            sp.put(stationId, count);
        } else {
            sp.put(stationId, 1L);
        }


//        transactions.add(parcelObserved);
    }

    public List<ParcelObserved> getParcelTrail(String parcelId) {
//        return null;
        if (ps.containsKey(parcelId)) {
//            System.out.println(ps.get(parcelId));
            return new ArrayList<>(ps.get(parcelId));
//            return ps.get(parcelId).stream()
//                    .collect(Collectors.toList());
        } else{
            return null;

        }
//        return ps.get(parcelId).stream()
////                .filter(observeEvent -> observeEvent.parcelId.equals(parcelId))
//                .collect(Collectors.toList());
    }

    public long getStopCount(String stationId) {

        return sp.get(stationId);
//                .filter(observeEvent -> observeEvent.stationId.equals(stationId))
//                .count();
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
