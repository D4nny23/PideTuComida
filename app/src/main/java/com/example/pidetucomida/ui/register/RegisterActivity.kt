package com.example.pidetucomida.ui.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.pidetucomida.R
import com.example.pidetucomida.databinding.ActivityRegisterBinding
import com.example.pidetucomida.model.client.ClientDto
import com.example.pidetucomida.ui.login.MainActivity
import com.example.pidetucomida.utils.UtilsChyper
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterActivityViewModel
    var email=false; var number=false; var pass=false; var name=false; var lastName=false; var address=false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel= ViewModelProvider(this)[RegisterActivityViewModel::class.java]
        setupListener()
        setupObservables()
        setupToolBar()
    }

    private fun setupToolBar() {
        binding.toolBar.tvTitle.visibility = View.VISIBLE
        binding.toolBar.tvTitle.text = getString(R.string.label_register)
        binding.toolBar.tvTitle.setTextAppearance(this, R.style.TitleStyle)

        binding.toolBar.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupListener() {
        binding.mbRegister.setOnClickListener {
            val client = ClientDto(null, binding.etEmail.text.toString(), UtilsChyper().hashPassword(binding.etPassword.text.toString()),binding.etName.text.toString()
                ,binding.etLastName.text.toString(), binding.etAdress.text.toString(),binding.etNumberPhone.text.toString())
            viewModelsFunctions()
            if(email && pass && name && lastName && address && number){
                viewModel.addClient(client, this)
                startActivity(Intent(this, MainActivity::class.java))
            }

        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupObservables(){
        val background = ContextCompat.getColor(this@RegisterActivity, R.color.dark_red)
        viewModel.isValidEmail.observe(this){ isValidEmail->
            when(isValidEmail){
                R.string.email_already_exist -> setError(binding.tiEmail, this, getString(R.string.email_already_exist), background, binding.etEmail)
                R.string.email_invalid -> setError(binding.tiEmail, this, getString(R.string.email_invalid), background, binding.etEmail)
                R.string.obligatory_field -> setError(binding.tiEmail, this, getString(R.string.obligatory_field), background, binding.etEmail)
                R.string.email_ok -> {
                    removeError(binding.tiEmail,this, binding.etEmail)
                    email=true
                }
            }
        }

        viewModel.isValidPhone.observe(this){ isValidPhone->
            when(isValidPhone){
                R.string.phone_already_exist -> setError(binding.tiNumberPhone, this, getString(R.string.phone_already_exist), background, binding.etNumberPhone)
                R.string.invalid_phone -> setError(binding.tiNumberPhone, this, getString(R.string.invalid_phone), background, binding.etNumberPhone)
                R.string.obligatory_field -> setError(binding.tiNumberPhone, this, getString(R.string.obligatory_field), background, binding.etNumberPhone)
                R.string.phone_ok -> {
                    removeError(binding.tiNumberPhone,this, binding.etNumberPhone)
                    number=true
                }
            }
        }

        viewModel.isValidPass.observe(this){ isValidPass->
            when(isValidPass){
                R.string.containts_numbers_caps_specialcharacters -> setError(binding.tiPass, this, getString(R.string.containts_numbers_caps_specialcharacters), background, binding.etPassword)
                R.string.obligatory_field -> setError(binding.tiPass, this, getString(R.string.obligatory_field), background, binding.etPassword)
                R.string.pass_ok -> {
                    removeError(binding.tiPass,this, binding.etPassword)
                }
            }
        }
        viewModel.isValidRepPass.observe(this){ isValidRepPass->
            when(isValidRepPass){
                R.string.obligatory_field -> setError(binding.tiRepeatPass, this, getString(R.string.obligatory_field), background, binding.etRepeatPass)
                R.string.pass_ok -> {
                    if (binding.etPassword.text.toString()==binding.etRepeatPass.text.toString()){
                        removeError(binding.tiRepeatPass, this, binding.etRepeatPass)
                        pass=true
                    }else{
                        setError(binding.tiRepeatPass, this, getString(R.string.same_password), background, binding.etRepeatPass)
                    }
                }
            }
        }
        viewModel.isValidName.observe(this){isValidName ->
            when(isValidName){
                R.string.obligatory_field -> setError(binding.tiName, this, getString(R.string.obligatory_field), background, binding.etName)
                R.string.name_no_contains_special_characters -> setError(binding.tiName, this, getString(R.string.name_no_contains_special_characters), background, binding.etName)
                R.string.name_ok ->{
                    removeError(binding.tiName,this, binding.etName)
                    name=true
                }
            }
        }

        viewModel.isValidLastName.observe(this){isValidLastName->
            when(isValidLastName){
                R.string.obligatory_field -> setError(binding.tiLastName, this, getString(R.string.obligatory_field), background, binding.etLastName)
                R.string.lastname_no_contains_special_characters -> setError(binding.tiLastName, this, getString(R.string.lastname_no_contains_special_characters), background, binding.etLastName)
                R.string.lastname_ok -> {
                    removeError(binding.tiLastName, this, binding.etLastName)
                    lastName=true
                }
            }

        }

        viewModel.isValidAddress.observe(this){isValidAddress ->
            when(isValidAddress){
                R.string.obligatory_field -> setError(binding.tiAdress, this, getString(R.string.obligatory_field), background,binding.etAdress)
                R.string.address_no_contains_special_characters -> setError(binding.tiAdress, this, getString(R.string.address_no_contains_special_characters), background,binding.etAdress)
                R.string.address_ok ->{
                    removeError(binding.tiAdress, this, binding.etAdress)
                    address=true
                }
            }

        }

        viewModel.success.observe(this){
            if (it){
                Toast.makeText(this, "Cliente añadido exitosamente", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Hubo un error al añadir el cliente", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.setError.observe(this){
            when(it){
                R.string.time_out_exception ->{
                    binding.tvNoConnection.visibility = View.VISIBLE
                    binding.tvNoConnection.text = getString(it)
                    binding.ivNoConnection.visibility = View.VISIBLE

                    binding.tiEmail.visibility=View.GONE
                    binding.tiPass.visibility=View.GONE
                    binding.tiAdress.visibility=View.GONE
                    binding.mbRegister.visibility=View.GONE
                    binding.tiLastName.visibility=View.GONE
                    binding.tiNumberPhone.visibility=View.GONE
                    binding.tiName.visibility=View.GONE
                    binding.tiRepeatPass.visibility=View.GONE
                }
                R.string.connect_exception ->{
                    binding.tvNoConnection.visibility = View.VISIBLE
                    binding.tvNoConnection.text = getString(it)
                    binding.ivNoConnection.visibility = View.VISIBLE
                    binding.ivNoConnection.setImageResource(R.drawable.ic_no_connection)

                    binding.tiEmail.visibility=View.GONE
                    binding.tiPass.visibility=View.GONE
                    binding.tiAdress.visibility=View.GONE
                    binding.mbRegister.visibility=View.GONE
                    binding.tiLastName.visibility=View.GONE
                    binding.tiNumberPhone.visibility=View.GONE
                    binding.tiName.visibility=View.GONE
                    binding.tiRepeatPass.visibility=View.GONE
                }
                else ->{
                    binding.ivNoConnection.visibility = View.GONE
                    binding.tvNoConnection.visibility = View.GONE
                }
            }
        }
    }

    private fun viewModelsFunctions(){
        viewModel.setupEmail(binding.etEmail.text.toString())
        viewModel.setupNumberPhone(binding.etNumberPhone.text.toString())
        viewModel.setupPassword(binding.etPassword.text.toString())
        viewModel.setupRepeatPassword(binding.etRepeatPass.text.toString())
        viewModel.setupName(binding.etName.text.toString())
        viewModel.setupLastName(binding.etLastName.text.toString())
        viewModel.setupAdress(binding.etAdress.text.toString())
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("UseCompatTextViewDrawableApis")
    private fun setError(ti: TextInputLayout, context: Context, message: String, background: Int, et: TextInputEditText){
        ti.error = message
        ti.setErrorIconDrawable(com.google.android.material.R.drawable.mtrl_ic_error)
        ti.background =
            ContextCompat.getDrawable(context, R.drawable.errorborder)
        et.compoundDrawableTintList = ColorStateList.valueOf(background)
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun removeError(ti: TextInputLayout, context: Context, et: TextInputEditText){
        ti.isErrorEnabled=false
        ti.errorIconDrawable=null
        ti.background =
            ContextCompat.getDrawable(context, R.drawable.border)
        et.compoundDrawableTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green))
    }

}