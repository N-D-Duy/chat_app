package com.example.userlogin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userlogin.adapter.MyRecyclerViewAdapter
import com.example.userlogin.adapter.ProgressDialogFragment
import com.example.userlogin.adapter.UserProfileViewModel
import com.example.userlogin.databinding.FragmentHomeBinding
import com.example.userlogin.model.User
import com.example.userlogin.model.UserProfile
import com.example.userlogin.ui.activities.ChatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mProcessDialog = ProgressDialogFragment.newInstance()
    private lateinit var adapterClick:MyRecyclerViewAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        mProcessDialog.show(childFragmentManager, "load data")
        val mainViewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]
        mainViewModel.users.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                Log.e("get view model from", "fragment home")
                updateUI(it, binding)
            }
        }


        return binding.root
    }

    private fun goToChatScreen(user: UserProfile) {
        val intent = Intent(requireContext(), ChatActivity::class.java)
        intent.putExtra("user-to-chat", user)
        startActivity(intent)
    }


    private fun updateUI(list:ArrayList<UserProfile>?, binding:FragmentHomeBinding){
        binding.rcvHome.layoutManager = LinearLayoutManager(requireContext())
        if(list!=null){
            val mAdapter = MyRecyclerViewAdapter(list, requireContext())
            adapterClick = mAdapter
            binding.rcvHome.adapter = mAdapter
            mAdapter.setOnClickListener(object : MyRecyclerViewAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {

                }
            })
        }
        mProcessDialog.dismiss()
        adapterClick.setOnClickListener(object: MyRecyclerViewAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                goToChatScreen(list!![position])
            }

        })
    }
}