package com.android.structure.helperclasses.kotlinhelper

import android.view.View

/**
 * KOTLIN Scripts class, use this class to write KOTLIN scripts only to perform data or UI operation.
 * Make sure to not override others work without their permission.
 * You may add your new method
 */


/*
 *  KOTLIN CONFIGURATION


// BUILD GRADLE

     ext {
            // shared build properties
            kotlin_version      = '1.2.10'
            buildToolsVersion   = '27.0.2'
        }

        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlin_version}"



// APP GRADLE

    apply plugin: 'kotlin-android'

    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlin_version}"
    implementation "org.jetbrains.kotlin:kotlin-reflect:${kotlin_version}"

 */



/**
 *  ONLY Project related specific scripts
 */


object KotlinScriptsForProject {

//    fun sortDeptArray(arrDept: List<DEPT>): List<DEPT> {
//        return arrDept.sortedWith(compareBy { it.descr })
//    }
}


/**
 *  Generic Android data related scripts
 */


object KotlinDataScripts {

    fun sortStringArrAscending(array: List<String>): List<String> {
        return array.sortedWith(compareBy { it })
    }


    fun sortStringArrDescending(array: List<String>): List<String> {
        return array.sortedWith(compareBy { it }).reversed()
    }

}


/**
 *  Generic Android UI related scripts
 */


object KotlinUIScripts {

    /**
     * Force a measure and re-layout of children at this instant.
     * This is useful when "requestLayout" is too late and causes visual "jumps".
     *
     * Note: If a view's width/height are 0, then you probably need to re-layout its *parent*.
     * @param view
     */

    fun layoutNow(view: View) {
        view.measure(
                View.MeasureSpec.makeMeasureSpec(view.measuredWidth, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(view.measuredHeight, View.MeasureSpec.EXACTLY))
        view.layout(view.left,
                view.top,
                view.right,
                view.bottom)
    }

}




