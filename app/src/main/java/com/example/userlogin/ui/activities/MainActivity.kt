package com.example.userlogin.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.userlogin.R
import com.example.userlogin.adapter.ConvertImage
import com.example.userlogin.adapter.MySharedPreferences
import com.example.userlogin.adapter.UserProfileViewModel
import com.example.userlogin.databinding.ActivityMainBinding
import com.example.userlogin.model.UserProfile
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.item_user_messenger.view.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        val mainViewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]
        mainViewModel.users.observe(this){
            if(it.isNotEmpty()){
                Log.e("get view model from", "main")
                updateUI(it)
            }
        }



        setSupportActionBar(binding.appBarMain.toolbar)


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_friends
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        /*val menuBinding = binding.appBarMain.toolbar.menu
        val btnSignOut = menuBinding.findItem(R.id.action_sign_out)*/



    }

    private fun updateUI(list: ArrayList<UserProfile>?) {
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        if (list != null) {
            Log.e("list main", "not empty")
            for(user in list){
                if(user.uid == uid){
                    Log.e("image url ", user.imgUrl)
                    val tvHeaderNav = binding.navView.getHeaderView(0).findViewById<TextView>(R.id.tv_nav_username)
                    val imgHeaderNav = binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.img_nav_profile)

                    ConvertImage(this).urlToImage(user.imgUrl, imgHeaderNav)
                    tvHeaderNav.text = user.displayName
                }
            }
        }
        else{
            Log.e("list main", "empty")
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_sign_out->{
                FirebaseAuth.getInstance().signOut()
                MySharedPreferences(this).putBooleanValue("isLogged?", false)
                val intent = Intent(this, SplashActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish()
            }
            R.id.action_settings->{
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    /*private fun getIntentAndUpdate(){
        val userProfile: UserProfile = intent.getSerializableExtra("user profile") as UserProfile
        val navBinding = NavHeaderMainBinding.inflate(layoutInflater)
        navBinding.imgNavProfile.setImageURI(Uri.parse(userProfile.imageUrl))
        navBinding.tvNavUsername.text = userProfile.usernameProfile
    }*/
}