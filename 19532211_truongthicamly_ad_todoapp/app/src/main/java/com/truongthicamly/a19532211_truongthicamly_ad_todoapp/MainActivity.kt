package com.truongthicamly.a19532211_truongthicamly_ad_todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.truongthicamly.a19532211_truongthicamly_ad_todoapp.adapter.RecycleAdapter
import com.truongthicamly.a19532211_truongthicamly_ad_todoapp.model.User
import com.truongthicamly.a19532211_truongthicamly_ad_todoapp.ui.FireMissilesDialogFragment
import com.truongthicamly.a19532211_truongthicamly_ad_todoapp.ui.Post
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.DividerItemDecoration

import android.R.string.no




class MainActivity : AppCompatActivity() {
    var data = ArrayList<User>()
    lateinit var adapter: RecycleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAdd.setOnClickListener {
            confirmFireMissiles()
        }

        data = ArrayList<User>()
        data.add(User("call mom","hello",1))
        recycle_view.layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        recycle_view.addItemDecoration(dividerItemDecoration)
        getList()
    }
    fun confirmFireMissiles() {
        val newFragment = FireMissilesDialogFragment()
        newFragment.show(supportFragmentManager, "missiles")
    }

//    fun readDatabase(){
//        val database = FirebaseDatabase.getInstance()
//        var myRef = database.getReference("message")
//
//        myRef.addValueEventListener(object :ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val value = snapshot.value
//                Log.d("Ly", "Value is: $value")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("Ly", "Failed to read value.", error.toException())
//            }
//        })
//    }
//
//    private fun addPostEventListener(postReference: DatabaseReference) {
//        // [START post_value_event_listener]
//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // Get Post object and use the values to update the UI
//                val user: Post = dataSnapshot.getValue(Post::class.java) as Post
//
//                // ...
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                Log.w("Ly", "loadPost:onCancelled", databaseError.toException())
//            }
//        }
//        postReference.addValueEventListener(postListener)
//        // [END post_value_event_listener]
//    }
    fun getList(){
        val  database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("list_users")

        myRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
        //        Log.w("Ly", "loadPost:onCancelled ${snapshot}")
                for (postSnapshot in snapshot.children) {
//                    Log.w("Ly", "loadPost:onCancelled ${snapshot.getValue(User::class.java)}")
//                    val user: User? = snapshot.getValue(User::class.java)
//                    if (user != null) {
//                        Log.w("Ly", "loadPost:onCancelled ${user.id}")
//                        data.add(user)
//                    }


                }
                adapter = RecycleAdapter(data)
                recycle_view.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Ly", "loadPost:onCancelled", error.toException())
            }
        })
    }
}