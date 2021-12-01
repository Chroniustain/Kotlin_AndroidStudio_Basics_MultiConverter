package com.example.multiconverter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.multiconverter.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.convertButton.setOnClickListener{convert()}

        binding.unitToConvertEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }

    }

    private fun convert(){

        val stringInTextField = binding.unitToConvertEditText.text.toString()
        val unitToConvert = stringInTextField.toDoubleOrNull()
        if (unitToConvert == null || unitToConvert == 0.0) {
            binding.conversionResult.text = "0.0"
            return
        }

        val multiplier = when (binding.conversionOptions.checkedRadioButtonId) {
            R.id.option_pound_kilogram -> 0.453592
            R.id.option_foot_meter -> 0.3048
            R.id.option_gallon_liter -> 3785.41
            else -> 28.3495
        }

        var convertedUnit: Double = unitToConvert * multiplier

        conversionResult(convertedUnit)
    }

    private fun conversionResult(convertedUnit: Double){
        val formatedResult = NumberFormat.getNumberInstance()
        binding.conversionResult.setText(formatedResult.format(convertedUnit))
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}