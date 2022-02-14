package fr.isen.rouveure.androiderestaurant

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.squareup.picasso.Request
import com.squareup.picasso.RequestHandler
import fr.isen.rouveure.androiderestaurant.databinding.ActivityUserBinding
import org.json.JSONObject
import java.util.regex.Pattern

interface UserActivityFragmentInteraction {
    fun showLogin()
    fun showRegister()
    fun makeRequest(email: String?, password: String?, firstname: String?, lastname: String?,adress: String?, isFromLogin: Boolean)
}

class UserActivity : AppCompatActivity(), UserActivityFragmentInteraction {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = RegisterFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit()

    }

    override fun showLogin() {
        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, loginFragment).commit()
    }

    override fun showRegister() {
        val registerFragment = RegisterFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, registerFragment).commit()
    }

   override fun makeRequest( email: String?,
                             password: String?,
                             firstname: String?,
                             lastname: String?,
                             adress: String?,
                             isFromLogin: Boolean
   ) { if(verifyInformation(email,password,firstname,lastname,adress,isFromLogin)) {
       launchRequest(email,password,firstname,lastname,adress, isFromLogin)
   } else {
       Toast.makeText(this,getString(R.string.invalidFours), Toast.LENGTH_LONG)?.show()
    }
   }

    private fun launchRequest(email: String?,
                             password: String?,
                             firstname: String?,
                             lastname: String?,
                             adress: String?,
                             isFromLogin: Boolean ) {
        val queue = Volley.newRequestQueue(this)
        var  requestPath = "http://test.api.catering.bluecodegames.com/"
        if (isFromLogin) {
           requestPath += "user/login"
        } else {
            requestPath += "user/register"
        }

        val jsonObject = JSONObject()
        jsonObject.put("id_shop", "1")
        jsonObject.put("email", email)
        jsonObject.put("password", password)
        jsonObject.put("firstname", firstname)
        jsonObject.put("lastname", lastname)
        jsonObject.put("adress", adress)
        if (!isFromLogin) {
            val jsonObject = JSONObject()
            jsonObject.put("firstname", firstname)
            jsonObject.put("lastname", lastname)
            jsonObject.put("adress", adress)
        }

        val jsonRequest = JsonObjectRequest(
            com.android.volley.Request.Method.POST, requestPath, jsonObject, {
               // Success
                Log.d("request", it.toString(2))
                val userResult = GsonBuilder().create().fromJson(it.toString(), UserResult::class.java)
                if (userResult.data != null) {
                    saveUser(userResult.data)
                }else {
                    Toast.makeText(this, "mauvais login", Toast.LENGTH_LONG).show()
                }
            }, {
                // Failure
                Log.e("request", it.message?:"une erreur c'est produite")
            }
        )
       queue.add(jsonRequest)

    }



    private fun saveUser(user: User) {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ID_USER, user.id)
        editor.apply()

        setResult(Activity.RESULT_OK)
        finish()

        val intent = Intent(this, OrderActivity::class.java)
        startActivity((intent))


    }

    private fun verifyInformation( email: String?,
                           password: String?,
                           firstname: String?,
                           lastname: String?,
                           adress: String?,
                           isFromLogin: Boolean
    ): Boolean {
        var verified = (email?.isNotEmpty() == true && isEmailValid(email) && password?.count() ?: 0 >= 6)

        if(!isFromLogin) {
            verified = verified && (firstname?.isNotEmpty() == true && lastname?.isNotEmpty() == true && adress?.isNotEmpty() == true)
        }
        return verified
    }

    companion object {
        const val USER_PREFERENCES_NAME = ""
        const val ID_USER = "ID_USER"
    }

    fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

}