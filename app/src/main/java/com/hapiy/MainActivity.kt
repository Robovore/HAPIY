package com.hapiy

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hapiy.ui.body.BodyFragment
import com.hapiy.ui.create.CreateFragment
import com.hapiy.ui.home.HomeFragment
import com.hapiy.ui.home.HomeViewModel
import com.hapiy.ui.mind.MindFragment
import com.hapiy.ui.sleep.SleepFragment


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(navListener)

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment,
                HomeFragment()
            ).commit()
        }



    }

    private val navListener: BottomNavigationView.OnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                var selectedFragment: Fragment? = null
                when (item.getItemId()) {
                    R.id.navigation_sleep -> selectedFragment = SleepFragment()
                    R.id.navigation_body -> selectedFragment = BodyFragment()
                    R.id.navigation_home -> selectedFragment = HomeFragment()
                    R.id.navigation_mind -> selectedFragment = MindFragment()
                    R.id.navigation_create -> selectedFragment = CreateFragment()
                }
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment,
                    selectedFragment!!
                ).commit()
                return true
            }
        }

    fun homeBtnClicked(v: View?) {
        val fragment: Fragment = HomeFragment()
        val fm = supportFragmentManager
        val transaction: FragmentTransaction = fm.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()

    }


    fun getVisibleFragment(): Fragment? {
        val fragmentManager =
            this@MainActivity.supportFragmentManager
        val fragments =
            fragmentManager.fragments
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment != null && fragment.isVisible) return fragment
            }
        }
        return null
    }
}
