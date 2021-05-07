package com.example.testmap

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import androidx.core.app.BundleCompat
import com.iod.mpbuilder.MeasurementProtocol

class MainActivity : AppCompatActivity() {

    val mpParam = MeasurementProtocol.Param

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var product1 = Bundle()
        product1.putString("item_id","TR5333")
        product1.putString("item_name","Kotlin%30Warhol%145T-Shirt")
        product1.putString("item_category","Apparel")
        product1.putString("item_brand","Google")
        product1.putString("item_variant","Yellow")
        product1.putString("item_position","1")
        product1.putString("item_quantity","1")
        product1.putString("item_price","32000")
        product1.putString("currency","IDR")
        product1.putString("custom_dimension1","Campaigntest")


        var product2 = Bundle()
        product2.putString("item_id","TR5334")
        product2.putString("item_name","Kotlin%30Warhol%145T-Shirt")
        product2.putString("item_category","Apparel")
        product2.putString("item_brand","Google")
        product2.putString("item_variant","Yellow")
        product2.putString("item_position","1")
        product2.putString("item_quantity","1")
        product2.putString("currency","IDR")
        product2.putString("custom_dimension1","Campaigntest")


        var items = ArrayList<Bundle>()
        items.add(product1)
        items.add(product2)

        var ecommerceBundle = Bundle()
        ecommerceBundle.putParcelableArrayList("items",items)
        ecommerceBundle.putString("cd1","testcd")
        ecommerceBundle.putString("trsss","testtrss")


        urlBuilder(ecommerceBundle)

    }

    private fun urlBuilder(bundle: Bundle) {
        val args = bundle.getParcelableArrayList<Bundle>("items")
        val keys = bundle.keySet()

        var builder = Uri.Builder()
        builder.scheme("https")
                .authority("gtm-pznw3c5-oda5y.uc.r.appspot2.com")
                .appendPath("collect")
                .appendQueryParameter(mpParam.VERSION,"1")
                .appendQueryParameter(mpParam.APP_VERSION,"1.0.1")
                .appendQueryParameter(mpParam.TRACKING_ID,"UA-126799593-3") //UA-126799593-3
                .appendQueryParameter(mpParam.CLIENT_ID,"123")
                .appendQueryParameter(mpParam.HIT_TYPE,"event")
                .appendQueryParameter(mpParam.APP_NAME,"myApp")
                .appendQueryParameter(mpParam.APP_ID,"com.example.androidhttp")
                .appendQueryParameter(mpParam.EVENT_CATEGORY,"ecommerce_iod6")
                .appendQueryParameter(mpParam.EVENT_ACTION,"iod_purchase6")
                .appendQueryParameter(mpParam.PRODUCT_ACTION,"purchase")

        for (key in keys) {
            val value = bundle.get(key)
            if (key == "items") {
                if (args != null) {
                    val items = keyValidator(args)
                    for ( i in items){
                        var tempBundle = Bundle()
                        tempBundle.putAll(i)
                        for (key in tempBundle.keySet()){
                            var values = tempBundle.get(key).toString()
                            builder.appendQueryParameter(key,values)
                            Log.d("TAG", "key: $key value: $values")
                        }
                    }
                }
            }
            else{
                builder.appendQueryParameter(key,value.toString())
            }
        }
        var myUrl = builder.build().toString()
        Log.d("TAG", "sisany key: $myUrl")
    }

    private fun keyValidator(args: ArrayList<Bundle>): ArrayList<Bundle>{

        var items = ArrayList<Bundle>()

        for ((i,value) in args.withIndex()) {
            var tempBundle = Bundle()
            var product = Bundle()

            tempBundle = value

            for (key in tempBundle.keySet()){
                when{
                    key.contains("id") -> {
                        val value = tempBundle.get(key).toString()
                        product.putString("pr"+ (i+1) +"id", value)
                        Log.d("TAG", "halo $value")
                    }
                    key.contains("name") ->  {
                        val value = tempBundle.get(key).toString()
                        product.putString("pr" + (i+1) +"nm", value)
                    }
                    key.contains("variant") ->  {
                        val value = tempBundle.get(key).toString()
                        product.putString("pr" + (i+1) +"va", value)
                    }
                    key.contains("brand") ->  {
                        val value = tempBundle.get(key).toString()
                        product.putString("pr" + (i+1) +"br", value)
                    }
                    key.contains("price") ->  {
                        val value = tempBundle.get(key).toString()
                        product.putString("pr" + (i+1)  +"pr", value)
                    }
                    key.contains("quantity") ->  {
                        val value = tempBundle.get(key).toString()
                        product.putString("pr"+ (i+1) +"qt", value)
                    }
                    key.contains("category") ->  {
                        val value = tempBundle.get(key).toString()
                        product.putString("pr" + (i+1) +"ca", value)
                    }
                    key.contains("position") ->  {
                        val value = tempBundle.get(key).toString()
                        product.putString("pr"+ (i+1) +"ps", value)
                    }
                    key.contains("currency") ->  {
                        val value = tempBundle.get(key).toString()
                        product.putString("cu", value)
                    }
                    key.contains("dimension1") ->  {
                        val value = tempBundle.get(key).toString()
                        product.putString("cd1", value)
                    }
                }
            }
            items.add(product)
        }
        return items
    }
}