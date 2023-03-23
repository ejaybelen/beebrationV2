package com.example.beebration.energyharvester

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.beebration.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EnergyDataActivity : AppCompatActivity() {

    private val dummyEnergyHarvester = DummyEnergyHarvester()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_energy_data)

        // Start generating energy data
        dummyEnergyHarvester.startGeneratingData()

        // Set up your RecyclerView here if needed

        // Fetch energy data from Firebase
        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        val database = FirebaseDatabase.getInstance()
        val energyDataRef = database.getReference("energy_data")

        energyDataRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val energyDataList = mutableListOf<EnergyData>()

                for (data in dataSnapshot.children) {
                    val energyData = data.getValue(EnergyData::class.java)
                    energyData?.let { energyDataList.add(it) }
                }

                // Call a function to update the UI with the fetched data
                displayEnergyData(energyDataList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w("Error", "Failed to read energy data.", databaseError.toException())
            }
        })
    }

    private fun displayEnergyData(energyDataList: List<EnergyData>) {
        // Update the UI with the energyDataList, e.g., using a RecyclerView adapter
    }
}