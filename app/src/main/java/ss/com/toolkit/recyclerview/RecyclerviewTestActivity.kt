package ss.com.toolkit.recyclerview
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ss.com.toolkit.R

class RecyclerviewTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview_test)

        var tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        tabLayout.addTab(tabLayout.newTab().setText("1111"))
        tabLayout.addTab(tabLayout.newTab().setText("2222"))
//        tabLayout.addTab(tabLayout.newTab().setText("3333"))

        var viewPager = findViewById<ViewPager>(R.id.viewPager)

        viewPager.adapter = PageAdapter(supportFragmentManager, tabLayout.tabCount)
        viewPager.addOnPageChangeListener(object : TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab) {
                viewPager.currentItem = p0.position
            }
        })
    }
}

class PageAdapter(fm: FragmentManager, val num: Int) : FragmentPagerAdapter(fm) {

    override fun getItem(p0: Int): Fragment {
        return createFragment(p0)
    }

    override fun getCount(): Int {
        return num
    }

    private fun createFragment(pos: Int): Fragment {
        return TestFragment.newInstance(pos)
    }

}

class TestFragment() : Fragment() {

    companion object {
        fun newInstance(pos: Int): TestFragment {
            val fg = TestFragment()
            val bundle = Bundle()
            bundle.putInt("pos", pos)
            fg.arguments = bundle
            return fg
        }

        var recycledViewPool = MyRecycledViewPool()
    }

    var fragmentPosition: Int = -1
    lateinit var recyclerView: RecyclerView
    var mDatas = arrayListOf<String>("1", "2", "3", "4", "5", "11", "12", "13", "14", "15", "1", "2", "3", "4", "5", "11", "12", "13", "14", "15")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recyclerview_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fragmentPosition = arguments?.get("pos") as Int
        recyclerView = view.findViewById(R.id.recycleView);
        var linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.recycleChildrenOnDetach = true
        recyclerView.layoutManager = linearLayoutManager
        recycledViewPool.setMaxRecycledViews(0, 2)
        recyclerView.setItemViewCacheSize(5)
        recyclerView.setRecycledViewPool(recycledViewPool)
        var adapter = TestAdapter(mDatas, fragmentPosition)
        recyclerView.adapter = adapter

    }

    class TestAdapter(val datas: ArrayList<String>, val fragmentPosition: Int) : RecyclerView.Adapter<MyViewHolder>() {
        companion object {
            const val TAG = "RecyclerviewTestActivity"
        }
        override fun getItemCount(): Int {
            return datas.size
        }

        override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
            Log.e(TAG, "${fragmentPosition}==onBindViewHolder: position=$position, pool.size()=${TestFragment.recycledViewPool.getRecycledViewCount(0)}")
            viewHolder.tv.text = datas.get(position)
        }

        override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyViewHolder {
            Log.e(TAG, "${fragmentPosition}==onCreateViewHolder: position=$position, pool.size()=${TestFragment.recycledViewPool.getRecycledViewCount(0)}")

            var holder = MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_recyclerview_test_item, parent, false))
            return holder
        }

    }

}

class MyRecycledViewPool : RecyclerView.RecycledViewPool() {

    var num = 0
    override fun getRecycledView(viewType: Int): RecyclerView.ViewHolder? {
        return super.getRecycledView(viewType)
    }

    override fun putRecycledView(scrap: RecyclerView.ViewHolder?) {
        super.putRecycledView(scrap)
    }
}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var tv: TextView = view.findViewById(R.id.textView)

}