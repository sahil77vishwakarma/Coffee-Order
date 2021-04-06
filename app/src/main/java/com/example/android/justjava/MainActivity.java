// Add your package below

package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;


/**
 * This app displays an order form to order coffee.
 */

public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
}

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        CheckBox whippedCreamChechBox = (CheckBox) findViewById(R.id.Whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamChechBox.isChecked();

        CheckBox chocolateCheckbox =(CheckBox) findViewById(R.id.Chocolate_checkbox);
        boolean hasChocolateCream = chocolateCheckbox.isChecked();

        EditText person_name = (EditText) findViewById(R.id.person_name);
        String name_getting = person_name.getText().toString();

       int price = calculatePrice(hasWhippedCream,hasChocolateCream);
       String allMessage = createOrderSummary(price,hasWhippedCream,hasChocolateCream, name_getting);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order summary for " + name_getting);
        intent.putExtra(Intent.EXTRA_TEXT, allMessage);
        intent.setData(Uri.parse("mailto:"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        else {
            Toast.makeText(this,"There is no such application that support the action ",Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Calculates the price of the order.
     *
     * Quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean cream, boolean chocolate) {
        int price =5;
        if(cream)
        {
            price = price + 1;
        }
        if (chocolate)
        {
            price = price + 2;
        }
        price *= quantity;
        return price;
    }

   public String createOrderSummary(int price, boolean whippedCreamCheck, boolean hasChocolateCream , String person_name)
   {
       String name = getString(R.string.order_summary_name,person_name) ;
       String priceMessage = name + "\n" + getString(R.string.add_whipped_cream,whippedCreamCheck) + whippedCreamCheck + "\n" + getString(R.string.Add_chocolate,hasChocolateCream)  + "\n" + getString(R.string.Quantity_text,quantity) + "\n" + getString(R.string.order_summary_price,price);
       priceMessage = priceMessage + "\n" + getString(R.string.thank_you);
       return priceMessage;
   }


    public void increment(View view)
    {
        if(quantity == 100)
        {
            Toast.makeText(this,"You can not have more than 100 coffee in one order",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view)
    {
        if(quantity == 1)
        {
            Toast.makeText(this,"You can not have less than 1 cofee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}