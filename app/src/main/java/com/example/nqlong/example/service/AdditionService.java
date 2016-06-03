package com.example.nqlong.example.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.nqlong.example.IAdditionService;

/**
 * Created by nqlong on 03-Jun-16.
 */
public class AdditionService extends Service {
    public static final String TAG = "AdditionService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return service;
    }

    IAdditionService.Stub service =new IAdditionService.Stub() {
        @Override
        public int add(int value1, int value2) throws RemoteException {
            Log.d(TAG, String.format("AdditionService.add(%d, %d)",value1, value2));
            return value1 + value2;
        }
    };
}
