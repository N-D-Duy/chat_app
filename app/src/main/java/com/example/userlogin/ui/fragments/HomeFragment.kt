package com.example.userlogin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userlogin.adapter.MyRecyclerViewAdapter
import com.example.userlogin.adapter.ProgressDialogFragment
import com.example.userlogin.adapter.UserProfileViewModel
import com.example.userlogin.databinding.FragmentHomeBinding
import com.example.userlogin.model.User


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        Log.e("list state", "empty")
        val mProcessDialog = ProgressDialogFragment.newInstance()
        mProcessDialog.show(childFragmentManager, "load data")

        val homeViewModel = ViewModelProvider(this)[UserProfileViewModel::class.java]
        homeViewModel.users.observe(viewLifecycleOwner) {
            if(it.isNotEmpty()){
                Log.e("user list", "is not empty")
                updateUI(it, binding)
            }else{
                Log.e("user list", "is empty")
            }
            mProcessDialog.dismiss()
        }

        return binding.root
    }

    private fun updateUI(list:ArrayList<User>, binding:FragmentHomeBinding){
        binding.rcvHome.layoutManager = LinearLayoutManager(requireContext())
        val mAdapter = MyRecyclerViewAdapter(list, requireContext())
        binding.rcvHome.adapter = mAdapter
        mAdapter.setOnClickListener(object : MyRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

            }
        })
    }
}