package com.fbproject1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class MainActivity : AppCompatActivity() {
    internal val random = Random()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "CREATED")

        val bClose = findViewById<View>(R.id.bClose) as Button
        bClose.setOnClickListener { Log.d(TAG, "EXITING 1"); finish() }

        /*
        val bOffline = findViewById<View>(R.id.bOffline) as Button
        bOffline.setOnClickListener { FirebaseFirestore.getInstance().disableNetwork() }

        val bOnline = findViewById<View>(R.id.bOffline) as Button
        bOnline.setOnClickListener { FirebaseFirestore.getInstance().enableNetwork() }
*/
        val bAdd = findViewById<View>(R.id.bAdd) as Button
        bAdd.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            val cities = db.collection("cities")

            Log.d(TAG, "DO ADD 1000")
            for (i in 1..1000) {
                val data1 = HashMap<String, Any>()
                val r1 = random.nextInt()
                data1["ID"] = "ID " + i
                data1["RND"] = "${r1}"
                //cities.document("Дата:" + Calendar.getInstance().getTime().toString()).set(data1)
                cities.document("N: " + i + " " + r1).set(data1)
                        .addOnSuccessListener { Log.d(TAG, "document set ok " + r1 + " " + i) }
                        .addOnFailureListener { e -> Log.w(TAG, "document set error", e) }
            }
            Log.d(TAG, "DONE ADD")
        }

        val bDelAll = findViewById<View>(R.id.bDelAll) as Button
        bDelAll.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            val cities = db.collection("cities")
            cities.get().addOnSuccessListener { result ->
                var i = 0
                Log.d(TAG, "DEL COUNT = " + result.count())
                for (d in result) {
                    Log.d(TAG, "" + i + ": ${d.id} => ${d.data}")
                    i++
                    cities.document(d.getId()).delete();
                }
                Log.d(TAG, "DEL DONE COUNT = " + result.count())
            }

        }


        val bFind = findViewById<View>(R.id.bFind) as Button
        bFind.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            val cities = db.collection("cities")
            //cities.whereGreaterThanOrEqualTo("ID", "2").get().addOnSuccessListener { result ->
            cities.whereGreaterThanOrEqualTo("ID", "ID 3").get().addOnSuccessListener { result ->
                var i = 0
                Log.d(TAG, "FIND COUNT = " + result.count())
                for (d in result) {
                    Log.d(TAG, "" + i + ": ${d.id} => ${d.data}")
                    i++
                }
                Log.d(TAG, "FIND DONE COUNT = " + result.count())
            }

        }

        val bTest = findViewById<View>(R.id.bTest) as Button
        bTest.setOnClickListener {
            var i = 0
            Log.d(TAG, "START LOOP")
            for (j in 1 .. 10) {
                Log.d(TAG, "${i} => ${j}")
                i++
            }
            Log.d(TAG, "END LOOP")
   }

    }

    companion object {
        private val TAG = "AAA"
    }
}
