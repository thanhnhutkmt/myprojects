package comicreader.software.nhut.doc_truyen.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.vending.billing.IInAppBillingService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import comicreader.software.nhut.doc_truyen.R;

/**
 * Created by Nhut on 8/9/2017.
 */

public class MyInAppBilling {
    /**
     * 1.Add
     * <uses-permission android:name="com.android.vending.BILLING" />
     * <uses-permission android:name="android.permission.INTERNET" />
     * compile 'com.google.android.gms:play-services:11.0.4'
     * main/src/aidl/com.android.vending.billing/IInAppBillingService
     * write product id and drawable name of all products into below arrays
     * build.gradle(app) compile 'com.android.support:multidex:1.0.1'
     *                   defaultConfig { multiDexEnabled true}
     * manifest : android:name="android.support.multidex.MultiDexApplication"
     *
     * 2.Code
     * In activity : add
     *  + private MyInAppBilling purchase;
     *  + purchase = new MyInAppBilling(this);
     *  + onActivityResult(int requestCode, int resultCode, Intent data) {
     *                      purchase.proceedBuyingProcess(requestCode, resultCode, data); }
     *  + if (purchase.isReady()) purchase.showProductDialog();
     *
     * 3.Server
     * If error in-app "app version not configure for inapp billing..."
     *  ++ Any change in code(more different from uploaded apk) may cause error verCode
     *  ++ LogCat : error verCode -> build signed APK and reupload latest version apk
     * Add test email (buy in test mode so not charged money ) :
     *  ++ All-app->setting->Account detail->License Testing->gõ email test vào(cách nhau dấu ',')
     * Create product for selling :
     *  ++ All-app->tên app->store presence->in-app products->tạo các product để bán
     *
     * DONE
    **/
    private String inapp_productIdArray[] = {"download7", "download30", "download90"};
    private int inapp_productImageArray[] = {R.drawable.item_download7, R.drawable.item_download30,
            R.drawable.item_download90};
    private String subscription_productIdArray[] = {"noads7", "noads30", "noads90"};
    private int subscription_productImageArray[] = {R.drawable.item_noads7,
            R.drawable.item_noads30, R.drawable.item_noads90};

    private Context context;
    private static int BILLING_RESPONSE_RESULT_OK = 0;
    private static int CONSUME_RESULT_OK = 0;
    private boolean ready = false;
    private IInAppBillingService mService;
    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            Log.i("MyTag", "onServiceDisconnected mService is null "+ (mService == null));
        }
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IInAppBillingService.Stub.asInterface(service);
            Log.i("MyTag", "onServiceConnected mService is null "+ (mService == null));
            if (context.getSharedPreferences("MyInAppBilling", Context.MODE_PRIVATE)
                .getBoolean(error, false))
                resetPurchaseItem();
            ready = true;
        }
    };

    public MyInAppBilling(Context context) {
        this.context = context;
        this.context.bindService(new Intent("com.android.vending.billing.InAppBillingService.BIND")
            .setPackage("com.android.vending"), mServiceConn, Context.BIND_AUTO_CREATE);
    }

    public boolean isReady() {
        return ready;
    }
    private void buyitem(String itemId) {
        try {
            ((AppCompatActivity)context).startIntentSenderForResult(
                ((PendingIntent)mService.getBuyIntent(3, context.getPackageName(),
                        itemId, getType(itemId), "").getParcelable("BUY_INTENT"))
                    .getIntentSender(), 1001, new Intent(), Integer.valueOf(0),
                    Integer.valueOf(0), Integer.valueOf(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String error = "luuyeucaureset";
    public void proceedBuyingProcess(int requestCode, int resultCode, Intent data) {
        Log.i("MyTag", "onActivityResult " + requestCode + ", " + resultCode);
        if (requestCode == 1001) {
            int responseCode = data.getIntExtra("RESPONSE_CODE", 0);
            String purchaseData = data.getStringExtra("INAPP_PURCHASE_DATA");
            String dataSignature = data.getStringExtra("INAPP_DATA_SIGNATURE");
            Log.i("MyTag", "onActivityResult " + responseCode + ", " + dataSignature);

            if (resultCode == ((Activity)context).RESULT_OK) {
                try {
                    int response = mService.consumePurchase(3, context.getPackageName(),
                        new JSONObject(purchaseData).getString("purchaseToken"));
                } catch (Exception e) {
                    e.printStackTrace();
                    context.getSharedPreferences("MyInAppBilling", Context.MODE_PRIVATE)
                        .edit().putBoolean(error, true);
                }
            }
        }
    }

    private String getType(String itemId) {
        for (int i = 0; i < inapp_productIdArray.length; i++) return "inapp";
        for (int i = 0; i < subscription_productIdArray.length; i++) return "subs";
        return "";
    }

    private List<MyAppProduct> list = new ArrayList<>();
    private void queryItem() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle skuInapp = null, skuSubs = null;
                    Bundle querySkus = new Bundle();
                    ArrayList<String> skuList = new ArrayList<String> ();
                    for (String id : inapp_productIdArray) skuList.add(id);
                    querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
                    skuInapp = mService.getSkuDetails(3,
                            context.getPackageName(), "inapp", querySkus);
                    skuList.clear();
                    for (String id : subscription_productIdArray) skuList.add(id);
                    querySkus.putStringArrayList("ITEM_ID_LIST", skuList);
                    skuSubs = mService.getSkuDetails(3,
                            context.getPackageName(), "subs", querySkus);
                    if ((skuInapp.getInt("RESPONSE_CODE") + skuSubs.getInt("RESPONSE_CODE"))
                        == BILLING_RESPONSE_RESULT_OK) {
                        List<String> listProduct = skuInapp.getStringArrayList("DETAILS_LIST");
                        listProduct.addAll(skuSubs.getStringArrayList("DETAILS_LIST"));
                        list.clear();
                        for (String product : listProduct) list.add(new MyAppProduct(product));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                    }
                });
            }
        }).start();
    }

    public void stop() {
        if (mService != null) {
            context.unbindService(mServiceConn);
        }
    }

    //only for managed/inapp/consumable item;
    // not for subscription, subs will end after its period of time
    // or manually set on ggole play store
    public void resetPurchaseItem() {
        Log.i("MyTag", "resetPurchaseItem");
        Bundle ownedItems = null;
        try {
            ownedItems = mService.getPurchases(3, context.getPackageName(), "inapp", null);
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
                    int respcode = mService.consumePurchase(3, context.getPackageName(),
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

    private ProgressDialog pd;
    public AlertDialog showProductDialog() {

        queryItem();
        //1.show loading dialog
        pd = new ProgressDialog(context);
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
        pd.setCancelable(false);
        //2.show product dialog
        ListView productList = new ListView(context);
        productList.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                if (convertView == null)
                    convertView = View.inflate(context, R.layout.item_inappbilling, null);
                ImageView iv = (ImageView)convertView.findViewById(R.id.productimg);
                TextView tv = (TextView)convertView.findViewById(R.id.producttitle);
                iv.setImageDrawable(context.getResources()
                    .getDrawable(list.get(position).getImageurl()));
                tv.setText(list.get(position).toString());
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        buyitem(list.get(position).getId());
                    }
                });
                return convertView;
            }
        });
        AlertDialog.Builder b = new AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.productdialog_title))
        .setView(productList)
        .setPositiveButton(context.getString(R.string.downloadfinish),
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog ad = b.create();
        b.show();
        return ad;
    }

    public class MyAppProduct {
        private String id;
        private String title;
        private String price;
        private String priceUnit;
        private String description;
        private int imageurl;

        @Override
        public String toString() {
            return title.replace(" (Read comics)", "") +
                    "\n" + price +
                    "\n" + description;
        }

        public MyAppProduct(String product) {
            try {
                JSONObject object = new JSONObject(product);
                id = object.getString("productId");
                title = object.getString("title");
                price = object.getString("price");
                description = object.getString("description");
                priceUnit = object.getString("price_currency_code");
                imageurl = getImageDrawable(id);
                Log.i("MyTag", String.format("productId %s : %s, %s %s, %s",
                        id, title, priceUnit, price, description));
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("MyTag", "error");
            }
        }

        private int getImageDrawable(String id) {
            for (int i = 0; i < inapp_productIdArray.length; i++)
                if (id.equals(inapp_productIdArray[i])) return inapp_productImageArray[i];
            for (int i = 0; i < subscription_productIdArray.length; i++)
                if (id.equals(subscription_productIdArray[i])) return subscription_productImageArray[i];
            return 0;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPriceUnit() {
            return priceUnit;
        }

        public void setPriceUnit(String priceUnit) {
            this.priceUnit = priceUnit;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getImageurl() {
            return imageurl;
        }

        public void setImageurl(int imageurl) {
            this.imageurl = imageurl;
        }
    }
}
