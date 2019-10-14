package com.future.fock.mock;
public class Client {

    public Data request(final String param) {
        //立即返回FutureData
        final FutureData futureData = new FutureData();
        //开启ClientThread线程装配RealData
        new Thread(new Runnable() {
			
			@Override
			public void run() {
				 //装配RealData
                RealData realData = new RealData(param);
                futureData.setRealData(realData);
			}
		}).start();
        return futureData;
    }
}