package com.example.littlelemon

interface Destinations {
    val route: String
}

object HomeDestination : Destinations {
    override val route = "Home"
}

object ProfileDestination : Destinations {
    override val route = "Profile"
}

object OnboardingDestination : Destinations {
    override val route = "Onboarding"
}
