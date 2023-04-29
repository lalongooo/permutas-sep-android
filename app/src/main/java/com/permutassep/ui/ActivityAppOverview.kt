package com.permutassep.ui

import android.animation.ObjectAnimator
import android.animation.StateListAnimator
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.lalongooo.permutassep.R
import com.permutassep.presentation.utils.Utils

class ActivityAppOverview : BaseActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var topImage1: ImageView
    private lateinit var topImage2: ImageView
    private lateinit var messages: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ca_activity_app_overview)
        viewPager = findViewById<View>(R.id.intro_view_pager) as ViewPager
        val startMessagingButton = findViewById<View>(R.id.start_messaging_button) as TextView
        startMessagingButton.text = getString(R.string.app_overview_start_posting_button)

        val animator = StateListAnimator()
        animator.addState(
            intArrayOf(
                android.R.attr.state_pressed,
            ),
            ObjectAnimator.ofFloat(
                startMessagingButton,
                "translationZ",
                Utils.dp(2f, this).toFloat(),
                Utils.dp(4f, this).toFloat(),
            ).setDuration(200),
        )
        animator.addState(
            intArrayOf(),
            ObjectAnimator.ofFloat(
                startMessagingButton,
                "translationZ",
                Utils.dp(4f, this).toFloat(),
                Utils.dp(2f, this).toFloat(),
            ).setDuration(200),
        )
        startMessagingButton.stateListAnimator = animator

        messages = intArrayOf(
            R.string.app_overview_1st_item_text,
            R.string.app_overview_2nd_item_text,
            R.string.app_overview_3rd_item_text,
            R.string.app_overview_4th_item_text,
        )
        topImage1 = findViewById(R.id.icon_image1)
        topImage2 = findViewById(R.id.icon_image2)
        topImage2.visibility = View.GONE
        with(viewPager) {
            adapter = IntroAdapter()
            pageMargin = 0
            offscreenPageLimit = 1
        }
        startMessagingButton.setOnClickListener {
            startActivity(Intent(this@ActivityAppOverview, ActivityMain::class.java))
            finish()
        }
    }

    private inner class IntroAdapter : PagerAdapter() {
        override fun getCount(): Int = 4

        override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = View.inflate(container.context, R.layout.activity_app_overview_vp, null)
            val messageTextView = view.findViewById<View>(R.id.message_text) as TextView
            container.addView(view, 0)
            messageTextView.text =
                Utils.replaceTags(getString(messages[position]), applicationContext)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}
