package com.example.bluebanktest.ui.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**

 *
 * How to use:
 * - Call it inside your fragment
 * private val binding: FragmentSampleBinding by viewBinding()
 * and access it normally like you use local variable.
 *
 */
inline fun <reified T : ViewBinding> Fragment.viewBinding(isForceRTL:Boolean=true) =
    FragmentViewBindingDelegate(T::class.java, this,isForceRTL)

class FragmentViewBindingDelegate<T : ViewBinding>(
    bindingClass: Class<T>,
    private val fragment: Fragment,
    private val isForceRTL: Boolean
) : ReadOnlyProperty<Fragment, T> {

    /**
     * initiate variable for binding view
     */
    private var binding: T? = null

    /**
     * get the bind method from View class
     */
    private val bindMethod = bindingClass.getMethod("bind", View::class.java)

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        binding?.let { return it }

        /**
         * Adding observer to the fragment lifecycle
         */
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            /**
                             * Clear the binding when Fragment lifecycle called the onDestroy
                             */
                            binding = null
                        }
                    })
                }
            }
        })


        /**
         * Checking the fragment lifecycle
         */
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            error("Cannot access view bindings. View lifecycle is ${lifecycle.currentState}!")
        }


        /**
         * Bind layout
         */
        val invoke = bindMethod.invoke(null, thisRef.requireView()) as T

        return invoke.also {
            this.binding = it
            if(isForceRTL){
                binding!!.root.layoutDirection = View.LAYOUT_DIRECTION_RTL
            }
        }
    }
}