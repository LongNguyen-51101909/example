package code;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nqlong.example.IAdditionService;
import com.example.nqlong.example.R;
import com.example.nqlong.example.service.AdditionService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created by nqlong on 03-Jun-16.
 */
public class AIDLDemo extends Activity {
    private static final String TAG = "AIDLDemo";
    IAdditionService service;
    AdditionServiceConnected connection;
    ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final boolean isServer = initService();
        /*serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder boundService) {
                service = IAdditionService.Stub.asInterface(boundService);
                Log.d(AIDLDemo.TAG, "onServiceConnected() connected");
                Toast.makeText(AIDLDemo.this, "Service connected", Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                service = null;
                Log.d(AIDLDemo.TAG, "onServiceDisconnected() disconnected");
                Toast.makeText(AIDLDemo.this, "Service connected", Toast.LENGTH_LONG)
                        .show();
            }
        };*/
        // Setup the UI
        Button buttonCalc = (Button) findViewById(R.id.buttonCalc);
        final TextView result = (TextView) findViewById(R.id.result);
        final EditText value1 = (EditText) findViewById(R.id.value1);
        final EditText value2 = (EditText) findViewById(R.id.value2);

        buttonCalc.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                int v1, v2, res = -1;
                v1 = NumberUtils.toInt(value1.getText().toString());
                v2 = NumberUtils.toInt(value2.getText().toString());
                try {
                    res = service.add(v1, v2);
                } catch (RemoteException e) {
                    Log.d(AIDLDemo.TAG, "onClick failed with: " + e);
                    e.printStackTrace();
                }
                result.setText(new Integer(res).toString());

            }
        });
        /*Intent intent = new Intent(this, AdditionService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);*/
    }

    /**
     * This class represents the actual service connection. It casts the bound
     * stub implementation of the service to the AIDL interface.
     */
    class AdditionServiceConnected implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = IAdditionService.Stub.asInterface(boundService);
            Toast.makeText(AIDLDemo.this, "Service connected", Toast.LENGTH_LONG)
                    .show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            service = null;
            Toast.makeText(AIDLDemo.this, "Service connected", Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * Binds his activity to the service
     */
    private boolean initService() {
        connection = new AdditionServiceConnected();
        Intent i = new Intent(this, AdditionService.class);
        i.setClassName(getPackageName(), getLocalClassName());
        boolean ret = bindService(i, connection, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "initService() bound with " + ret);
        return ret;
    }

    /**
     * Unbinds this activity from the service.
     */
    private void releaseService() {
        unbindService(connection);
        connection = null;
        Log.d(TAG, "releaseService() unbound.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseService();
    }
}
