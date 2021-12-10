package com.truongthicamly.a19532211_truongthicamly_ad_todoapp.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialog_add.*
import com.truongthicamly.a19532211_truongthicamly_ad_todoapp.R
import android.widget.EditText
import com.google.firebase.ktx.Firebase
import com.truongthicamly.a19532211_truongthicamly_ad_todoapp.model.User


class FireMissilesDialogFragment: DialogFragment() {
    private lateinit var database: DatabaseReference
    private var editTextId: EditText? = null
    private var editTextTitel: EditText? = null
    private val TAG = "ReadAndWriteSnippets"
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;
            val view :View = inflater.inflate(R.layout.dialog_add,null)
            builder.setView(view)
                // Add action buttons
                .setPositiveButton("SAVE",
                    DialogInterface.OnClickListener { dialog, id ->
                        val id : Int = editTextId?.text.toString().toInt()
                        val title : String = editTextTitel?.text.toString()
                        val user= User(title,title,id)
                        add(user)
                     //   writeNewUser(title,title)
                      //  onclickAddUser(user)
                      //  writeNewPost(title,title,title,title)

                    })
                .setNegativeButton("CANCEL",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            editTextId = view.findViewById(R.id.edtId)

            editTextTitel = view.findViewById(R.id.edtAdd)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


    fun add(user: User){
        val  database = FirebaseDatabase.getInstance()
        val key = database.getReference("list_users")
        val pathObject: String = java.lang.String.valueOf(user.id)
        key.child(pathObject).setValue(user
        ) { error, ref -> Log.e("Ly", "Thanh cong"); }
    }

    fun onclickAddUser(user: User){
        val  database = FirebaseDatabase.getInstance().reference
        val key = database.child("posts").push().key
        if (key == null) {
            Log.w(TAG, "Couldn't get push key for posts")
            return
        }
        val postValues = user.toMap()
        val childUpdates = hashMapOf<String, Any>(
            "/posts/$key" to postValues
        )
        database.updateChildren(childUpdates)
    }





    private fun addPostEventListener(postReference: DatabaseReference) {
        // [START post_value_event_listener]
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val post = dataSnapshot.value
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        postReference.addValueEventListener(postListener)
        // [END post_value_event_listener]
    }

    // [START write_fan_out]
    private fun writeNewPost(userId: String, username: String, title: String, body: String) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        database = FirebaseDatabase.getInstance().reference
        val key = database.child("posts").push().key
        if (key == null) {
            Log.w(TAG, "Couldn't get push key for posts")
            return
        }

        val post = Post(userId, username)
        val postValues = post.toMap()

        val childUpdates = hashMapOf<String, Any>(
            "/posts/$key" to postValues
        )

        database.updateChildren(childUpdates)
    }
    // [END write_fan_out]


    // [START post_stars_transaction]
    private fun onStarClicked(postRef: DatabaseReference) {
        // [START_EXCLUDE]
        val uid = ""
        // [END_EXCLUDE]
        postRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val p = mutableData.getValue(Post::class.java)
                    ?: return Transaction.success(mutableData)

                if (p.stars.containsKey(uid)) {
                    // Unstar the post and remove self from stars
                    p.starCount = p.starCount - 1
                    p.stars.remove(uid)
                } else {
                    // Star the post and add self to stars
                    p.starCount = p.starCount + 1
                    p.stars[uid] = true
                }

                // Set value and report transaction success
                mutableData.value = p
                return Transaction.success(mutableData)
            }

            override fun onComplete(
                databaseError: DatabaseError?,
                committed: Boolean,
                currentData: DataSnapshot?,
            ) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError!!)
            }
        })
    }
    // [END post_stars_transaction]

    // [START post_stars_increment]
    private fun onStarClicked(uid: String, key: String) {
        val updates: MutableMap<String, Any> = HashMap()
        updates["posts/$key/stars/$uid"] = true
        updates["posts/$key/starCount"] = ServerValue.increment(1)
        updates["user-posts/$uid/$key/stars/$uid"] = true
        updates["user-posts/$uid/$key/starCount"] = ServerValue.increment(1)
        database.updateChildren(updates)
    }
}