package com.isen.ERestaurant.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.isen.ERestaurant.R
import com.isen.ERestaurant.adapter.CarousselAdapter
import com.isen.ERestaurant.databinding.ActivityCartDetailBinding
import com.isen.ERestaurant.objects.CartData
import com.isen.ERestaurant.objects.CartItem
import java.io.*


class CartDetailActivity : AppCompatActivity() {


    private lateinit var binding: ActivityCartDetailBinding
    private var count = 1
    lateinit var item: CartItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        item = intent.getSerializableExtra(CartActivity.ITEM_KEY) as CartItem
        binding.detailTitle2.text = item.plat.name_fr


        val carousseladapter = CarousselAdapter(this,item.plat.images)
        binding.detailSlider2.adapter =  carousseladapter
        count = item.quantité
        binding.compteurView2.text = count.toString()
        binding.totalView2.text=getString(R.string.detail_modifier,(item.plat.prices[0].price.toFloat()*count).toString())
        binding.prixView2.text = getString(R.string.detail_prix,item.plat.prices[0].price)
        binding.ingredientsView2.text = getString(R.string.detail_ingredients,item.plat.ingredients.joinToString { it.name_fr })
        var actionBar = supportActionBar
        actionBar!!.title = item.plat.name_fr
        actionBar.setIcon(R.drawable.ic_shopping_cart_24)
        actionBar.setDisplayHomeAsUpEnabled(true)
        binding.totalView2.setOnClickListener{
            modifyCart(it.context)
        }
        binding.buttonSupprimer.setOnClickListener{
            delete(it.context)
        }
    }
    fun delete(context: Context){
        val intent = Intent(this, CartActivity::class.java)
        val cartitem = CartItem(item.plat,count)
        val itemsPresent = Gson().fromJson(getStringFromFile(), CartData::class.java)
        itemsPresent.data.remove(cartitem)
        val gson = Gson()
        if (itemsPresent.data.isEmpty()){
            create(context,"panier.json","null")
        }else{
            val jsonString2 = gson.toJson(itemsPresent)
            create(context,"panier.json",jsonString2)
        }
        startActivity(intent)
        Toast.makeText(context, cartitem.plat.name_fr+" supprimé", Toast.LENGTH_SHORT).show()
    }
    fun getStringFromFile():String{
        var jsonString: String
        jsonString = ""
        try {

            val fos: FileInputStream = openFileInput("panier.json")
            val size: Int = fos.available()
            val buffer = ByteArray(size)
            val gson = Gson()
            fos.read(buffer)
            fos.close()
            jsonString = String(buffer, charset("UTF-8"))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return jsonString
    }
    fun modifyCart(context: Context) {
        val intent = Intent(this, CartActivity::class.java)
        val cartitem = CartItem(item.plat,count)
            val itemsPresent = Gson().fromJson(getStringFromFile(), CartData::class.java)
            for (i in itemsPresent.data.indices){
                if (itemsPresent.data[i].plat.name_fr==cartitem.plat.name_fr){
                    itemsPresent.data[i].quantité=count
                    val gson = Gson()
                    val jsonString2 = gson.toJson(itemsPresent)
                    create(context,"panier.json",jsonString2)
                }
            }
        startActivity(intent)
        Toast.makeText(context, cartitem.plat.name_fr+" modifié", Toast.LENGTH_SHORT).show()
    }
    fun decrement(view : View){
        count++
        binding.compteurView2.text=(""+count)
        binding.totalView2.text=getString(R.string.detail_modifier,(item.plat.prices[0].price.toFloat()*count).toString())

    }
    fun increment(view : View){
        if (count<=1) count =1
        else count--
        binding.compteurView2.text=(""+count)
        binding.totalView2.text=getString(R.string.detail_modifier,(item.plat.prices[0].price.toFloat()*count).toString())
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.panier -> {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.commande ->{
                return true
            }
            R.id.vider ->{
                create(this,"panier.json", "null")
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun create(context: Context, fileName: String, jsonString: String?): Boolean {
        val FILENAME = "panier.json"
        return try {
            val fos: FileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            if (jsonString != null) {
                fos.write(jsonString.toByteArray())
            }
            fos.close()
            true
        } catch (fileNotFound: FileNotFoundException) {
            false
        } catch (ioException: IOException) {
            false
        }
    }
    fun isFilePresent(context: Context, fileName: String): Boolean {
        val path: String = context.getFilesDir().getAbsolutePath().toString() + "/" + fileName
        val file = File(path)
        return file.exists()
    }
}