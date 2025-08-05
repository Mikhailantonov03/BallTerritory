    package com.hfad.navigation.bottomNav

    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.border
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.draw.shadow
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.graphics.ColorFilter
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavController
    import androidx.navigation.compose.currentBackStackEntryAsState
    import com.hfad.navigation.R
    import com.hfad.navigation.extensions.navigateToRoute
    import com.hfad.navigation.routes.CoachesRoute
    import com.hfad.navigation.routes.HomeRoute
    import com.hfad.navigation.routes.ProfileRoute
    import com.hfad.navigation.routes.RentRoute
    import com.hfad.navigation.routes.ScheduleRoute

    data class BottomNavTab(
        val label: String,
        val iconRes: Int,
        val route: String,
        val activeColor: Color = Color(0xFFFF9800), // Жёлтый по умолчанию
        val inactiveColor: Color = Color(0xFF777777)
    )

    @Composable
    fun BottomNavBar(navController: NavController) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val items = listOf(
            BottomNavTab("Главная", R.drawable.icon_tms, HomeRoute::class.qualifiedName!!, activeColor = Color(0xFFFF4F4F)),
            BottomNavTab("Расписание", R.drawable.icon_calendar, ScheduleRoute::class.qualifiedName!!),
            BottomNavTab("Тренеры", R.drawable.icon_team, CoachesRoute::class.qualifiedName!!),
            BottomNavTab("Аренда", R.drawable.icon_parquet, RentRoute::class.qualifiedName!!),
            BottomNavTab("Профиль", R.drawable.icon_kids, ProfileRoute::class.qualifiedName!!)
        )

        if (currentRoute !in items.map { it.route }) return

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(32.dp))
                .clip(RoundedCornerShape(32.dp))
                .background(Color(0xFF272933))
                .border(
                    width = 1.dp,
                    color = Color(0xFF3A3B40),
                    shape = RoundedCornerShape(32.dp)
                )
                .height(80.dp) // увеличили
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val selected = currentRoute == item.route
                val tint = if (selected) item.activeColor else item.inactiveColor

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            if (!selected) {
                                navController.navigateToRoute(item.route, popUpToStart = false)
                            }
                        }
                ) {
                    Image(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.label,
                        modifier = Modifier.size(32.dp), // увеличили
                        colorFilter = ColorFilter.tint(tint)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.label,
                        fontSize = 13.sp, // увеличили
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
        }