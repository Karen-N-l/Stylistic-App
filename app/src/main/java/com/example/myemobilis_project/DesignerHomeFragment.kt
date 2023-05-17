package com.example.myemobilis_project

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myemobilis_project.databinding.FragmentDesignerHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.google.firebase.auth.FirebaseUser


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DesignerHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DesignerHomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:ProfileAdapter
    private lateinit var database:DatabaseReference
    private lateinit var profiles:MutableList<UserProfile>








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
        val view = inflater.inflate(R.layout.fragment_designer_home,container,false)
        recyclerView=view.findViewById(R.id.profilesRecyclerView)
        recyclerView.layoutManager=LinearLayoutManager(activity)
        profiles= mutableListOf()
        adapter=ProfileAdapter(profiles)
        recyclerView.adapter=adapter
        database=FirebaseDatabase.getInstance().reference
        database.child("users").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val newProfiles= mutableListOf<UserProfile>()
                for (userSnapshot in snapshot.children){
                    val userProfile=userSnapshot.getValue(UserProfile::class.java)
                    if (userProfile !=null){
                        newProfiles.add(userProfile)
                    }
                }
                profiles.clear()
                profiles.addAll(newProfiles)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Failed to read profile data:${error.message}",Toast.LENGTH_SHORT).show()

            }
        })


        return view


    }
    private inner class ProfileAdapter(private val profiles:List<UserProfile>):
    RecyclerView.Adapter<ProfileViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
            val view= layoutInflater.inflate(R.layout.item_profile,parent,false)
            return ProfileViewHolder(view)
        }

        override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
            val userProfile=profiles[position]
            holder.bind(userProfile)
        }

        override fun getItemCount(): Int {
            return profiles.size
        }
    }
    private inner class ProfileViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        private val imageView:ImageView=itemView.findViewById(R.id.profile_limage)
        private val businessTextView:TextView=itemView.findViewById(R.id.business_lname)
        private val descriptionTextView:TextView=itemView.findViewById(R.id.description_lname)
        private val phoneNumberTextView:TextView=itemView.findViewById(R.id.phone_lnumber)
        fun bind(userProfile: UserProfile){
            businessTextView.text=userProfile.businessName
            descriptionTextView.text=userProfile.description
            phoneNumberTextView.text=userProfile.phoneNumber
            if (userProfile.imageUri.isNotEmpty()){
                Picasso.get().load(userProfile.imageUri).into(imageView)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DesignerHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DesignerHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}