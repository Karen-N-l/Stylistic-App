package com.example.myemobilis_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClientSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientSearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private lateinit var database: DatabaseReference

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
        val view =  inflater.inflate(R.layout.fragment_client_search, container, false)
        searchView=view.findViewById(R.id.search_viewc)
        recyclerView=view.findViewById(R.id.search_resultsc)
        database= FirebaseDatabase.getInstance().reference.child("users")
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager= LinearLayoutManager(context)
        val options = FirebaseRecyclerOptions.Builder<UserProfile>()
            .setQuery(database,UserProfile::class.java)
            .build()
        adapter = MyAdapter(options)
        recyclerView.adapter=adapter
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query !=null){
                    searchFirebase(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })


        return view
    }
    private fun searchFirebase(query:String){
        val firebaseSearchQuery=database.orderByChild("businessName").startAt(query).endAt("$query\uf8ff")
        val options=FirebaseRecyclerOptions.Builder<UserProfile>()
            .setQuery(firebaseSearchQuery,UserProfile::class.java)
            .build()
        adapter=MyAdapter(options)
        adapter.startListening()
        recyclerView.adapter=adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
    inner class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bind(user:UserProfile){
            itemView.findViewById<TextView>(R.id.item_dname).text=user.businessName
            itemView.findViewById<TextView>(R.id.item_ddescription).text=user.description
        }
    }
    inner class MyAdapter(options: FirebaseRecyclerOptions<UserProfile>):
        FirebaseRecyclerAdapter<UserProfile, MyViewHolder>(options){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view=LayoutInflater.from(parent.context).inflate(R.layout.serach_ditem,parent,false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: UserProfile) {
            holder.bind(model)
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClientSearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClientSearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}