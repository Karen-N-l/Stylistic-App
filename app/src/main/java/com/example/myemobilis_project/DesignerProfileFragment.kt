package com.example.myemobilis_project

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.provider.MediaStore
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myemobilis_project.databinding.FragmentDesignerProfileBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.UUID


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DesignerProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DesignerProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var imageView: ImageView
    private lateinit var changeImageButton: Button
    private lateinit var businessEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var currentUser:FirebaseUser









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
        val view=inflater.inflate(R.layout.fragment_designer_profile,container,false)
        imageView=view.findViewById(R.id.profileImageView)
        changeImageButton=view.findViewById(R.id.changeImageButton)
        businessEditText=view.findViewById(R.id.businessNameEditText)
        descriptionEditText=view.findViewById(R.id.descriptionEditText)
        phoneNumberEditText=view.findViewById(R.id.phoneNumberEditText)
        saveButton=view.findViewById(R.id.saveButton)
        database=FirebaseDatabase.getInstance().reference
        auth= FirebaseAuth.getInstance()
        storage=FirebaseStorage.getInstance()
        currentUser=auth.currentUser!!
        database.child("users").child(currentUser.uid).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
               val userProfile=snapshot.getValue(UserProfile::class.java)
                if (userProfile != null) {
                   businessEditText.setText(userProfile.businessName)
                    descriptionEditText.setText(userProfile.description)
                    phoneNumberEditText.setText(userProfile.phoneNumber)
                    if (userProfile.imageUri.isNotEmpty()){
                        Picasso.get().load(userProfile.imageUri).into(imageView)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Failed to retrieve profile data",
                Toast.LENGTH_SHORT).show()
                Log.e(TAG,"Failed to retrieve profile data",error.toException())
            }
        })
        changeImageButton.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
        }
        saveButton.setOnClickListener {
            val businessName=businessEditText.text.toString()
            val description=descriptionEditText.text.toString()
            val phoneNumber=phoneNumberEditText.text.toString()
            val userProfile=UserProfile(businessName,description,phoneNumber)
            database.child("users").child(currentUser.uid).setValue(userProfile).addOnCompleteListener { task->
                if (task.isSuccessful){
                    Toast.makeText(context,"Profile saved ",Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(context,"Failed to save profile",Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
       if (requestCode== REQUEST_IMAGE_PICK && resultCode==Activity.RESULT_OK){
           val imageUri=data?.data
           if (imageUri !=null){
               val imageRef=storage.reference.child("users/${currentUser.uid}/image.jpg")
               imageRef.putFile(imageUri).addOnCompleteListener { task->
                   if (task.isSuccessful){
                       imageRef.downloadUrl.addOnCompleteListener { downloadTask->
                           if (downloadTask.isSuccessful){
                               val userProfileUpdates=HashMap<String,Any>()
                               userProfileUpdates["imageUri"]=downloadTask.result.toString()
                               database.child("users").child(currentUser.uid).updateChildren(userProfileUpdates)

                           }else{
                               Toast.makeText(context,"Failed to upload image:${downloadTask.exception?.message}",
                               Toast.LENGTH_SHORT).show()
                           }
                       }
                   }else{
                       Toast.makeText(context,"Failed to upload image:${task.exception?.message}",
                       Toast.LENGTH_SHORT).show()
                   }
               }
           }
       }
    }


    companion object {
        private const val TAG="DesignerProfileFragment"
        private const val REQUEST_IMAGE_PICK=1




        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DesignerProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
