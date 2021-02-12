package com.hapiy

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hapiy.ui.home.HappyScoringFragment
import com.hapiy.ui.body.BodyFragment
import com.hapiy.ui.body.BodyScoringFragment
import com.hapiy.ui.create.CreateFragment
import com.hapiy.ui.home.HomeFragment
import com.hapiy.ui.mind.CreateScoringFragment
import com.hapiy.ui.mind.MindFragment
import com.hapiy.ui.mind.MindScoringFragment
import com.hapiy.ui.sleep.SleepFragment
import com.hapiy.ui.sleep.SleepScoringFragment
import kotlinx.android.synthetic.main.fragment_body.*
import kotlinx.android.synthetic.main.fragment_body_scoring.*
import kotlinx.android.synthetic.main.fragment_create_scoring.*
import kotlinx.android.synthetic.main.fragment_mind_scoring.*
import kotlinx.android.synthetic.main.fragment_sleep_scoring.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    //Track daily input tracking
    // database[date][pillar][key] = value
    // database[20201117][SLEEP][TYPE] = value
    var database = Array(1000) {Array(5) {Array(10) {0} } }
//    var database = arrayOfNulls<Array<Array<Int>>>(1000000)
    enum class PILLAR { MAIN, SLEEP, BODY, MIND, CREATE }
    enum class MAIN_TYPE { OVERALL }

    // Manage Sleep Scorings
    enum class SLEEP_TYPE { SLEEP_SCORE, SLEEP_TIME_AVG, WAKE_TIME_AVG }
    val sleepTimeDatabase: Queue<Int> = LinkedList<Int>()
    val wakeTimeDatabase: Queue<Int> = LinkedList<Int>()

    // Manage Fitness Scorings
    enum class BODY_TYPE { FOOD_SCORE, LOW_FITNESS, HIGH_FITNESS, GOOD_FOOD, BAD_FOOD, FITNESS_SCORE }

    // Manage Mind Scorings
    enum class MIND_TYPE { MIND_SCORE, RELAX_SCORE, STRESS_SCORE }

    // Manage Create Scorings
    enum class CREATE_TYPE { CREATE_SCORE, ART, MUSIC, WORK, READ, COOK, CODE, DANCE }

    // Track if logging all scores or just single pillar
    var boolAddAll = 0;


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(navListener)

        // Keep the selected fragment when rotating the device
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

    fun addBtnClicked(v: View?) {
        val fragment: Fragment = SleepScoringFragment()
        val fm = supportFragmentManager
        val transaction: FragmentTransaction? = fm?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment, fragment)
        transaction?.commit()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveMindLogging(v: View?) {
        // Save score
        database?.get(getDateAsInt(LocalDateTime.now()))?.get(MainActivity.PILLAR.MIND.ordinal)
            ?.set(MainActivity.MIND_TYPE.MIND_SCORE.ordinal,
                mindSeeker.progress
            )

        // Return to Sleep Fragment
        if(boolAddAll == 0) {
            val fragment: Fragment = MindFragment()
            val fm = supportFragmentManager
            val transaction: FragmentTransaction? = fm?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragment)
            transaction?.commit()
        }
        else{
            val fragment: Fragment = CreateScoringFragment()
            val fm = supportFragmentManager
            val transaction: FragmentTransaction? = fm?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragment)
            transaction?.commit()
        }
    }

    fun addMindBtnClicked(v: View?) {
        val fragment: Fragment = MindScoringFragment()
        val fm = supportFragmentManager
        val transaction: FragmentTransaction? = fm?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment, fragment)
        transaction?.commit()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun bodyLoggingBtnClicked(v: View?) {
        // Save values
        var fitnessAvg = (lowFitnessSeeker.progress + highFitnessSeeker.progress) / 2
        var foodAvg = (goodFoodSeeker.progress + badFoodSeeker.progress) / 2
        database?.get(getDateAsInt(LocalDateTime.now()))?.get(MainActivity.PILLAR.BODY.ordinal)
            ?.set(MainActivity.BODY_TYPE.FITNESS_SCORE.ordinal,
                fitnessAvg
            )
        database?.get(getDateAsInt(LocalDateTime.now()))?.get(MainActivity.PILLAR.BODY.ordinal)
            ?.set(MainActivity.BODY_TYPE.LOW_FITNESS.ordinal,
                lowFitnessSeeker.progress
            )
        database?.get(getDateAsInt(LocalDateTime.now()))?.get(MainActivity.PILLAR.BODY.ordinal)
            ?.set(MainActivity.BODY_TYPE.HIGH_FITNESS.ordinal,
                highFitnessSeeker.progress
            )
        database?.get(getDateAsInt(LocalDateTime.now()))?.get(MainActivity.PILLAR.BODY.ordinal)
            ?.set(MainActivity.BODY_TYPE.FOOD_SCORE.ordinal,
                foodAvg
            )
        database?.get(getDateAsInt(LocalDateTime.now()))?.get(MainActivity.PILLAR.BODY.ordinal)
            ?.set(MainActivity.BODY_TYPE.GOOD_FOOD.ordinal,
                goodFoodSeeker.progress
            )
        database?.get(getDateAsInt(LocalDateTime.now()))?.get(MainActivity.PILLAR.BODY.ordinal)
            ?.set(MainActivity.BODY_TYPE.BAD_FOOD.ordinal,
                badFoodSeeker.progress
            )

        // Return to Sleep Fragment
        if(boolAddAll == 0) {
            val fragment: Fragment = BodyFragment()
            val fm = supportFragmentManager
            val transaction: FragmentTransaction? = fm?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragment)
            transaction?.commit()
        }
        else{
            val fragment: Fragment = MindScoringFragment()
            val fm = supportFragmentManager
            val transaction: FragmentTransaction? = fm?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragment)
            transaction?.commit()
        }
    }

    fun bodyAddClicked(v: View?) {
        val fragment: Fragment = BodyScoringFragment()
        val fm = supportFragmentManager
        val transaction: FragmentTransaction? = fm?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment, fragment)
        transaction?.commit()
    }

    fun addAllScores(v: View?) {
        boolAddAll = 1;
        val fragment: Fragment = HappyScoringFragment()
        val fm = supportFragmentManager
        val transaction: FragmentTransaction? = fm?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment, fragment)
        transaction?.commit()
    }

    fun saveMood(v: View?) {
        if(boolAddAll == 1) {
            val fragment: Fragment = SleepScoringFragment()
            val fm = supportFragmentManager
            val transaction: FragmentTransaction? = fm?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragment)
            transaction?.commit()
        }
        else
        {
            val fragment: Fragment = HomeFragment()
            val fm = supportFragmentManager
            val transaction: FragmentTransaction? = fm?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragment)
            transaction?.commit()
        }
    }

    fun addCreateBtn(v: View?) {
        val fragment: Fragment = CreateScoringFragment()
        val fm = supportFragmentManager
        val transaction: FragmentTransaction? = fm?.beginTransaction()
        transaction?.replace(R.id.nav_host_fragment, fragment)
        transaction?.commit()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveCreateBtn(v: View?) {
        // Save create info
        database?.get(getDateAsInt(LocalDateTime.now()))?.get(MainActivity.PILLAR.CREATE.ordinal)
            ?.set(MainActivity.CREATE_TYPE.CREATE_SCORE.ordinal,
                createSeeker.progress
            )

        // Return to Sleep Fragment
        if(boolAddAll == 0) {
            val fragment: Fragment = CreateFragment()
            val fm = supportFragmentManager
            val transaction: FragmentTransaction? = fm?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragment)
            transaction?.commit()
        }
        else {
            boolAddAll = 0;
            val fragment: Fragment = HomeFragment()
            val fm = supportFragmentManager
            val transaction: FragmentTransaction? = fm?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragment)
            transaction?.commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sleepSaveBtnClicked(v: View?) {
        // Save values
        sleepTimeDatabase?.add(sleepTime.progress)
        wakeTimeDatabase?.add(wakeTime.progress)
        database?.get(getDateAsInt(LocalDateTime.now()))?.get(MainActivity.PILLAR.SLEEP.ordinal)
            ?.set(MainActivity.SLEEP_TYPE.SLEEP_TIME_AVG.ordinal,
                sleepTimeDatabase.average().toInt()
            )
        database?.get(getDateAsInt(LocalDateTime.now()))?.get(MainActivity.PILLAR.SLEEP.ordinal)
            ?.set(MainActivity.SLEEP_TYPE.WAKE_TIME_AVG.ordinal,
                wakeTimeDatabase.average().toInt()
            )
        database?.get(getDateAsInt(LocalDateTime.now()))?.get(MainActivity.PILLAR.SLEEP.ordinal)
            ?.set(MainActivity.SLEEP_TYPE.SLEEP_SCORE.ordinal,
                (sleepTime.progress+wakeTime.progress)
            )

        // Return to Sleep Fragment
        if(boolAddAll == 0) {
            val fragment: Fragment = SleepFragment()
            val fm = supportFragmentManager
            val transaction: FragmentTransaction? = fm?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragment)
            transaction?.commit()
        }
        else{
            val fragment: Fragment = BodyScoringFragment()
            val fm = supportFragmentManager
            val transaction: FragmentTransaction? = fm?.beginTransaction()
            transaction?.replace(R.id.nav_host_fragment, fragment)
            transaction?.commit()
        }

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

    // database[20201117][SLEEP][TYPE] = value
    fun setPillarValue(date: Int?, pillar: Int?, key: Int?, value: Int?) {
        database[date!!]?.get(pillar!!)?.set(key!!, value!!);
    }

    fun getPillarValue(date: Int?, pillar: Int?, key: Int?): Int? {
        return database[date!!]?.get(pillar!!)?.get(key!!)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateAsInt(date: LocalDateTime? ): Int {
        if (date != null) {
            var dateCode = (date.format(DateTimeFormatter.ISO_ORDINAL_DATE)) //Year and day of year	'2012-337'

            val dateValues: List<String> = dateCode.split("-").map { it -> it.trim() }
            return (365 * (dateValues[0].toInt()-2021)) + dateValues[1].toInt()
        }
        return 0;
    }
}
