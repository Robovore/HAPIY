package com.hapiy

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hapiy.ui.body.BodyFragment
import com.hapiy.ui.create.CreateFragment
import com.hapiy.ui.home.HomeFragment
import com.hapiy.ui.mind.MindFragment
import com.hapiy.ui.sleep.SleepFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class MainActivity : AppCompatActivity() {
    //Track daily input tracking
    // database[date][pillar][key] = value
    // database[20201117][SLEEP][CONS] = value
    var database = arrayOfNulls<Array<Array<Int>>>(20)


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

    fun setPillarValue(date: Int?, pillar: Int?, key: Int?, value: Int?) {
        if (value != null) {
            if (pillar != null) {
                database[date!!]?.get(pillar)?.set(key!!, value)
            }
        };
    }

    fun getPillarValue(date: Int?, pillar: Int?, key: Int?): Int? {
        return database[date!!]?.get(pillar!!)?.get(key!!);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateAsInt(date: LocalDateTime? ): Int {
        if (date != null) {
            return (date.format(DateTimeFormatter.BASIC_ISO_DATE)).toInt()
        }
        return 0;
    }
}
