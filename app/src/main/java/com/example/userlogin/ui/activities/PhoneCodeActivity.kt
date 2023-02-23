package com.example.userlogin.ui.activities/*
package com.example.userlogin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userlogin.R
import com.example.userlogin.adapter.MyRecyclerViewAdapter
import com.example.userlogin.model.PhoneCode
import kotlinx.android.synthetic.main.activity_phone_code.*

class PhoneCodeActivity : AppCompatActivity() {
    var list: ArrayList<PhoneCode> = arrayListOf()
    private lateinit var mAdapter:MyRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_code)

        val phoneCode1 = PhoneCode("Vietnam", "+84")
        val phoneCode2 = PhoneCode("Vietnam1", "+85")
        val phoneCode3 = PhoneCode("America", "+86")
        val phoneCode4 = PhoneCode("France", "+87")
        val phoneCode5 = PhoneCode("Japan", "+88")

        list.add(phoneCode1)
        list.add(phoneCode2)
        list.add(phoneCode3)
        list.add(phoneCode4)
        list.add(phoneCode5)






        mAdapter = MyRecyclerViewAdapter(list)
        rcv_phone_code.layoutManager = LinearLayoutManager(this)
        rcv_phone_code.adapter = mAdapter

        mAdapter.setOnClickListener(object : MyRecyclerViewAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                setResult(0, intent.putExtra("Phone Code", list[position].code))
                finish()
            }
        })

    }

    */
/*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val menuItem = menu!!.findItem(R.id.action_search)
        val searchView:SearchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mAdapter.getFilter()!!.filter(newText)
                return false
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.action_search) return true
        return super.onOptionsItemSelected(item)
    }*//*


    override fun onResume() {
        sv_phone_code.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                mAdapter.getFilter()!!.filter(newText)
                return false
            }

        })
        super.onResume()
    }
}*/
