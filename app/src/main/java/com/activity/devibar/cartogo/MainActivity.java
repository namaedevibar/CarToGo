package com.activity.devibar.cartogo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.activity.devibar.cartogo.Adapter.ShoppingAdapter;
import com.activity.devibar.cartogo.ShoppingCart.ShoppingCart;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private TextView mDisplay;
    private ShoppingCart item = null;
    private ListView mListView;
    private List<ShoppingCart> dataSource = new ArrayList<>();
    private Double totalPriceAll=0.00;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);


        mDisplay = (TextView)findViewById(R.id.txtTotalPrice);
        mListView = (ListView) findViewById(R.id.listView);



        if (dataSource.isEmpty()){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("EMPTY CART");
            builder.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.shoppingcart_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.Add_menu: Intent intent = new Intent(MainActivity.this,ScannerActivity.class);
                startActivityForResult(intent,1);
                break;

            case R.id.Sms_menu: sms();
                break;
            case R.id.Email_menu: email();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && data!=null){
            if (resultCode == RESULT_OK){
                String edit = data.getStringExtra("RESULT");
                listing(edit);
            } }



    }


    public void listing(String data) {
        try {
            StringTokenizer itemToken = new StringTokenizer(data, "|||");

            String[] itemContent = new String[3];

            while (itemToken.hasMoreTokens()) {

                itemContent[0] = itemToken.nextElement().toString();
                itemContent[1] = itemToken.nextElement().toString();
                itemContent[2] = itemToken.nextElement().toString();

            }

            if (Integer.parseInt(itemContent[1]) >= 1 &&
                    Double.parseDouble(itemContent[2]) >= 1) {


                item = new ShoppingCart(itemContent[0], Integer.parseInt(itemContent[1]),
                        Double.parseDouble(itemContent[2]));

                dataSource.add(item);

                totalPriceAll += item.getPrice() * item.getQuantity();;
                mDisplay.setText(String.format("%.2f", totalPriceAll) + "");

                ShoppingAdapter adapter = new ShoppingAdapter(this, R.layout.item_list, dataSource);

                mListView.setAdapter(adapter);

            }
        }catch(Exception e){
            Toast.makeText(this,"INVALID.",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,ScannerActivity.class);
            startActivityForResult(intent,1);
        }
    }

    public String store(){

        ShoppingCart temp = null;
        String message = "";
        for (int loop = 0; loop < dataSource.size(); loop++) {
            temp = dataSource.get(loop);
            message += "Item Name: "+ temp.getItem() + System.getProperty("line.separator")
                    + "Quantity: " + temp.getQuantity() + System.getProperty("line.separator")
                    +"Price: "+ temp.getPrice() + System.getProperty("line.separator")
                    + System.getProperty("line.separator") + System.getProperty("line.separator");
        }

        message += "TOTAL PRICE: " +String.format("%.2f",totalPriceAll);
        return message;
    }



    public void sms()
    {
        if (!dataSource.isEmpty()){
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("sms_body",store());
            startActivity(smsIntent);
        }
        else   {
            Toast.makeText(this,"Please add an order first.",Toast.LENGTH_SHORT).show();
        }
    }

    public void email(){
        if (!dataSource.isEmpty()){
            Intent intent = new Intent(Intent.ACTION_SEND);

            intent.putExtra(Intent.EXTRA_SUBJECT, "Shopping Cart");
            intent.putExtra(Intent.EXTRA_TEXT, store() );
            intent.setType("message/rfc822");

            Intent chooser = Intent.createChooser(intent, getString(R.string.email));
            startActivity(chooser);
        }
        else   {
            Toast.makeText(this,"Please add an order first.",Toast.LENGTH_SHORT).show();
        }
    }

}