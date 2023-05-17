package com.example.myemobilis_project

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var imageView: ImageView
    private lateinit var businessTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var phoneNumberTextView: TextView
    private lateinit var editButton: Button
    private lateinit var deleteButton: Button
    private lateinit var logoutButton: Button
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_view, container, false)
        imageView=view.findViewById(R.id.image_dview)
        businessTextView=view.findViewById(R.id.business_name_dtextview)
        descriptionTextView=view.findViewById(R.id.description_name_dtextview)
        phoneNumberTextView=view.findViewById(R.id.phone_number_dtextview)
        editButton=view.findViewById(R.id.edit_button)
        deleteButton=view.findViewById(R.id.delete_button)
        logoutButton=view.findViewById(R.id.logout_button)
        database=FirebaseDatabase.getInstance().reference
        auth=FirebaseAuth.getInstance()
        currentUser=auth.currentUser!!
        database.child("users").child(currentUser.uid).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               val userProfile=snapshot.getValue(UserProfile::class.java)
                if (userProfile !=null){
                    businessTextView.text=userProfile.businessName
                    descriptionTextView.text=userProfile.description
                    phoneNumberTextView.text=userProfile.phoneNumber
                    if (userProfile.imageUri.isNotEmpty()){
                        Picasso.get().load(userProfile.imageUri).into(imageView)

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
               Toast.makeText(context,"Failed to read profile data: ${error.message}",
               Toast.LENGTH_SHORT).show()
            }
        })
        editButton.setOnClickListener {
            val fragment=DesignerProfileFragment()
            val transaction=parentFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_view_tag,fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        deleteButton.setOnClickListener {
            currentUser.delete().addOnCompleteListener { task->
                if (task.isSuccessful){
                    database.child("users").child(currentUser.uid).removeValue()
                    auth.signOut()
                    val intent = Intent(activity,DesignerloginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }else{
                    Toast.makeText(context,"Failed to delete user account:${task.exception?.message}",
                    Toast.LENGTH_SHORT).show()
                }
            }
        }
        logoutButton.setOnClickListener {
            auth.signOut()
            val intent=Intent(activity,DesignerloginActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        return view
    }

    companion object {
        private const val TAG="ViewFragment"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}