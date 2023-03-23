package com.example.beebration.energyharvester

import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class DummyEnergyHarvester {

    private val database = FirebaseDatabase.getInstance()
    private val energyDataRef = database.getReference("energy_data")

    fun startGeneratingData() {
        GlobalScope.launch {
            while (true) {
                val energyData = EnergyData(
                    timestamp = System.currentTimeMillis(),
                    energyValue = Random.nextDouble(0.0, 100.0)
                )

                energyDataRef.push().setValue(energyData)

                delay(5000) // Change the delay as needed
            }
        }
    }
}