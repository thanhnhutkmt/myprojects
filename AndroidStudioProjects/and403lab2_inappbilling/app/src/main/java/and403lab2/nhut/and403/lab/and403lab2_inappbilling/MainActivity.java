package and403lab2.nhut.and403.lab.and403lab2_inappbilling;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.android.vending.billing.IInAppBillingService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    IInAppBillingService mService;
    ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            Log.i("MyTag", "onServiceDisconnected mService is null "+ (mService == null));
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
            Log.i("MyTag", "onServiceConnected mService is null "+ (mService == null));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button btn = (Button) findViewById(R.id.btn_next);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyitem();
            }
        });
        Button btnreset = (Button) findViewById(R.id.btn_reset);
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mService == null) try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        resetPurchaseItem();
                    }
                }).start();
            }
        });
        bindService(new Intent("com.android.vending.billing.InAppBillingService.BIND")
            .setPackage("com.android.vending"), mServiceConn, Context.BIND_AUTO_CREATE);
    }

    private void buyitem() {
        try {
            queryItem();
            Bundle buyIntentBundle = mService.getBuyIntent(3, getPackageName(),
                    "drink", "inapp", "123456789");
            startIntentSenderForResult(
                    ((PendingIntent)buyIntentBundle.getParcelable("BUY_INTENT"))
                            .getIntentSender(), 1001, new Intent(), Integer.valueOf(0),
                    Integer.valueOf(0), Integer.valueOf(0));
            Bundle buyIntentBundle1 = mService.getBuyIntent(3, getPackageName(),
                    "gas", "inapp", "123456789");
            startIntentSenderForResult(
                    ((PendingIntent)buyIntentBundle1.getParcelable("BUY_INTENT"))
                            .getIntentSender(), 1001, new Intent(), Integer.valueOf(0),
                    Integer.valueOf(0), Integer.valueOf(0));
            Bundle buyIntentBundle2 = mService.getBuyIntent(3, getPackageName(),
                    "infinite_gas", "inapp", "123456789");
            startIntentSenderForResult(
                    ((PendingIntent)buyIntentBundle2.getParcelable("BUY_INTENT"))
                            .getIntentSender(), 1001, new Intent(), Integer.valueOf(0),
                    Integer.valueOf(0), Integer.valueOf(0));
            Bundle buyIntentBundle3 = mService.getBuyIntent(3, getPackageName(),
                    "food", "subs", "123456789");
            startIntentSenderForResult(
                    ((PendingIntent)buyIntentBundle3.getParcelable("BUY_INTENT"))
                            .getIntentSender(), 1001, new Intent(), Integer.valueOf(0),
                    Integer.valueOf(0), Integer.valueOf(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int BILLING_RESPONSE_RESULT_OK = 0;
    private static int CONSUME_RESULT_OK = 0;

    private void queryItem() {
        ArrayList<String> skuList = new ArrayList<String> ();
        skuList.add("infinite_gas");
        skuList.add("gas");
        skuList.add("drink");
        final Bundle querySkus = new Bundle();
        querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bundle skuDetails = null;
                try {
                    skuDetails = mService.getSkuDetails(3,
                            getPackageName(), "inapp", querySkus);
                    int response = skuDetails.getInt("RESPONSE_CODE");
                    if (response == BILLING_RESPONSE_RESULT_OK) {
                        ArrayList<String> responseList
                                = skuDetails.getStringArrayList("DETAILS_LIST");

                        for (String thisResponse : responseList) {
                            try {
                                JSONObject object = new JSONObject(thisResponse);
                                String sku = object.getString("productId");
                                String price = object.getString("price");
                                Log.i("MyTag", sku + " : " + price);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mService != null) {
            unbindService(mServiceConn);
        }
    }

    private void resetPurchaseItem() { //only for managed/inapp/consumable item; not for subscription, subs will end after its period of time
        Log.i("MyTag", "resetPurchaseItem");
        Bundle ownedItems = null;
        try {
            ownedItems = mService.getPurchases(3, getPackageName(), "inapp", null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        int response = ownedItems.getInt("RESPONSE_CODE");
        if (response == 0) {
            ArrayList<String> ownedSkus =
                ownedItems.getStringArrayList("INAPP_PURCHASE_ITEM_LIST");
            ArrayList<String>  purchaseDataList =
                ownedItems.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
            ArrayList<String>  signatureList =
                ownedItems.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
            String continuationToken =
                ownedItems.getString("INAPP_CONTINUATION_TOKEN");

            for (int i = 0; i < purchaseDataList.size(); ++i) {
                String purchaseData = purchaseDataList.get(i);
                if (purchaseData == null) continue;
                String signature = signatureList.get(i);
                String sku = ownedSkus.get(i);
                try {
                    int respcode = mService.consumePurchase(3, getPackageName(),
                            new JSONObject(purchaseData).getString("purchaseToken"));
                    Log.i("MyTag", "item " + sku + ", consume code " + respcode
                        + ", purchaseData " + purchaseData + ", signature " + signature);
                    if (respcode == CONSUME_RESULT_OK)
                        Log.i("MyTag", "item " + sku + " consumed!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MyTag", "onActivityResult " + requestCode + ", " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");
            Log.i("MyTag", "onActivityResult " + responseCode + ", " + dataSignature);

            if (resultCode == RESULT_OK) {
                try {
                    JSONObject jo = new JSONObject(purchaseData);
                    String sku = jo.getString("productId");
//                    int response = mService.consumePurchase(3, getPackageName(),
//                        jo.getString("purchaseToken"));
                    Log.i("MyTag", "You have bought the " + sku + ". Excellent choice, adventurer!");
                    Toast.makeText(this, "You have bought the " + sku + ". Excellent choice," +
                            "adventurer!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.i("MyTag", "Failed to parse purchase data.");
                    Toast.makeText(this, "Failed to parse purchase data.", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }
}

