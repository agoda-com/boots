package com.agoda.boots.sample

import com.agoda.kakao.KView
import com.agoda.kakao.Screen

class MainScreen : Screen<MainScreen>() {

    val progressBar = KView { withId(R.id.progressBar) }
    val textView = KView { withId(R.id.textView) }

}
