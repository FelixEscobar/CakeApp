package com.piedrasyartesanias.cakeapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.piedrasyartesanias.cakeapp.databinding.FragmentLoginBinding
import com.piedrasyartesanias.cakeapp.models.LoginModel
import com.piedrasyartesanias.cakeapp.utils.MySharedPreferences
import com.piedrasyartesanias.cakeapp.utils.TOKEN
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var myPreferences: MySharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun validateEmptyFields() {

        val signUpTextWatcher = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val emailInput = binding.editTextEmail.text.toString().trim()
                val passwordInput = binding.editTextPassword.text.toString().trim()

                binding.button.isEnabled = (!emailInput.isEmpty() && !passwordInput.isEmpty())
            }

        }

        binding.editTextEmail.addTextChangedListener(signUpTextWatcher)
        binding.editTextPassword.addTextChangedListener(signUpTextWatcher)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateEmptyFields()
        myPreferences = MySharedPreferences(this.context!!)

        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val responseLogin = withContext(Dispatchers.Default){
                        val cakeRepository = CakeRepository()
                        cakeRepository.login(LoginModel(binding.editTextEmail.text.toString(),binding.editTextPassword.text.toString()))
                    }

                    myPreferences.addString(TOKEN,responseLogin.token)
                    startActivity(Intent(this@LoginFragment.context, ShowProductsActivity::class.java))
                }catch (e: Exception){
                    Toast.makeText(this@LoginFragment.context, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}